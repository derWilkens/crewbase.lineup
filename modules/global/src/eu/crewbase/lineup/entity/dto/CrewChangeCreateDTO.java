package eu.crewbase.lineup.entity.dto;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import eu.crewbase.lineup.entity.coredata.CraftType;
import eu.crewbase.lineup.entity.coredata.Site;
import java.util.Date;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

@NamePattern("%s %s|crewChangeDate,arrivalSite")
@MetaClass(name = "lineup$CrewChangeCreateDTO")
public class CrewChangeCreateDTO extends BaseUuidEntity {
    private static final long serialVersionUID = 8628238232285382909L;

    @MetaProperty
    protected Date crewChangeDate;

    @MetaProperty
    protected Date takeOff;

    @Lookup(type = LookupType.DROPDOWN)
    @MetaProperty
    protected Site departureSite;

    @Lookup(type = LookupType.DROPDOWN)
    @MetaProperty
    protected Site arrivalSite;

    @MetaProperty
    protected Integer occupiedSeatsWay1;

    @Lookup(type = LookupType.DROPDOWN)
    @MetaProperty
    protected CraftType helicopterType;

    @MetaProperty
    protected Integer occupiedSeatsWay2;

    public void setArrivalSite(Site arrivalSite) {
        this.arrivalSite = arrivalSite;
    }

    public Site getArrivalSite() {
        return arrivalSite;
    }


    public void setDepartureSite(Site departureSite) {
        this.departureSite = departureSite;
    }

    public Site getDepartureSite() {
        return departureSite;
    }

    public void setOccupiedSeatsWay1(Integer occupiedSeatsWay1) {
        this.occupiedSeatsWay1 = occupiedSeatsWay1;
    }

    public Integer getOccupiedSeatsWay1() {
        return occupiedSeatsWay1;
    }

    public void setOccupiedSeatsWay2(Integer occupiedSeatsWay2) {
        this.occupiedSeatsWay2 = occupiedSeatsWay2;
    }

    public Integer getOccupiedSeatsWay2() {
        return occupiedSeatsWay2;
    }


    public void setCrewChangeDate(Date crewChangeDate) {
        this.crewChangeDate = crewChangeDate;
    }

    public Date getCrewChangeDate() {
        return crewChangeDate;
    }

    public void setTakeOff(Date takeOff) {
        this.takeOff = takeOff;
    }

    public Date getTakeOff() {
        return takeOff;
    }

    public void setHelicopterType(CraftType helicopterType) {
        this.helicopterType = helicopterType;
    }

    public CraftType getHelicopterType() {
        return helicopterType;
    }


}