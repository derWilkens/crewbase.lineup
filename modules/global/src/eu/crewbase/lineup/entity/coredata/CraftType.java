/*
 * Copyright (c) 2016 saas
 */
package eu.crewbase.lineup.entity.coredata;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;

/**
 * @author christian
 */
@NamePattern("%s|name")
@Table(name = "LINEUP_CRAFT_TYPE")
@Entity(name = "lineup$CraftType")
public class CraftType extends StandardClientEntity {
    private static final long serialVersionUID = 5779475331537662563L;

    @Column(name = "NAME", nullable = false, length = 50)
    protected String name;

    @Column(name = "SEATS", nullable = false)
    protected Integer seats;

    @Column(name = "MAX_RANGE")
    protected Integer maxRange;

    @Column(name = "PAYLOAD_OUTBOUND")
    protected Integer payloadOutbound;

    @Column(name = "PAYLOAD_INBOUND")
    protected Integer payloadInbound;

    @Lookup(type = LookupType.DROPDOWN)
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODE_OF_TRANSFER_ID")
    protected ModeOfTransfer modeOfTransfer;

    public void setPayloadOutbound(Integer payloadOutbound) {
        this.payloadOutbound = payloadOutbound;
    }

    public Integer getPayloadOutbound() {
        return payloadOutbound;
    }

    public void setPayloadInbound(Integer payloadInbound) {
        this.payloadInbound = payloadInbound;
    }

    public Integer getPayloadInbound() {
        return payloadInbound;
    }


    public void setMaxRange(Integer maxRange) {
        this.maxRange = maxRange;
    }

    public Integer getMaxRange() {
        return maxRange;
    }


    public void setModeOfTransfer(ModeOfTransfer modeOfTransfer) {
        this.modeOfTransfer = modeOfTransfer;
    }

    public ModeOfTransfer getModeOfTransfer() {
        return modeOfTransfer;
    }


    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Integer getSeats() {
        return seats;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}