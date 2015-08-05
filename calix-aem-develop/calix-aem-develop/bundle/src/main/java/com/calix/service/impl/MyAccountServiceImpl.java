/**
  * MyAccountServiceImpl.java
  * @Author: Epsilon-KhoaPD3
  * 
  * Objective: Provide services about my account info
  * Completion Date: July 31, 2015
  * 
  * @version: 1.0 
*/
package com.calix.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calix.model.Account;
import com.calix.service.MyAccountService;
import com.calix.service.RestInvoker;
import com.calix.utils.WSConstants;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * The Class MyAccountServiceImpl.
 */
@Component(name = "com.calix.service.MyAccountServiceImpl", metatype = true, immediate = true,
label = "Calix My Account Service", description = "Calix My Account Service")

@Service
public class MyAccountServiceImpl implements MyAccountService{
	
	/** The Constant RECORDS. */
	public static final String RECORDS = "records";
    
	/** The Constant logger. */
    private static final Logger  logger = LoggerFactory.getLogger(MyAccountServiceImpl.class);
	 /** The rest invoker. */
 	@Reference
	private RestInvoker restInvoker;
	 
	/* (non-Javadoc)
	 * @see com.calix.service.MyAccountService#getAccountTeamMembers(java.lang.String)
	 */
	@Override
	public List<Account> getAccountTeamMembers(final String userIdRequest) {
		List<Account> accounts = null;
		try{

			String response = restInvoker.invoke(WSConstants.ENPOINT_WS.concat(WSConstants.ACCOUNT_TEAM_WS), userIdRequest);
			
			/*Init*/
	    	ObjectMapper jsonMapper = new ObjectMapper();
			JSONObject result = new JSONObject(response);
			
	    	/*process json to get account list*/
			if(result.has(RECORDS)){
				JSONArray accountsJson = (JSONArray) result.get(RECORDS);
				if (accountsJson != null) {
			    	accounts = new ArrayList<Account>();
			        for (int index = 0, length = accountsJson.length(); index < length; index++) {
			            JSONObject accountJson = (JSONObject) accountsJson.get(index);
			            Account account = jsonMapper.readValue(accountJson.toString(), Account.class);
			            if(null != account){
			                accounts.add(account);
			            }
			        }
			    }
			}
		} catch(Exception e){
			logger.error("Exeption When Get Account Team Member: {}",  e);
			accounts = null;
		}
		return accounts;
	}

	
}
