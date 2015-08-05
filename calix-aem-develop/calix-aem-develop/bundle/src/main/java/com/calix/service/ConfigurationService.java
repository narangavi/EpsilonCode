/**
  * ConfigurationService class
  * 
  * @Author: Epsilon-DuongDD1
  * Objective: This class is used for read all the configurations from OSGI. 
  * Completion Date: July 31, 2015
  * 
  * @version: 1.0 
*/
package com.calix.service;

import java.io.IOException;
import java.util.Dictionary;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.PropertyUnbounded;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ConfigurationService.
 */
@Component(name = "com.calix.service.ConfigurationService", metatype = true, immediate = true, label = "Calix Configuration Service", description = "Calix Configuration Service")
@Service(ConfigurationService.class)
public class ConfigurationService {
	
	/** The config admin. */
	@Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY, policy = ReferencePolicy.STATIC)
	private static ConfigurationAdmin configAdmin = null;
	
	/** The conf. */
	private static Configuration conf = null;
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(ConfigurationService.class);

	/** The Constant AUTHENTICATION_WS. */
	@Property(unbounded = PropertyUnbounded.DEFAULT)
	public static final String AUTHENTICATION_WS = "authentication.ws";
	
	/** The Constant ENDPOINT_WS. */
	@Property(unbounded = PropertyUnbounded.DEFAULT)
	private static final String ENDPOINT_WS = "endpoint.ws";
	
	/** The Constant USER_REGISTRATION_WS. */
	@Property(unbounded = PropertyUnbounded.DEFAULT)
	private static final String USER_REGISTRATION_WS = "user.registration.ws";
	
	/** The Constant ACCOUNT_TEAM_WS. */
	@Property(unbounded = PropertyUnbounded.DEFAULT)
	private static final String ACCOUNT_TEAM_WS = "account.team.ws";
	
	/** The Constant USER_REGISTRATION_SERVLET. */
	@Property(unbounded = PropertyUnbounded.DEFAULT)
	private static final String USER_REGISTRATION_SERVLET = "user.registration.servlet";
	
	/** The Constant NOTIFICATION_SERVLET. */
	@Property(unbounded = PropertyUnbounded.DEFAULT)
	private static final String NOTIFICATION_SERVLET = "notification.servlet";
	
	/** The Constant READ_NOTIFICATION_WS. */
	@Property(unbounded = PropertyUnbounded.DEFAULT)
	private static final String READ_NOTIFICATION_WS = "read.notification.servlet";

	/**
	 * Activate.
	 *
	 * @param context the context
	 */
	@Activate
	protected void activate(BundleContext context) {
		try {
			configAdmin = (ConfigurationAdmin) context
					.getService(context.getServiceReference(ConfigurationAdmin.class.getName()));
			conf = configAdmin.getConfiguration(ConfigurationService.class.getName());
		} catch (IOException e) {
			logger.error("Exception when activate the ConfigurationService {}", e);
		}
	}

	/**
	 * Gets the configuration value by key.
	 *
	 * @param key the key
	 * @return the value of configuration
	 */
	public static String getConfig(String key) {
		if (null != conf && null != conf.getProperties() && null != conf.getProperties().get(key)) {
			return conf.getProperties().get(key).toString();
		} else {
			logger.error("ConfigurationService: configuration for key {} is null", key);
		}
		return null;
	}

	/**
	 * Gets all the configurations.
	 *
	 * @return the the map of configuration
	 */
	public static Dictionary<?, ?> getConfigs() {
		if (null != conf && null != conf.getProperties()) {
			return conf.getProperties();
		} else {
			logger.error("ConfigurationService: properties is null");
		}
		return null;
	}
}
