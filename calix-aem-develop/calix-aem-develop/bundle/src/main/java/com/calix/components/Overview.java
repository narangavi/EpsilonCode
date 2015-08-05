package com.calix.components;

import java.util.List;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.calix.utils.CommonUtils;
import com.calix.utils.JcrUtils;
import com.calix.model.Tile;

public class Overview extends WCMUse {
    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(Overview.class);
    private List<Tile> tiles;
    private String curve;

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public String getCurve() {
        return curve;
    }

    public void setCurve(String curve) {
        this.curve = curve;
    }

    public static Logger getLogger() {
        return logger;
    }

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
        try {
            SlingHttpServletRequest slingRequest = this.getRequest();
            final Session session = CommonUtils.getSession(slingRequest);
            Node currentNode = slingRequest.getResource().adaptTo(Node.class);
            boolean displayTopCurves = JcrUtils.getBooleanValue(currentNode, "displayTopCurves");
            boolean displayBottomCurves = JcrUtils.getBooleanValue(currentNode, "displayBottomCurves");
            curve = "";
            if (displayBottomCurves) {
                curve += "bottomBorder";
            }
            if (displayTopCurves) {
                if ("".equals(curve)) {
                    curve = "topBorder";
                } else
                    curve += " topBorder";
            }
            String[] listTiles = JcrUtils.getStringArrayValue(currentNode, "listTiles");
            tiles = CommonUtils.getTiles(listTiles);
        } catch (Exception ex) {
            logger.error("Exception Overview: ", ex);
        }
    }
}
