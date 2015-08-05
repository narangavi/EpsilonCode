/**
  * RegistrationForm.java
  * @Author: Epsilon-TranHNM
  * 
  * Objective: This implements logic to display Registration Form 
  * Completion Date: July 28, 2015
  * 
  * @version: 1.0 
*/
package com.calix.components;

import javax.jcr.Node;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.calix.utils.JcrUtils;
import com.calix.utils.WSConstants;

public class RegistrationForm extends WCMUse {
	/**i
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(RegistrationForm.class);
    private String title;
    private String actionURL;
    private String imageDesc;
    private String successmsg;
    
    //field labels
    private String fnamelbl;
    private String lnamelbl;
    private String emailbl;
    private String confirmemailbl;
    private String companylbl;
    private String titlelbl;
    private String jobfunctionlbl;
    private String address1lbl;
    private String address2lbl;
    private String citylbl;
    private String statelbl;
    private String postalcodelbl;
    private String countrylbl;
    private String primaryphonelbl;
    private String secondaryphonelbl;
    private String phonetypelbl;
    private String phonenumberlbl;
    private String phoneextlbl;
    private String textinboxlbl;
    private String referredbylbl;
    private String buttonlbl;
    
    //field description
    private String fnamedesc;
    private String lnamedesc;
    private String emaildesc;
    private String confirmemaildesc;
    private String companydesc;
    private String titledesc;
    private String addess1desc;
    private String addess2desc;
    private String citydesc;
    private String statedesc;
    private String postalcodedesc;
    private String countrydesc;
    private String primaryphonedesc;
    private String secondaryphonedesc;
    private String textinboxdesc;
    private String referredbydesc;
    private String jobfuncdesc;

	public static Logger getLogger() {
		return logger;
	}
	public final String TITLE = "New User Registration";
	public final String FNAMELBL = "First Name";
	public final String LNAMELBL = "Last Name";
	public final String EMAILBL = "Email";
	public final String CONFIRMEMAILBL = "Confirm Email";
	public final String COMPANYLBL = "Company";
	public final String TITLELBL = "Title";
	public final String JOBFUNCTIONLBL = "Job Function";
	public final String ADDRESS1LBL = "Address 1";
	public final String ADDRESS2LBL = "Address 2";
	public final String CITYLBL = "City";
	public final String STATELBL = "State";
	public final String POSTALCODELBL = "Zip/Postal Code";
	public final String COUNTRYLBL = "Country";
	public final String PRIMARYPHONELBL = "Primary Phone";
	public final String SECONDARYPHONELBL = "Secondary Phone";
	public final String PHONETYPELBL = "Type";
	public final String PHONENUMBERLBL = "Number";
	public final String PHONEEXTLBL = "Ext";
	public final String TEXTINBOXLBL = "Text in Box at Right";
	public final String REFERREDBYLBL = "Referred By";
	public final String BUTTONLBL = "Go";
	
	
	
	@Override
	public void activate() throws Exception {
		SlingHttpServletRequest slingRequest = this.getRequest();
		actionURL = WSConstants.USER_REGISTRATION_SERVLET;
		Node currentNode = slingRequest.getResource().adaptTo(Node.class);
		title = getProperties().get("title", TITLE);
		imageDesc = JcrUtils.getStringValue(currentNode, "imagedesc");
		successmsg = JcrUtils.getStringValue(currentNode, "successmsg");
		
		// field labels
		fnamelbl = getProperties().get("fnamelbl", FNAMELBL);
	    lnamelbl = getProperties().get("lnamelbl", LNAMELBL);
	    emailbl = getProperties().get("emailbl", EMAILBL);
	    confirmemailbl = getProperties().get("confirmemailbl", CONFIRMEMAILBL);
	    companylbl = getProperties().get("companylbl", COMPANYLBL);
	    titlelbl = getProperties().get("titlelbl", TITLELBL);
	    jobfunctionlbl = getProperties().get("jobfunctionlbl", JOBFUNCTIONLBL);
	    address1lbl =getProperties().get("address1lbl", ADDRESS1LBL);
	    address2lbl = getProperties().get("address2lbl", ADDRESS2LBL);
	    citylbl = getProperties().get("citylbl", CITYLBL);
	    statelbl = getProperties().get("statelbl", STATELBL);
	    postalcodelbl = getProperties().get("postalcodelbl", POSTALCODELBL);
	    countrylbl = getProperties().get("countrylbl", COUNTRYLBL);
	    primaryphonelbl = getProperties().get("primaryphonelbl", PRIMARYPHONELBL);
	    secondaryphonelbl = getProperties().get("secondaryphonelbl", SECONDARYPHONELBL);
	    phonetypelbl = getProperties().get("phonetypelbl", PHONETYPELBL);
	    phonenumberlbl = getProperties().get("phonenumberlbl", PHONENUMBERLBL);
	    phoneextlbl = getProperties().get("phoneextlbl", PHONEEXTLBL);
	    textinboxlbl = getProperties().get("textinboxlbl", TEXTINBOXLBL);
	    referredbylbl = getProperties().get("referredbylbl", REFERREDBYLBL);
	    buttonlbl = getProperties().get("buttonlbl", BUTTONLBL);
	    
	    //field descriptions
	    fnamedesc = JcrUtils.getStringValue(currentNode, "fnamedesc");
	    lnamedesc = JcrUtils.getStringValue(currentNode, "lnamedesc");
	    emaildesc = JcrUtils.getStringValue(currentNode, "emaildesc");
	    confirmemaildesc = JcrUtils.getStringValue(currentNode, "confirmemaildesc");
	    companydesc = JcrUtils.getStringValue(currentNode, "companydesc");
	    titledesc = JcrUtils.getStringValue(currentNode, "titledesc");
	    jobfuncdesc = JcrUtils.getStringValue(currentNode, "jobfuncdesc");
	    addess1desc = JcrUtils.getStringValue(currentNode, "addess1desc");
	    addess2desc = JcrUtils.getStringValue(currentNode, "addess2desc");
	    citydesc = JcrUtils.getStringValue(currentNode, "citydesc");
	    statedesc = JcrUtils.getStringValue(currentNode, "statedesc");
	    postalcodedesc = JcrUtils.getStringValue(currentNode, "postalcodedesc");
	    countrydesc = JcrUtils.getStringValue(currentNode, "countrydesc");
	    primaryphonedesc = JcrUtils.getStringValue(currentNode, "primaryphonedesc");
	    secondaryphonedesc = JcrUtils.getStringValue(currentNode, "secondaryphonedesc");
	    textinboxdesc = JcrUtils.getStringValue(currentNode, "textinboxdesc");
	    referredbydesc = JcrUtils.getStringValue(currentNode, "referredbydesc");
	    
	}

	public String getJobfuncdesc() {
		return jobfuncdesc;
	}

	public void setJobfuncdesc(String jobfuncdesc) {
		this.jobfuncdesc = jobfuncdesc;
	}
	
	public String getActionURL() {
		return actionURL;
	}

	public void setActionURL(String actionURL) {
		this.actionURL = actionURL;
	}

	public String getTitle() {
		return title;
	}

	public String getImageDesc() {
		return imageDesc;
	}

	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFnamelbl() {
		return fnamelbl;
	}

	public void setFnamelbl(String fnamelbl) {
		this.fnamelbl = fnamelbl;
	}

	public String getLnamelbl() {
		return lnamelbl;
	}

	public void setLnamelbl(String lnamelbl) {
		this.lnamelbl = lnamelbl;
	}

	public String getEmailbl() {
		return emailbl;
	}

	public void setEmailbl(String emailbl) {
		this.emailbl = emailbl;
	}

	public String getConfirmemailbl() {
		return confirmemailbl;
	}

	public void setConfirmemailbl(String confirmemailbl) {
		this.confirmemailbl = confirmemailbl;
	}

	public String getCompanylbl() {
		return companylbl;
	}

	public void setCompanylbl(String companylbl) {
		this.companylbl = companylbl;
	}

	public String getTitlelbl() {
		return titlelbl;
	}

	public void setTitlelbl(String titlelbl) {
		this.titlelbl = titlelbl;
	}

	public String getJobfunctionlbl() {
		return jobfunctionlbl;
	}

	public void setJobfunctionlbl(String jobfunctionlbl) {
		this.jobfunctionlbl = jobfunctionlbl;
	}

	public String getAddress1lbl() {
		return address1lbl;
	}

	public void setAddress1lbl(String address1lbl) {
		this.address1lbl = address1lbl;
	}

	public String getAddress2lbl() {
		return address2lbl;
	}

	public void setAddress2lbl(String address2lbl) {
		this.address2lbl = address2lbl;
	}

	public String getCitylbl() {
		return citylbl;
	}

	public void setCitylbl(String citylbl) {
		this.citylbl = citylbl;
	}

	public String getStatelbl() {
		return statelbl;
	}

	public void setStatelbl(String statelbl) {
		this.statelbl = statelbl;
	}

	public String getPostalcodelbl() {
		return postalcodelbl;
	}

	public void setPostalcodelbl(String postalcodelbl) {
		this.postalcodelbl = postalcodelbl;
	}

	public String getCountrylbl() {
		return countrylbl;
	}

	public void setCountrylbl(String countrylbl) {
		this.countrylbl = countrylbl;
	}

	public String getPrimaryphonelbl() {
		return primaryphonelbl;
	}

	public void setPrimaryphonelbl(String primaryphonelbl) {
		this.primaryphonelbl = primaryphonelbl;
	}

	public String getSecondaryphonelbl() {
		return secondaryphonelbl;
	}

	public void setSecondaryphonelbl(String secondaryphonelbl) {
		this.secondaryphonelbl = secondaryphonelbl;
	}

	public String getPhonetypelbl() {
		return phonetypelbl;
	}

	public void setPhonetypelbl(String phonetypelbl) {
		this.phonetypelbl = phonetypelbl;
	}

	public String getPhonenumberlbl() {
		return phonenumberlbl;
	}

	public void setPhonenumberlbl(String phonenumberlbl) {
		this.phonenumberlbl = phonenumberlbl;
	}

	public String getPhoneextlbl() {
		return phoneextlbl;
	}

	public void setPhoneextlbl(String phoneextlbl) {
		this.phoneextlbl = phoneextlbl;
	}

	public String getTextinboxlbl() {
		return textinboxlbl;
	}

	public void setTextinboxlbl(String textinboxlbl) {
		this.textinboxlbl = textinboxlbl;
	}

	public String getReferredbylbl() {
		return referredbylbl;
	}

	public void setReferredbylbl(String referredbylbl) {
		this.referredbylbl = referredbylbl;
	}

	public String getButtonlbl() {
		return buttonlbl;
	}

	public void setButtonlbl(String buttonlbl) {
		this.buttonlbl = buttonlbl;
	}

	public String getFnamedesc() {
		return fnamedesc;
	}

	public void setFnamedesc(String fnamedesc) {
		this.fnamedesc = fnamedesc;
	}

	public String getLnamedesc() {
		return lnamedesc;
	}

	public void setLnamedesc(String lnamedesc) {
		this.lnamedesc = lnamedesc;
	}

	public String getEmaildesc() {
		return emaildesc;
	}

	public void setEmaildesc(String emaildesc) {
		this.emaildesc = emaildesc;
	}

	public String getConfirmemaildesc() {
		return confirmemaildesc;
	}

	public void setConfirmemaildesc(String confirmemaildesc) {
		this.confirmemaildesc = confirmemaildesc;
	}

	public String getCompanydesc() {
		return companydesc;
	}

	public void setCompanydesc(String companydesc) {
		this.companydesc = companydesc;
	}

	public String getTitledesc() {
		return titledesc;
	}

	public void setTitledesc(String titledesc) {
		this.titledesc = titledesc;
	}

	public String getAddess1desc() {
		return addess1desc;
	}

	public void setAddess1desc(String addess1desc) {
		this.addess1desc = addess1desc;
	}

	public String getAddess2desc() {
		return addess2desc;
	}

	public void setAddess2desc(String addess2desc) {
		this.addess2desc = addess2desc;
	}

	public String getCitydesc() {
		return citydesc;
	}

	public void setCitydesc(String citydesc) {
		this.citydesc = citydesc;
	}

	public String getStatedesc() {
		return statedesc;
	}

	public void setStatedesc(String statedesc) {
		this.statedesc = statedesc;
	}

	public String getPostalcodedesc() {
		return postalcodedesc;
	}

	public void setPostalcodedesc(String postalcodedesc) {
		this.postalcodedesc = postalcodedesc;
	}

	public String getCountrydesc() {
		return countrydesc;
	}

	public void setCountrydesc(String countrydesc) {
		this.countrydesc = countrydesc;
	}

	public String getPrimaryphonedesc() {
		return primaryphonedesc;
	}

	public void setPrimaryphonedesc(String primaryphonedesc) {
		this.primaryphonedesc = primaryphonedesc;
	}

	public String getSecondaryphonedesc() {
		return secondaryphonedesc;
	}

	public void setSecondaryphonedesc(String secondaryphonedesc) {
		this.secondaryphonedesc = secondaryphonedesc;
	}

	public String getTextinboxdesc() {
		return textinboxdesc;
	}

	public void setTextinboxdesc(String textinboxdesc) {
		this.textinboxdesc = textinboxdesc;
	}

	public String getReferredbydesc() {
		return referredbydesc;
	}

	public void setReferredbydesc(String referredbydesc) {
		this.referredbydesc = referredbydesc;
	}

	public String getSuccessmsg() {
		return successmsg;
	}

	public void setSuccessmsg(String successmsg) {
		this.successmsg = successmsg;
	}

    
}
