package eu.crewbase.lineup.web.secondclass;

import com.haulmont.cuba.gui.screen.*;
import eu.crewbase.lineup.entity.tmp.SecondClass;

@UiController("lineup$SecondClass.browse")
@UiDescriptor("second-class-browse.xml")
@LookupComponent("secondClassesTable")
@LoadDataBeforeShow
public class SecondClassBrowse extends StandardLookup<SecondClass> {
}