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
import javax.validation.constraints.Past;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

@NamePattern("%s %s|startDateTime,destinationSite")
@MetaClass(name = "lineup$CrewChangeCreateDTO")
public class CrewChangeCreateDTO extends BaseUuidEntity {
    private static final long serialVersionUID = 8628238232285382909L;

    @NotNull
    @MetaProperty
    protected Date startDateTime;

    @NotNull
    @Lookup(type = LookupType.DROPDOWN)
    @MetaProperty
    protected Site departureSite;

    @NotNull
    @Lookup(type = LookupType.DROPDOWN)
    @MetaProperty
    protected Site destinationSite;

    @NotNull
    @Lookup(type = LookupType.DROPDOWN)
    @MetaProperty
    protected CraftType craftType;

    @MetaProperty
    protected Integer bookedSeatsOutbound;

    @MetaProperty
    protected Integer bookedSeatsInbound;

    public void setDestinationSite(Site destinationSite) {
        this.destinationSite = destinationSite;
    }

    public Site getDestinationSite() {
        return destinationSite;
    }


    public void setBookedSeatsOutbound(Integer bookedSeatsOutbound) {
        this.bookedSeatsOutbound = bookedSeatsOutbound;
    }

    public Integer getBookedSeatsOutbound() {
        return bookedSeatsOutbound;
    }

    public void setCraftType(CraftType craftType) {
        this.craftType = craftType;
    }

    public CraftType getCraftType() {
        return craftType;
    }

    public void setBookedSeatsInbound(Integer bookedSeatsInbound) {
        this.bookedSeatsInbound = bookedSeatsInbound;
    }

    public Integer getBookedSeatsInbound() {
        return bookedSeatsInbound;
    }


    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }



    public void setDepartureSite(Site departureSite) {
        this.departureSite = departureSite;
    }

    public Site getDepartureSite() {
        return departureSite;
    }



}