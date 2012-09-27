/* 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jain.i18N.domain;

import java.io.Serializable;

import com.jain.addon.web.bean.JPropertyType;
import com.jain.addon.web.bean.JVisibilityType;
import com.jain.addon.web.bean.annotation.JNIAttribute;
import com.jain.addon.web.bean.annotation.JNIEmbedded;

@SuppressWarnings("serial")
public class Address implements Serializable {
	private String address1;
	private String street;
	private String city;
	private ZIP zip;

	public Address(ZIP zip) {
		this.zip = zip;
	}
	
	@JNIAttribute (lable = "address", order = 100, type = JPropertyType.TEXT_AREA)
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@JNIAttribute (lable = "street", visibility = JVisibilityType.COLLAPSED, order = 101)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@JNIAttribute (lable = "city", order = 102)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@JNIEmbedded
	public ZIP getZip() {
		return zip;
	}

	public void setZip(ZIP zip) {
		this.zip = zip;
	}
}
