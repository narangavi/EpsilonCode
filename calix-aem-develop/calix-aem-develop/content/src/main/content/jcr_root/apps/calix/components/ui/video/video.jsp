<%@page session="false"%>
<%@include file="/apps/calix/global.jsp"%><%
    String wistiaId = properties.get("wistiaId", "");
	String imageLink = properties.get("imageLink", "");
	String title = properties.get("title", "");
	String description = properties.get("description", "");
%>
<% if(WCMMode.fromRequest(request) == WCMMode.EDIT) { %>
    [Edit video]
<% } %>

<div class="video">
	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-xs-12 videoPreview">
				<% if(imageLink != null && imageLink != "") { %>
					<img src="<%=imageLink%>" width="100%" alt="" />
				<% } %>
				<div class="description">
				  <h3><%=title%></h3>
				  <p><%=description%></p>
				  <p><%=wistiaId%></p>
				</div>
			</div>
		</div>
	</div>
</div>