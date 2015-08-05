<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>

<c:if test="${isEdit}">
</c:if>

<%
    String tagline = (String) inVM.getInherited("tagline","");
%>

<%= tagline %>