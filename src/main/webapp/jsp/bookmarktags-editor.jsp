<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>URLManager Struts</title>
<%@ include file="includes/common-header.jspf" %>
<script type="text/javascript" src="<c:url value='/js/bookmarktags-editor.js'/>"></script>
</head>
<body>

<tiles:insert page="/jsp/templates/page-template.jsp">
	<tiles:put name="header" content="/jsp/templates/topbar-pane.jsp"/>
	<tiles:put name="menucolumn" content="/jsp/templates/menubar-pane.jsp"/>
	<tiles:put name="body" content="/jsp/templates/bookmarktags-editor-pane.jsp"/>
</tiles:insert>

</body>
</html>