<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>

<c:if test="${isEdit}">
    [My Calix]<br/>
</c:if>

<%
    String myCalix = (String) inVM.getInherited("myCalix","My Calix");
    String myCalixLink = (String) inVM.getInherited("myCalixLink","#");
%>

<div class="my-calix">
    <div class="myCalix">
        <a href="<%= myCalixLink %>"> <%= myCalix%> </a>
    </div>
</div>