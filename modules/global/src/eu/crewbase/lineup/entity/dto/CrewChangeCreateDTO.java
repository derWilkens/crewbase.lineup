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

@NamePattern("%s %s|crewChangeDate,destinationSite")
@MetaClass(name = "lineup$CrewChangeCreateDTO")
public class CrewChangeCreateDTO extends BaseUuidEntity {
    private static final long serialVersionUID = 8628238232285382909L;

    @MetaProperty
    protected Date crewChangeDate;

    @MetaProperty
    protected Date takeOff;

    @Lookup(type = LookupType.DROPDOWN)
    @MetaProperty
    protected Site startSite;

    @Lookup(type = LookupType.DROPDOWN)
    @MetaProperty
    protected Site destinationSite;

    @Lookup(type = LookupType.DROPDOWN)
    @MetaProperty
    protected CraftType helicopterType;

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

    public void setStartSite(Site startSite) {
        this.startSite = startSite;
    }

    public Site getStartSite() {
        return startSite;
    }

    public void setDestinationSite(Site destinationSite) {
        this.destinationSite = destinationSite;
    }

    public Site getDestinationSite() {
        return destinationSite;
    }

    public void setHelicopterType(CraftType helicopterType) {
        this.helicopterType = helicopterType;
    }

    public CraftType getHelicopterType() {
        return helicopterType;
    }


}