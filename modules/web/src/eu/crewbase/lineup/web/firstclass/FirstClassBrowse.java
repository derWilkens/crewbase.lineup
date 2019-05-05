package eu.crewbase.lineup.web.firstclass;

import com.haulmont.cuba.gui.screen.*;
import eu.crewbase.lineup.entity.tmp.FirstClass;

@UiController("lineup$FirstClass.browse")
@UiDescriptor("first-class-browse.xml")
@LookupComponent("firstClassesTable")
@LoadDataBeforeShow
public class FirstClassBrowse extends StandardLookup<FirstClass> {
}