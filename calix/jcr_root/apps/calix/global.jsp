<%@ page session="false" import="com.adobe.granite.xss.XSSAPI,
                                 com.day.cq.i18n.I18n,
                                 javax.jcr.Node,
                                 com.day.cq.wcm.api.WCMMode" %>
<%@include file="/libs/foundation/global.jsp" %>
<%@ page import="com.day.cq.commons.inherit.InheritanceValueMap" %>
<%@ page import="com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap" %>
<%@ page import="com.calix.service.UtilService" %>

<%
    boolean isEdit = WCMMode.fromRequest(request) == WCMMode.EDIT;
    boolean isDesign = WCMMode.fromRequest(request) == WCMMode.DESIGN;
    InheritanceValueMap inVM = new HierarchyNodeInheritanceValueMap(resource);
    UtilService utilService = sling.getService(UtilService.class);
    try {
        currentPage.getContentResource().adaptTo(Node.class).setProperty("cq:designPath", "/etc/designs/calix");

        if (currentNode == null) {
            log.info("Creating node:::::" + resource.getPath());
            Node parentNode = resource.getParent().adaptTo(Node.class);
            utilService.createNode(parentNode, resource);
        }

        final I18n i18n = new I18n(slingRequest.getResourceBundle(currentPage.getLanguage(false)));

        if (currentNode != null) {
            inVM = new HierarchyNodeInheritanceValueMap(resourceResolver.getResource(currentNode.getPath()));
        }

        if (!isEdit && !isDesign) {
            componentContext.setDefaultDecorationTagName( "" );
        }
    } catch (Exception e) {
        log.error("Exception in global JSP >>>> ", e);
    }
%>
<c:set var="isEdit" value="<%= isEdit %>"/>
<c:set var="isDesign" value="<%= isDesign %>"/>