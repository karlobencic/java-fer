<%@ page import="java.util.Random" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body bgcolor="${selectedBgColor}">
<%
Random rand = new Random();
String[] colors = {"RED","BLUE","WHITE","BLACK","PINK"};
%>
	<p><font color="<%= colors[rand.nextInt(Integer.SIZE - 1)%5] %>">
		IF YOU ARE REAL MLG PRO AND YOU LIKE MLG MONTAGE PARODIES, THEN YOU SHOULD LIKE THIS SITE, CAUSE IT'S MADE BY FANS FOR FANS AND FOR THE GOOD OF ALL MANKID. 
		THIS IS A 100% CONFIRMED PLACE WHERE YOU CAN PLAY MLG FLAPPY BIRD 420 ONLINE AND GET AT LEAST 420 SCORE IF U EVER QUICKSCOPE m8.
		THIS IS THE TOP RATED GAME EVER AND FIRST MLG GAME EVER CONFIRMED BY MLG PROS WHICH MAKES IT THE GAME OF THE CENTURY. 
		AS AN MLG PRO YOU CAN 360 NOSCOPE AND 420 BLAZE IT, EAT DORITOS, DRINK MTN DEW AND SMOKE WEED EVERY DAY.
		EVERY 60 SECONDS IN THIS GAME, A MINUTE PASSES.
		<br>
		<a href="https://yoloswagclub.com" target="_blank"><strong>YOLO SWAG CLUB</strong></a>
	</font>
	</p>
</body>
</html>