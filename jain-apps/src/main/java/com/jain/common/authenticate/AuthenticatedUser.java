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
package com.jain.common.authenticate;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;

import com.jain.addon.resource.I18NProvider;
import com.jain.addon.resource.I18NResourceProvider;

@SuppressWarnings("serial")
@SessionScoped
public class AuthenticatedUser implements Serializable {
	private String loggedInUser;
	private Locale locale;

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public String getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(String loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public boolean isGuestSession () {
		return loggedInUser == null;
	}

	public String getDisplayIdentity () {
		I18NProvider provider = I18NResourceProvider.instance(); 
		if (isGuestSession())
			return provider.getTitle(locale, "user.guest.welcome");
		else {
			return provider.getMessage(locale, "user.welcome", loggedInUser);
		}
	}

	public boolean hasPermission(String permission) {
		if (isGuestSession() && (permission == null || permission.equalsIgnoreCase("view") || permission.equalsIgnoreCase("login")))
			return true;
		if (isGuestSession()) 
			return false;
		if (!isGuestSession() && (permission != null && permission.equalsIgnoreCase("login")))
			return false;
		return true;
	}
}
