package com.jain.common;

import java.util.Locale;

import com.jain.addon.JNINamed;

public enum JLocale implements JNINamed {
	ENGLISH("en", "English"), 
	HINDI("hin", "हिंदी");

	private final String displayName;
	private final String name;
	private final Locale locale;
	private final boolean defaultLocale;
	
	private JLocale(final String name, final String displayName) {
		this(name, displayName, false);
	}
	
	private JLocale(final String name, final String displayName, final boolean defaultLocale) {
		this.name = name;
		this.displayName = displayName;
		this.locale = new Locale(name, name, name);
		
		if (defaultLocale)
			Locale.setDefault(locale);
		
		this.defaultLocale = defaultLocale;
	}
	
	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getDescription() {
		return displayName;
	}
	
	public boolean isDefaultLocale() {
		return defaultLocale;
	}

	public Locale getLocale() {
		return locale;
	}
}
