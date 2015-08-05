package com.calix.model;

import com.calix.utils.Constants;
import org.apache.commons.lang.StringUtils;

/**
 * The Class Slide.
 */
public class CTA {

	private String title;
	private String link;
	private String buttonType;
	private Boolean display;
	private String target;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target)
	{
		this.target = target;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	private String style;

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = getFormattedLink(link);
	}

	public String getButtonType() {
		return buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}

	public String getFormattedLink(String link){
		String result = link;
		if(StringUtils.isNotBlank(result)) {
			if (!result.toLowerCase().startsWith("http")) {
				if (!Constants.EXTENSION_PATTERN_LINK.matcher(result).matches() && !result.startsWith("#")) {
					result += Constants.SUFFIX_HTML;
				}
			}
		}
		return result;
	}

}
