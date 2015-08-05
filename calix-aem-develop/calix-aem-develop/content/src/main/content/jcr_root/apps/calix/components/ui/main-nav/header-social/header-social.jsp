<%@ page import="java.net.URLEncoder" %>
<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>

<c:if test="${isEdit}">
    [Header social]

</c:if>


<%	
    pageContext.setAttribute("twitter", inVM.getInherited("twitter", false));
	pageContext.setAttribute("facebook", inVM.getInherited("facebook", false));
    pageContext.setAttribute("linkedin", inVM.getInherited("linkedin", false));
    pageContext.setAttribute("googleplus", inVM.getInherited("googleplus", false));
    pageContext.setAttribute("tumblr", inVM.getInherited("tumblr", false));
    pageContext.setAttribute("mail", inVM.getInherited("mail", false));

	String title = pageProperties.get("jcr:title", "Calix");
	String discription = pageProperties.get("jcr:description", title);
	
	String encodedTitle = URLEncoder.encode(title, "UTF-8");
	String encodedDescription = URLEncoder.encode(discription + " - Calix", "UTF-8");
	
	pageContext.setAttribute("encodedTitle", encodedTitle);
	pageContext.setAttribute("encodedDesc", encodedDescription);

	String mailTitle = title;
    String mailSubject = mailTitle + " - Calix.com";
    String mailBody = mailTitle + "%0ASee more here:%0A"+ slingRequest.getRequestURL();
    String emailLink = "mailto:?subject=" + mailSubject + "&body=" + mailBody;
    pageContext.setAttribute("mailToLink", emailLink);
%>
<c:choose>
    <c:when test="${(not twitter) and (not facebook) and (not linkedin) and (not googleplus) and (not tumblr) and (not mail)}">
    </c:when>

    <c:otherwise>
		<c:if test="${twitter}">
			<a href="http://twitter.com/share?original_referer=${slingRequest.requestURL}&url=${slingRequest.requestURL}&text=${encodedTitle}&via=thecalixnetwork" target="_blank"><i class="fa fa-twitter"></i></a>
		</c:if>
		<c:if test="${facebook}">
			<a href="http://www.facebook.com/sharer.php?u=${slingRequest.requestURL}" target="_blank"><i class="fa fa-facebook"></i></a>
		</c:if>
		<c:if test="${linkedin}">
			<a href="http://www.linkedin.com/shareArticle?mini=true&url=${slingRequest.requestURL}&title=${encodedTitle}&summary=${encodedDesc}" target="_blank"><i class="fa fa-linkedin"></i></a>
		</c:if>
		<a data-placement="bottom" data-toggle="share-social" data-container="body" type="button" data-html="true" href="javascript:;"><i class="fa fa-share-alt"></i></a>
		<div id="social-content" class="hide">
			<c:if test="${googleplus}">
				<a href="https://plus.google.com/share?url=${slingRequest.requestURL}" target="_blank" class="gplus-icon social-icon-pop"><img src="/etc/designs/calix/clientlibs/img/social/gplus-icon.png" height="28"/></a>
			</c:if>
			<c:if test="${tumblr}">
				<a href="http://www.tumblr.com/share/link?url=${slingRequest.requestURL}&name=${encodedTitle}&description=${encodedDesc}" target="_blank" class="tumblr-icon social-icon-pop"><img src="/etc/designs/calix/clientlibs/img/social/tumblr-icon.png" height="28"/></a>
			</c:if>
			<c:if test="${mail}">
				<a href="${mailToLink}" class="mail-icon social-icon-pop"><img src="/etc/designs/calix/clientlibs/img/social/mail-icon.png" height="25"/></a>
			</c:if>
		</div>
	</c:otherwise>
</c:choose>