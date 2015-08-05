package com.calix.components;

import javax.jcr.Node;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.CtaButton;
import com.calix.model.TeaserItem;
import com.calix.model.TextArea;
import com.calix.utils.JcrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Teaser.
 */
public class Teaser extends WCMUse {

    /**
     * The teaser item.
     */
    private TeaserItem teaserItem;

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(Teaser.class);

    /**
     * Get the teaser item.
     *
     * @param teaserItem the new teaser item
     */
    public TeaserItem getTeaserItem() {
        return teaserItem;
    }

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
        SlingHttpServletRequest slingRequest = this.getRequest();
        Node currentNode = slingRequest.getResource().adaptTo(Node.class);
        teaserItem = new TeaserItem();
        boolean displayTopCurve = JcrUtils.getBooleanValue(currentNode, "displaycurvetop");
        boolean displayBottomCurve = JcrUtils.getBooleanValue(currentNode, "displaycurvebottom");
        String teaserTitle = getProperties().get("title", StringUtils.EMPTY);
        teaserItem.setDisplayTopCurve(displayTopCurve);
        teaserItem.setDisplayBottomCurve(displayBottomCurve);
        teaserItem.setTeaserTitle(teaserTitle);
        String cssClass = getCssClass(teaserItem);
        teaserItem.setCssClass(cssClass);
        //get properties value for primary text area
        TextArea primaryTextArea = getPrimaryTextArea();
        teaserItem.setPrimaryTextArea(primaryTextArea);

        // get properties value for secondary text area
        TextArea secondaryTextArea = getSecondaryTextArea();
        teaserItem.setSecondaryTextArea(secondaryTextArea);
    }

    /**
     * Gets the primary text area.
     *
     * @return the primary text area
     */
    private TextArea getPrimaryTextArea() {
        TextArea primaryTextArea = new TextArea();
        String headLine = getProperties().get("teaserTitle1", StringUtils.EMPTY);
        String bodyText = getProperties().get("teaserCopy1", StringUtils.EMPTY);
        String imagePath = getProperties().get("image1", StringUtils.EMPTY);
        // get properties for primary CTA
        String primaryTitle = getProperties().get("titleCTA1", StringUtils.EMPTY);
        String primaryLink = getProperties().get("linkCTA1", StringUtils.EMPTY);

        boolean isNewWindow1 = true;
        if (StringUtils.isNotBlank(primaryLink) && !primaryLink.toLowerCase().startsWith("http")) {
            primaryLink = primaryLink.concat(".html");
            isNewWindow1 = false;
        }
        String primaryButtonType = "link";
        CtaButton primaryCta = new CtaButton();
        primaryCta.setTitle(primaryTitle);
        primaryCta.setLink(primaryLink);

        String primaryTarget = getCtaTarget(isNewWindow1);
        primaryCta.setTarget(primaryTarget);

        // get style for CTA button
        String primaryCtaStyle = getCtaButtonStyle(primaryButtonType);
        primaryCta.setStyle(primaryCtaStyle);
        primaryCta.setButtonType(primaryButtonType);

        primaryTextArea.setHeadLine(headLine);
        primaryTextArea.setBodyText(bodyText);
        primaryTextArea.setImagePath(imagePath);
        primaryTextArea.setPrimaryCta(primaryCta);

        return primaryTextArea;
    }

    /**
     * Gets the secondary text area.
     *
     * @return the secondary text area
     */
    private TextArea getSecondaryTextArea() {
        TextArea secondaryTextArea = new TextArea();
        String headLine = getProperties().get("teaserTitle2", StringUtils.EMPTY);
        String bodyText = getProperties().get("teaserCopy2", StringUtils.EMPTY);
        String imagePath = getProperties().get("image2", StringUtils.EMPTY);

        // get properties for primary CTA
        String primaryTitle = getProperties().get("titleCTA2", StringUtils.EMPTY);
        String primaryLink = getProperties().get("linkCTA2", StringUtils.EMPTY);
        boolean isNewWindow1 = true;
        if (StringUtils.isNotBlank(primaryLink) && !primaryLink.toLowerCase().startsWith("http")) {
            primaryLink = primaryLink.concat(".html");
            isNewWindow1 = false;
        }
        String primaryButtonType = "link";
        CtaButton primaryCta = new CtaButton();
        primaryCta.setTitle(primaryTitle);
        primaryCta.setLink(primaryLink);

        String primaryTarget = getCtaTarget(isNewWindow1);
        primaryCta.setTarget(primaryTarget);

        // get style for CTA button
        String primaryCtaStyle = getCtaButtonStyle(primaryButtonType);
        primaryCta.setStyle(primaryCtaStyle);
        primaryCta.setButtonType(primaryButtonType);

        secondaryTextArea.setHeadLine(headLine);
        secondaryTextArea.setBodyText(bodyText);
        secondaryTextArea.setImagePath(imagePath);
        secondaryTextArea.setPrimaryCta(primaryCta);
        return secondaryTextArea;
    }

    private String getCssClass(TeaserItem teaserItem) {
        String cssClass = "";
        if (teaserItem != null) {
            if (teaserItem.isDisplayTopCurve()) {
                cssClass += " topCurve";
            }
            if (teaserItem.isDisplayBottomCurve()) {
                cssClass += " bottomCurve";
            }
        }
        return cssClass;
    }

    /**
     * Gets the cta button style.
     *
     * @param btnType the btn type
     * @return the cta button style
     */
    private String getCtaButtonStyle(String btnType) {
        String style = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(btnType)) {
            if (StringUtils.containsIgnoreCase(btnType, "green")) {
                style = "grnBtn";
            } else if (StringUtils.containsIgnoreCase(btnType, "white")) {
                style = "grnBtn whiteBtn";
            } else if (StringUtils.containsIgnoreCase(btnType, "outline")) {
                style = "grnBtn outlineBtn";
            } else if (StringUtils.containsIgnoreCase(btnType, "text")) {
                style = "link";
            }
        }
        return style;
    }

    /**
     * Gets the cta target.
     *
     * @param isOpenOnNewWindow the is open on new window
     * @return the cta target
     */
    private String getCtaTarget(boolean isOpenOnNewWindow) {
        String target = StringUtils.EMPTY;
        if (isOpenOnNewWindow) {
            target = "_blank";
        }
        return target;
    }
}
