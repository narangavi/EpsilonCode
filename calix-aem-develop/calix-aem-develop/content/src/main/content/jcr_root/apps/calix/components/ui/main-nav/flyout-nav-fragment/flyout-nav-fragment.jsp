<%@ page import="org.apache.sling.commons.json.JSONObject" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="javax.jcr.Property" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>


<c:if test="${isEdit}">[Edit flyout-nav fragment component]</c:if>

<%
    try {
        Property property = null;

        if (currentNode.hasProperty("listItems")) {
            property = currentNode.getProperty("listItems");
        }

        String linksHeading = properties.get("linksHeading", "Enter links heading here");

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

<h3 class="subHdr"><%= linksHeading %>
</h3>
<ul class="menuSub">
    <li class="mobileBack mobileDisplay">
        <a href="#"><i class="fa fa-chevron-left"></i> Back</a> <span>
        <a class="closeMobileMenu" href="#"><i class="fa fa-times"></i></a></span></li>
    <%
        for (Value val : values) {
            listItem = new JSONObject(val.getString());
            if(listItem==null || StringUtils.isBlank(listItem.getString("listItem"))){
                continue;
            }
    %>
    <c:set var="listItemLink" value="<%= listItem.get("listItemLink") %>"/>
    <c:set var="listItem" value="<%= listItem.get("listItem") %>"/>
    <c:choose>
        <c:when test="${fn:startsWith(listItemLink, '/content')}">
            <li>
                <a href="${listItemLink}.html">${listItem}</a>
            </li>
        </c:when>
        <c:otherwise>
            <li>
                <a href="${listItemLink}">${listItem}</a>
            </li>
        </c:otherwise>
    </c:choose>
    <%
        } // end for loop
    %>
</ul>

<%
        }
    } catch (Exception e) {
        log.error("Exception in fragment: ", e);
    }
%>