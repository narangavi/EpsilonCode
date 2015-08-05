/**
  * MyAccountService.java
  * @Author: Epsilon-KhoaPD3
  * 
  * Objective: Provide service contract for my account
  * Completion Date: Jul 31, 2015
  * 
  * @version: 1.0 
*/
package com.calix.service;

import java.util.List;

import com.calix.model.Account;

/**
 * The Interface MyAccountService.
 */
public interface MyAccountService {

	/**
	 * Gets the account team members by user id as json string.<br/>
	 *
	 * @return the account team members
	 */
	public List<Account> getAccountTeamMembers(final String userIdRequest);
}
