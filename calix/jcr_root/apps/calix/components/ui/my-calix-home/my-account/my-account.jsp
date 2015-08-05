<%@page session="false" %>
<%@include file="/apps/calix/global.jsp" %>
<%@page import="com.calix.utils.JcrUtils"%>

<c:if test="${isEdit}">
    [Edit My Account Components ]
</c:if>
<%
    String  accountSettings = (String) properties.get("account", "");
    String  logout = (String) properties.get("logout", "");
    // username = where i can get it
%>



<div class="myAccount">
	<cq:include path="notification" resourceType="/apps/calix/components/ui/my-calix-home/notification"/>
	<span class="navbar-account mobileHide"><a data-placement="bottom" data-toggle="popover" data-container="body" type="button" data-html="true" href="#" id="login"><span class="userName">JohnUserNameLonger</span><i class="glyphicon glyphicon-triangle-bottom"></i></a>
	  <div id="popover-content" class="hide">
	      <form class="form-inline" role="form">
	          <div class="form-group">
	              <div class="forgotPass"><a href="<%= accountSettings%>">Account Settings</a></div>
	              <a class="grnBtn center-block" href="<%= logout%>"><span>LOG OUT</span></a>
	          </div>
	      </form>
	  </div>
	</span>
</div>







