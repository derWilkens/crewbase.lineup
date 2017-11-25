package eu.crewbase.lineup.web.department;

import eu.crewbase.lineup.entity.coredata.Department;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.security.entity.EntityOp;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class DepartmentBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link Department} records
     * to be displayed in {@link DepartmentBrowse#departmentsTable} on the left
     */
    @Inject
    private CollectionDatasource<Department, UUID> departmentsDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link DepartmentBrowse#departmentsDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link DepartmentBrowse#init(Map)} method
     */
    @Inject
    private Datasource<Department> departmentDs;

    /**
     * The {@link Table} instance, containing a list of {@link Department} records,
     * loaded via {@link DepartmentBrowse#departmentsDs}
     */
    @Inject
    private Table<Department> departmentsTable;

    /**
     * The {@link BoxLayout} instance that contains components on the left side
     * of {@link SplitPanel}
     */
    @Inject
    private BoxLayout lookupBox;

    /**
    * The {@link BoxLayout} instance that contains components on the right side
    * of {@link SplitPanel}
    */
    @Inject
    private BoxLayout editBox;

    /**
     * The {@link BoxLayout} instance that contains buttons to invoke Save or Cancel actions in edit mode
     */
    @Inject
    private BoxLayout actionsPane;

    /**
     * The {@link FieldGroup} instance that is linked to {@link DepartmentBrowse#departmentDs}
     * and shows fields of the selected {@link Department} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link DepartmentBrowse#departmentsTable}
     */
    @Named("departmentsTable.remove")
    private RemoveAction departmentsTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link Department} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link departmentsDs}
         * The listener reloads the selected record with the specified view and sets it to {@link departmentDs}
         */
        departmentsDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                Department reloadedItem = dataSupplier.reload(e.getDs().getItem(), departmentDs.getView());
                departmentDs.setItem(reloadedItem);
            }
        });

        /*
         * Adding {@link CreateAction} to {@link departmentsTable}
         * The listener removes selection in {@link departmentsTable}, sets a newly created item to {@link departmentDs}
         * and enables controls for record editing
         */
        departmentsTable.addAction(new CreateAction(departmentsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                departmentsTable.setSelected(Collections.emptyList());
                departmentDs.setItem((Department) newItem);
                refreshOptionsForLookupFields();
                enableEditControls(true);
            }
        });

        /*
         * Adding {@link EditAction} to {@link departmentsTable}
         * The listener enables controls for record editing
         */
        departmentsTable.addAction(new EditAction(departmentsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (departmentsTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }

            @Override
            public void refreshState() {
                if (target != null) {
                    CollectionDatasource ds = target.getDatasource();
                    if (ds != null && !captionInitialized) {
                        setCaption(messages.getMainMessage("actions.Edit"));
                    }
                }
                super.refreshState();
            }

            @Override
            protected boolean isPermitted() {
                CollectionDatasource ownerDatasource = target.getDatasource();
                boolean entityOpPermitted = security.isEntityOpPermitted(ownerDatasource.getMetaClass(), EntityOp.UPDATE);
                if (!entityOpPermitted) {
                    return false;
                }
                return super.isPermitted();
            }
        });

        /*
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link departmentsTableRemove}
         * to reset record, contained in {@link departmentDs}
         */
        departmentsTableRemove.setAfterRemoveHandler(removedItems -> departmentDs.setItem(null));

        /*
         * Adding ESCAPE shortcut that invokes cancel() method
         */
        editBox.addShortcutAction(new ShortcutAction(new KeyCombination(KeyCombination.Key.ESCAPE),
        shortcutTriggeredEvent -> cancel()));

        disableEditControls();
    }

    private void refreshOptionsForLookupFields() {
        for (Component component : fieldGroup.getOwnComponents()) {
            if (component instanceof LookupField) {
                CollectionDatasource optionsDatasource = ((LookupField) component).getOptionsDatasource();
                if (optionsDatasource != null) {
                    optionsDatasource.refresh();
                }
            }
        }
    }

    /**
     * Method that is invoked by clicking Ok button after editing an existing or creating a new record
     */
    public void save() {
        if (!validate(Collections.singletonList(fieldGroup))) {
            return;
        }
        getDsContext().commit();

        Department editedItem = departmentDs.getItem();
        if (creating) {
            departmentsDs.includeItem(editedItem);
        } else {
            departmentsDs.updateItem(editedItem);
        }
        departmentsTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        Department selectedItem = departmentsDs.getItem();
        if (selectedItem != null) {
            Department reloadedItem = dataSupplier.reload(selectedItem, departmentDs.getView());
            departmentsDs.setItem(reloadedItem);
        } else {
            departmentDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link Department} is being created
     */
    private void enableEditControls(boolean creating) {
        this.creating = creating;
        initEditComponents(true);
        fieldGroup.requestFocus();
    }

    /**
     * Disabling editing controls
     */
    private void disableEditControls() {
        initEditComponents(false);
        departmentsTable.requestFocus();
    }

    /**
     * Initiating edit controls, depending on if they should be enabled/disabled
     * @param enabled if true - enables editing controls and disables controls on the left side of the splitter
     *                if false - visa versa
     */
    private void initEditComponents(boolean enabled) {
        fieldGroup.setEditable(enabled);
        actionsPane.setVisible(enabled);
        lookupBox.setEnabled(!enabled);
    }
}