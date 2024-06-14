<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<c:choose>
		<c:when test="${not empty blogEntries}">
			<c:forEach items="${blogEntries}" var="blogEntry">
				<br><a href="${nick}/${blogEntry.id}">${blogEntry.title}</a>
			</c:forEach>
		</c:when>
		<c:otherwise>
			There are no entries for this user.
		</c:otherwise>
	</c:choose>
	<c:if test="${sessionScope['current.user.nick'] == nick}">
		<br><a href="${nick}/new">Add a new blog entry</a>
	</c:if>
	<p>
	<a href="javascript:javascript:history.go(-1)">Go back</a>
	<br>
	<a href="/blog/">Go to Index</a>
</body>
</html>