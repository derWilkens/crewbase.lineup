package eu.crewbase.lineup.entity.batchimport;

import java.util.Date;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.AbstractNotPersistentEntity;

@NamePattern("%s %s|itemDesignation,campaignNo")
@MetaClass(name = "lineup$PeriodImportRecord")
public class PeriodImportRecord extends AbstractNotPersistentEntity {
    private static final long serialVersionUID = -6787400577197723061L;

    @MetaProperty
    protected String itemDesignation;

    @MetaProperty
    protected String campaignNo;

    @MetaProperty
    protected Date start;

    @MetaProperty
    protected Date end;

    @MetaProperty
    protected Boolean shutdown;

    public void setItemDesignation(String itemDesignation) {
        this.itemDesignation = itemDesignation;
    }

    public String getItemDesignation() {
        return itemDesignation;
    }

    public void setCampaignNo(String campaignNo) {
        this.campaignNo = campaignNo;
    }

    public String getCampaignNo() {
        return campaignNo;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStart() {
        return start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getEnd() {
        return end;
    }

    public void setShutdown(Boolean shutdown) {
        this.shutdown = shutdown;
    }

    public Boolean getShutdown() {
        return shutdown;
    }


}