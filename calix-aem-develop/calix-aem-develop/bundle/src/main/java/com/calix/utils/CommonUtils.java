package com.calix.utils;

import com.calix.model.*;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.designer.Style;
import com.day.cq.wcm.foundation.Image;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.scripting.SlingBindings;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author DuongDD1
 * Review by: 
 * Review date: 
 *
 */
public class CommonUtils {
    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    private CommonUtils() {
    }

    private static ObjectMapper jsonMapper = new ObjectMapper();

    public static <T> List<T> parseJsonStringToListObject(final String[] objs, Class<T> classType) {
        final List<T> objList = new ArrayList<T>();
        if (null == objs) {
            return objList;
        }

        for (final String objStr : objs) {
            try {
                final T obj = jsonMapper.readValue(objStr, classType);
                objList.add(obj);
            } catch (final Exception e) {
                logger.error("error parseJsonStringToListObject: " + e.getMessage());
            }
        }
        return objList;
    }
    
	/**
	 * Author(s): DuongDD1
	 * Objective: map the JSON to an appropriate Object
	 * Completion Date: Jul 29, 2015
	 * Revision History: 
	 * @param json
	 * @param classType
	 * @return
	 */
	public static <T> T mapJsonToObject(String json, Class<T> classType) {
		T obj = null;
		if (StringUtils.isBlank(json)) {
			return null;
		}
		try {
			obj = jsonMapper.readValue(json, classType);
		} catch (Exception e) {
			logger.error("error mapJsonToObject: " + e.getMessage());
		}
		return obj;
	}
	
	/**
	 * @author DuongDD1
	 * @objective convertDictionaryToMap
	 * @completion-date Jul 31, 2015
	 * @revision-history
	 * @param dictionary
	 * @param classKey
	 * @param classValue
	 * @return
	 */
	public static <K, V> Map<K, V> convertDictionaryToMap(Dictionary<K, V> dictionary) {
		if (dictionary == null) {
			return null;
		}
		Map<K, V> map = new HashMap<K, V>(dictionary.size());
		Enumeration<K> keys = dictionary.keys();
		while (keys.hasMoreElements()) {
			K key = keys.nextElement();
			map.put(key, dictionary.get(key));
		}
		return map;
	}

    private static Map<String, String> IMAGE_SIZE_MAP = new HashMap();

    public static String getBaseUrl(final SlingHttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String baseUrl = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath();
//        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":"
//                + request.getServerPort() + request.getContextPath();
        return baseUrl;
    }

    public static String getImagePath(final Resource resource) {
        String imgSrc = StringUtils.EMPTY;
        if (resource == null) {
            return imgSrc;
        }
        final Image image = new Image(resource, "image");
        // check if the image from dam
        String fileReference = image.getFileReference();
        if (StringUtils.isNotEmpty(fileReference)) {
            return fileReference;
        }
        // else drag & drop image
        image.setSelector(".img");
        if (image.hasContent()) {
            imgSrc = image.getSrc();
        }
        return imgSrc;
    }

    public static String getImagePathFromCustomImageComponent(final Resource resource, final String imageNodeName) {
        String imgSrc = StringUtils.EMPTY;
        if (resource == null) {
            return imgSrc;
        }
        final Image image = new Image(resource, imageNodeName);
        // check if the image from dam
        String fileReference = image.getFileReference();
        if (StringUtils.isNotEmpty(fileReference)) {
            return fileReference;
        }
        // else drag & drop image
        image.setSelector(".img");
        if (image.hasContent()) {
            imgSrc = image.getSrc();
        }
        return imgSrc;
    }

    /**
     * Get image path for page for both drag & drop image & image from dam
     */
    public static String getPageImagePath(final Resource resource) {
        String imgSrc = StringUtils.EMPTY;
        if (resource == null) {
            return imgSrc;
        }
        final Image image = new Image(resource, "image");
        // check if the image from dam
        String fileReference = image.getFileReference();
        if (StringUtils.isNotEmpty(fileReference)) {
            return fileReference;
        }
        // else drag & drop image
        image.setSelector(".img");
        if (image.hasContent()) {
            imgSrc = image.getSrc();
            imgSrc = imgSrc.replaceAll("image.img", "image/file.img");
        }
        return imgSrc;
    }

    public static TagManager getTagManager(final SlingHttpServletRequest request) {
        final ResourceResolver resourceResolver = request.getResourceResolver();
        return resourceResolver.adaptTo(TagManager.class);
    }

    public static PageManager getPageManager(final SlingHttpServletRequest request) {
        final ResourceResolver resourceResolver = request.getResourceResolver();
        return resourceResolver.adaptTo(PageManager.class);
    }

    /**
     * get current page from request
     *
     * @param request
     * @return
     */
    public static Page getCurrentPage(SlingHttpServletRequest request) {
        Resource resource = request.getResource();
        PageManager pageManager = getPageManager(request);
        return pageManager.getContainingPage(resource);
    }

    public static Style getCurrentStyle(SlingHttpServletRequest request) {
        final ResourceResolver resourceResolver = request.getResourceResolver();
        return resourceResolver.adaptTo(Style.class);
    }

    /**
     * Returns {@link SlingScriptHelper} by slingRequest
     *
     * @param request
     * @return
     */
    public static SlingScriptHelper getSlingScriptHelper(SlingHttpServletRequest request) {
        SlingBindings bindings = (SlingBindings) request.getAttribute(SlingBindings.class.getName());
        return bindings.getSling();
    }

    /**
     * return jcr Session object from request
     *
     * @param request
     */
    public static Session getSession(SlingHttpServletRequest request) {
        ResourceResolver resourceResolver = request.getResourceResolver();
        return resourceResolver.adaptTo(Session.class);
    }

    /**
     * return jcr current node object from request
     *
     * @param request
     */
    public static Node getCurrentNode(SlingHttpServletRequest request) {
        ResourceResolver resourceResolver = request.getResourceResolver();
        return resourceResolver.adaptTo(Node.class);
    }

    public static String sanitize(String string) {
        return string
                .replaceAll("(?i)<script.*?>.*?</script.*?>", "")   // case 1
                .replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "") // case 2
                .replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "")      // case 3
                .replaceAll("(?i).*on.*?=.*?\"", "");              // case
    }

    public static String formatDate(String dateFormat, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(dateFormat);
        String dateValue = simpleDateFormat.format(date);
        return dateValue;
    }

    public static Date convertStringToDateNoTimeZone(String format, String value) {
        SimpleDateFormat sd = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sd.parse(value);
        } catch (ParseException e) {
            logger.error("Error when convert String to Date {}", value);
        }
        return date;
    }

    public static Date convertStringToDate(String format, String value) {
        SimpleDateFormat sd = new SimpleDateFormat(format);
        sd.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = null;
        try {
            date = sd.parse(value);
        } catch (ParseException e) {
            logger.error("Error when convert String to Date {}", value);
        }
        return date;
    }

    /**
     * Gets the date GMT.
     *
     * @param date the date
     * @return the date GMT
     */
    public static Date getDateGMT(Date date) {
        Date dateGMT = new Date();
        if (date != null) {
            TimeZone timeZone = TimeZone.getTimeZone("ICT");
            GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone);
            gregorianCalendar.setTime(date);
            gregorianCalendar.set(GregorianCalendar.HOUR, -8);
            dateGMT = gregorianCalendar.getTime();
        }
        return dateGMT;
    }

    static {
        IMAGE_SIZE_MAP.put("small-image", "220.150");
        IMAGE_SIZE_MAP.put("medium-image", "480.320");
        IMAGE_SIZE_MAP.put("large-image", "770.360");
        IMAGE_SIZE_MAP.put("landscape-image", "370.150");
    }

    //get slides
    public static List<Slide> getSlides(final String[] slidesString) {
        final List<Slide> slides = new ArrayList<Slide>();

        if (null == slidesString) {
            return slides;
        }

        for (final String slideString : slidesString) {
            try {
                final Slide slide = jsonMapper.readValue(slideString, Slide.class);
                if (null != slide) {
                    String link = slide.getLinkCTA();
                    if (org.apache.commons.lang.StringUtils.isNotBlank(link)) {
                        slide.setLinkCTA(getFormattedLink(link));
                        slide.setTargetCTA(getTargetByLink(link));
                    }
                    String btnType = slide.getButtonType();
                    slide.setButtonType("grnBtn");
                    String title = slide.getTitleCTA();
                    if (org.apache.commons.lang.StringUtils.isNotBlank(btnType)) {
                        if (StringUtils.containsIgnoreCase(btnType, "green")) {
                            if (StringUtils.isNotBlank(title) && title.length() > 10) {
                                slide.setButtonType("grnBtnLg");
                            } else {
                                slide.setButtonType("grnBtn");
                            }
                        } else if (StringUtils.containsIgnoreCase(btnType, "white")) {
                            slide.setButtonType("grnBtn whiteBtn");
                            if (StringUtils.isNotBlank(title) && title.length() > 10) {
                                slide.setButtonType("grnBtnLg whiteBtn");
                            }

                        } else if (StringUtils.containsIgnoreCase(btnType, "outline")) {
                            slide.setButtonType("grnBtn outlineBtn");
                            if (StringUtils.isNotBlank(title) && title.length() > 10) {
                                slide.setButtonType("grnBtnLg outlineBtn");
                            }
                        } else if (StringUtils.containsIgnoreCase(btnType, "text")) {
                            slide.setButtonType("link");
                        }
                    }

                }
                slides.add(slide);
            } catch (final Exception e) {
                logger.error("GET Slides Error " + e.getMessage());
            }
        }
        return slides;
    }

    /**
     * Author(s): 
     * Objective: format url
     * Completion Date: Jul 29, 2015
     * Revision History: 
     * @param link
     * @return formatted url
     */
	public static String getFormattedLink(String link) {
		String result = link;
		if (StringUtils.isNotBlank(result)) {
			if (!result.toLowerCase().startsWith(Constants.PREFIX_HTTP)
					|| !result.toLowerCase().startsWith(Constants.PREFIX_HTTPS)) {
				if (!Constants.EXTENSION_PATTERN_LINK.matcher(result).matches() && !result.startsWith("#")) {
					result += Constants.SUFFIX_HTML;
				}
			}
		}
		return result;
	}

    /**
     * Author(s): 
     * Objective: Get target by link
     * Completion Date: Jul 29, 2015
     * Revision History: 
     * @param link
     * @return target
     */
	public static String getTargetByLink(String link) {
		String target = Constants.BLANK_TARGET;
		if (StringUtils.isNotBlank(link)) {
			if (!link.toLowerCase().startsWith(Constants.PREFIX_HTTP)
					|| !link.toLowerCase().startsWith(Constants.PREFIX_HTTPS)) {
				if (!Constants.EXTENSION_PATTERN_LINK.matcher(link).matches() && !link.startsWith("#")) {
					target = Constants.SEFL_TARGET;
				}
			}
		}
		return target;
	}
    
	/**
	 * Author(s): DuongDD1
	 * Objective: Get style by url 
	 * Completion Date: Jul 29, 2015
	 * Revision History: 
	 * @param link
	 * @return style
	 */
	public static String getStyleByLink(String link) {
		String style = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(link) && (link.toLowerCase().startsWith(Constants.PREFIX_HTTP)
				|| link.toLowerCase().startsWith(Constants.PREFIX_HTTPS))) {
			style = Constants.EXTERNAL_ICON;
		}
		return style;
	}

    //get products
    public static List<RelatedProduct> getProducts(final String[] productsString) {
        final List<RelatedProduct> products = new ArrayList<RelatedProduct>();

        if (null == productsString) {
            return products;
        }

        for (final String productString : productsString) {
            try {
                final RelatedProduct product = jsonMapper.readValue(productString, RelatedProduct.class);

                products.add(product);
            } catch (final Exception e) {
                logger.error("GET Products Error " + e.getMessage());
            }
        }
        return products;
    }

    // Gets the list video group.
    public static List<VideoGroup> getListVideoGroup(SlingHttpServletRequest slingRequest, String path) {
        List<VideoGroup> videoGroups = new ArrayList<VideoGroup>();
        if (org.apache.commons.lang.StringUtils.isNotBlank(path)) {
            Video video = new Video();
            video = getVideo(slingRequest, path);

            String wistaId = video.getWistiaId();
            if (org.apache.commons.lang.StringUtils.isNotBlank(wistaId)) {
                VideoGroup group = new VideoGroup();
                List<Video> videos = new ArrayList<Video>();
                videos.add(video);
                group.setVideos(videos);
                videoGroups.add(group);
            }

        }

        return videoGroups;
    }

    public static Video getVideo(SlingHttpServletRequest slingRequest, String path) {
        Video video = new Video();
        try {
            Node videoNode = JcrUtils.getNode(slingRequest, path + Constants.JCR_CONTENT_NARROW_CONTAINER_VIDEO);

            video.setTitle(JcrUtils.getStringValue(videoNode, Constants.TITLE));
            video.setDescription(JcrUtils.getStringValue(videoNode, Constants.DESCRIPTION));
            video.setWistiaId(JcrUtils.getStringValue(videoNode, Constants.WISTIA_ID));
            video.setImageLink(JcrUtils.getStringValue(videoNode, Constants.IMAGE_LINK));
        } catch (RepositoryException e) {
            logger.error("Error when convert search result {}", e);
        }
        return video;
    }

    //convert CTA for current node

    /**
     * Convert node to CTA.
     *
     * @return the CTA
     */
    public static CTA convertNodeToCTA(Node currentNode, String index) {
        CTA cta = null;
        if (null != currentNode) {
            cta = new CTA();
            String title = JcrUtils.getStringValue(currentNode, "titleCTA" + index);
            String link = JcrUtils.getStringValue(currentNode, "linkCTA" + index);

            String target = "_blank";
            if (org.apache.commons.lang.StringUtils.isNotBlank(link)) {
                if (!link.toLowerCase().startsWith("http")) {
                    if (!Constants.EXTENSION_PATTERN_LINK.matcher(link).matches() && !link.startsWith("#")) {
                        target = "_self";
                        link += Constants.SUFFIX_HTML;
                    }
                }
            }

            String btnType = JcrUtils.getStringValue(currentNode, "btnType" + index);
            cta.setTitle(title);
            cta.setLink(link);
            cta.setButtonType(btnType);
            cta.setDisplay(false);
            cta.setTarget(target);
            cta.setStyle("grnBtn");
            if (org.apache.commons.lang.StringUtils.isNotBlank(title) && org.apache.commons.lang.StringUtils.isNotBlank(link)) {
                cta.setDisplay(true);
            }

            if (org.apache.commons.lang.StringUtils.isNotBlank(btnType)) {
                if (org.apache.commons.lang.StringUtils.containsIgnoreCase(btnType, "green")) {
                    cta.setStyle("grnBtn");
                } else if (org.apache.commons.lang.StringUtils.containsIgnoreCase(btnType, "white")) {
                    cta.setStyle("grnBtn whiteBtn");
                } else if (org.apache.commons.lang.StringUtils.containsIgnoreCase(btnType, "outline")) {
                    cta.setStyle("grnBtn outlineBtn");
                } else if (org.apache.commons.lang.StringUtils.containsIgnoreCase(btnType, "text")) {
                    cta.setStyle("link");
                }

            }
        }
        return cta;
    }

    //get tiles
    public static List<Tile> getTiles(final String[] tilesString) {
        final List<Tile> tiles = new ArrayList<Tile>();

        if (null == tilesString) {
            return tiles;
        }

        for (final String tileString : tilesString) {
            try {
                final Tile tile = jsonMapper.readValue(tileString, Tile.class);
                tiles.add(tile);
            } catch (final Exception e) {
                logger.error("GET Titles Exception " + e.getMessage());
            }
        }
        return tiles;
    }
    //get btntype
    public static String getBtnTypeStyleByTitle(String title, String btnType){
        String style = "grnBtnLg";
        if (StringUtils.isNotBlank(btnType)) {
            if (StringUtils.containsIgnoreCase(btnType, "green")) {
                if (StringUtils.isNotBlank(title) && title.length() > 8) {
                    style =  "grnBtnLg";
                } else {
                    style =  "grnBtn";
                }
            } else if (StringUtils.containsIgnoreCase(btnType, "white")) {
                style = "grnBtn whiteBtn";
                if (StringUtils.isNotBlank(title) && title.length() > 8) {
                    style = "grnBtnLg whiteBtn";
                }
            } else if (StringUtils.containsIgnoreCase(btnType, "outline")) {
                style = "grnBtn outlineBtn";
                if (StringUtils.isNotBlank(title) && title.length() > 8) {
                    style = "grnBtnLg outlineBtn";
                }
            } else if (StringUtils.containsIgnoreCase(btnType, "text")) {
                style = "link";
            }
        }
        return style;
    }
}
