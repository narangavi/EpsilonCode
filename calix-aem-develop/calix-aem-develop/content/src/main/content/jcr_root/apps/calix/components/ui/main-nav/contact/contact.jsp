<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>


<c:if test="${isEdit}">
</c:if>

<%
    String contact = (String) inVM.getInherited("contact","Contact");
    String contactLink = (String) inVM.getInherited("contactLink","#");
%>

 <a href="<%= contactLink %>.html"><%= contact %></a>