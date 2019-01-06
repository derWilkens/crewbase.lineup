package eu.crewbase.lineup.service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.TripDTO;
import eu.crewbase.lineup.entity.wayfare.FavoriteTrip;
import eu.crewbase.lineup.entity.wayfare.Transfer;

public interface TravelOptionService {
    String NAME = "lineup_TravelOptionService";
    
    public List<Site> getReachableSites(UUID transferId);
    public void createTravelOption(Transfer transfer);
    public void sendTravelOptionNotification();
    
    public List<TripDTO> getGroupedTickets(Transfer transfer);
    public HashMap<UUID, Integer> getBookedSeatsMap(List<TripDTO> groupedTickets, Transfer transferWithFavTrips); 
    public TripDTO getFreeCapacityForTrip(FavoriteTrip desiredTrip,  Transfer transferWithFavTrips, Transfer transfer);
    
}