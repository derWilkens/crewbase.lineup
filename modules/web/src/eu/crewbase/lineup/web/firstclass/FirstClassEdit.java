package eu.crewbase.lineup.web.firstclass;

import com.haulmont.cuba.gui.screen.*;
import eu.crewbase.lineup.entity.tmp.FirstClass;

@UiController("lineup$FirstClass.edit")
@UiDescriptor("first-class-edit.xml")
@EditedEntityContainer("firstClassDc")
@LoadDataBeforeShow
public class FirstClassEdit extends StandardEditor<FirstClass> {
}