<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
	margin-bottom: 20px;
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

#main_comments {
	margin-top: 20px;
	margin-bottom: 20px;
	text-align: left;
}

#comment {
	border-style: none;
	padding: 5px 5px 5px 5px;
	border-color: white;
	background-color: #636DA0;
	border-width: 1px;
	border-radius: 5px;
	margin-top: 5px;
	color: white;
	font: menu;
}

#around_commenter {

}

#commenter_name {
	color: #FFEB3B;
}

#comment_content {
	margin: 8px;
	color: white;
}

#comment_desc_font {
	font-size: 13px;
}	

#feeds {
	margin-top: 20px;
	margin-bottom: 40px;
}	

#feed {
	font-size: 13px;
}

#logo_img {
	width: 50px;
	height: 50px;
}

#feed_table {
	text-align: justify;
}

#feed_text_cell {
	border-bottom: 1px;
	border-bottom-style: dotted;
}

#feed_time {
	font: menu;
	font-size: 15px;
	color: white;
}

</style>
	
</head>
<body>
<% String current_color = "#787878"; %>
<% String status = "NOT STARTED"; %>
<c:choose>
   	<c:when test="${eventDetails.event.active == true}"><% current_color = "#B1B7D7"; %><% status = "NOT STARTED"; %></c:when>
   	<c:otherwise><% current_color = "#787878"; %><% status = "COMPLETED"; %></c:otherwise>
</c:choose> 
<div id="main_div">
<!-- User -->
<%@include  file="menu.html" %>
<!-- Event -->
<div id="event" style="background-color: <%= current_color%>">


<a href="${contextPath}/getList" style="text-align: left; font=size: 5px;">Return to list</a>
<p id="status" align="left">Status: <%= status%></p>

	<h1 id="event_title">${eventDetails.event.title}</h1>
	<p id="event_description">
	${eventDetails.event.content}
	</p>
	<p id="start_date" align="left">Start Date: <fmt:formatDate value="${eventDetails.event.startDate}" type="date" pattern="yyyy-MM-dd"/></p>
	<c:if test="${eventDetails.isEditingEvent == false}">
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<a href="${contextPath}/eventDetails/editEvent/${eventDetails.event.eventID}">Edit event</a>
	</sec:authorize>
	<div id="feeds">
		<table id="feed_table">
			<c:choose>
			<c:when test="${not empty eventDetails.event.tweets}">
				<c:forEach var="tweet" items="${eventDetails.event.tweets}">	
			<tr>
				<td><img src="http://oi61.tinypic.com/24n2psj.jpg" id="logo_img"></td>
				<td id="feed_text_cell">
					<font id="feed_time"><fmt:formatDate value="${tweet.date}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></font>
					<br/>
					${tweet.content}
				</td>
			</tr>

				</c:forEach>
			</c:when>
			<c:otherwise>
			<div id="comment_content">No tweets.</div>
			</c:otherwise>
		</c:choose>
		
		
		

		</table>
	</div>


	<div id="main_comments">
		<c:choose>
			<c:when test="${not empty eventDetails.comments}">
				<c:forEach var="commentDetails" items="${eventDetails.comments}">	
					<div id="comment">
						<div id="top_comment"><font id="comment_desc_font"><font id="around_commenter"><fmt:formatDate value="${commentDetails.publishDate}" type="date" pattern="yyyy-MM-dd"/> : User </font><font id="commenter_name">${commentDetails.author}</font><font id="around_commenter"> say:</font></font></div>
						<div id="comment_content">${commentDetails.content}</div>
						<c:if test="${commentDetails.author eq pageContext.request.userPrincipal.name or pageContext.request.isUserInRole('ROLE_ADMIN')}">
						<a href="${contextPath}/eventDetails/${eventDetails.event.eventID}/edit/${commentDetails.commentaryID}">Edit</a>
						<a href="${contextPath}/eventDetails/${eventDetails.event.eventID}/remove/${commentDetails.commentaryID}">Remove</a></c:if>
						<c:if test="${eventDetails.editing_comment == commentDetails.commentaryID}">
						<form:form method="post" action="#" modelAttribute="commentary">
							<font style="font-size: 18px;">Leave comment:</font><br/>
							<form:textarea style="width: 500px; height: 100px; margin: 5px;" path="content"></form:textarea><br/>
							<form:input type="hidden" path="author" value="${pageContext.request.userPrincipal.name}"></form:input>
							<input type="submit" value="OK" />
						</form:form>
						
						</c:if>
						
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
			<div id="comment_content">No comments.</div>
			</c:otherwise>
		</c:choose>
	</div>
	<c:if test="${eventDetails.isEditing == false}">
	<form:form method="post" modelAttribute="commentary">
		<font style="font-size: 18px;">Leave comment:</font><br/>
		<form:textarea style="width: 500px; height: 100px; margin: 5px;" path="content"></form:textarea><br/>
		<form:input type="hidden" path="author" value="${pageContext.request.userPrincipal.name}"></form:input>
		<input type="submit" value="OK" />
	</form:form>
	</c:if>
	</c:if>
	<c:if test="${eventDetails.isEditingEvent == true}">
		<form:form method="post" action="#" modelAttribute="event" id="regForm">
			<table>
				<tr>
					<td>Title :</td>
					<td><form:input path="title"/></td>
				</tr>
				<tr>
					<td>Content :</td>
					<td><form:textarea name="content" path="content" form="regForm" rows="8" cols="50"></form:textarea></td>
				</tr>
				<tr>
					<td>Date :</td>
					<td><form:input type="date" path="startDate"/></td>
				</tr>
				<tr>
					<td>Hashtag :</td>
					<td><form:input path="hashtag"/></td>
				</tr>
				<tr>
				<td> </td>  
     				<td><input type="submit" value="Save" /></td>  
    			</tr> 
			</table>
		</form:form>
	</c:if>
</div>
</div>

</body>
</html>