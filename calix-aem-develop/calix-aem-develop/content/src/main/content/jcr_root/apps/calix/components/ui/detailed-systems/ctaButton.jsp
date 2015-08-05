<%@page session="false" %>
<%@include file="/apps/calix/global.jsp" %>
<%@page import="com.calix.utils.CommonUtils"%>

<%
    String ctaTitle1 = (String) inVM.getInherited("ctaTitle", "");
    String ctaLink1 = (String) inVM.getInherited("ctaLink", "");
    String buttonType1 = (String) inVM.getInherited("btnType", "");
    String target1 = "_blank";
    boolean isDisplay1 = (!ctaTitle1.equals("") && !ctaLink1.equals("")) ? true : false;

    boolean isExternalLink1 = ctaLink1.toLowerCase().startsWith("http");
    String ctaUrl1 = ctaLink1;
    if (!isExternalLink1) {
        ctaUrl1 += ".html";
        target1 = "_self";
    }
    String classLink1 = CommonUtils.getBtnTypeStyleByTitle(ctaTitle1, buttonType1);

    String ctaTitle2 = (String) inVM.getInherited("titleCTA2", "");
    String ctaLink2 = (String) inVM.getInherited("linkCTA2", "");
    String buttonType2 = (String) inVM.getInherited("btnType2", "");
    String target2 = "_blank";
    boolean isDisplay2 = (!ctaTitle2.equals("") && !ctaLink2.equals("")) ? true : false;

    boolean isExternalLink2 = ctaLink2.toLowerCase().startsWith("http");
    String ctaUrl2 = ctaLink2;
    if (!isExternalLink2) {
        ctaUrl2 += ".html";
        target2 = "_self";
    }
    String classLink2 = CommonUtils.getBtnTypeStyleByTitle(ctaTitle2, buttonType2);
%>
<c:if test="<%= isDisplay1 %>">
    <a class="<%= classLink1 %>" href="<%= ctaUrl1 %>" target="<%= target1 %>"><span><%= ctaTitle1 %></span></a>
</c:if>

<c:if test="<%= isDisplay2 %>">
    <a class="<%= classLink2 %>" href="<%= ctaUrl2 %>" target="<%= target2 %>"><span><%= ctaTitle2 %></span></a>
</c:if>
