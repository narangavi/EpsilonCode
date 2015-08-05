package com.calix.components;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.RelatedProduct;
import com.calix.utils.JcrUtils;
import com.calix.utils.JsonUtils;

/**
 * NewsCarousel Class.
 *
 * @author epsilon-admin
 */
public class RelatedProductComp extends WCMUse {

    private static final Logger  logger = LoggerFactory.getLogger(RelatedProductComp.class);
    boolean displayTopCurves;
    boolean displayBottomCurves ;
    boolean displayBackground;
    String styleBackground;
    private String curveStyle;
    String titleRelatedProduct;
    private List<RelatedProduct> products;

    public static Logger getLogger() {
        return logger;
    }

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {

        SlingHttpServletRequest slingRequest = this.getRequest();
        //final Session session = CommonUtils.getSession(slingRequest);
        Node currentNode = slingRequest.getResource().adaptTo(Node.class);

        displayTopCurves = JcrUtils.getBooleanValue(currentNode, "displayTopCurve");
        displayBottomCurves = JcrUtils.getBooleanValue(currentNode, "displayBottomCurve");
        displayBackground = JcrUtils.getBooleanValue(currentNode, "displayBG");
        titleRelatedProduct = getProperties().get("titleDashboad", "");

        styleBackground ="relatedProductsWrap";
        // handle curve style (top/bottom curved )
        curveStyle = "";
        if(displayTopCurves){
            styleBackground += " curve-top";
        }
        if(displayBottomCurves){
            styleBackground += " curve-bottom";
        }

        // assing background style
        displayBackground = JcrUtils.getBooleanValue(currentNode, "displayBG");
        if(displayBackground) {
            styleBackground += " greyBackground";
        }
        else {
            styleBackground += "";
        }

    	String[] jsonArray = JcrUtils.getStringArrayValue(currentNode, "products");
    	products =  convertJsonToProduct(jsonArray);
    }
    
    private List<RelatedProduct> convertJsonToProduct(String[] jsonArray){
    	List<RelatedProduct> relatedProducts = new ArrayList<RelatedProduct>();
    	if(ArrayUtils.isNotEmpty(jsonArray)){
			try {
				RelatedProduct relatedProduct;
				for (int i = 0; i < jsonArray.length; i++) {
					relatedProduct = new RelatedProduct();
					JSONObject obj = new JSONObject(jsonArray[i]);
					
					String title = JsonUtils.getJsonValue("title", obj);
					String image = JsonUtils.getJsonValue("imagePath", obj);
					String description = JsonUtils.getJsonValue("description", obj);
					String path = JsonUtils.getJsonValue("path", obj) + ".html";
					// set values for related product here
					relatedProduct.setProductTitle(title);
					relatedProduct.setProductDescription(description);
					relatedProduct.setProductImage(image);
					relatedProduct.setProductPath(path);
					relatedProducts.add(relatedProduct);
				}
			} catch (JSONException e) {
				logger.error("Error when convertJsonToProduct {}", e);
			}
    	}
    	return relatedProducts;
    }


    public boolean isDisplayBottomCurves() {
        return displayBottomCurves;
    }
    public boolean isDisplayTopCurves() {
        return displayTopCurves;
    }
    public boolean isDisplayBackground() {
        return displayBackground;
    }
    public List<RelatedProduct> getProducts() {
        return products;
    }

    public String getStyleBackground() {
        return styleBackground;
    }

    public String getTitleRelatedProduct() {
        return titleRelatedProduct;
    }

    public String getCurveStyle() {
        return curveStyle;
    }
}
