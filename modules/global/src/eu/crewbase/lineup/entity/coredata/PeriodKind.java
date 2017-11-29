package eu.crewbase.lineup.entity.coredata;

import javax.annotation.Nullable;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

public enum PeriodKind implements EnumClass<String> {

	OperationPeriod("1"), MaintenanceCampaign("2"), OutageCampaign("3"), Absence("4"), Attendence("5");

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