package eu.crewbase.lineup.service;

import java.util.UUID;

import eu.crewbase.lineup.entity.coredata.Site;

public interface TransferService {
    String NAME = "lineup_TransferService";
    
    public void createTickets(UUID transferId, Site siteA, Site siteB, int bookedSeats);
    public void createTickets(UUID transferId, Site siteA, Site siteB, int bookedSeats, UUID travelOptionId);
}