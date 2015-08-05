<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>


<c:if test="${isEdit}">
</c:if>

<%
    String login = (String) inVM.getInherited("login","Login");
    String loginLink = (String) inVM.getInherited("loginLink","#");
    String register = (String) inVM.getInherited("register","Register");
    String registerLink = (String) inVM.getInherited("registerLink","#");
%>

    <a href="<%= loginLink %>#"><%= login %></a> | <a href="<%= registerLink %>"><%= register %></a>
