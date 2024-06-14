<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body bgcolor="${selectedBgColor}">
	
	<table border="1">
	<tr><td>x</td><td>sin(x)</td><td>cos(x)</td></tr>
	<c:forEach var="e" items="${trig}">
	<tr><td>${e.key}</td><td>${e.value[0]}</td><td> ${e.value[1]}</td></tr>
	</c:forEach>
	</table>
</body>
</html>