<%@include file="/apps/calix/global.jsp" %>
<%@page session="false" %>
<body>
<cq:include script="header.jsp"/>

<div id="contentWrapper">
    <div class="innerWrap">

        <cq:include path="breadcrumbs" resourceType="calix/components/ui/breadcrumbs"/>

        <cq:include path="my-calix" resourceType="calix/components/ui/my-calix"/>

        <cq:include script="content.jsp"/>

    </div>
</div>
<cq:include script="footer.jsp"/>
</body>