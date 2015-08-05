<%@include file="/apps/calix/global.jsp"%>
<%@page session="false"%>

<div class="wideContainer">
	<cq:include path="heroPar" resourceType="foundation/components/parsys"/>
		<div class="myCalixWrap">
			<div class="narrowContainer clearfix">
				<cq:include path="logo_image" resourceType="/apps/calix/components/ui/my-calix-home/image"/>
				<cq:include path="jump_menu" resourceType="/apps/calix/components/ui/my-calix-home/jump-menu"/>
				<cq:include path="headerPar" resourceType="foundation/components/parsys"/>
				<div class="myCalixMain col-sm-9 col-xs-12">
					<div>
						<cq:include path="largePar" resourceType="foundation/components/parsys"/>
					</div>
				</div>
				<div class="col-sm-3 col-xs-12">
					<div class="rightCol">
						<cq:include path="smallPar" resourceType="foundation/components/parsys"/>
					</div>
				</div>
			</div>
		</div>
</div>
