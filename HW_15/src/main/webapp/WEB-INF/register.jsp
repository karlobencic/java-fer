<html>
<head></head>
<body>
	${sessionScope["registrationError"]}
	<form action="register" method="post">
		First name: <input type="text" name="firstname"> <br>
		Last name: <input type="text" name="lastname"> <br>
		Email: <input type="email" name="email"> <br>
		Nickname: <input type="text" name="nick"> <br>
		Password: <input type="password" name="pass"> <br>
		<button type="submit">Register</button>
	</form>	
	<p>
	<a href="/blog/">Go to Index</a>
</body>
</html>