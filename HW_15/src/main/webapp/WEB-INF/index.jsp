<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
<h2>Blog</h2>
	<c:choose>
		<c:when test="${empty sessionScope['current.user.id']}">
			${sessionScope.error}
			<c:remove var="error" scope="session" />
			<form action="login" method="post">
				Nickname: <input type="text" name="nick"> <br>
				Password: <input type="password" name="pass"> <br>
				<button type="submit">Login</button>
			</form>
			<a href="register">Registriraj se</a><br>
		</c:when>
		<c:otherwise>
			Trenutno si logiran kao ${sessionScope["current.user.nick"]} a tvoj ime je
			${sessionScope["current.user.fn"]} ${sessionScope["current.user.ln"]}
			<br>
			<a href="logout">Logout</a>
		</c:otherwise>
	</c:choose>
	<p> Registered Users
	<c:forEach items="${registeredUsers}" var="user">
		<br><a href="author/${user.nick}">${user.nick}</a>
	</c:forEach>
</body>
</html>