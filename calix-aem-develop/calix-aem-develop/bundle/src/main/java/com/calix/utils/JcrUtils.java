package com.calix.utils;

import com.calix.model.Spotlight;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class JcrUtils {

    private static final Logger logger = LoggerFactory.getLogger(JcrUtils.class);

    public static Node getNode(final SlingHttpServletRequest request, String nodePath)
            throws RepositoryException {
        final Session session = CommonUtils.getSession(request);
        final Node root = session.getRootNode();
        nodePath = convertToRelativePath(nodePath);
        if (root.hasNode(nodePath)) {
            return root.getNode(nodePath);
        }
        return null;
    }

    private static String convertToRelativePath(final String nodePath) {
        if (nodePath.charAt(0) == CharPool.FORWARD_SLASH) {
            return nodePath.substring(1, nodePath.length());
        }
        return nodePath;
    }

    /**
     * Gets value of a property by propertyName. If no <code>propertyName</code> found in {@link Node}, or blank value, or exception, the <code>null</code> value will be returned
     *
     * @param node         a {@link Node}
     * @param propertyName
     * @return string
     */
    public static String getStringValue(Node node, String propertyName) {
        String val = null;
        try {
            if (node != null && node.hasProperty(propertyName)) {
                val = node.getProperty(propertyName).getValue().getString();
            }
        } catch (Exception ex) {
            logger.error("Exception: get string value [" + propertyName + "] error:" + ex.toString(), ex);
        }
        return val;
    }

    /**
     * Gets String[] value of a property by propertyName with type String[]. If no <code>propertyName</code> found in {@link Node}, or blank value, or exception, the <code>null</code> value will be returned
     *
     *
     * @param node         a {@link Node}
     * @param propertyName
     * @return String[]
     */
    public static String[] getStringArrayValue(Node node, String propertyName) {
        String[] ls = null;
        try {
            if (node != null && node.hasProperty(propertyName)) {
                Property references = node.getProperty(propertyName);
                if (references.isMultiple()){
                    Value[] values = references.getValues();
                    ls = new String[values.length];
                    int count = 0;
                    for (Value v : values) {
                        ls[count++] = v.getString();
                    }
                }else {
                    Value value = references.getValue();
                    ls = new String[1];
                    ls[0] = value.getString();
                }

            }
        } catch (Exception ex) {
            logger.error("Exception: get string value [" + propertyName + "] error:" + ex.toString(), ex);
        }
        return ls;
    }

    /***
     * Get child node by Resource Type
     *
     * @param node
     * @param resourceType
     * @return {@link Node}
     */
    public static Node findNodeFromNodeByResourceType(final Node node, final String resourceType) {
        try {
            if (null == node) {
                return null;
            }
            if (node.hasProperty(Constants.SLING_RESOURCE_TYPE)
                    && resourceType.equals(getStringValue(node, Constants.SLING_RESOURCE_TYPE))) {
                return node;
            }
            if (node.hasNodes()) {
                final NodeIterator children = node.getNodes();
                while (children.hasNext()) {
                    final Node result = findNodeFromNodeByResourceType(children.nextNode(), resourceType);
                    if (null != result) {
                        return result;
                    }
                }
            }
            return null;
        } catch (final RepositoryException e) {
            return null;
        }
    }

    public static Spotlight getSpotlightByPath(String path, final SlingHttpServletRequest request ){
        Spotlight spotlight = new Spotlight();
        try {
            final Session session = CommonUtils.getSession(request);
            if (path != null && !path.isEmpty()) {
                Node node = session.getNode(path + "/jcr:content");

                if (node != null){
                    spotlight.setLinkAction(node.getPath());
                    if(node.hasProperty("jcr:title")){
                        spotlight.setTitle(node.getProperty("jcr:title").getValue().getString());
                    }
                    if(node.hasProperty("image")){
                        spotlight.setImage(node.getProperty("image").getValue().getString());
                    }
                    if(node.hasProperty("jcr:description")){
                        spotlight.setDescription(node.getProperty("jcr:description").getValue().getString());
                    }
                    if(node.hasProperty("cq:lastModified")){
                        String dateString = node.getProperty("cq:lastModified").getValue().getString();
                        if(dateString != null && !dateString.isEmpty()){
                            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                            sd.setTimeZone(TimeZone.getTimeZone("GMT"));
                            Date date =  null;
                            try {
                                date = sd.parse(dateString);
                                spotlight.setDate(CommonUtils.formatDate(Constants.DATE_FORMAT_MMM_DD_YYYY, date));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if(node.hasProperty("wistiaId")){
                        String id = node.getProperty("wistiaId").getValue().getString();
                        String shortId = id.substring(7);
                        spotlight.setWistiaId(id);
                        spotlight.setShortWistiaId(shortId);
                    }
                }
            }
        }catch(Exception ex){
            logger.error("Exception getVideoByPath: ", ex);
        }
        return spotlight;
    }


    public static Spotlight getSpotlightByPath(String path, final SlingHttpServletRequest request, int characterLimit ){
        Spotlight spotlight = new Spotlight();
        try {
            final Session session = CommonUtils.getSession(request);
            if (path != null && !path.isEmpty()) {
                Node node = session.getNode(path );

                String title = JcrUtils.getStringValue(node,"jcr:content/jcr:title") ;
                String createDate = JcrUtils.getStringValue(node,"jcr:content/jcr:created");
                String modifiedDate = "";
                if(createDate != null && !createDate.isEmpty()){

                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                    sd.setTimeZone(TimeZone.getTimeZone("GMT"));
                    Date date =  null;
                    try {
                        date = sd.parse(createDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    modifiedDate = CommonUtils.formatDate(Constants.DATE_FORMAT_MMM_DD_YYYY, date);

                }
                if(title != null && title.length() > characterLimit){
                    title = title.substring(0,characterLimit);
                }
                spotlight.setTitle(title);
                spotlight.setDate(modifiedDate);

            }
        }catch(Exception ex){
            logger.error("Exception getVideoByPath: ", ex);
        }
        return spotlight;
    }

    /**
     *
     * @param node
     * @param propertyName
     * @param defaultValue
     * @return
     */
    public static boolean getBooleanValue(Node node, String propertyName, boolean defaultValue) {
        boolean val = defaultValue;
        try {
            if (node != null && node.hasProperty(propertyName)) {
                val = node.getProperty(propertyName).getValue().getBoolean();
            }
        } catch (Exception ex) {
            logger.error("Exception: get string value [" + propertyName + "] error:" + ex.toString(), ex);
        }
        return val;
    }

    /***
     * get boolean value of property from node by property name.
     * @param node
     * @param propertyName
     * @return value as boolean. If property is not exits, return false.
     */
    public static boolean getBooleanValue(Node node, String propertyName) {
        boolean val = false;
        try {
            if (node != null && node.hasProperty(propertyName)) {
                val = node.getProperty(propertyName).getValue().getBoolean();
            }
        } catch (Exception ex) {
            logger.error("Exception: get boolean value [" + propertyName + "] error:" + ex.toString(),ex);
        }
        return val;
    }
    
    public static Node getNodeByPagePath(final SlingHttpServletRequest slingRequest, final String path, final String property, final String propertyValue){
        Node node = null;
        try {
            if(StringUtils.isNotBlank(path)) {
                final ResourceResolver resourceResolver = slingRequest.getResourceResolver();
                final QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
                final Session session = CommonUtils.getSession(slingRequest);
                
                Map<String, String> params = new HashMap<String, String>();
                QueryBuilderUtils.buildRootParam(params, 0, 0, path);
                params.put(Constants.PROPERTY, property);
                params.put(Constants.PROPERTY_VALUE, propertyValue);
                Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);

                SearchResult result = query.getResult();
                if (result.getHits().size() > 0) {
                    String nodePath = result.getHits().get(0).getPath();
                    node = JcrUtils.getNode(slingRequest, nodePath);
                }
            }
        }
        catch(RepositoryException e) {
            logger.error("Error when get node", e);
        }
        catch(Exception e) {
            logger.error("Error when search node: ", e);
        }
        return node;
    }
    
    public static boolean hasNodeByPagePath(final SlingHttpServletRequest slingRequest, final String path, final String property, final String propertyValue){
        boolean hasNode = false;
        Node node = getNodeByPagePath(slingRequest, path, property, propertyValue);
        if (node != null) {
        	hasNode = true;
        }
        return hasNode;
    }
    
    public static Node findPageByNode(final SlingHttpServletRequest slingRequest, final Node node) {
        try {
            if (null == node) {
                return null;
            }
            Page page = QueryBuilderUtils.getPageByNode(slingRequest, node);
            if (null != page) {
                return node;
            }  
            final Node result = findPageByNode(slingRequest, node.getParent());
            if (null != result) {
                return result;
            }
            return null;
        } catch (final RepositoryException e) {
            return null;
        }
    }
}