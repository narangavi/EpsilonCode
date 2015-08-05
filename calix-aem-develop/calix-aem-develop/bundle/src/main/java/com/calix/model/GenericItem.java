package com.calix.model;
import java.util.List;
import com.calix.model.Video;
import com.calix.model.VideoGroup;


/**
 * The Class Slide.
 */
public class GenericItem {

	/** The title. */
	private String title;
	
	/** The desc. */
	private String desc;
	
	/** The image path. */
	private String imagePath;
	
	/** The cta1. */
	private CTA cta1;
	
	/** The cta2. */
	private CTA cta2;
	
	/** The bg style. */
	private String bgStyle;
	
	/** The type. */
	private String type;
	
	/** The display bg. */
	private Boolean displayBG;
	
	/** The position. */
	private String position;
	
	/** The display data. */
	private Boolean displayData;
	
	/** The modal id. */
	private String modalId;
	
	/** The main video. */
	private Video mainVideo;
	
	/** The video groups.*/
	private List<VideoGroup> videoGroups;

	/** The caption.*/
	private String caption;

	public List<VideoGroup> getVideoGroups() {
        return videoGroups;
    }
	
	public void setVideoGroups(List<VideoGroup> videoGroups) {
        this.videoGroups = videoGroups;
    }
	/**
	 * Gets the display data.
	 *
	 * @return the display data
	 */
	public Boolean getDisplayData() {
		return displayData;
	}

	/**
	 * Sets the display data.
	 *
	 * @param displayData the new display data
	 */
	public void setDisplayData(Boolean displayData) {
		this.displayData = displayData;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * Gets the bg style.
	 *
	 * @return the bg style
	 */
	public String getBgStyle() {
		return bgStyle;
	}

	/**
	 * Sets the bg style.
	 *
	 * @param bgStyle the new bg style
	 */
	public void setBgStyle(String bgStyle) {
		this.bgStyle = bgStyle;
	}

	/**
	 * Gets the display bg.
	 *
	 * @return the display bg
	 */
	public Boolean getDisplayBG() {
		return displayBG;
	}

	/**
	 * Sets the display bg.
	 *
	 * @param displayBG the new display bg
	 */
	public void setDisplayBG(Boolean displayBG) {
		this.displayBG = displayBG;
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position the new position
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the desc.
	 *
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Sets the desc.
	 *
	 * @param desc the new desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * Gets the image path.
	 *
	 * @return the image path
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * Sets the image path.
	 *
	 * @param imagePath the new image path
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * Gets the cta1.
	 *
	 * @return the cta1
	 */
	public CTA getCta1() {
		return cta1;
	}

	/**
	 * Sets the cta1.
	 *
	 * @param cta1 the new cta1
	 */
	public void setCta1(CTA cta1) {
		this.cta1 = cta1;
	}

	/**
	 * Gets the cta2.
	 *
	 * @return the cta2
	 */
	public CTA getCta2() {
		return cta2;
	}

	/**
	 * Sets the cta2.
	 *
	 * @param cta2 the new cta2
	 */
	public void setCta2(CTA cta2) {
		this.cta2 = cta2;
	}

	/**
	 * Gets the modal id.
	 *
	 * @return the modal id
	 */
	public String getModalId() {
		return modalId;
	}

	/**
	 * Sets the modal id.
	 *
	 * @param modalId the new modal id
	 */
	public void setModalId(String modalId) {
		this.modalId = modalId;
	}

	/**
	 * Gets the main video.
	 *
	 * @return the main video
	 */
	public Video getMainVideo() {
		return mainVideo;
	}

	/**
	 * Sets the main video.
	 *
	 * @param mainVideo the new main video
	 */
	public void setMainVideo(Video mainVideo) {
		this.mainVideo = mainVideo;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
}
