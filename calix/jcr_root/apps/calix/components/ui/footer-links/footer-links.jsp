<%@ page import="org.apache.sling.commons.json.JSONObject" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="org.apache.sling.commons.json.JSONArray" %>
<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>

    <%
        try {
            Property property = inVM.getInherited("listItems", Property.class);

            /* if (currentNode.hasProperty("listItems")) {
                property = currentNode.getProperty("listItems");
            } */

            // String linksHeading = properties.get("linksHeading","Enter links heading here");

            String linksHeading = (String) inVM.getInherited("linksHeading","[ Enter links heading here ]");


            if (property != null) {
                JSONObject listItem = null, subListItem = null;
                Value[] values = null;

                if (property.isMultiple()) {
                    values = property.getValues();
                } else {
                    values = new Value[1];
                    values[0] = property.getValue();
                }
    %>
    <h5><%= linksHeading %></h5>
    <ul>
        <%
            for (Value val : values) {
                listItem = new JSONObject(val.getString());
        %>

        <c:set var="string" value="<%= listItem.get("listItemLink") %>"/>

        <c:choose>
            <c:when test="${fn:startsWith(string, '/content')}">
                <li><a href="<%= listItem.get("listItemLink")%>.html"> <%= listItem.get("listItem") %> </a>
            </c:when>
            <c:otherwise>
                <li><a href="<%= listItem.get("listItemLink")%>"> <%= listItem.get("listItem") %> </a>
            </c:otherwise>
        </c:choose>


            <%
                if (listItem.has("subListItems")) {
                    JSONArray subListItems = (JSONArray) listItem.get("subListItems");
            %>
            <ul>
                <%
                    if (subListItems != null) {
                        for (int index = 0, length = subListItems.length(); index < length; index++) {
                            subListItem = (JSONObject) subListItems.get(index);
                %>

                <c:set var="string" value="<%= listItem.get("listItemLink") %>"/>

                <c:choose>
                    <c:when test="${fn:startsWith(string, '/content')}">
                        <li><a href="<%= subListItem.get("sublistLink") %>.html"><%= subListItem.get("subListItem") %></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%= subListItem.get("sublistLink") %>" target="_blank"><%= subListItem.get("subListItem") %></a></li>
                    </c:otherwise>
                </c:choose>

                <%
                    } // end inner for-loop
                %>
            </ul>
            <%
                }
            %>

        </li>

        <%
            }
        %>


        <%
            } //end outer for-loop

        %>

    </ul>
<%
} else {
%>

<c:if test="${isEdit}">
    [Add footer link entries in this dialog]

    <style type="text/css">
        #ContentWrapper {
            height: 100%;
        }
    </style>

</c:if>
<br><br>
<%
        }
    } catch (Exception e) {
        e.printStackTrace(new PrintWriter(out));
        }
%>