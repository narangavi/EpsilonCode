<%@ page import="org.apache.sling.commons.json.JSONObject" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="org.apache.sling.commons.json.JSONArray" %>
<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>

<%
    try {
        Property property = inVM.getInherited("linkItems", Property.class);

            if (currentNode.hasProperty("linkItem")) {
                property = currentNode.getProperty("linkItem");
            }

        // String linksHeading = properties.get("linksHeading","Enter links heading here");

        // String linksHeading = (String) inVM.getInherited("linksHeading","[ Enter links heading here ]");


        if (property != null) {
            JSONObject linkItem = null;
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
            linkItem = new JSONObject(val.getString());
    %>

    <c:set var="string" value="<%= linkItem.get("path") %>"/>

    <c:choose>
        <c:when test="${fn:startsWith(string, '/content')}">
            <li class="grid-item"><a href="<%= linkItem.get("path")%>.html"><%= linkItem.get("linkText") %></a></li>
        </c:when>
        <c:otherwise>
            <li class="grid-item"><a href="<%= linkItem.get("path")%>"><%= linkItem.get("linkText") %></a></li>
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