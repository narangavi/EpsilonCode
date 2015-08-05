<%@page session="false" %>
<%@include file="/apps/calix/global.jsp" %>
<c:if test="${isEdit}">
    <br/>[Edit the Image&Text layout here]
</c:if>
<%
	String title = properties.get("title", "");
    String columnLayout = properties.get("columnLayout", "threeColumns");
    int numOfItems = properties.get("numOfItems", 1);
    boolean topCurves = properties.get("displayTopCurves", false);
    boolean bottomCurves = properties.get("displayBottomCurves", false);

    String coverClass = "imageWithTextBelowThreeItems";
    String itemClass = "col-xs-12 col-sm-4 col-md-4";
    if (columnLayout.equalsIgnoreCase("twoColumns")) {
        coverClass = "imageWithTextBelowTwoItems";
        itemClass = "col-xs-12 col-sm-6 col-md-6";
    } else {
        coverClass = "imageWithTextBelowThreeItems";
        itemClass = "col-xs-12 col-sm-4 col-md-4";
    }
    if (topCurves) {
        coverClass += " topCurves";
    }
    if (bottomCurves) {
        coverClass += " bottomCurves";
    }
%>
<div class="<%=coverClass%>">
<a name="image-text"></a>
    <div class="container">
		<h1><%=title%></h1>
        <div class="row">
            <c:forEach var="i" begin="1" end="<%=numOfItems%>">
                <div class="<%=itemClass%>">
                    <cq:include path="image_text_item_${i}" resourceType="/apps/calix/components/ui/image-text-item"/>
                </div>
            </c:forEach>
        </div>
    </div>
</div>