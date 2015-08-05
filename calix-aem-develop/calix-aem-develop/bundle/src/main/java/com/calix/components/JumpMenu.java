/**
  * JumpMenu class
  * 
  * @Author: Epsilon-DongTS
  * Objective: This implements logic to display jump menu.
  * Completion Date: July 31, 2015
  * 
  * @version: 1.0 
*/
package com.calix.components;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;

import org.apache.commons.lang.ArrayUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.Menu;
import com.calix.utils.CommonUtils;
import com.calix.utils.JcrUtils;
import com.calix.utils.JsonUtils;

/**
 * The Class JumpMenu.
 */
public class JumpMenu extends WCMUse {

    /** The Constant logger. */
    private static final Logger  logger = LoggerFactory.getLogger(JumpMenu.class);
    
    /** The menus. */
    private List<Menu> menus;
	
	/** The label. */
	private String label;

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Gets the menus.
	 *
	 * @return the menus
	 */
	public List<Menu> getMenus() {
		return menus;
	}

    /**
     * Gets the logger.
     *
     * @return the logger
     */
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
		label = JcrUtils.getStringValue(currentNode, "label");
    	String[] jsonArray = JcrUtils.getStringArrayValue(currentNode, "menus");
    	menus =  convertJsonToMenu(jsonArray);
    }
    
    /**
     * Convert JSON array to menu item.
     *
     * @param jsonArray the JSON array
     * @return the list menu item.
     */
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
					menu.setPath(CommonUtils.getFormattedLink(path));
					menus.add(menu);
				}
			} catch (JSONException e) {
				logger.error("Error when convertJsonToMenu{}", e);
			}
    	}
    	return menus;
    }
}
