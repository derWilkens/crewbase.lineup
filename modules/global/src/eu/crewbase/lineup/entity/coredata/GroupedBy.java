package eu.crewbase.lineup.entity.coredata;

import javax.annotation.Nullable;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

public enum GroupedBy implements EnumClass<String> {

	Site("1"), User("2"), Type("3");

	private String id;

	GroupedBy(String value) {
		this.id = value;
	}

	public String getId() {
		return id;
	}

	@Nullable
	public static GroupedBy fromId(String id) {
		for (GroupedBy at : GroupedBy.values()) {
			if (at.getId().equals(id)) {
				return at;
			}
		}
		return null;
	}
}