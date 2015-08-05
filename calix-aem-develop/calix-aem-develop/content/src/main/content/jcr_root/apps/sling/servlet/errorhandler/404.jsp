<%@ page session="false" %>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %>
<sling:defineObjects/>
<%@ page import="
    org.apache.sling.engine.auth.Authenticator,
                 org.apache.sling.engine.auth.NoAuthenticationHandlerException" %>
<%
                     request.getRequestDispatcher("/content/calix/en/site/404.html").forward(request, response);
%>