package com.jain.i18N.domain;

import java.io.Serializable;

import com.jain.addon.web.bean.JVisibilityType;
import com.jain.addon.web.bean.annotation.JNIAttribute;


@SuppressWarnings("serial")
public class ZIP implements Serializable {
	private String zip;

	@JNIAttribute (lable = "zip", visibility = JVisibilityType.COLLAPSED, order = 201)
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
