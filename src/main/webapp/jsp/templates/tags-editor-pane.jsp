<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
var JSPVARS = {
	dbmsTagsListAll : "<c:url value='/dbms/tags/list-all.do'/>",		
	dbmsTagFind : "<c:url value='/dbms/tags/find.do'/>",
	dbmsTagInsert : "<c:url value='/dbms/tags/insert.do'/>",
	dbmsTagRename : "<c:url value='/dbms/tags/rename.do'/>",
	dbmsTagDelete : "<c:url value='/dbms/tags/delete.do'/>"
};
</script>

<body>

<h2>Tags Editor</h2>

<div id="tags-list">
	<div id="jqgrid">
		<table id="grid">
		</table>
		<div id="pager"></div>
	</div>
</div>

<div class="vertical-spacer"></div>

<div id="tabs">
	<ul>
		<li><a href="#tabs-insert">Insert new</a></li>
		<li><a href="#tabs-rename">Rename current</a></li>
		<li><a href="#tabs-delete">Delete current</a></li>
	</ul>
	<div id="tabs-insert">
		<label for="new-tag-text">New name</label> 
		<input type="text" id="new-tag-text" value="">
		<button id="btnInsert">Insert</button>
	</div>
	<div id="tabs-rename">
		<label for="rename-tag-text">New name</label> 
		<input type="text" id="rename-tag-text" value="">
		<button id="btnRename">Rename</button>
	</div>
	<div id="tabs-delete">
		<div style="text-align:center;">
			<button id="btnDelete">Delete</button>
		</div>
	</div>
</div>

<div id="tag-already-exists" style="display:none;">
	The tag with this name already exists.
</div>

<div id="delete-confirm" style="display:none;">
	Confirm deleting selected tag? action cannot by undone.
</div>
