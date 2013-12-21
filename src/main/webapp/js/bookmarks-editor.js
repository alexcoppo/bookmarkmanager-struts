var currentSelectedRowId = -1;
var lastQuery = "";

var getBookmarkId = function() {
	var rowId = $("#grid").getGridParam('selrow');
	var rowData = $("#grid").getRowData(rowId);
	
	return rowData.ID;
};

var gridReload = function(bookmarks) {
	var grid = $("#grid");
	
	grid.clearGridData();
	
	for (var index = 0; index < bookmarks.length; index++) {
		grid.addRowData(
			index,
			{
				"ID" : bookmarks[index].id,
				"Domain" : bookmarks[index].domain,
				"URL" : bookmarks[index].url
			}
		);
	}
	
	currentSelectedRowId = -1;
	refreshEnables();
};

var runQueryUntagged = function() {
	var bookmarks = null;
	
	ajaxJsonRequest(
		JSPVARS.dbmsBookmarksListUntagged,
		function(data) {
			bookmarks = data.items;
		},
		false
	);

	gridReload(bookmarks);
	
	lastQuery = "query-untagged";
};

var runQueryByTag = function() {
	var tag = $("#query-tag-text").val();
	var bookmarks = null;
	
	ajaxJsonRequest(
		JSPVARS.dbmsBookmarksListByTag + "?tag=" + encodeURIComponent(tag),
		function(data) {
			bookmarks = data.items;
		},
		false
	);

	gridReload(bookmarks);
	
	lastQuery = "query-by-tag";
};

var runQueryByUrlPattern = function() {
	var pattern = $("#query-url-pattern").val();
	var bookmarks = null;
	
	ajaxJsonRequest(
		JSPVARS.dbmsBookmarksListByUrlPattern + "?pattern=" + encodeURIComponent(pattern),
		function(data) {
			bookmarks = data.items;
		},
		false
	);

	gridReload(bookmarks);
	
	lastQuery = "query-by-url-pattern";
};

var rerunLastQuery = function() {
	if (lastQuery == "")
		return;
	else if (lastQuery == "query-untagged")
		runQueryUntagged();
	else if (lastQuery == "query-by-tag")
		runQueryByTag();
	else if (lastQuery == "query-by-url-pattern")
		runQueryByUrlPattern();
};

var checkIfBookmarkExists = function(newURL) {
	var result = null;
	
	ajaxJsonRequest(
		JSPVARS.dbmsBookmarkFindByUrl + "?url=" + newURL,
		function(data) {
			if (data.error !== undefined)
				result = data.error;
			else
				result = (data.id !== 0);
		},
		false
	);
	
	return result;
};

var doInsertBookmark = function(newURL) {
	var result = null;
	
	ajaxJsonRequest(
		JSPVARS.dbmsBookmarkInsert + "?url=" + encodeURIComponent(newURL),
		function(data) {
			if (data.error !== undefined)
				result = data.error;
			else
				result = data.id;
		},
		false
	);
	
	return result;
};

var doUpdateBookmark = function(id, updateURL) {
	var result = null;
	
	ajaxJsonRequest(
		JSPVARS.dbmsBookmarkUpdate + "?id=" + id + "&url=" + encodeURIComponent(updateURL),
		function(data) {
			if (data.error !== undefined)
				result = data.error;
			else
				result = data.id;
		},
		false
	);
	
	return result;
};

var doBookmarkDelete = function(bookmarkId) {
	ajaxJsonRequest(
		JSPVARS.dbmsBookmarkDelete + "?id=" + bookmarkId,
		function(data) {
			if (data.error !== undefined)
				result = data.error;
			else
				result = data.id;
		},
		false
	);
};

var bookmarkInsert = function() {
	var newURL = $("#new-url-text").val();

	var checkResult = checkIfBookmarkExists(newURL);
	
	if (typeof checkResult === "string") {
		alert("Database error:" + check);
	} else if (checkResult === true) {
		$("#url-already-exists").dialog({
			modal: true,
			buttons: { Ok: function() { $(this).dialog("close"); } }
		});		
	} else {
		doInsertBookmark(newURL);
		rerunLastQuery(); 
	}
};

var bookmarkUpdate = function() {
	var bid = getBookmarkId();
	var updateURL = $("#update-url-text").val();

	var checkResult = checkIfBookmarkExists(updateURL);
	
	if (typeof checkResult === "string") {
		alert("Database error:" + check);
	} else if (checkResult === true) {
		$("#url-already-exists").dialog({
			modal: true,
			buttons: { Ok: function() { $(this).dialog("close"); } }
		});		
	} else {
		doUpdateBookmark(bid, updateURL);
		rerunLastQuery();
	}
};

var bookmarkDelete = function() {
	var bid = getBookmarkId();
	
	$("#delete-confirm").dialog({
		modal : true,
		buttons : {
			"Delete": function() {
		 		$(this).dialog("close");
		 		doBookmarkDelete(bid);
		 		rerunLastQuery();
			},
		 	Cancel : function() {
		 		$(this).dialog("close");
		 	}
		}
	});	
};

var bookmarkEditTags = function() {
	var rowId = $("#grid").getGridParam('selrow');
	var rowData = $("#grid").getRowData(rowId);

	window.open(JSPVARS.jspBookmarkEditTags + "?id=" + rowData.ID);
};

var bookmarkOpen = function() {
	var rowId = $("#grid").getGridParam('selrow');
	var rowData = $("#grid").getRowData(rowId);

	window.open(rowData.URL);
};

var refreshEnables = function() {
	if (currentSelectedRowId === -1) {
		$("#btnUpdate").button("disable");
		$("#btnDelete").button("disable");
		$("#btnEditTags").button("disable");
		$("#btnOpenBookmark").button("disable");
	} else {
		$("#btnUpdate").button("enable");
		$("#btnDelete").button("enable");
		$("#btnEditTags").button("enable");
		$("#btnOpenBookmark").button("enable");
	}
};

var onChangeGridSelection = function(id) {
	currentSelectedRowId = id;
	
	var url = $("#grid").getCell(id, "URL");
	$("#update-url-text").val(url);

	refreshEnables();
};

$(document).ready(function(){
	var grid = $("#grid");
	
	grid.jqGrid({
		datatype: "array",
		colModel : [
			{ name : "ID", sortable: false, key : true, hidden: true },
			{ name : "Domain", editable: false },
			{ name : "URL", editable: false }
		],
		pager : '#pager',
		multiselect: false,
		onSelectRow : onChangeGridSelection 
	});
	
	grid.navGrid('#pager', {
		edit : false,
		add : false,
		del : false,
		refresh : false,
		search : false
	});

	grid.setGridWidth(688);

	$("#tabs-queries").tabs();
	$("#tabs-actions").tabs();

	$("#btnQueryUntagged").button();
	$("#btnQueryUntagged").on("click", runQueryUntagged);
	$("#btnQueryByTag").button();
	$("#btnQueryByTag").on("click", runQueryByTag);
	$("#btnQueryByUrlPattern").button();
	$("#btnQueryByUrlPattern").on("click", runQueryByUrlPattern);
	$("#btnEditTags").button();
	$("#btnEditTags").on("click", bookmarkEditTags);
	$("#btnOpenBookmark").button();
	$("#btnOpenBookmark").on("click", bookmarkOpen);
	
	$("#btnInsert").button();
	$("#btnInsert").on("click", bookmarkInsert);
	$("#btnUpdate").button();
	$("#btnUpdate").on("click", bookmarkUpdate);
	$("#btnDelete").button();
	$("#btnDelete").on("click", bookmarkDelete);

	refreshEnables();
});
