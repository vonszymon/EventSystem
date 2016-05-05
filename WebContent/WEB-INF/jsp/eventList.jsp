<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
	<title>List of events</title>
	<meta charset="ISO-8859-2">
<style>
body {
	background-color: #F5F5F5; /*tlo strony*/
	color: black;
	text-align: center;
}

#main_div {
	border-radius: 5px;
	width: 800px;
	display: inline-block;
}

#event {
	border-style: double;
	border-width: 5px;
	border-color: #070F3A;  /*tlo obramowki*/
	border-radius: 10px;
	background-color: #B1B7D7; /*tlo eventu*/
	margin: 10px 10px 10px 10px;
	padding: 10px 30px 10px 30px;
}

#event_description {
	padding: 0 0 0 0;
	margin: 0 0 0 0;
	text-align: justify;
	text-indent: 1cm;
	font-size: 17px;
}

#event_title {
	font-size: 40px;
	text-align: center;
	padding: 0 0 0 0;
	margin: 0 0 0 0;
	font-variant: small-caps;
}

a:link {
	color: #0E2806;
	text-decoration: none;
}

a:visited {
	color: #2F4D25;
	text-decoration: none;
}

a:hover {
	color: #6DC452;
	text-decoration: none;
}

#bottom_menu {
	text-align: right;
}

#date {
	color: white;
	text-align: left;
	display: inline-block;
}

#extra_info {
	margin-top: 10px;
	color: #545994;
	text-align: right;
}

</style>
</head>
<body>
<jsp:useBean id="pagedListHolder" scope="request" 
   type="org.springframework.beans.support.PagedListHolder"/>
<%-- // create link for pages, "~" will be replaced 
   later on with the proper page number --%>
<c:url value="/getList" var="pagedLink">
<c:param name="p" value="~"/>
</c:url>
<% String current_color = "#787878"; %>
<% String status = "NOT STARTED"; %>
<div id="main_div">
<%@include  file="menu.html" %>
	<!-- Event -->	
	<c:forEach var="event" items="${pagedListHolder.pageList}">
		<c:choose>
   			<c:when test="${event.active == true}"><% current_color = "#B1B7D7"; %><% status = "NOT STARTED"; %></c:when>
   			<c:otherwise><% current_color = "#787878"; %><% status = "COMPLETED"; %></c:otherwise>
		</c:choose> 
		<div id="event" style="background-color: <%= current_color%>">
			<p id="status" align="left">Status: <%= status%></p>
			<h1 id="event_title">${event.title}</h1>
			<p id="event_description">${event.content}</p>
			<p id="start_date" align="left">Start Date: <fmt:formatDate value="${event.startDate}" type="date" pattern="yyyy-MM-dd"/></p>
			<p id="extra_info">
				Comments: ${event.numOfComments}<br/>
			</p>
			<div id="bottom_menu">
				<a href="eventDetails/${event.eventID}">Read more</a>
			</div>
		</div>
	</c:forEach>
	<tg:paging pagedListHolder="${pagedListHolder}" pagedLink="${pagedLink}"/>
</div>

</body>
</html>