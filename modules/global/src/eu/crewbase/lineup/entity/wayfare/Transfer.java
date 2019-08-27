/*
 * Copyright (c) 2016 saas
 */
package eu.crewbase.lineup.entity.wayfare;

import com.esotericsoftware.minlog.Log;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.*;
import com.haulmont.cuba.core.global.DeletePolicy;
import eu.crewbase.lineup.entity.coredata.*;
import eu.crewbase.lineup.entity.dto.TripDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author christian
 */
@Listeners({"lineup_TransferListener"})
@Table(name = "LINEUP_TRANSFER")
@Entity(name = "lineup$Transfer")
public class Transfer extends StandardClientEntity {
    private static final long serialVersionUID = -5709533341256299692L;

    private static final Logger log = LoggerFactory.getLogger(Transfer.class);
    // private static final Logger log =
    // LoggerFactory.getLogger(Transfer.class);

    @Column(name = "TRANSFER_ORDER_NO", nullable = false)
    protected Integer transferOrderNo;

    @OnDelete(DeletePolicy.CASCADE)
    @OrderBy("posOrder")
    @Composition
    @OneToMany(mappedBy = "transfer", cascade = CascadeType.PERSIST)
    protected List<Waypoint> waypoints;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "transfer", cascade = CascadeType.PERSIST)
    protected List<Ticket> tickets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREW_CHANGE_ID")
    protected CrewChange crewChange;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATED_BY_ID")
    protected Company operatedBy;

    @Lookup(type = LookupType.DROPDOWN, actions = {"clear"})
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODE_OF_TRANSFER_ID")
    protected ModeOfTransfer modeOfTransfer;

    @Lookup(type = LookupType.DROPDOWN)
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CRAFT_TYPE_ID")
    protected CraftType craftType;

    public Transfer() {
        this.tickets = new ArrayList<>();
        this.waypoints = new ArrayList<>();
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public CraftType getCraftType() {
        return craftType;
    }

    public void setCraftType(CraftType craftType) {
        this.craftType = craftType;
    }

    public CrewChange getCrewChange() {
        return crewChange;
    }

    public void setCrewChange(CrewChange crewChange) {
        this.crewChange = crewChange;
    }

    public Integer getTransferOrderNo() {
        return transferOrderNo;
    }

    public void setTransferOrderNo(Integer transferOrderNo) {
        this.transferOrderNo = transferOrderNo;
    }

    public ModeOfTransfer getModeOfTransfer() {
        return modeOfTransfer;
    }

    public void setModeOfTransfer(ModeOfTransfer modeOfTransfer) {
        this.modeOfTransfer = modeOfTransfer;
    }

    public Company getOperatedBy() {
        return operatedBy;
    }

    public void setOperatedBy(Company operatedBy) {
        this.operatedBy = operatedBy;
    }

    public int getTotalDistance() {
        int totalDistance = 0;
        Waypoint preWaypoint = null;
        Waypoint currentWaypoint = null;

        Iterator<Waypoint> it = this.getWaypoints().iterator();
        while (it.hasNext()) {
            if (preWaypoint == null) {
                preWaypoint = it.next();
            } else if (it.hasNext()) {
                currentWaypoint = it.next();
                totalDistance = totalDistance + preWaypoint.getSite().getDistanceTo(currentWaypoint.getSite());
                preWaypoint = currentWaypoint;
            }
        }
        return totalDistance;
    }

    public String getRouteShort() {
        String route = "";
        String delim = "";

        for (Waypoint waypoint : this.getWaypoints()) {
            route = route + delim + waypoint.getSite().getItemDesignation();
            delim = " - ";
        }
        return route;
    }

    public String getRoute() {
        String route = "";
        String delim = "";

        for (Waypoint waypoint : this.getWaypoints()) {
            route = route + delim + waypoint.getSite().getSiteName();
            delim = " - ";
        }
        return route;
    }

    public List<Site> getSites() {
        return getWaypoints().stream().map(w -> w.getSite()).collect(Collectors.toList());
    }

    public HashMap<UUID, Site> getSiteHash() {
        HashMap<UUID, Site> resultMap = new HashMap<UUID, Site>();
        // return (HashMap<UUID, Site>)
        // getSites().stream().collect(Collectors.toMap(p.getId(), p -> p, (p1,
        // p2) -> p1));
        for (Waypoint waypoint : getWaypoints()) {
            resultMap.put(waypoint.getSite().getId(), waypoint.getSite());
        }
        return resultMap;
    }

    public void renumber() {
        for (Waypoint waypoint : this.getWaypoints()) {
            waypoint.setPosOrder(this.getWaypoints().indexOf(waypoint));
        }
    }

    public boolean addWaypoint(Waypoint waypoint) {
        return this.addWaypointAt(waypoint, 0);
    }
    private int getOptimalPosition(Waypoint waypoint, int startPosition, int endPosition){
        int optimalPosition = -1;
        if (this.getSiteHash().containsKey(waypoint.getSite().getId())) {
            return optimalPosition;
        }
        CraftType craft = this.getCraftType();
        int minDistance = craft.getMaxRange();

        for (int i = startPosition; i < endPosition; i++) {

            this.getWaypoints().add(i, waypoint);

            if (this.getTotalDistance() < craft.getMaxRange() && this.getTotalDistance() < minDistance) {
                minDistance = this.getTotalDistance();
                optimalPosition = i;
            }
            this.getWaypoints().remove(waypoint);
        }
        return optimalPosition;

    }
    public boolean addWaypointShortestWay(Waypoint wp1, Waypoint wp2) {
        //wenn wp1 enthalten ist, die Position fix nehmen und wp2 irgendwo dahinter einfügen
        //wenn wp2 drin ist, wp1 irgendwo davor einfügen
        if (this.getSiteHash().containsKey(wp1.getSite().getId())) {
            int wp1Pos = this.getWaypointBySide(wp1.getSite()).posOrder;
            int wp2Pos = getOptimalPosition(wp2,wp1Pos+1, this.getWaypoints().size());
            if(wp2Pos > -1) this.addWaypointAt(wp2,wp2Pos);
            this.renumber();
            this.recalcTakeOff();
            return true;
        }
        else
        if (this.getSiteHash().containsKey(wp2.getSite().getId())) {
            int wp2Pos = this.getWaypointBySide(wp2.getSite()).posOrder;
            int wp1Pos = getOptimalPosition(wp2,0,wp2Pos);
            if(wp2Pos > -1) this.addWaypointAt(wp1,wp1Pos);
            this.renumber();
            this.recalcTakeOff();
            return true;
        }else{
            //wp1 einsetzen, wp2 dahinter einsetzen, optimum finden
            int minimalDistance = this.getCraftType().getMaxRange();
            int wp1PosMin = -1;
            int wp2PosMin = -1;
            for(int i = 1 ; i<this.getWaypoints().size();i++){
                this.getWaypoints().add(i,wp1);
                for(int j = i; j<this.getWaypoints().size();j++){
                    int wp2Pos = this.getOptimalPosition(wp2,i+1,this.getWaypoints().size());
                    if(wp2Pos > 0){
                        this.getWaypoints().add(wp2Pos,wp2);
                        if(this.getTotalDistance()<minimalDistance){
                            minimalDistance = this.getTotalDistance();
                            wp1PosMin = i;
                            wp2PosMin = j;
                        }
                    }
                }
            }
            if(wp1PosMin > 0){
                this.getWaypoints().add(wp1PosMin,wp1);
                this.getWaypoints().add(wp2PosMin,wp2);
            }
        }
        return false;
    }

    // Wegpunkt wird so eingesetzt, dass die Strecke minimal ist
    // @fixme optimum wird erreicht, wenn PAX-Reisedauer UND Strecke minimal ist
    public boolean addWaypointShortestWay(Waypoint waypoint) {

        // schon drin, also true zurück
        if (this.getSiteHash().containsKey(waypoint.getSite().getId())) {
            return true; //auch wenns eigentlich falsch ist, weil der Wegpunkt dann nicht unbedingt auch an der kürzesten Stelle ist
        }

        waypoint.setTransfer(this);
        int optimalPosition = getOptimalPosition(waypoint,1,this.getWaypoints().size());

        if ( optimalPosition > 0) {
            this.addWaypointAt(waypoint, optimalPosition);
            this.renumber();
            this.recalcTakeOff();
            return true;
        }
        return false;
    }

    public boolean addWaypointAt(Waypoint waypoint, int position) {
        waypoint.setTransfer(this);

        if (position == 0) {
            this.getWaypoints().add(waypoint);
        } else {
            this.getWaypoints().add(position, waypoint);
        }

        if (this.getTotalDistance() > this.getCraftType().getMaxRange()) {
            this.getWaypoints().remove(position);
            return false;
        }
        this.renumber();
        this.recalcTakeOff();
        return true;
    }

    private void recalcTakeOff() {
        Waypoint preWaypoint = null;
        Waypoint currentWaypoint = null;

        Iterator<Waypoint> it = this.getWaypoints().iterator();
        while (it.hasNext()) {
            if (preWaypoint == null) {
                preWaypoint = it.next();
            } else {
                currentWaypoint = it.next();
                currentWaypoint.calculateTakeOff(preWaypoint);
                preWaypoint = currentWaypoint;
            }
        }
    }

    public boolean isLastWaypoint(Waypoint waypoint) {
        return this.getWaypoints().get(this.getWaypoints().size() - 1).equals(waypoint);
    }

    /**
     * Es werden die Tickets gruppiert nach Sites übergeben -> Tickets bilden
     * Strecken ab mit entsprechenden gebuchten Plätzen
     */
    public int getFreeCapacityForTrip(List<TripDTO> groupedTickets, TravelOption travelOption) {
        return getFreeCapacityForTrip(groupedTickets, travelOption.getFavoriteTrip().getStartSite(),
                travelOption.getFavoriteTrip().getDestination());
    }

    public Integer getFreeCapacityForTrip(List<TripDTO> groupedTickets, FavoriteTrip favoriteTrip) {
        return getFreeCapacityForTrip(groupedTickets, favoriteTrip.getStartSite(),
                favoriteTrip.getDestination());
    }

    public int getFreeCapacityForTrip(List<TripDTO> groupedTickets, Site siteA, Site siteB) {

        HashMap<UUID, Integer> capaMap = getBookedSeatsMap(groupedTickets);

        // dann über Fav-Strecke und Result iterieren
        // int capa = transfer.getCraftType().getSeats();
        int bookedSeats = 0;
        // den kompletten Transfer durchlaufen und die minimale Kapazität auf
        // der desiredRoute ermitteln
        // Route: A (5) - B(5+2) - C(-5) - D(+10) - E
        // Kapa Strecke C - E ? Booked C (2) ...

        for (Waypoint waypoint : this.getWaypoints()) {
            if (!this.isLastWaypoint(waypoint)) {
                Site favSite = waypoint.getSite();

                boolean onboard = false;
                if (favSite.getId().equals(siteA.getId())) {
                    onboard = true;
                }
                if (favSite.getId().equals(siteB.getId())) {
                    onboard = false;
                    log.debug(favSite.getItemDesignation() + ": an Bord: " + bookedSeats);
                    break;
                }
                // es sind welche zugestiegen, hochzählen
                if (onboard && capaMap.get(favSite.getId()) != null && capaMap.get(favSite.getId()) > bookedSeats) {
                    bookedSeats = bookedSeats + capaMap.get(favSite.getId());
                }

            }
        }

        log.debug("Booked in total: " + bookedSeats + " | Transfer: " + this.getRouteShort());
        return this.getCraftType().getSeats() - bookedSeats;
    }

    /**
     * Für jede Site werden die Booked Seats berechnet. Für die Ticketgruppe wird
     * die Strecke durchlaufen und die besetzten Plätze aufaddiert.
     */
    public HashMap<UUID, Integer> getBookedSeatsMap(List<TripDTO> groupedTickets) {

        HashMap<UUID, Integer> resultCapaMap = new HashMap<UUID, Integer>();

        // über die tickets iterieren

        for (TripDTO ticketGroup : groupedTickets) {
            log.debug("Booked Tickets " + ticketGroup.getSiteA().getItemDesignation() + " - "
                    + ticketGroup.getSiteB().getItemDesignation() + ": " + ticketGroup.getBookedSeats());

            // capaMap enthält booked seats beim Verlassen der Site:
            // über die Strecke iterieren, wenn site = startSite -> PAX steigen
            // zu, wenn Site = destination -> PAX steigen aus
            boolean onboard = false;

            // den letzten Waypoint nicht mehr berücksichtigen, weil die Site
            // normalerweise identisch mit dem Start ist
            for (int i = 0; i < this.getWaypoints().size() - 1; i++) {
                Waypoint waypoint = this.getWaypoints().get(i);
                Site site = waypoint.getSite();

                if (site.getId().equals(ticketGroup.getSiteB().getId()) && onboard) {
                    onboard = false;
                } else if (site.getId().equals(ticketGroup.getSiteA().getId()) || onboard) {
                    int currentBookedSeats = 0;
                    if (resultCapaMap.containsKey(site.getId())) {
                        currentBookedSeats = resultCapaMap.get(site.getId());
                    }
                    log.debug("Site: " + site.getItemDesignation() + " Seats already Booked: " + currentBookedSeats
                            + ", Seats to add: " + ticketGroup.getBookedSeats());
                    resultCapaMap.put(site.getId(), currentBookedSeats + ticketGroup.getBookedSeats());
                    onboard = true;
                }
            }
        }
        return resultCapaMap;
    }

    public Waypoint getWaypointBySide(Site site) {
        for(Waypoint wp:getWaypoints()){
            if(wp.getSite().getId().equals(site.getId())){
                return  wp;
            }
        }
        return null;
    }


}