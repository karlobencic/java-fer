<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="band" class="hr.fer.zemris.java.servlets.Band" scope="request" />

<html>
<body bgcolor="${selectedBgColor}">
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na
		link kako biste glasali!</p>
	<ol>
	<c:forEach var="band" items="${bands}">
		<li><a href="glasanje-glasaj?id=${band.key}">${band.value.name}</a></li>
	</c:forEach>
	</ol>
</body>
</html>