<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body bgcolor="${pickedBgCol}">
	<h2>Hello World!</h2>
	<a href="colors">Colors</a>
	<br>
	<a href="trigonometric?a=0&b=90">Trigonometric</a>
	<br>	
	<form action="trigonometric" method="GET">
		Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
		Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
	</form>
	<br>
	<a href="funny">Funny stories</a>
	<br>
	<a href="showreport">OS Report</a>
	<br>
	<a href="powers?a=1&b=100&n=3">Powers</a>
	<br>
	<a href="appinfo">AppInfo</a>
	<br>
	<a href="glasanje">Glasanje bendovi</a>
</body>
</html>