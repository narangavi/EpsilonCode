package com.calix.service;

/**
 * The Interface NotificationService.
 */
public interface NotificationService {

	/**
	 * Gets the notification header.
	 *
	 * @return the notification header
	 */
	public void getNotificationHeader();
	
	/**
	 * Gets the notification details.
	 *
	 * @return the notification details
	 */
	public void getNotificationDetails();
	
	/**
	 * Mark notification as read.
	 */
	public void markAsRead();
}
