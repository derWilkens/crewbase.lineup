package eu.crewbase.lineup.web.thirdclass;

import com.haulmont.cuba.gui.screen.*;
import eu.crewbase.lineup.entity.tmp.ThirdClass;

@UiController("lineup$ThirdClass.browse")
@UiDescriptor("third-class-browse.xml")
@LookupComponent("thirdClassesTable")
@LoadDataBeforeShow
public class ThirdClassBrowse extends StandardLookup<ThirdClass> {
}