<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery-ui.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/ui.jqgrid.css'/>">
<script type="text/javascript" src="<c:url value='/js/jquery-1.10.2.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.jqGrid.src.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/js/grid.locale-en.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/common.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/bookmarktags-editor.js'/>"></script>

<script type="text/javascript">
var JSPVARS = {
	currentBookmarkId : ${ param.id },
	dbmsBookmarkLoad : "<c:url value='/dbms/bookmarks/load.do'/>",
	dbmsTagListUsedByBookmark : "<c:url value='/dbms/tags/list-usedbybookmark.do'/>",
	dbmsTagListUnusedByBookmark : "<c:url value='/dbms/tags/list-unusedbybookmark.do'/>",
	dbmsBookmarkTagAdd : "<c:url value='/dbms/bookmarktags/add.do'/>",
	dbmsBookmarkTagDelete : "<c:url value='/dbms/bookmarktags/delete.do'/>",
	dbmsBookmarkTagDeleteAll : "<c:url value='/dbms/bookmarktags/delete-all.do'/>"
};
</script>

<body>

<h2>Bookmark Tags Editor</h2>

<p id="currentURL" style="font-size:14pt; font-weight:bold;"></p>

<div style="display:table;">
	<div style="display:table-row;">
		<div style="display:table-cell;">
			<div id="unusedtags-list" style="text-align:center;">
				Available tags
				<div id="jqgrid-unusedtags">
					<table id="grid-unusedtags">
					</table>
					<div id="pager-unusedtags"></div>
				</div>
			</div>
		</div>
		<div style="display:table-cell; width:100px;">
			<div style="text-align:center;">
				<br><br>
				<button id="btnAdd">&gt;</button><br><br>
				<button id="btnRemove">&lt;</button><br><br>
				<button id="btnRemoveAll">&lt;&lt;</button>
			</div>
		</div>
		<div style="display:table-cell;">
			<div id="usedtags-list" style="text-align:center;">
				Used tags
				<div id="jqgrid-usedtags">
					<table id="grid-usedtags">
					</table>
					<div id="pager-usedtags"></div>
				</div>
			</div>
		</div>
	</div>
</div>
