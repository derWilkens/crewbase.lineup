package eu.crewbase.lineup.entity.coredata;

import javax.annotation.Nullable;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

public enum PeriodKind implements EnumClass<String> {

	OperationPeriod("eu.crewbase.lineup.entity.period.OperationPeriod"), MaintenanceCampaign("eu.crewbase.lineup.entity.period.MaintenanceCampaign"), OutageCampaign("eu.crewbase.lineup.entity.period.OutageCampaign"), Absence("eu.crewbase.lineup.entity.period.AbsencePeriod"), Attendence("eu.crewbase.lineup.entity.period.AttendencePeriod");

	private String id;

	PeriodKind(String value) {
		this.id = value;
	}

	public String getId() {
		return id;
	}

	@Nullable
	public static PeriodKind fromId(String id) {
		for (PeriodKind at : PeriodKind.values()) {
			if (at.getId().equals(id)) {
				return at;
			}
		}
		return null;
	}
}