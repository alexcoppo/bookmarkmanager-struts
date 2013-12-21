<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
var JSPVARS = {
	dbmsBookmarksListUntagged : "<c:url value='/dbms/bookmarks/list-untagged.do'/>",		
	dbmsBookmarksListByTag : "<c:url value='/dbms/bookmarks/list-bytag.do'/>",		
	dbmsBookmarksListByUrlPattern : "<c:url value='/dbms/bookmarks/list-byurlpattern.do'/>",		
	dbmsBookmarkFindByUrl : "<c:url value='/dbms/bookmarks/findbyurl.do'/>",
	dbmsBookmarkInsert : "<c:url value='/dbms/bookmarks/insert.do'/>",
	dbmsBookmarkUpdate : "<c:url value='/dbms/bookmarks/update.do'/>",
	dbmsBookmarkDelete : "<c:url value='/dbms/bookmarks/delete.do'/>",
	jspBookmarkEditTags : "<c:url value='/jsp/bookmarktags-editor.jsp'/>"
};
</script>

<body>

<h2>Bookmarks Editor</h2>

<div id="tabs-queries">
	<ul>
		<li><a href="#tabs-query-untagged">Untagged</a></li>
		<li><a href="#tabs-query-bytag">By tag</a></li>
		<li><a href="#tabs-query-byurlpattern">URL pattern</a></li>
		<li><a href="#tabs-open-bookmark">Open</a></li>
		<li><a href="#tabs-edit-tags">Edit tags</a></li>
	</ul>
	<div id="tabs-query-untagged">
		<div style="text-align:center;">
			<button id="btnQueryUntagged">Execute</button>
		</div>
	</div>
	<div id="tabs-query-bytag">
		<label for="query-tag-text">Tag</label> 
		<input type="text" id="query-tag-text" value="">
		<button id="btnQueryByTag">Execute</button>
	</div>
	<div id="tabs-query-byurlpattern">
		<label for="query-url-pattern">Pattern</label> 
		<input type="text" id="query-url-pattern" value="">
		<button id="btnQueryByUrlPattern">Execute</button>
	</div>
	<div id="tabs-edit-tags">
		<div style="text-align:center;">
			<button id="btnEditTags">Edit</button>
		</div>
	</div>
	<div id="tabs-open-bookmark">
		<div style="text-align:center;">
			<button id="btnOpenBookmark">Open</button>
		</div>
	</div>
</div>

<div class="vertical-spacer"></div>

<div id="bookmarks-list">
	<div id="jqgrid">
		<table id="grid">
		</table>
		<div id="pager"></div>
	</div>
</div>

<div class="vertical-spacer"></div>

<div id="tabs-actions" style="width:680px;">
	<ul>
		<li><a href="#tabs-insert">Insert new</a></li>
		<li><a href="#tabs-update">Update current</a></li>
		<li><a href="#tabs-delete">Delete current</a></li>
	</ul>
	<div id="tabs-insert">
		<label for="new-url-text">New URL</label> 
		<input type="text" id="new-url-text" value="">
		<button id="btnInsert">Insert</button>
	</div>
	<div id="tabs-update">
		<label for="update-url-text">New URL</label> 
		<input type="text" id="update-url-text" value="">
		<button id="btnUpdate">Update</button>
	</div>
	<div id="tabs-delete">
		<div style="text-align:center;">
			<button id="btnDelete">Delete</button>
		</div>
	</div>
</div>

<div id="url-already-exists" style="display:none;">
	A bookmark with the given URL already exists.
</div>

<div id="delete-confirm" style="display:none;">
	Confirm deleting selected bookmark? action cannot by undone.
</div>
