var currentSrcRowId = -1;
var currentDstRowId = -1;

var getGridName = function(isSrc) {
	if (isSrc)
		return "grid-unusedtags";
	else
		return "grid-usedtags";
};

var getTagId = function(isSrc) {
	var grid = $("#" + getGridName(isSrc));
	
	var rowId = grid.getGridParam('selrow');
	var rowData = grid.getRowData(rowId);
	
	return rowData.ID;
};

var gridReload = function(name, tags) {
	var grid = $("#grid" + name);
	
	grid.clearGridData();
	
	for (var index = 0; index < tags.length; index++) {
		grid.addRowData(
			index,
			{
				"ID" : tags[index].id,
				"Tag" : tags[index].tag
			}
		);
	}
};

var loadCurrentUrl = function() {
	ajaxJsonRequest(
		JSPVARS.dbmsBookmarkLoad + "?id=" + JSPVARS.currentBookmarkId,
		function(data) {
			$("#currentURL").text(data.url);
		},
		false
	);
};

var loadGrids = function(id) {
	ajaxJsonRequest(
		JSPVARS.dbmsTagListUnusedByBookmark + "?id=" + JSPVARS.currentBookmarkId,
		function(data) {
			gridReload("-unusedtags", data.items);
		},
		false
	);
			
	ajaxJsonRequest(
		JSPVARS.dbmsTagListUsedByBookmark + "?id=" + JSPVARS.currentBookmarkId,
		function(data) {
			gridReload("-usedtags", data.items);
		},
		false
	);
};

var onAddClicked = function() {
	ajaxJsonRequest(
		JSPVARS.dbmsBookmarkTagAdd + "?id=" + JSPVARS.currentBookmarkId + "&tid=" + getTagId(true),
		function(data) { },
		false
	);
	
	currentSrcRowId = -1;
	currentDstRowId = -1;
	loadGrids();
	refreshEnables();
};

var onRemoveClicked = function() {
	ajaxJsonRequest(
		JSPVARS.dbmsBookmarkTagDelete + "?id=" + JSPVARS.currentBookmarkId + "&tid=" + getTagId(false),
		function(data) { },
		false
	);
		
	currentSrcRowId = -1;
	currentDstRowId = -1;
	loadGrids();
	refreshEnables();
};

var onRemoveAllClicked = function() {
	ajaxJsonRequest(
		JSPVARS.dbmsBookmarkTagDeleteAll + "?id=" + JSPVARS.currentBookmarkId,
		function(data) { },
		false
	);
			
	currentSrcRowId = -1;
	currentDstRowId = -1;
	loadGrids();
	refreshEnables();
};

var refreshEnables = function() {
	if (currentSrcRowId === -1) {
		$("#btnAdd").button("disable");
	} else {
		$("#btnAdd").button("enable");
	}
	if (currentDstRowId === -1) {
		$("#btnRemove").button("disable");
	} else {
		$("#btnRemove").button("enable");
	}
	
	if ($("#grid-usedtags").jqGrid('getGridParam', 'reccount') > 0)
		$("#btnRemoveAll").button("enable");
	else
		$("#btnRemoveAll").button("disable");
};

var setupGrid = function(name, isSrc) {
	$("#grid" + name).jqGrid({
		datatype: "array",
		colModel : [
			{ name : "ID", sortable: false, key : true, hidden: true },
			{ name : "Tag", editable: false }
		],
		pager : "#pager" + name,
		multiselect: false,
		onSelectRow : function(id) {
			if (isSrc)
				currentSrcRowId = id;
			else
				currentDstRowId = id;
			refreshEnables();
		}
	});
	
	$("#grid" + name).navGrid("#pager" + name, {
		edit : false,
		add : false,
		del : false,
		refresh : false,
		search : false
	});
	
	$("#grid" + name).setGridWidth(400);
};

$(document).ready(function(){
	loadCurrentUrl();
	
	$("#btnAdd").button();
	$("#btnAdd").on("click", onAddClicked);
	$("#btnRemove").button();
	$("#btnRemove").on("click", onRemoveClicked);
	$("#btnRemoveAll").button();
	$("#btnRemoveAll").on("click", onRemoveAllClicked);

	setupGrid("-unusedtags", true);
	setupGrid("-usedtags", false);

	loadGrids();
	
	refreshEnables();
});
