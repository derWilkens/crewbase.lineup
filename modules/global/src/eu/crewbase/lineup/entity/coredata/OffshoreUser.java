/*
 * Copyright (c) 2016 saas
 */
package eu.crewbase.lineup.entity.coredata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Listeners;

/**
 * @author christian
 */

@Listeners("LINEUP_OffshoreUserEntityListener")
@NamePattern(" %s, %s|lastname,firstname")
@Entity(name = "lineup$OffshoreUser")
public class OffshoreUser extends AppUser {
    private static final long serialVersionUID = 6555877070622366614L;

    @Column(name = "WEIGHT")
    protected Integer weight;

    @Temporal(TemporalType.DATE)
    @Column(name = "WEIGHT_CHANGE_DATE")
    protected Date weightChangeDate;



















    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeightChangeDate(Date weightChangeDate) {
        this.weightChangeDate = weightChangeDate;
    }

    public Date getWeightChangeDate() {
        return weightChangeDate;
    }


}