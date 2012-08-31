package com.jain.i18N.domain;

import com.jain.addon.JNINamed;

public enum Gender implements JNINamed {
	MALE("male.name"),
	FEMALE("female.name");

	private final String displayName;

	private Gender(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getName() {
		return displayName;
	}

	public String getDescription() {
		return displayName;
	}
}
