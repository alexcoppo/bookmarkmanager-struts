var currentSelectedRowId = -1;

var getTagId = function() {
	var rowId = $("#grid").getGridParam('selrow');
	var rowData = $("#grid").getRowData(rowId);
	
	return rowData.ID;
};

var gridReload = function() {
	var tags = null;
	
	ajaxJsonRequest(
		JSPVARS.dbmsTagsListAll,
		function(data) {
			tags = data.items;
		},
		false
	);

	var grid = $("#grid");
	
	grid.clearGridData();
	
	for (var index = 0; index < tags.length; index++) {
		grid.addRowData(
			index,
			{
				"ID" : tags[index].id,
				"TAG" : tags[index].tag
			}
		);
	}
	
	currentSelectedRowId = -1;
	refreshEnables();
};

var checkIfTagExists = function(newTag) {
	var result;
	
	ajaxJsonRequest(
		JSPVARS.dbmsTagFind + "?tag=" + encodeURIComponent(newTag),
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

var doInsertTag = function(newTag) {
	var result;
	
	ajaxJsonRequest(
		JSPVARS.dbmsTagInsert + "?tag=" + encodeURIComponent(newTag),
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

var doRenameTag = function(id, renameTag) {
	var result;
	
	ajaxJsonRequest(
		JSPVARS.dbmsTagRename + "?id=" + id + "&tag=" + encodeURIComponent(renameTag),
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

var doTagDelete = function(tagId) {
	ajaxJsonRequest(
		JSPVARS.dbmsTagDelete + "?id=" + tagId,
		function(data) {
			if (data.error !== undefined)
				result = data.error;
			else
				result = data.id;
		},
		false
	);
};

var tagInsert = function() {
	var newTag = $("#new-tag-text").val();

	var checkResult = checkIfTagExists(newTag);
	
	if (typeof checkResult === "string") {
		alert("Database error:" + check);
	} else if (checkResult === true) {
		$("#tag-already-exists").dialog({
			modal: true,
			buttons: { Ok: function() { $(this).dialog("close"); } }
		});		
	} else {
		doInsertTag(newTag);
		gridReload();
	}
};

var tagRename = function() {
	var tid = getTagId();
	var renameTag = $("#rename-tag-text").val();

	var checkResult = checkIfTagExists(renameTag);
	
	if (typeof checkResult === "string") {
		alert("Database error:" + check);
	} else if (checkResult === true) {
		$("#tag-already-exists").dialog({
			modal: true,
			buttons: { Ok: function() { $(this).dialog("close"); } }
		});		
	} else {
		doRenameTag(tid, renameTag);
		gridReload();
	}
};

var tagDelete = function() {
	var tid = getTagId();
	
	$("#delete-confirm").dialog({
		modal : true,
		buttons : {
			"Delete": function() {
		 		$(this).dialog("close");
		 		doTagDelete(tid);
		 		gridReload();
			},
		 	Cancel : function() {
		 		$(this).dialog("close");
		 	}
		}
	});	
};

var refreshEnables = function() {
	if (currentSelectedRowId === -1) {
		$("#btnRename").button("disable");
		$("#btnDelete").button("disable");
	} else {
		$("#btnRename").button("enable");
		$("#btnDelete").button("enable");
	}
};

$(document).ready(function(){
	var grid = $("#grid");
	
	grid.jqGrid({
		datatype: "array",
		colModel : [
			{ name : "ID", sortable: false, key : true, hidden: true },
			{ name : "TAG", editable: false }
		],
		pager : '#pager',
		multiselect: false,
		onSelectRow : function(id) {
			currentSelectedRowId = id;
			console.debug("id:" + id);
			refreshEnables();
		}
	});
	
	grid.navGrid('#pager', {
		edit : false,
		add : false,
		del : false,
		refresh : false,
		search : false
	});

	grid.setGridWidth(608);

	$("#tabs").tabs();
	
	$("#btnInsert").button();
	$("#btnInsert").on("click", tagInsert);
	$("#btnRename").button();
	$("#btnRename").on("click", tagRename);
	$("#btnDelete").button();
	$("#btnDelete").on("click", tagDelete);

	refreshEnables();
	
	gridReload();
});
