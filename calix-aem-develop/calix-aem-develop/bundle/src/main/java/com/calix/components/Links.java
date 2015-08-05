package com.calix.components;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.Link;
import com.calix.model.Tile;
import com.calix.utils.CommonUtils;
import com.calix.utils.JcrUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class Links extends WCMUse {
    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(Links.class);
    private List<Link> tiles;
    private String compTitle;
    private String compDesc;

    public String getCompTitle() {
        return compTitle;
    }

    public String getCompDesc() {
        return compDesc;
    }

    public List<Link> getTiles() {
        return tiles;
    }

    public void setTiles(List<Link> tiles) {
        this.tiles = tiles;
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
            compTitle = JcrUtils.getStringValue(currentNode, "compTitle");
            compDesc = JcrUtils.getStringValue(currentNode, "compDesc");

            String[] listTiles = JcrUtils.getStringArrayValue(currentNode, "listTiles");
            tiles = getLinks(slingRequest, listTiles);
        } catch (Exception ex) {
            logger.error("Exception Overview: ", ex);
        }
    }
    /**
     * Author(s): Objective: Completion Date: Jul 29, 2015 Revision
     * @param slingRequest
     * @param linksArr
     * @return
     */
    protected List<Link> getLinks(SlingHttpServletRequest slingRequest, String... linksArr) {
        List<Link> links = new ArrayList<Link>();
        if (null == linksArr) {
            return links;
        }
        try {
            for (String linkArr : linksArr) {
                Link link = CommonUtils.mapJsonToObject(linkArr, Link.class);
                String url = link.getLink();
                if (StringUtils.isNotBlank(url)) {
                    link.setTarget(CommonUtils.getTargetByLink(url));
                    link.setLinkStyle(CommonUtils.getStyleByLink(url));
                    link.setLink(CommonUtils.getFormattedLink(url));
                }
                links.add(link);
            }
        } catch (Exception e) {
            logger.error("Error when get quick links: ", e);
        }
        return links;
    }
}
