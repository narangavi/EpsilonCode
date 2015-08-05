<%@page session="false"%>
<%@include file="/apps/calix/global.jsp"%>
<%@ page import="java.util.Arrays" %>
<%
	boolean isDisplayBackground = properties.get("displayBackground", false);
    String tabs = Arrays.toString(properties.get("tabs", new String[0]));
    tabs = tabs.substring(1, tabs.length() - 1);
	String tabContainerId = currentNode.getPath().substring(1).replace("/", "-").replace(":", "-");

	String displayBackgroundClass = "";
    if (isDisplayBackground) {
    	displayBackgroundClass = "displayBackground";
    }
	pageContext.setAttribute("displayBackgroundClass", displayBackgroundClass);
	pageContext.setAttribute("tabContainerId", tabContainerId);
%>
<c:if test="${isEdit}">
    <br/>[Edit the Tab layout here. In order to edit other tabs,
    switch to preview mode, select relevant tab and revert to edit mode]
</c:if>

<div class="fullTabWrap container">
    <ul id="${tabContainerId}" class="myTab nav nav-tabs">
        <c:set var="i" value="1"/>
        <c:forTokens items="<%=tabs%>" delims="," var="tab">
            <c:choose>
                <c:when test="${i > 1}">
                    <li><a href="#${tabContainerId}${i}" data-toggle="tab">${tab}</a></li>
                </c:when>
                <c:otherwise>
                    <li class="active"><a href="#${tabContainerId}${i}" data-toggle="tab" class="hello">${tab}</a></li>
                </c:otherwise>
            </c:choose>
            <c:set var="i" value="${i + 1}"/>
        </c:forTokens>
    </ul>
    <div id="${tabContainerId}Content" class="myTabContent tab-content">
        <c:set var="i" value="1"/>
        <c:forTokens items="<%=tabs%>" delims="," var="tab">
            <c:choose>
                <c:when test="${i > 1}">
                    <div class="tab-pane fade clearfix" id="${tabContainerId}${i}">
                        <div class="tabBlock ${displayBackgroundClass}"><cq:include path="section${i}" resourceType="foundation/components/parsys"/></div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="tab-pane fade in active clearfix" id="${tabContainerId}${i}">
                        <div class="tabBlock ${displayBackgroundClass}"><cq:include path="section${i}" resourceType="foundation/components/parsys"/></div>
                    </div>
                </c:otherwise>
            </c:choose>
            <c:set var="i" value="${i + 1}"/>
        </c:forTokens>
    </div>
</div>