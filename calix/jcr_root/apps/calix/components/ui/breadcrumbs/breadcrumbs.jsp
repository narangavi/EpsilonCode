<%@page session="false" %>
<%@include file="/apps/calix/global.jsp" %>
<%@page import="com.calix.utils.JcrUtils"%>

<c:if test="${isEdit}">
    [Edit Breadcrumb - Hierarchically inherits configuration]
</c:if>
<%
    boolean hideBreadCrumb = (Boolean) properties.get("hideBreadcrumbs", false);
    if(hideBreadCrumb) { %>
    <style type="text/css">
        .breadCrumbs {
            visibility: hidden;
        }
    </style>
<%    } else if (!hideBreadCrumb) {
    String homepagePath = inVM.getInherited("homepagePath", "/content/calix/en");
%>
<a href="<%=homepagePath%>.html">
    <i class="fa fa-home"></i>
</a>
<%
    // get starting point of trail
    long absParent = inVM.getInherited("absParent", 2L);
    long relParent = inVM.getInherited("relParent", 1L);
    String delim = inVM.getInherited("delim", ">");
    String trailStr = inVM.getInherited("trail", ">");


    int currentLevel = currentPage.getDepth();
    //out.print("\n absParent: " + absParent);
    //out.print("\n curlevel: " + currentLevel);
    //out.print("\n relParent: " + relParent);
    while (absParent < currentLevel - relParent) {
        Page trail = currentPage.getAbsoluteParent((int) absParent);

        if (trail == null) {
            break;
        }
        String path = trail.getPath();
        if(null != path && "" != path){
            path += "/jcr:content";
        }
        Node pageNode = JcrUtils.getNode(slingRequest, path);
        boolean hideInBreadcrums = JcrUtils.getBooleanValue(pageNode, "hideinbreadcrumbs");

        if(hideInBreadcrums){
            break;
        }
        String title = trail.getNavigationTitle();
        if (title == null || title.isEmpty()) {
            title = trail.getTitle();
        }
        if (title == null || title.isEmpty()) {
            title = trail.getName();
        }
%><%= xssAPI.filterHTML(delim) %><%
%><a href="<%= xssAPI.getValidHref(trail.getPath()+".html") %>"
     onclick="CQ_Analytics.record({event:'followBreadcrumb',values: { breadcrumbPath: '<%= xssAPI.getValidHref(trail.getPath()) %>' },collect: false,options: { obj: this },componentPath: '<%=resource.getResourceType()%>'})"><%
%><%= xssAPI.encodeForHTML(title) %><%
%></a> <%
        absParent++;
    }
    if (trailStr.length() > 0) {
%><%= xssAPI.filterHTML(trailStr) %><%
        }
    }
%>
