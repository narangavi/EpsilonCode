package com.calix.model;

/**
 * The Class Profile.
 */
public class ProfileItem {

	private String firstName;
	private String lastName;
	private String job;
	private String department;
	private String type;
	private String description;
	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProfileItem(){
	}
	public ProfileItem(String fName, String lName, String job, String department, String type, String desc, String image){
		this.firstName = fName;
		this.lastName = lName;
		this.job = job;
		this.department = department;
		this.type = type;
		this.description = desc;
		this.image = image;
	}
}
