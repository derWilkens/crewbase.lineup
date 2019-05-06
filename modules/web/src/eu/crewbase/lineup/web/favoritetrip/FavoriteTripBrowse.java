package eu.crewbase.lineup.web.favoritetrip;

import com.haulmont.cuba.gui.screen.*;
import eu.crewbase.lineup.entity.wayfare.FavoriteTrip;

@UiController("lineup$FavoriteTrip.browse")
@UiDescriptor("favorite-trip-browse.xml")
@LookupComponent("favoriteTripsTable")
@LoadDataBeforeShow
public class FavoriteTripBrowse extends StandardLookup<FavoriteTrip> {
}