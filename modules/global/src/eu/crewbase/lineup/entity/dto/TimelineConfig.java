package eu.crewbase.lineup.entity.dto;

import java.util.function.Function;

public class TimelineConfig {

	/*
	 * Die Konfiguration stellt Funktionen für das TimelineDTO zur Verfügung.
	 * Abhängig von den unterschiedlichen PeriodItems werden die Attribute unterschiedlich interpretiert 
	 */
	private Function<?, String> groupIdFunction;
	private Function<?, String> groupLabelFunction;
	private Function<?, String> parentGroupIdFunction;
	private Function<?, String> itemLabelFunction;
	private Function<?, String> styleFunction;
	private Function<?, Boolean> editableFunction;
	private Function<?, String> typeFunction;
	
	public Function<?, String> getParentGroupIdFunction() {
		return parentGroupIdFunction;
	}
	public void setParentGroupIdFunction(Function<?, String> parentGroupIdFunction) {
		this.parentGroupIdFunction = parentGroupIdFunction;
	}

	public Function<?, String> getGroupIdFunction() {
		return groupIdFunction;
	}
	public void setGroupIdFunction(Function<?, String> groupFunction) {
		this.groupIdFunction = groupFunction;
	}

	public Function<?, String> getGroupLabelFunction() {
		return groupLabelFunction;
	}
	public void setGroupLabelFunction(Function<?, String> groupLabelFunction) {
		this.groupLabelFunction = groupLabelFunction;
	}
	public Function<?, String> getItemLabelFunction() {
		return itemLabelFunction;
	}
	public void setItemLabelFunction(Function<?, String> itemLabelFunction) {
		this.itemLabelFunction = itemLabelFunction;
	}
	
	public Function<?, String> getStyleFunction() {
		return styleFunction;
	}
	public void setStyleFunction(Function<?, String> styleFunction) {
		this.styleFunction = styleFunction;
	}
	public Function<?, Boolean> getEditableFunction() {
		return editableFunction;
	}
	public void setEditableFunction(Function<?, Boolean> editableFunction) {
		this.editableFunction = editableFunction;
	}
	public Function<?, String> getTypeFunction() {
		return typeFunction;
	}
	public void setTypeFunction(Function<?, String> typeFunction) {
		this.typeFunction = typeFunction;
	}
}
