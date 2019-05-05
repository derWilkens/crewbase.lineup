package eu.crewbase.lineup.web.secondclass;

import com.haulmont.cuba.gui.screen.*;
import eu.crewbase.lineup.entity.tmp.SecondClass;

@UiController("lineup$SecondClass.edit")
@UiDescriptor("second-class-edit.xml")
@EditedEntityContainer("secondClassDc")
@LoadDataBeforeShow
public class SecondClassEdit extends StandardEditor<SecondClass> {
}