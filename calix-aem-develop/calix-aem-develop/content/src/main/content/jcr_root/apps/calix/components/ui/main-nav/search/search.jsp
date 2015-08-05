<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>


<c:if test="${isEdit}">
</c:if>

<%
    String placeholder = (String) inVM.getInherited("placeholder","");
%>

<input type="text" placeholder="<%= placeholder %>" /> <a class="navbar-searchSubmit" type="submit" href="#"><i class="fa fa-search"></i></a>