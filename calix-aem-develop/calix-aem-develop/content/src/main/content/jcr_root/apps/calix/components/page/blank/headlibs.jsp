<%--
  Copyright 1997-2010 Day Management AG
  Barfuesserplatz 6, 4001 Basel, Switzerland
  All Rights Reserved.

  This software is the confidential and proprietary information of
  Day Management AG, ("Confidential Information"). You shall not
  disclose such Confidential Information and shall use it only in
  accordance with the terms of the license agreement you entered into
  with Day.

  ==============================================================================

  Includes the scripts and css to be included in the head tag

  ==============================================================================

--%>
<%@ page session="false" %>
<%@include file="/apps/calix/global.jsp" %>

<cq:includeClientLib categories="cq.foundation-main"/>
<cq:includeClientLib categories="apps.calix.one"/>
<cq:includeClientLib categories="apps.calix.two"/>
<cq:includeClientLib categories="apps.calix.three"/>
<cq:include script="/libs/cq/cloudserviceconfigs/components/servicelibs/servicelibs.jsp"/>
<%currentDesign.writeCssIncludes(pageContext); %>
<c:if test="${isEdit}">
    <cq:includeClientLib categories="apps.calix.author"/>
</c:if>