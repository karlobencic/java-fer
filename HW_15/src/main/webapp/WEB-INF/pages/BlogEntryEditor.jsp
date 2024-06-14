<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setAttribute("ante", "ante");
%>
<html>
  <body>
  <c:choose>
	  <c:when test="${not empty sessionScope['current.user.nick']}">
			<c:choose>
				<c:when test="${empty entry}">
					<form method="post" action="/blog/servleti/createNew">
						<input type="hidden" name="creator" value="${sessionScope['current.user.nick']}">
 						Title: <input name="title"> <br>
						Message: <textarea name="text"></textarea>
						<br><input type="submit">
					</form>
				</c:when>
				<c:otherwise>
					<form method="post" action="/blog/servleti/editExisting">
						<input type="hidden" name="id" value="${entry.id}">
						Title: <input name="title" value="${entry.title}"> <br>
						Message: <textarea name="text">${entry.text}</textarea>
						<br><input type="submit">
					</form>
				</c:otherwise>
			</c:choose>
			<br>
			<a href="javascript:javascript:history.go(-1)">Go back</a>
			<br>
			<a href="/blog/">Go to Index</a>
		</c:when>
		<c:otherwise>
			<a href="/blog/">Go back to start and login there or register</a>		
		</c:otherwise>
	</c:choose>
  </body>
</html>