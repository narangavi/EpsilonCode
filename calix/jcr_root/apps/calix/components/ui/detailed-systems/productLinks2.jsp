<%@ page import="org.apache.sling.commons.json.JSONObject" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="org.apache.sling.commons.json.JSONArray" %>
<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>

<%
    try {
        Property property = inVM.getInherited("linkItems2", Property.class);

            if (currentNode.hasProperty("linkItem2")) {
                property = currentNode.getProperty("linkItem2");
            }

        // String linksHeading = properties.get("linksHeading","Enter links heading here");

        // String linksHeading = (String) inVM.getInherited("linksHeading","[ Enter links heading here ]");


        if (property != null) {
            JSONObject linkItem2 = null;
            Value[] values = null;

            if (property.isMultiple()) {
                values = property.getValues();
            } else {
                values = new Value[1];
                values[0] = property.getValue();
            }
%>
    <%
        for (Value val : values) {
            linkItem2 = new JSONObject(val.getString());
    %>

    <c:set var="string" value="<%= linkItem2.get("path2") %>"/>

    <c:choose>
        <c:when test="${fn:startsWith(string, '/content')}">
            <li class="grid-item"><a href="<%= linkItem2.get("path2")%>.html"><%= linkItem2.get("linkText2") %></a></li>
        </c:when>
        <c:otherwise>
            <li class="grid-item"><a href="<%= linkItem2.get("path2")%>"><%= linkItem2.get("linkText2") %></a></li>
        </c:otherwise>
    </c:choose>

    <%
        } //end outer for-loop

    %>

<%
} else {
%>

<c:if test="${isEdit}">
    [Product link entries go in this section]
</c:if>
<%
        }
    } catch (Exception e) {
        e.printStackTrace(new PrintWriter(out));
    }
%>