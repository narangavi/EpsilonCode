<%@page session="false" %>
<%@ page import="com.day.cq.wcm.foundation.Placeholder" %>
<%@include file="/apps/calix/global.jsp" %>
<%@page import="com.calix.utils.CommonUtils"%>


<%
    String leadin = (String) inVM.getInherited("leadin", "");
    String headline = (String) inVM.getInherited("headline", "[Please input headline]");
    String subhead = (String) inVM.getInherited("text", "");
    String ctaTitle = (String) inVM.getInherited("titleCTA", "");
    String ctaLink = (String) inVM.getInherited("linkCTA", "");
    String buttonType = (String) inVM.getInherited("buttonType", "");
    boolean topCurves = (boolean) inVM.getInherited("displayTopCurves", false);
    boolean bottomCurves = (boolean) inVM.getInherited("displayBottomCurves", false);
    String target = "_blank";
    boolean isDisplay = (!ctaTitle.equals("") && !ctaLink.equals("")) ? true : false;

    boolean isExternalLink = ctaLink.toLowerCase().startsWith("http");
    String ctaUrl = ctaLink;
    if (!isExternalLink) {
        ctaUrl += ".html";
        target = "_self";
    }

    String classHeader = "textOnlyHeader";
    if (topCurves || bottomCurves) {
        classHeader = "textOnlyArchedHeader";
        if (topCurves) {
            classHeader += " topCurves";
        }
        if (bottomCurves) {
            classHeader += " bottomCurves";
        }
    }

    String classLink = CommonUtils.getBtnTypeStyleByTitle(ctaTitle, buttonType);
%>
<c:if test="${isEdit}">
    <br/>[Edit the Text Only component here, In-place editing available]
</c:if>

<div class="<%= classHeader %>">
    <div class="container">
        <div class="row">
            <div class="col-sm-10 col-xs-12">
                <h3><%= leadin %>
                </h3>

                <h2><%= headline %>
                </h2>
                <%= subhead %>
                <c:if test="<%= isDisplay %>">
                    <a class="<%= classLink %>" href="<%= ctaUrl %>" target="<%= target %>"><span><%= ctaTitle %></span></a>
                </c:if>
            </div>
        </div>
    </div>
</div>