package eu.crewbase.lineup.entity.coredata;

import javax.annotation.Nullable;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

public enum PeriodSubClass implements EnumClass<String> {

	ModeOfOperation("1"), Administration("2"), DutyPeriod("3");

	private String id;

	PeriodSubClass(String value) {
		this.id = value;
	}

	public String getId() {
		return id;
	}

	@Nullable
	public static PeriodSubClass fromId(String id) {
		for (PeriodSubClass at : PeriodSubClass.values()) {
			if (at.getId().equals(id)) {
				return at;
			}
		}
		return null;
	}
}