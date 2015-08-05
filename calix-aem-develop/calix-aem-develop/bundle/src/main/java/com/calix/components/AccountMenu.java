package com.calix.components;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.Menu;
import com.calix.utils.JcrUtils;
import com.calix.utils.JsonUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import java.util.ArrayList;
import java.util.List;

/**
 * NewsCarousel Class.
 *
 * @author epsilon-admin
 */
public class AccountMenu extends WCMUse {

    private static final Logger  logger = LoggerFactory.getLogger(AccountMenu.class);
    private List<Menu> menus;

    public static Logger getLogger() {
        return logger;
    }

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {

        SlingHttpServletRequest slingRequest = this.getRequest();
        Node currentNode = slingRequest.getResource().adaptTo(Node.class);
    	String[] jsonArray = JcrUtils.getStringArrayValue(currentNode, "menus");
    	menus =  convertJsonToMenu(jsonArray);
    }
    
    private List<Menu> convertJsonToMenu(String[] jsonArray){
    	List<Menu> menus = new ArrayList<Menu>();
    	if(ArrayUtils.isNotEmpty(jsonArray)){
			try {
				Menu menu;
				for (int i = 0; i < jsonArray.length; i++) {

					menu = new Menu();
					JSONObject obj = new JSONObject(jsonArray[i]);
					String title = JsonUtils.getJsonValue("title", obj);
					String path = JsonUtils.getJsonValue("path", obj);
					// set values for menu here
					menu.setTitle(title);
					menu.setPath(path);
					menus.add(menu);
				}
			} catch (JSONException e) {
				logger.error("Error when convertJsonToMenu{}", e);
			}
    	}
    	return menus;
    }

    public List<Menu> getMenus() {
        return menus;
    }
}
