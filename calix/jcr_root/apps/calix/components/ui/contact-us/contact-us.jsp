<%@page session="false" %>
<%@include file="/apps/calix/global.jsp" %>
<%@page import="com.calix.utils.JcrUtils"%>

<c:if test="${isEdit}">
    [Edit IFrame Components ]
</c:if>
<%
    String  iFrameURL = (String) properties.get("iframeurl", "");
    String  width = (String) properties.get("width", "1024px");
    width = width + "px";
    String  height = (String) properties.get("height", "900px");
    height = height + "px";
%>

<style type="text/css" >
    #contentWrapper {
        width: 1024px;
        margin: 0 auto;
        background: #fff;
    }
</style>
<iframe src="<%=iFrameURL%>" width="<%=width%>" height="<%=height%>" style="border:none"></iframe>



