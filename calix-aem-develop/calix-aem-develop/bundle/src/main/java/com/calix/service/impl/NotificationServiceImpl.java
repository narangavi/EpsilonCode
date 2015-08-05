/**
  * NotificationServiceImpl class
  * 
  * @Author: Epsilon-HuyDQ11
  * Objective: This class is used for process which related to Notification WebService.
  * Completion Date: July 31, 2015
  * 
  * @version: 1.0 
*/
package com.calix.service.impl;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import com.calix.service.NotificationService;
import com.calix.service.RestInvoker;
import com.calix.utils.WSConstants;


/**
 * The Class NotificationServiceImpl.
 */
@Component(name = "com.calix.service.NotificationServiceImpl", metatype = true, immediate = true,
label = "Calix Notification Service", description = "Calix Notification Service")

@Service
public class NotificationServiceImpl implements NotificationService{

	 /** The rest invoker. */
 	@Reference
	 private RestInvoker restInvoker;
	 
	/* (non-Javadoc)
	 * @see com.calix.service.NotificationService#getNotificationHeader()
	 */
	@Override
	public void getNotificationHeader() {
		restInvoker.invoke(WSConstants.ENPOINT_WS.concat(WSConstants.READ_NOTIFICATION_WS), null);
		
	}

	/* (non-Javadoc)
	 * @see com.calix.service.NotificationService#getNotificationDetails()
	 */
	@Override
	public void getNotificationDetails() {
	}

	/* (non-Javadoc)
	 * @see com.calix.service.NotificationService#markAsRead()
	 */
	@Override
	public void markAsRead() {
	}

	
}
