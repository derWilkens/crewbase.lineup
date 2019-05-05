package eu.crewbase.lineup.web.thirdclass;

import com.haulmont.cuba.gui.screen.*;
import eu.crewbase.lineup.entity.tmp.ThirdClass;

@UiController("lineup$ThirdClass.edit")
@UiDescriptor("third-class-edit.xml")
@EditedEntityContainer("thirdClassDc")
@LoadDataBeforeShow
public class ThirdClassEdit extends StandardEditor<ThirdClass> {
}