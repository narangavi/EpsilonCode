<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>

<c:if test="${isEdit}">
    [Edit footer link entries here - Note: Hierarchically Inherits content]
</c:if>

<%
    String rightsReserved = (String) inVM.getInherited("rightsReserved", "");
    String rightsReservedLink = (String) inVM.getInherited("rightsReservedLink", "");
    rightsReservedLink = xssAPI.filterHTML(rightsReservedLink);

    String termsOfUse = (String) inVM.getInherited("termsOfUse", "");
    String termsOfUseLink = (String) inVM.getInherited("termsOfUseLink", "");
    termsOfUseLink = xssAPI.filterHTML(termsOfUseLink);

    String privacyPolicy = (String) inVM.getInherited("privacyPolicy", "");
    String privacyPolicyLink = (String) inVM.getInherited("privacyPolicyLink", "");
    privacyPolicyLink = xssAPI.filterHTML(privacyPolicyLink);

    String twitterLink = (String) inVM.getInherited("twitterLink", "");
    twitterLink = xssAPI.filterHTML(twitterLink);
    String facebookLink = (String) inVM.getInherited("facebookLink", "");
    facebookLink = xssAPI.filterHTML(facebookLink);
    String youtubeLink = (String) inVM.getInherited("youtubeLink", "");
    youtubeLink = xssAPI.filterHTML(youtubeLink);
    String linkedInLink = (String) inVM.getInherited("linkedInLink", "");
    linkedInLink = xssAPI.filterHTML(linkedInLink);
%>

<div class="col-md-3 col-sm-12 col-xs-12 text-center footer-desktop-left"><a href="<%= rightsReservedLink %>.html"><%= rightsReserved %></a></div>
<div class="col-md-4  col-sm-12 col-xs-12 text-center terms-privacy footer-desktop-left"><a href="<%= termsOfUseLink %>"><span style="display:inline"><%= termsOfUse %></span></a> <a href="<%= privacyPolicyLink %>"><span style="display:inline; margin-left: 30px"><%= privacyPolicy %></span></a></div>
<div class="col-md-3 col-sm-12 col-xs-12 text-center footer-desktop-right">
    <ul class="socialIcons">
        <li>
            <div>
                <a href="<%= twitterLink %>" target="_blank"><i class="fa ml10 fa-twitter"></i></a>
                <a href="<%= facebookLink %>" target="_blank"><i class="fa ml10 fa-facebook"></i></a>
                <a href="<%= youtubeLink %>" target="_blank"><i class="fa ml10 fa-youtube"></i></a>
                <a href="<%= linkedInLink %>" target="_blank"><i class="fa ml10 fa-linkedin"></i></a>
            </div>
        </li>
    </ul>
</div>
