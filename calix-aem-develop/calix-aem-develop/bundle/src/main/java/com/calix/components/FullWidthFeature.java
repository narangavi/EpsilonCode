package com.calix.components;

import javax.jcr.Node;

import com.calix.utils.CommonUtils;
import com.calix.utils.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.CtaButton;
import com.calix.model.FullWidthFeatureItem;
import com.calix.model.TextArea;
import com.calix.utils.JcrUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * The Class FullWidthFeature.
 */
public class FullWidthFeature extends WCMUse {

    /**
     * The feature item.
     */
    private FullWidthFeatureItem featureItem;
    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(FullWidthFeature.class);

    /**
     * Gets the feature item.
     *
     * @return the feature item
     */
    public FullWidthFeatureItem getFeatureItem() {
        return featureItem;
    }

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
        SlingHttpServletRequest slingRequest = this.getRequest();
        Node currentNode = slingRequest.getResource().adaptTo(Node.class);
        featureItem = new FullWidthFeatureItem();
        boolean displayTopCurve = JcrUtils.getBooleanValue(currentNode, "displaycurvetop");
        boolean displayBottomCurve = JcrUtils.getBooleanValue(currentNode, "displaycurvebottom");
        boolean displaySecondaryTextArea = JcrUtils.getBooleanValue(currentNode, "displaysecondary");
        TextArea primaryTextArea = getPrimaryTextArea(currentNode);
        featureItem.setDisplayTopCurve(displayTopCurve);
        featureItem.setDisplayBottomCurve(displayBottomCurve);
        featureItem.setDisplaySecondaryTextArea(displaySecondaryTextArea);
        featureItem.setPrimaryTextArea(primaryTextArea);
        // check secondary textarea.
        if (displaySecondaryTextArea) {
            TextArea secondaryTextArea = getSecondaryTextArea(currentNode);
            featureItem.setSecondaryTextArea(secondaryTextArea);
            String cssClassName = getCssClassForSecondary(featureItem);
            featureItem.getSecondaryTextArea().setCssClassName(cssClassName);
        }
        String cssClassName = getCssClassForPrimary(featureItem);
        featureItem.getPrimaryTextArea().setCssClassName(cssClassName);

    }

    /**
     * Gets the primary text area.
     *
     * @param currentNode the current node
     * @return the primary text area
     */
    private TextArea getPrimaryTextArea(Node currentNode) {

        TextArea primaryTextArea = new TextArea();
        if (currentNode != null) {
            String headLine = getProperties().get("headline", StringUtils.EMPTY);
            String bodyText = getProperties().get("bodytext", StringUtils.EMPTY);
            String imagePath = getProperties().get("image", StringUtils.EMPTY);
            String imagePosition = getProperties().get("imageposition", StringUtils.EMPTY);

            // Primary CTA
            String primaryTitle = getProperties().get("titleCTA1", StringUtils.EMPTY);
            String primaryLink = getProperties().get("linkCTA1", StringUtils.EMPTY);
            //get target for primary cta
            CtaButton primaryCta = new CtaButton();
            String primaryTarget = CommonUtils.getTargetByLink(primaryLink);
            primaryCta.setTarget(primaryTarget);

            if (StringUtils.isNotBlank(primaryLink) && !primaryLink.toLowerCase().startsWith("http")) {
                primaryLink = primaryLink.concat(".html");
            }
            String primaryButtonType = getProperties().get("buttontype1", StringUtils.EMPTY);
            primaryCta.setTitle(primaryTitle);
            primaryCta.setLink(primaryLink);

            // get style for CTA button
            String primaryCtaStyle = CommonUtils.getBtnTypeStyleByTitle(primaryTitle, primaryButtonType);
            primaryCta.setStyle(primaryCtaStyle);
            primaryCta.setButtonType(primaryButtonType);

            // Secondary CTA
            String secondaryTitle = getProperties().get("titleCTA2", StringUtils.EMPTY);
            String secondaryLink = getProperties().get("linkCTA2", StringUtils.EMPTY);
            //get target for secondary cta
            String secondaryTarget = CommonUtils.getTargetByLink(secondaryLink);
            CtaButton secondaryCta = new CtaButton();
            secondaryCta.setTarget(secondaryTarget);

            if (StringUtils.isNotBlank(secondaryLink) && !secondaryLink.toLowerCase().startsWith("http")) {
                secondaryLink = secondaryLink.concat(".html");
            }
            String secondaryButtonType = getProperties().get("buttontype2", StringUtils.EMPTY);
            secondaryCta.setTitle(secondaryTitle);
            secondaryCta.setLink(secondaryLink);

            //get style for CTA button
            String secondaryCtaStyle = CommonUtils.getBtnTypeStyleByTitle(secondaryTitle, secondaryButtonType);
            secondaryCta.setStyle(secondaryCtaStyle);
            secondaryCta.setButtonType(secondaryButtonType);

            boolean displayBackground = JcrUtils.getBooleanValue(currentNode, "displaybackground1");
            primaryTextArea.setBodyText(bodyText);
            primaryTextArea.setHeadLine(headLine);
            primaryTextArea.setImagePath(imagePath);
            primaryTextArea.setImagePosition(imagePosition);
            primaryTextArea.setDisplayBackground(displayBackground);
            primaryTextArea.setPrimaryCta(primaryCta);
            primaryTextArea.setSecondaryCta(secondaryCta);
        }

        return primaryTextArea;
    }

    /**
     * Gets the secondary text area.
     *
     * @param currentNode the current node
     * @return the secondary text area
     */
    private TextArea getSecondaryTextArea(Node currentNode) {
        TextArea secondaryTextArea = new TextArea();
        if (currentNode != null) {
            String headLine = getProperties().get("primaryheadline", StringUtils.EMPTY);
            String bodyText = getProperties().get("primarybodytext", StringUtils.EMPTY);

            // Primary CTA
            String primaryTitle = getProperties().get("titleCTA3", StringUtils.EMPTY);
            String primaryLink = getProperties().get("linkCTA3", StringUtils.EMPTY);
            CtaButton primaryCta = new CtaButton();
            //get target for primary cta
            String primaryTarget = CommonUtils.getTargetByLink(primaryLink);
            primaryCta.setTarget(primaryTarget);

            if (StringUtils.isNotBlank(primaryLink) && !primaryLink.toLowerCase().startsWith("http")) {
                primaryLink = primaryLink.concat(".html");
            }
            String primaryButtonType = getProperties().get("buttontype3", StringUtils.EMPTY);
            primaryCta.setTitle(primaryTitle);
            primaryCta.setLink(primaryLink);

            // get style for CTA button
            String primaryCtaStyle = CommonUtils.getBtnTypeStyleByTitle(primaryTitle, primaryButtonType);
            primaryCta.setStyle(primaryCtaStyle);
            primaryCta.setButtonType(primaryButtonType);
            // Secondary CTA
            String secondaryTitle = getProperties().get("titleCTA4", StringUtils.EMPTY);
            String secondaryLink = getProperties().get("linkCTA4", StringUtils.EMPTY);
            CtaButton secondaryCta = new CtaButton();
            //get target for secondary cta
            String secondaryTarget = CommonUtils.getTargetByLink(secondaryLink);
            secondaryCta.setTarget(secondaryTarget);
            if (StringUtils.isNotBlank(secondaryLink) && !secondaryLink.toLowerCase().startsWith("http")) {
                secondaryLink = secondaryLink.concat(".html");
            }
            String secondaryButtonType = getProperties().get("buttontype4", StringUtils.EMPTY);

            secondaryCta.setTitle(secondaryTitle);
            secondaryCta.setLink(secondaryLink);

            //get style for CTA button
            String secondaryCtaStyle = CommonUtils.getBtnTypeStyleByTitle(secondaryTitle, secondaryButtonType);
            secondaryCta.setStyle(secondaryCtaStyle);
            secondaryCta.setButtonType(secondaryButtonType);

            boolean displayBackground = JcrUtils.getBooleanValue(currentNode, "displaybackground2");
            secondaryTextArea.setBodyText(bodyText);
            secondaryTextArea.setHeadLine(headLine);
            secondaryTextArea.setDisplayBackground(displayBackground);
            secondaryTextArea.setPrimaryCta(primaryCta);
            secondaryTextArea.setSecondaryCta(secondaryCta);
        }

        return secondaryTextArea;
    }

    /**
     * Gets the css class for primary.
     *
     * @param featureItem the feature item
     * @return the css class for primary
     */
    private String getCssClassForPrimary(FullWidthFeatureItem featureItem) {
        StringBuffer cssClass = new StringBuffer(StringUtils.EMPTY);
        TextArea primaryTextArea = featureItem.getPrimaryTextArea();
        // just display primary TextArea.
        if (!featureItem.isDisplayBottomCurve() && !featureItem.isDisplayTopCurve()) {
            cssClass.append(" short");
        }
        if (featureItem.isDisplaySecondaryTextArea()) {
            if (featureItem.isDisplayTopCurve() && primaryTextArea.isDisplayBackground()) {
                cssClass.append(" topCurve outLine");
            } else if (featureItem.isDisplayTopCurve() && !primaryTextArea.isDisplayBackground()) {
                cssClass.append(" topCurve");
            } else if (!featureItem.isDisplayTopCurve() && primaryTextArea.isDisplayBackground()) {
                cssClass.append(" primary-background");
            }
        } else {

            // just display primary TextArea.
            if (featureItem.isDisplayTopCurve() && featureItem.isDisplayBottomCurve() && primaryTextArea.isDisplayBackground()) {
                cssClass.append(" v4-full");
            } else if (featureItem.isDisplayTopCurve() && featureItem.isDisplayBottomCurve() && !primaryTextArea.isDisplayBackground()) {
                cssClass.append(" topCurve bottomCurve");
            } else if (featureItem.isDisplayTopCurve() && !featureItem.isDisplayBottomCurve() && !primaryTextArea.isDisplayBackground()) {
                cssClass.append(" topCurve");
            } else if (featureItem.isDisplayTopCurve() && !featureItem.isDisplayBottomCurve() && primaryTextArea.isDisplayBackground()) {
                cssClass.append(" topCurve outLine");
            } else if (!featureItem.isDisplayTopCurve() && !featureItem.isDisplayBottomCurve() && primaryTextArea.isDisplayBackground()) {
                cssClass.append(" noCurve");
            } else if (!featureItem.isDisplayTopCurve() && featureItem.isDisplayBottomCurve() && !primaryTextArea.isDisplayBackground()) {
                cssClass.append(" bottomCurve");
            } else if (!featureItem.isDisplayTopCurve() && featureItem.isDisplayBottomCurve() && primaryTextArea.isDisplayBackground()) {
                cssClass.append(" bottomCurve outLine");
            } else if (!featureItem.isDisplayTopCurve() && !featureItem.isDisplayBottomCurve() && primaryTextArea.isDisplayBackground()) {
                cssClass.append(" topCurve noOutline");
            }
        }
        return cssClass.toString();
    }

    /**
     * Gets the css class for secondary.
     *
     * @param featureItem the feature item
     * @return the css class for secondary
     */
    private String getCssClassForSecondary(FullWidthFeatureItem featureItem) {
        StringBuffer cssClass = new StringBuffer(StringUtils.EMPTY);
        if (!featureItem.isDisplayBottomCurve() && !featureItem.isDisplayTopCurve()) {
            cssClass.append(" short");
        }
        if (featureItem.isDisplaySecondaryTextArea()) {
            TextArea secondTextArea = featureItem.getSecondaryTextArea();
            if (featureItem.isDisplayBottomCurve() && secondTextArea.isDisplayBackground()) {
                cssClass.append(" bottomCurve outLine fullwidth-less-top");
            } else if (featureItem.isDisplayBottomCurve() && !secondTextArea.isDisplayBackground()) {
                cssClass.append(" bottomCurve fullwidth-less-top-white");
            } else if (!featureItem.isDisplayBottomCurve() && secondTextArea.isDisplayBackground()) {
                cssClass.append(" second-background fullwidth-less-top");
            }
        }

        return cssClass.toString();
    }
}
