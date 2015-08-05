<%--
    Include the metadata of the page for social sharing purpose
--%>
<%@ page session="false" %>
<%@include file="/apps/calix/global.jsp" %>

<%-- Include metadata here --%>
<%
    String title = pageProperties.get("jcr:title", "Calix");
    String description = pageProperties.get("jcr:description", title);

    String imagePath = inVM.getInherited("logo", "/etc/designs/calix/clientlibs/img/calixLogo.png");

    String url = request.getRequestURL().toString();
    String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath();
    String fullImagePath = baseURL + imagePath;

    pageContext.setAttribute("postTitle", title);
    pageContext.setAttribute("postDesc", description);
    pageContext.setAttribute("postImgPath", fullImagePath);
%>

<!-- for Facebook -->
<meta property="og:title" content="${fn:escapeXml(postTitle)}"/>
<meta property="og:type" content="article"/>
<meta property="og:url" content="${slingRequest.requestURL}"/>
<meta property="og:image" content="${postImgPath}"/>
<meta property="og:description" content="${fn:escapeXml(postDesc)}"/>
<meta property="og:site_name" content="Calix"/>

<!-- for Twitter -->
<meta name="twitter:card" content="summary"/>
<meta name="twitter:title" content="${fn:escapeXml(postTitle)}"/>
<meta name="twitter:url" content="${slingRequest.requestURL}"/>
<meta name="twitter:image" content="${postImgPath}"/>
<meta name="twitter:description" content="${fn:escapeXml(postDesc)}"/>

<!-- for Google+ -->
<meta itemprop="name" content="${fn:escapeXml(postTitle)}">
<meta itemprop="description" content="${fn:escapeXml(postDesc)}">
<meta itemprop="image" content="${postImgPath}">





