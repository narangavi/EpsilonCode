<%@ page import="com.day.cq.wcm.api.Page" %>
<%@ page import="org.apache.sling.commons.json.JSONObject" %>
<%@ page import="javax.jcr.Value" %>
<%@ page import="java.util.*" %>
<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>

<%
    try {
        Property property = inVM.getInherited("listItems", Property.class);
        Map<String, Page> navMap = new LinkedHashMap<String, Page>();
		ArrayList< String > urls = new ArrayList< String >();
        if (property != null) {
            Value[] values = null;

            if (property.isMultiple()) {
                values = property.getValues();
            } else {
                values = new Value[1];
                values[0] = property.getValue();
            }

            for (Value val : values) {
                JSONObject jsonVals = new JSONObject(val.getString());
                String pagePath = jsonVals.getString("listItemLink");
                String linkLbl = jsonVals.getString("listItem");
				String pageLink = "";
                if (jsonVals.has("pageLink")) {
                  pageLink = jsonVals.getString("pageLink");
                }
				pageLink = pageLink != null ? pageLink : "";
                boolean isExternalLink = pageLink.toLowerCase().startsWith("http");

				if (!isExternalLink && !pageLink.equals("")) {
    				pageLink += ".html";
				}

				urls.add(pageLink);

				if(pagePath != null){
                Page pg = pageManager.getPage(pagePath);
                    if(pg != null){
                		if (linkLbl == null || linkLbl.isEmpty()) {
                    		linkLbl = pg.getNavigationTitle();
               			 }
               			 if (linkLbl == null || linkLbl.isEmpty()) {
                    		linkLbl = pg.getTitle();
                		 }
                    }

               			 navMap.put(linkLbl, pg);

				}
            }
        }

        pageContext.setAttribute("navMap", navMap);
		pageContext.setAttribute("urls", urls);
    } catch (Exception e) {
        if (isEdit) {
            out.print("[ERROR in the Navigation page configuration]");
        }
        log.error("exception in mega menu: ", e);
    }
%>

<c:if test="${isEdit}">
    [Edit Main Navigation - Hierarchically inherits]
</c:if>
<c:if test="${!isEdit}">
    <ul class="nav navbar-nav">
            <c:forEach var="map" items="${navMap}" varStatus="loop">


			 <c:if test="${map.key eq 'contact-us'}">
        		 <li class="dropdown yamm-fw main-nav-contact-us"><i class="fa fa-phone"></i>
				 <a href="${urls[loop.index]}">${map.key}</a>
       		 </c:if>
             <c:if test="${map.key != 'contact-us'}">
         		<li class="dropdown yamm-fw">
				<a id="dlDropDown" href="${urls[loop.index]}" data-toggle="dropdown" class="dropdown-toggle pushIt"
                       data-url="${map.value.path}">
                        ${map.key}
                    </a>
       		 </c:if>
                    
                    <c:if test="${!isEdit}">
                        <cq:include resourceType="calix/components/ui/main-nav/flyout-nav"
                                    path="${map.value.path}/jcr:content/flyout-nav"/>
                    </c:if>
				 </li>
            </c:forEach>
    </ul>

    <script>

       $(window).load(function() {
            var isMobile = window.matchMedia("only screen and (max-width: 760px)");
            $(".navbar-nav .navColAlt").each(function () {
                if ($(this).find(".menuSub").length == 0) {
                    $(this).remove();
                }
            });	

		  $(".navbar-nav .dropdown").each(function () {
              $(this).click(function() {
                  if(!isMobile.matches){

                      var pageDetail = $(this).children();
                      var url   = pageDetail.attr('href'); 
                      if(url != null && url != ""){
                      	window.location.replace(url);
                      }
                  }
              });
          });

        });
    </script>
</c:if>