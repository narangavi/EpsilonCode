<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>

<div class="mainHdrContainer">
    <div class="container">
        <div class="navbar yamm">
            <div class="navbar-header clearfix">
                <button type="button" data-toggle="collapse" data-target="#navbar-collapse-grid" class="navbar-toggle">
                    <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
                </button>

                <c:choose>
                    <c:when test="${isEdit}">
                        <span class="navbar-brand" style="height:111px; margin-right: 40px;margin-left: -10px;">
                    </c:when>
                    <c:otherwise>
                        <span class="navbar-brand" style="height:38px; margin-right: 40px;margin-left: -10px;">
                    </c:otherwise>
                </c:choose>
                <cq:include path="logo" resourceType="calix/components/ui/main-nav/logo"/>
                </span>

                <span class="navbar-subBrand mobileHide">
                    <cq:include path="tagline" resourceType="calix/components/ui/main-nav/tagline"/>
                </span>

                <span class="navbar-subBrand contact mobileHide"><i class="fa fa-phone"></i>
                    <cq:include path="contact" resourceType="calix/components/ui/main-nav/contact"/>
                </span>

                <span class="navbar-search mobileHide">
                    <form>
                        <cq:include path="search" resourceType="calix/components/ui/main-nav/search"/>
                    </form>
                </span>

                <cq:include path="account" resourceType="/apps/calix/components/ui/my-calix-home/my-account"/>

                <div class="row mobileSearch">
                    <div class="col-xs-11">
                        <div class="input-group">
                            <input type="text" class="form-control mobileSearchInput" placeholder="Search"/>
                      <span class="input-group-addon">
                          <i class="fa fa-search"></i>
                      </span>
                        </div>
                    </div>
                    <div class="col-xs-1"><a class="closeMobileSearch" href="#"><i class="fa fa-times"></i></a></div>
                </div>
                <a class="closeMobileMenu-main" href="#" style="display:none"><i class="fa fa-times"></i></a>
                <a class="mobileDisplay showSearch" href="#"><i class="fa fa-search"></i></a>
            </div>
            <div id="navbar-collapse-grid" class="navbar-collapse collapse">

                <!-- Grid 12 Menu -->
                <%--<li class=""><a href="#">Home</a></li>
                <cq:include path="flyout-nav-1" resourceType="calix/components/ui/main-nav/flyout-nav"/>--%>
                <cq:include path="megamenu" resourceType="calix/components/ui/main-nav/megamenu"/>

                <div class="socialIcons mobileHide">
                    <%--<a href="#" target="_blank"><i class="fa fa-twitter"></i></a>
                    <a href="#" target="_blank"><i class="fa fa-facebook"></i></a>
                    <a href="#" target="_blank"><i class="fa fa-linkedin"></i></a>
                    <a href="#" target="_blank"><i class="fa fa-share-alt"></i></a>--%>
                    <cq:include path="header-social" resourceType="calix/components/ui/main-nav/header-social"/>
                </div>

                <div class="mobileDisplay btnContain clearfix">
                    <a class="grnBtn pull-left" href="#"><span>LOGIN</span></a>
                    <a class="grnBtn pull-right" href="#"><span>Register</span></a>
                </div>
            </div>
        </div>
    </div>
</div>