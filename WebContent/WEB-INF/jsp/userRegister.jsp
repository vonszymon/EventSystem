<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>TAI | Spring </title>
<style>
body {
	font-size: 20px;
	color: teal;
	font-family: Calibri;
}

td {
	font-size: 15px;
	color: black;
	width: 100px;
	height: 22px;
	text-align: left;
}

.heading {
	font-size: 18px;
	color: white;
	font: bold;
	background-color: orange;
	border: thick;
}
</style>
</head>
<body>
	<div align="right">
		<%@include  file="menu.html" %>
	</div>
	<center>
		<br /> <br /> <br /> <b>TAI | User Registration Form </b> <br />
		<br />
		<div>
			<form:form method="post" action="insertUser" modelAttribute="user">
				<table>
					<tr>
						<td>Username :</td>
						<td><form:input path="username" /></td>
					</tr>
					<tr>
						<td>Password :</td>
						<td><form:input path="password" /></td>
					</tr>
					<tr>
					<td> </td>  
      				<td><input type="submit" value="Register" /></td>  
     				</tr>  
					<tr>
						<td colspan="2"><a href="login">Click Here to Sign In</a></td>
					</tr>
				</table>
			</form:form>
		</div>
	</center>
</body>
</html>