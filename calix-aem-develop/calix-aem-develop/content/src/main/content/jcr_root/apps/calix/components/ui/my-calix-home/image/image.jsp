<%@page session="false" %>
<%@include file="/apps/calix/global.jsp" %>
<c:if test="${isEdit}">
	<br/>[Edit My Calix Logo here]
</c:if>
<%
	String path = properties.get("image", "/content/dam/calix/images/My%20Calix/myCalixLogo.png");
%>
<img class="myCalixLogo" src="<%=path%>" width="100%" height="100%" alt="My Calix Logo" />