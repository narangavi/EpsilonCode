/**
  * ConfigurationUtils class
  * 
  * @Author: Epsilon-DuongDD1
  * Objective: This is a utility class which used for read configurations from OSGI. 
  * Completion Date: July 31, 2015
  * 
  * @version: 1.0 
*/
package com.calix.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calix.service.ConfigurationService;
import com.calix.servlets.UserRegistrationServlet;

/**
 * The Class ConfigurationUtils.
 */
public class ConfigurationUtils {
	
	/** The Constant logger. */
	private final static Logger logger = LoggerFactory.getLogger(UserRegistrationServlet.class);

	/**
	 * Gets the config.
	 *
	 * @param key the key
	 * @return the config
	 */
	public static String getConfig(String key) {
		String result = ConfigurationService.getConfig(key);
		return result;
	}

	/**
	 * Gets the configs.
	 *
	 * @return the configs
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getConfigs() {
		Map<String, String> map = (Map<String, String>) CommonUtils.convertDictionaryToMap(ConfigurationService.getConfigs());
		return map;
	}
}
