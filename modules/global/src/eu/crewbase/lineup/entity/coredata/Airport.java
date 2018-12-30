package eu.crewbase.lineup.entity.coredata;

import javax.persistence.MappedSuperclass;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.global.DesignSupport;

@Table(name = "LINEUP_AIRPORT")
@Entity(name = "lineup$Airport")
public class Airport extends Site {
    private static final long serialVersionUID = 6321023424166849366L;

    @Column(name = "ICAO_CODE", length = 4)
    protected String icaoCode;

    @Column(name = "IATA_CODE", length = 4)
    protected String iataCode;

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getIataCode() {
        return iataCode;
    }


}