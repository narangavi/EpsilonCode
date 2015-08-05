<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>

<c:if test="${isEdit}">
    [Edit flyout-nav container component]
</c:if>

<%
    String heading = (String) properties.get("heading", "");
    String description = (String) properties.get("description", "");
    String headerImage = (String) properties.get("headerImage", "");
    String appendStyle = "";

    String pageTitle = "";
    if (currentPage != null) {
        pageTitle = currentPage.getNavigationTitle();
        if (pageTitle != null && pageTitle.isEmpty()) {
            pageTitle = currentPage.getTitle();
        }

        if(currentPage.getPath().contains("/nav")) {
            // This is the nav page, show the dropdown menu
            appendStyle = "style='display:block'";
            %>
<cq:include script="/libs/cq/cloudserviceconfigs/components/servicelibs/servicelibs.jsp"/>
<%currentDesign.writeCssIncludes(pageContext); %>
<c:if test="${isEdit}">
    <cq:includeClientLib categories="apps.calix.author"/>

    <style type="text/css">
        .dropdown-menu {
            top: 0;
        }
    </style>

</c:if>
            <%
        }
    }

%>

<ul class="dropdown-menu" <%=appendStyle%>>
    <li class="mobileBackMain mobileDisplay"><a href="#"><i class="fa fa-chevron-left"></i> Back</a> <span><a class="closeMobileMenu" href="#"><i class="fa fa-times"></i></a></span></li>
    <li class="mobileGrid">
        <div class="bleh">
            <div class="navFeature col-sm-12">
                <img class="navFeatureOverview mobileHide" src="<%= headerImage %>">
                <div class="navFeatureDescription">
                    <h4><%= heading %></h4>
                    <p><%= description %></p>
                </div>
            </div>
        </div>
        <div class="clearfix">
            <div class="col-sm-3 mobileCol">
                <cq:include path="col-par1" resourceType="foundation/components/parsys"/>
            </div>
            <div class="col-sm-3 mobileCol">
                <cq:include path="col-par2" resourceType="foundation/components/parsys"/>
            </div>
            <div class="col-sm-3 mobileCol">
                <cq:include path="col-par3" resourceType="foundation/components/parsys"/>
            </div>
            <div class="col-sm-3 mobileCol navColAlt">
                <cq:include path="col-par4" resourceType="foundation/components/parsys"/>
            </div>
        </div>
    </li>
</ul>
