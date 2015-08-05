<%@include file="/apps/calix/global.jsp"%>
<%@page session="false"%>

<div class="wideContainer">
	<cq:include path="heroPar" resourceType="foundation/components/parsys"/>
	<div class="narrowContainer">
		<cq:include path="narrowContainer" resourceType="foundation/components/parsys"/>
	</div>
	<cq:include path="contentPar" resourceType="foundation/components/parsys"/>
</div>