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

<div id="main_div">
<div style="font: menu; text-align: right; margin-right: 20px;">
<font style="font-size: 10px; margin-right: 10px;">Logged as:</font><b>wacek_123</b>
<a href="dsa" style="color:red; font-size: 10px; margin-left: 10px;">(Logout)</a>
</div>
<!-- Event -->
<div id="event">
	<form>
		Title:<br/>
		<input type="text" style="width: 400px;"></input><br/>
		Description:<br/>
		<textarea style="width: 400px; height: 200px;"></textarea><br/>
		Feed source:<br/>
		<input type="text" style="width: 400px;"></input><br/>
		<input type="submit" value="Add event" style="margin-top: 20px;"></input>
	</form>
</div>

</body>
</html>
