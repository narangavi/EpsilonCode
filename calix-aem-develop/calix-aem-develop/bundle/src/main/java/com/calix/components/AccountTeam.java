/**
  * AccountTeam.java
  * @Author: Epsilon-KhoaPD3
  * 
  * Objective: Get account team members of current user
  * Completion Date: Jul 31, 2015
  * 
  * @version: 1.0 
*/
package com.calix.components;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.*;
import com.calix.service.MyAccountService;
import com.calix.utils.JcrUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;

import java.util.List;


/**
 * The Class AccountTeam.
 */
public class AccountTeam extends WCMUse {

    /** The Constant DEFAULT_NUMBER_OF_ACCOUNT_TO_DISPLAY. */
    private static final int DEFAULT_NUMBER_OF_ACCOUNT_TO_DISPLAY = 2;

	/** The Constant NUMBER. */
	private static final String NUMBER = "number";

	/** The Constant TITLE. */
	private static final String TITLE = "title";

	/** The Constant DEFAULT_TITLE. */
	private static final String DEFAULT_TITLE = "My Account Team";

	/** The Constant USER_ID. */
    public static final String USER_ID = "userId";
    
	/** The Constant logger. */
    private static final Logger  logger = LoggerFactory.getLogger(AccountTeam.class);
        
    /** The accounts. */
    private List<Account> accounts;
	
	/** The title. */
	private String title;
	
	/** The displayed account number. */
	public int displayedAccountNumber;
	
	/** The has data. */
	private boolean hasData = true;
	
    /** Gets the accounts.
	 * 
	 * @return the accounts
	 */
    public List<Account> getAccounts() {
		return accounts;
	}
    
    /** Gets the title.
	 * 
	 * @return the title
	 */
    public String getTitle() {
		return title;
	}
	
	/**
	 * Gets the displayed account number.
	 * 
	 * @return the displayed account number
	 */
	public int getDisplayedAccountNumber() {
		return displayedAccountNumber;
	}
	
	/**
	 * Gets the checks for data.
	 * 
	 * @return the checks for data
	 */
	public boolean getHasData() {
		return hasData;
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
            MyAccountService myAccountService = this.getSlingScriptHelper().getService(MyAccountService.class);
            
            processConfigInfoInCurrentNode(currentNode);
            
            JSONObject request = initRequestData();            
            accounts = myAccountService.getAccountTeamMembers(request.toString());
            
            if(accounts == null || accounts.size() == 0) {
            	hasData = false;
            }
        }catch (Exception e){
            logger.error("Exeption Account Team: {}", e);
            hasData = false;
        }
    }

	/**
	 * Inits the request data.<br/>
	 * Put user id as json request
	 * 
	 * @return the JSON object
	 * @throws JSONException
	 *             the JSON exception
	 */
	private JSONObject initRequestData() throws JSONException {
		JSONObject request = new JSONObject();
		try {
			String currentUserId = "005V0000001zHuc";
			request.put(USER_ID, currentUserId);
		} catch (Exception e){
			logger.error("Exeption When Init Request Data: ", e);
		}
		return request;
	}

	/**
	 * Process config info in current node.<br/>
	 * Get title configuration. Default value is "My Account Team". <br/>
	 * Get number of accounts to display. Default value is 2 
	 * 
	 * @param currentNode
	 *            the current node
	 */
	private void processConfigInfoInCurrentNode(Node currentNode) {
		//process title
		title = JcrUtils.getStringValue(currentNode, TITLE);
		title = StringUtils.isNotBlank(title) ? title : DEFAULT_TITLE;
		
		//process displayed account number
		try{
			displayedAccountNumber = Integer.parseInt(JcrUtils.getStringValue(currentNode, NUMBER));
			displayedAccountNumber = displayedAccountNumber <= 0 ? DEFAULT_NUMBER_OF_ACCOUNT_TO_DISPLAY : displayedAccountNumber;
		} catch (Exception e) {
			logger.error("Exeption When Parse Displayed Account Number: ", e);
			displayedAccountNumber = DEFAULT_NUMBER_OF_ACCOUNT_TO_DISPLAY;
		}
	}
}
