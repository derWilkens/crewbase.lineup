package eu.crewbase.lineup.listener;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esotericsoftware.minlog.Log;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.listener.AfterUpdateEntityListener;
import com.haulmont.cuba.core.listener.BeforeDeleteEntityListener;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import com.haulmont.cuba.security.entity.User;

import eu.crewbase.lineup.entity.coredata.CraftType;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.TripDTO;
import eu.crewbase.lineup.entity.wayfare.FavoriteTrip;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.entity.wayfare.TravelOption;
import eu.crewbase.lineup.entity.wayfare.TravelOptionStatus;
import eu.crewbase.lineup.entity.wayfare.Waypoint;
import eu.crewbase.lineup.service.TravelOptionService;
import com.haulmont.cuba.core.listener.AfterInsertEntityListener;

@Component("lineup_TransferListener")
public class TransferListener implements BeforeDeleteEntityListener<Transfer>, BeforeInsertEntityListener<Transfer>,
		BeforeUpdateEntityListener<Transfer>, AfterUpdateEntityListener<Transfer>, AfterInsertEntityListener<Transfer> {

	private static final Logger log = LoggerFactory.getLogger(TransferListener.class);

	@Inject
	public TravelOptionService travelOptionService;
	@Inject
	public Metadata metadata;
	@Inject
	public DataManager dataManager;
	@Inject
	public Persistence persistence;

	@Override
	public void onBeforeDelete(Transfer entity, EntityManager entityManager) {

	}

	@Override
	public void onBeforeInsert(Transfer transfer, EntityManager entityManager) {
		transfer.renumber();
	}

	@Override
	public void onBeforeUpdate(Transfer transfer, EntityManager entityManager) {
		transfer.renumber();

	}
    @Override
    public void onAfterInsert(Transfer transfer, Connection connection) {
    	//travelOptionService.createTravelOptions(transfer.getId());
    }
	@Override
	public void onAfterUpdate(Transfer entity, Connection connection) {
		//travelOptionService.updateTravelOptions(entity.getId());
	}

	

	

	private User getUserByUsername(String createdBy, EntityManager entityManager) {

		String queryString = "select e from sec$User e where e.login = :login";

		return entityManager.createQuery(queryString, User.class).setParameter("login", createdBy).getFirstResult();
	}

	
	@SuppressWarnings("unchecked")
	public List<TripDTO> getGroupedTickets(Transfer transfer, EntityManager entityManager) {
		List<TripDTO> resultList = new ArrayList<TripDTO>();
		List<Object[]> ticketList = entityManager
				.createQuery("SELECT t.startSite AS siteA, t.destinationSite, COUNT(t) AS total "
						+ "FROM lineup$Ticket t where t.transfer.id = :transferId "
						+ "GROUP BY t.startSite, t.destinationSite ORDER BY t.startSite.itemDesignation, t.destinationSite.itemDesignation ASC")
				.setParameter("transferId", transfer.getId()).getResultList();

		for (Object[] result : ticketList) {
			TripDTO tmp = new TripDTO();
			tmp.setSiteA((Site) result[0]);
			tmp.setSiteB((Site) result[1]);
			tmp.setBookedSeats(((Number) result[2]).intValue());
			resultList.add(tmp);
		}

		return resultList;
	}










	private Map<UUID, Site> getReachableSitesHash(Transfer transfer) {
		Map<UUID, Site> resultHash = new HashMap<UUID, Site>();
		List<Site> siteResultList = getReachableSites(transfer);
		for (Site site : siteResultList) {
			log.debug("ReachableSite: " + site.getItemDesignation());
			resultHash.put(site.getId(), site);
		}
		return resultHash;
	}

	public List<Site> getReachableSites(Transfer transfer) {
		List<Site> siteResultList;
		siteResultList = new ArrayList<Site>();
		CraftType craft = transfer.getCraftType();

		List<UUID> collect = transfer.getSites().stream().map(Site::getId).collect(Collectors.toList());

		List<Site> siteList = dataManager.load(Site.class)
				.query("select s from lineup$Site s where s.id not in :transferSiteList")
				.parameter("transferSiteList", collect).list();

		// welche Sites können noch erreicht werden?
		// - Liste der vorhandenen Sites übergeben

		// jede site an jeder position einfügen und Gesamtdistanz ausrechnen
		log.debug("Vor Site add/remove " + transfer.getRouteShort());
		for (Site site : siteList) {
			Waypoint addedWaypoint = createWaypoint(site);
			if (transfer.addWaypointShortestWay(addedWaypoint)) {
				siteResultList.add(site);
				transfer.getWaypoints().remove(addedWaypoint);
				transfer.renumber();
			}
			log.debug("Nachdem Site add/remove " + transfer.getRouteShort());
		}
		return siteResultList;
	}

	private Waypoint createWaypoint(Site site) {
		// Neuen WP erstellen
		Waypoint waypoint = metadata.create(Waypoint.class);
		waypoint.setSite(site);
		return waypoint;
	}

}