<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%!private void writeDate(javax.servlet.jsp.JspWriter out, long appStart) throws java.io.IOException {
		long delta = System.currentTimeMillis() - appStart;
		long sec = (delta / 1000) % 60;
		long min = (delta / (1000 * 60)) % 60;
		long hrs = (delta / (1000 * 60 * 60)) % 24;
		long days = (delta / (1000 * 60 * 60 * 24)) % 365;
		out.print(days + " days " + hrs + " hours " + min + " minutes " + sec + " seconds and " + (delta % 1000)
				+ " miliseconds ");
	}%>
<html>
<body bgcolor="${selectedBgColor}">
	<h1>
		Your application has been running for
		<%
		long time = Long.parseLong(String.valueOf(application.getAttribute("applicationStart")));
		writeDate(out, time);
		%>
	</h1>
</body>
</html>