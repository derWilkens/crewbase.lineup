package eu.crewbase.lineup.entity.period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;

import eu.crewbase.lineup.entity.dto.PeriodDTO;

@Table(name = "LINEUP_MAINTENANCE_CAMPAIGN")
@NamePattern("%s |campaignNumber")
@Entity(name = "lineup$MaintenanceCampaign")
public class MaintenanceCampaign extends SitePeriod {
	private static final long serialVersionUID = 2133165937821283408L;

	@Column(name = "CAMPAIGN_NUMBER", length = 10)
	protected String campaignNumber;
	
	public void readDto(PeriodDTO dto) {
		super.readDto(dto);
	}
	
	public void setCampaignNumber(String campaignNumber) {
		this.campaignNumber = campaignNumber;
	}

	public String getCampaignNumber() {
		return campaignNumber;
	}

}