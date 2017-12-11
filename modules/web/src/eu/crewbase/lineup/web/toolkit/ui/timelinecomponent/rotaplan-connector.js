eu_crewbase_lineup_web_toolkit_ui_timelinecomponent_RotaplanComponent = function() {
	var connector = this;
	var element = connector.getElement();
	$(element)
			.html(
							 "<div>"
							+ "<ul id='dutyItems' class='items'/>"
							//+ "<button id='genSites'>Sites generieren</button>"
							+ "</div>"
							+ "<div class='cbTimeline' height='600px' overflow-y='scroll' overflow-x='hidden' id='visualization'/>"
							);
	$(element).css("padding", "5px 10px");
	$(element).css("width", "95%");

	var container = document.getElementById('visualization');

	// specify options
	var options = {
		locale: 'de',
		stack: false,
		start: new Date(),
		end: new Date(1000 * 60 * 60 * 24 * 14 + (new Date()).valueOf()),
		editable: true,
		orientation: 'top',
		width: '1800px', // 100% funktioniert nicht, auf der Komponente
							// gesetzt, verschwindet dass nach itemAdded
		snap: function (date, scale, step) {
		      var day = 24 * 60 * 60 * 1000 ;
		      var tmp =  Math.round(date / day) * day ;
		      var res =  vis.moment(date).startOf('day');
		      return res;
		      
		},
		moment: function(date) {
		    return vis.moment(date).utc();
		  },
		groupOrder: function(a, b) {
			if (a.content < b.content)
				return -1;
			else if (a.content == b.content)
				return 0;
			else
				return 1;
		},
//		onAdd : function(item, callback) {
//			if (item) {
//				var newItem = {};
//				newItem.start = item.start;
//				newItem.end = item.end;
//				newItem.siteId = item.siteId;
//				//newItem.functionCategoryId = item.functionCategoryId;
//				newItem.duration = item.defaultDuration;
//				newItem.userId = item.group;
//				newItem.clazzName = item.clazzName;
//				connector.itemAdded(newItem);
//				callback(item); // 1938 send back adjusted new item
//				
//			} else {
//				callback(null); // cancel item creation
//			}
//		},

		onMove : function(item, callback) {
			connector.itemMoved(item);
			callback(item); // send back item as confirmation (can be
			// changed)

		},
		onRemove : function(item, callback) {
			if (item) {
				connector.itemDeleted(item);
				callback(item);
			} else {
				callback(null);
			}
//		},
//
//		onDropObjectOnItem : function(objectData, item) {
//			if (objectData) {
//				var newItem = {};
//				newItem.start = objectData.start;
//				newItem.end = objectData.end;
//				newItem.siteId = objectData.siteId;
//				//newItem.functionCategoryId = objectData.functionCategoryId;
//				newItem.duration = objectData.duration;
//				newItem.userId = objectData.group;
//				newItem.clazzName = objectData.clazzName;
//				connector.itemAdded(newItem);
//			} 
		}
	
		
	};

	// neue Daten vom Backend holen, wird vom Backend initiiert
	this.onStateChange = function() {

		var state = connector.getState();

		timeline.setGroups(new vis.DataSet(this.getState().timelineGroups));
		timeline.setItems(new vis.DataSet(this.getState().timelineItems));
		createItemList();
	}

	// Create a Timeline

	var timeline = new vis.Timeline(container);
	timeline.setOptions(options);
	timeline.setGroups(new vis.DataSet(this.getState().timelineGroups));
	timeline.setItems(new vis.DataSet(this.getState().timelineItems));

	timeline.on('doubleClick', function(props) {
		if (props.what = "item" && props.item) {
			connector.editItem(props.item, props.clazzName);
		} else if (props.what = "background" && props.group) {
			var newItem = {};
			newItem.start = props.time;
			newItem.userId = props.group;
			connector.itemAdded(newItem);
		} else {
			var newItem = {};
			newItem.start = props.time;
			connector.itemAdded(newItem);
		}
		console.log(props);
		props.event.preventDefault();
	});

	function createItemList() {
		dutyItems
		var siteDutyNode = document.getElementById("dutyItems");
		while (siteDutyNode.firstChild) {
			siteDutyNode.removeChild(siteDutyNode.firstChild);
		}
		var state = connector.getState();
		var templateItems = state.dutyPeriodTemplates;
		for (var i = templateItems.length - 1; i >= 0; i--) {
			var node = document.createElement("LI"); // Create a <li> node
			node.setAttribute("draggable", "true");
			node.setAttribute("class", "templateItem");
			node.setAttribute("style", "background-color: "
					+ templateItems[i].color);
			
			//node.setAttribute("data-functionCategoryId", templateItems[i].functionCategoryId);
//			node.setAttribute("data-siteId", templateItems[i].siteId);
//			node.setAttribute("data-defaultDuration", templateItems[i].duration);
//			node.setAttribute("data-clazzName", templateItems[i].clazzName);
			node.addEventListener('dragstart', handleDragStart.bind(this),
					false);
			
			var label = "≣ ";
			if(templateItems[i].itemDesignation){
				label = label + templateItems[i].itemDesignation;
			} else if(templateItems[i].categoryName){
				label = label + templateItems[i].categoryName
			}
			
			var textnode = document.createTextNode(label);
			node.appendChild(textnode); // Append the text to <li>
			dutyItems.appendChild(node); // Append the text to <ul>
		}
	}
	
	function handleDragStart(event) {
		dragSrcEl = event.target;
		//target:'item',
//			functionCategoryId:event.target.getAttribute('data-functionCategoryId'),

		event.dataTransfer.effectAllowed = 'move';
		var item = {
			id : new Date(),
			type : 'range',
			//clazzName : event.target.getAttribute('data-clazzName'),
			//siteId:event.target.getAttribute('data-siteid'),
			//defaultDuration:event.target.getAttribute('data-defaultDuration'),
			content : event.target.innerHTML.trim()
			
		};
		event.dataTransfer.setData("text", JSON.stringify(item));
	}

	$("#genSites").click(createItemList);
	var items = document.querySelectorAll('.items .item .siteItems');

	// an jedes Item den DragHandler setzen, damit dieses vorm Hinzufügen
	// parametrisiert werden kann
	for (var i = items.length - 1; i >= 0; i--) {
		var item = items[i];
		item.addEventListener('dragstart', handleDragStart.bind(this), false);
	}

}