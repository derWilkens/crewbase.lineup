package eu.crewbase.lineup.web.favoritetrip;

import com.haulmont.cuba.gui.screen.*;
import eu.crewbase.lineup.entity.wayfare.FavoriteTrip;

@UiController("lineup$FavoriteTrip.edit")
@UiDescriptor("favorite-trip-edit.xml")
@EditedEntityContainer("favoriteTripDc")
@LoadDataBeforeShow
public class FavoriteTripEdit extends StandardEditor<FavoriteTrip> {
}