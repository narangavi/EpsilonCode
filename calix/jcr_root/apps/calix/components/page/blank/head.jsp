<%@page session="false" %>
<%--
  Copyright 1997-2010 Day Management AG
  Barfuesserplatz 6, 4001 Basel, Switzerland
  All Rights Reserved.

  This software is the confidential and proprietary information of
  Day Management AG, ("Confidential Information"). You shall not
  disclose such Confidential Information and shall use it only in
  accordance with the terms of the license agreement you entered into
  with Day.

  ==============================================================================

  Default head script.

  Draws the HTML head with some default content:
  - includes the WCML init script
  - includes the head libs script
  - includes the favicons
  - sets the HTML title
  - sets some meta data

  ==============================================================================

--%>
<%@include file="/libs/foundation/global.jsp" %>
<%
%>
<%@ page import="com.day.cq.commons.Doctype" %>
<%
    String xs = Doctype.isXHTML(request) ? "/" : "";
    String favIcon = "/etc/designs/calix/favicon.ico";
    if (resourceResolver.getResource(favIcon) == null) {
        favIcon = null;
    }
%>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"<%=xs%>>
    <meta name="keywords" content="<%= xssAPI.encodeForHTMLAttr(WCMUtils.getKeywords(currentPage, false)) %>"<%=xs%>>
    <meta name="description" content="<%= xssAPI.encodeForHTMLAttr(properties.get("jcr:description", "")) %>"<%=xs%>>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <cq:include script="headlibs.jsp"/>
    <cq:include script="/libs/wcm/core/components/init/init.jsp"/>
    <cq:include script="stats.jsp"/>
    <% if (favIcon != null) { %>
    <link rel="icon" type="image/vnd.microsoft.icon" href="<%= xssAPI.getValidHref(favIcon) %>"<%=xs%>>
    <link rel="shortcut icon" type="image/vnd.microsoft.icon" href="<%= xssAPI.getValidHref(favIcon) %>"<%=xs%>>
    <link rel="shortcut icon" type="image/x-icon" href="/etc/designs/calix/favicon.ico">
    <% } %>
    <title><%= currentPage.getTitle() == null ? xssAPI.encodeForHTML(currentPage.getName()) : xssAPI.encodeForHTML(currentPage.getTitle()) %>
    </title>

    <!-- styles -->
    <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900,400italic' rel='stylesheet'
          type='text/css'>
    <!-- <link rel="stylesheet" href="/etc/designs/calix/css/font-awesome.min.css"> -->
    <!--[if IE 7]>
    <link rel="stylesheet"
          href="https://aaa-re.googlecode.com/hg-history/dcb75023657c2ace238da3069055578c069e7a5f/public/fontello/css/fontello-ie7.css">
    <![endif]-->
	
	<script> 
	if (/*@cc_on!@*/false) { 
		var headHTML = document.getElementsByTagName('head')[0].innerHTML; 
			headHTML += ' <link rel="stylesheet" type="text/css" href="/etc/designs/calix/clientlibs/css/IE10.css" />'; 
			document.getElementsByTagName('head')[0].innerHTML = headHTML; 
	} 
	</script>
	
	<!--[if gt IE 8]>
	<link rel="stylesheet" type="text/css" href="/etc/designs/calix/clientlibs/css/IE9.css" />
	<![endif]-->
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.2/html5shiv.min.js"></script>
    <![endif]-->
    <cq:include script="/libs/wcm/mobile/components/simulator/simulator.jsp"/>

</head>
