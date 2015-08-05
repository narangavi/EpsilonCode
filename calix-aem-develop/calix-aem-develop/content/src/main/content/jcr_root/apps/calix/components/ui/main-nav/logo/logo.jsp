<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>
<c:if test="${isEdit}">
</c:if>
<%
    String logo = (String) inVM.getInherited("logo", "/etc/designs/calix/clientlibs/img/calixLogo.png");
    String logoLink = (String) inVM.getInherited("logoLink", "/content/calix/en");
%>
<a href="<%=logoLink%>.html" class="navbar-brand" style="background: url('<%=logo%>') top left no-repeat;"></a>


<!-- apply clear:both style to fix logo edit box -->
<div class="both"></div>