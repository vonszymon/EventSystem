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
		<br /> <br /> <br /> <b>TAI | Registration Form </b> <br />
		<br />
		<div>
			<form:form method="post" action="insertEvent" modelAttribute="event" id="regForm">
				<table>
					<tr>
						<td>Title :</td>
						<td><form:input path="title" /></td>
					</tr>
					<tr>
						<td>Content :</td>
						<td><textarea name="content" path="content" form="regForm" rows="8" cols="50"></textarea></td>
					</tr>
					<tr>
						<td>Date :</td>
						<td><form:input type="date" path="startDate" /></td>
					</tr>
					<tr>
						<td>Hashtag :</td>
						<td><form:input path="hashtag" /></td>
					</tr>
					<tr>
					<td> </td>  
      				<td><input type="submit" value="Save" /></td>  
     				</tr>  
					<tr>
						<td colspan="2"><a href="getList">Click Here to See Event List</a></td>
					</tr>
				</table>
			</form:form>
		</div>
	</center>
</body>
</html>