<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>

  <c:choose>
    <c:when test="${blogEntry==null}">
      Nema unosa!
    </c:when>
    <c:otherwise>
      <h1><c:out value="${blogEntry.title}"/></h1>
      <p><c:out value="${blogEntry.text}"/></p>
      <c:if test="${blogEntry.creator.id == sessionScope['current.user.id']}">
		<a href="edit?eid=${blogEntry.id}">Edit this post</a>
      </c:if>
      <c:if test="${!blogEntry.comments.isEmpty()}">
      <ul>
      <c:forEach var="e" items="${blogEntry.comments}">
        <li><div style="font-weight: bold">[Korisnik=<c:out value="${e.usersEMail}"/>] <c:out value="${e.postedOn}"/></div><div style="padding-left: 10px;"><c:out value="${e.message}"/></div></li>
      </c:forEach>
      </ul>
      </c:if>
      <form action="/blog/servleti/comment" method="post">
      	<input type="hidden" name="eid" value="${blogEntry.id}">
      	<input type="hidden" name="nick" value="${blogEntry.creator.nick}">
      	Your email : <input type="email" name="email"> <br>
      	Comment: <textarea rows="10" cols="20" name="message"></textarea>
      	<br>
      	<input type="submit" value="Comment">
      </form>
    </c:otherwise>
  </c:choose>
  	<p>
	<a href="javascript:javascript:history.go(-1)">Go back</a>
	<br>
	<a href="/blog/">Go to Index</a>

  </body>
</html>