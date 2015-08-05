<%@include file="/apps/calix/global.jsp"%>
<%@page session="false"%>

<div class="wideContainer">
	<cq:include path="heroPar" resourceType="foundation/components/parsys"/>
	<div class="narrowContainer">
		<div class="left-col col-sm-8 col-xs-12">
			<cq:include path="largePar" resourceType="foundation/components/parsys"/>
		</div>
		<div class="right-col col-sm-4 col-xs-12">
			<cq:include path="smallPar" resourceType="foundation/components/parsys"/>
		</div>
	</div>
</div>