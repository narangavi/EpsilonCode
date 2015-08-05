/**
  * Notification.java
  * @Author: Epsilon-KhoaPD3
  * 
  * Objective: Get notification info to display in header bar
  * Completion Date: Jul 31, 2015
  * 
  * @version: 1.0 
*/
package com.calix.components;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.CTA;
import com.calix.utils.CommonUtils;
import com.calix.utils.JcrUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;


/**
 * The Class AccountTeam.
 */
public class Notification extends WCMUse {

	private static final String DEFAULT_CTA_TITLE = "See All Notifications";

	private static final int DEFAULT_NUMBER_OF_NOTIFICATIONS = 5;

	private static final String NUMBER_OF_NOTIFICATIONS = "numberOfNotifications";

	private static final String DEFAULT_NOTIFICATION_SETTINGS_TEXT = "Notification Settings";

	private static final String DEFAULT_MARK_AS_READ_TEXT = "Mark all notifications as read";

	private static final String NOTIFICATION_SETTINGS_TEXT = "notificationSettingsText";

	private static final String MARK_AS_READ_TEXT = "markAsReadText";

	/** The Constant logger. */
    private static final Logger  logger = LoggerFactory.getLogger(Notification.class);
    
    private String markAsReadText;
    private String notificationSettingsText;
    private int numberOfNotifications;
    private CTA cta;
    
	/**
	 * Gets the mark as read text.
	 *
	 * @return the mark as read text
	 */
	public String getMarkAsReadText() {
		return markAsReadText;
	}
	
	/**
	 * Gets the notification settings text.
	 *
	 * @return the notification settings text
	 */
	public String getNotificationSettingsText() {
		return notificationSettingsText;
	}
	
	/**
	 * Gets the number of notifications.
	 *
	 * @return the number of notifications
	 */
	public int getNumberOfNotifications() {
		return numberOfNotifications;
	}
	
	/**
	 * Gets the cta.
	 *
	 * @return the cta
	 */
	public CTA getCta() {
		return cta;
	}
	/* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
        try {
        	/*Init info*/
            SlingHttpServletRequest slingRequest = this.getRequest();
            Node currentNode = slingRequest.getResource().adaptTo(Node.class);

            processConfigInfoInCurrentNode(currentNode);
            
        }catch (Exception e){
            logger.error("Exeption Notification: ", e);
        }
    }

	/**
	 * Process config info in current node.<br/>
	 * Get info for markAsReadText field, notificationSettingsText field, numberOfNotifications field, cta field
	 *
	 * @param currentNode the current node
	 */
	private void processConfigInfoInCurrentNode(Node currentNode) {
		//process mark as read text
		markAsReadText = JcrUtils.getStringValue(currentNode, MARK_AS_READ_TEXT);
		markAsReadText = StringUtils.isNotBlank(markAsReadText) ? markAsReadText : DEFAULT_MARK_AS_READ_TEXT;
		
		//process notification settings text
		notificationSettingsText = JcrUtils.getStringValue(currentNode, NOTIFICATION_SETTINGS_TEXT);
		notificationSettingsText = StringUtils.isNotBlank(notificationSettingsText) ? notificationSettingsText : DEFAULT_NOTIFICATION_SETTINGS_TEXT;
		
		//process number of notifications
		try{
			numberOfNotifications = Integer.parseInt(JcrUtils.getStringValue(currentNode, NUMBER_OF_NOTIFICATIONS));
			numberOfNotifications = numberOfNotifications <= 0 ? DEFAULT_NUMBER_OF_NOTIFICATIONS : numberOfNotifications;
		} catch (Exception e) {
			logger.error("Exeption When Parse Number Of Notifications: ", e);
			numberOfNotifications = DEFAULT_NUMBER_OF_NOTIFICATIONS;
		}
		
		//process cta
		cta = CommonUtils.convertNodeToCTA(currentNode, StringUtils.EMPTY);
		if(cta != null && StringUtils.isBlank(cta.getTitle())){
			cta.setTitle(DEFAULT_CTA_TITLE);
		}
	}
}
