<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<h1>${currPoll.title}</h1>
	<p>${currPoll.message}</p>
	<ol>
	<c:forEach var="opcija" items="${opcije}">
		<li><a href="glasaj?id=${opcija.ID}">${opcija.optionTitle}</a></li>
	</c:forEach>
	</ol>
</body>
</html>