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
