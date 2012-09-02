package com.jain.i18N.domain;

import java.util.Date;

import com.jain.addon.JNINamed;
import com.jain.addon.web.bean.JConstraintType;
import com.jain.addon.web.bean.JPropertyType;
import com.jain.addon.web.bean.JVisibilityType;
import com.jain.addon.web.bean.annotation.JNIAttribute;
import com.jain.addon.web.bean.annotation.JNIConstraint;
import com.jain.addon.web.bean.annotation.JNIEmbedded;

@SuppressWarnings("serial")
public class Person implements JNINamed {
	private String name;
	private String description;
	private Gender gender;
	private Date birthDate;
	private String email;
	private byte[] picture;
	private Address address;

	@JNIAttribute(lable = "name", order = 1)
	@JNIConstraint(types = JConstraintType.REQUIRED)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JNIAttribute(lable = "description", order = 900, visibility = JVisibilityType.COLLAPSED, type = JPropertyType.RICH_TEXT_AREA)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JNIAttribute(lable = "gender", order = 2)
	@JNIConstraint(width = "90%", types = JConstraintType.REQUIRED)
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@JNIAttribute(lable = "birth.date", order = 4)
	@JNIConstraint(width =  "90%", types = JConstraintType.REQUIRED)
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@JNIAttribute(lable = "email", order = 5)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JNIEmbedded(lable = "address.group.name", columns = 2)
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@JNIAttribute(lable = "picture", type = JPropertyType.IMAGE, order = 899)
	@JNIConstraint(width = "40%")
	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public String getDisplayName() {
		return name;
	}
}
