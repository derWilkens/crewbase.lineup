package eu.crewbase.lineup.service;

import java.util.UUID;

import eu.crewbase.lineup.entity.coredata.Site;

public interface TransferService {
	String NAME = "lineup_TransferService";

	void createTickets(UUID travelOptionId);

	void createTickets(UUID transferId, Site siteA, Site siteB, int bookedSeats);

	boolean addWaypoint(UUID transferId, UUID siteId);

	boolean addWaypoint(UUID transferId, UUID siteId, int position);

	void removeWaypoint(UUID transferId, UUID siteId);

	void removeWaypoint(UUID transferId, int position);
	
	
}