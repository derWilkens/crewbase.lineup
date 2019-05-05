/*
 * Copyright (c) 2016 saas
 */
package eu.crewbase.lineup.entity.wayfare;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import eu.crewbase.lineup.entity.coredata.StandardClientEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author christian
 */
@Listeners("lineup_CrewChangeListener")
@Table(name = "LINEUP_CREW_CHANGE")
@Entity(name = "lineup$CrewChange")
public class CrewChange extends StandardClientEntity {
    private static final long serialVersionUID = 3072080113502808893L;


    @Temporal(TemporalType.DATE)
    @Column(name = "START_DATE", nullable = false)
    protected Date startDate;

    @OrderBy("transferOrderNo")
    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "crewChange", cascade = CascadeType.PERSIST)
    protected List<Transfer> transfers;

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }


    public CrewChange() {
		this.transfers = new ArrayList<Transfer>();
	}

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }





}
