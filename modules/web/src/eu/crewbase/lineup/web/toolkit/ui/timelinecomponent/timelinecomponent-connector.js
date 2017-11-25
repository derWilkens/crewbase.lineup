eu_crewbase_lineup_web_toolkit_ui_timelinecomponent_TimelineComponent = function() {
	var connector = this;
	var element = connector.getElement();
	$(element)
			.html(
					"<div id='visualization' style='width:1000px; height:400px;'></div>");
	$(element).css("padding", "5px 10px");

	var container = document.getElementById('visualization');

	this.onStateChange = function() {
		
		var state = connector.getState();
				
		timeline.setGroups(new vis.DataSet(this.getState().timelineGroups));
		timeline.setItems(new vis.DataSet(this.getState().timelineItems));
	}


	// Configuration for the Timeline
	var options = {start:this.getState().start,end:this.getState().end};
	var options = {
			    groupOrder: 'content'  // groupOrder can be a property name or a sorting function
			  };
	// Create a Timeline
	
	  var timeline = new vis.Timeline(container);
	  timeline.setOptions(options);
	  timeline.setGroups(new vis.DataSet(this.getState().timelineGroups));
	  timeline.setItems(new vis.DataSet(this.getState().timelineItems));
}