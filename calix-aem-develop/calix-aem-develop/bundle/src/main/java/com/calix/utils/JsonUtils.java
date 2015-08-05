package com.calix.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class JsonUtils.
 */
public class JsonUtils {
	
	/** The Constant logger. */
	private static final Logger  LOGGER = LoggerFactory.getLogger(JsonUtils.class);
	
	/**
	 * Gets the JSON value.
	 *
	 * @param key the key
	 * @param obj the JSON Object
	 * @return the JSON value
	 */
	public static String getJsonValue(String key, JSONObject obj){
		String jsonValue = StringUtils.EMPTY;
		if(StringUtils.isNotBlank(key) && obj != null){
			if(obj.has(key)){
				try {
					jsonValue = obj.getString(key);
				} catch (JSONException e) {
					LOGGER.error("Error when get JSON value {}", e);
				}
			}
		}
		return jsonValue;
	}
	
	
	/**
	 * Gets the JSON array.
	 *
	 * @param key the key
	 * @param obj the JSON Object
	 * @return the JSON array
	 */
	public static JSONArray getJsonArray(String key, JSONObject obj){
		JSONArray jsonArray = new JSONArray();
		if(StringUtils.isNotBlank(key) && obj != null){
			if(obj.has(key)){
				try {
					jsonArray = obj.getJSONArray(key);
				} catch (JSONException e) {
					LOGGER.error("Error when get JSON array {}", e);
				}
			}
		}
		return jsonArray;
	}
	
	
	
	/**
	 * Gets the json object.
	 *
	 * @param key the key
	 * @param obj the obj
	 * @return the json object
	 */
	public static JSONObject getJsonObject(String key, JSONObject obj){
		JSONObject jsonObject = new JSONObject();
		if(StringUtils.isNotBlank(key) && obj != null){
			if(obj.has(key)){
				try {
					jsonObject = obj.getJSONObject(key);
				} catch (JSONException e) {
					LOGGER.error("Error when get JSON object {}", e);
				}
			}
		}
		return jsonObject;
	}
}
