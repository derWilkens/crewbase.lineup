package eu.crewbase.lineup.entity.dto;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.wayfare.CrewChange;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import java.util.List;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s %s %s %s|crewChange,transfer,siteA,siteB")
@MetaClass(name = "lineup$TripDTO")
public class TripDTO extends BaseUuidEntity {
	private static final long serialVersionUID = 3997215805728195100L;

	@MetaProperty
	protected CrewChange crewChange;

	@MetaProperty
	protected Transfer transfer;

	@MetaProperty
	protected Site siteA;

	@MetaProperty
	protected Site siteB;

	
	
	

    @MetaProperty
    protected Integer bookedSeats;

    public void setBookedSeats(Integer bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public Integer getBookedSeats() {
        return bookedSeats;
    }


	public void setCrewChange(CrewChange crewChange) {
		this.crewChange = crewChange;
	}

	public CrewChange getCrewChange() {
		return crewChange;
	}

	public void setSiteA(Site siteA) {
		this.siteA = siteA;
	}

	public Site getSiteA() {
		return siteA;
	}

	public void setSiteB(Site siteB) {
		this.siteB = siteB;
	}

	public Site getSiteB() {
		return siteB;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

	public Transfer getTransfer() {
		return transfer;
	}
	
	public String toString(){
		return siteA.getItemDesignation() + " - " + siteB.getItemDesignation() + ": " + getBookedSeats();
	}

}