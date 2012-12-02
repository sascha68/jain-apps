package com.jain.addon.action;

import com.jain.addon.JNStyleConstants;

public enum JNActionGroupType {
	BUTTON (JNStyleConstants.J_ACTION_BUTTON_GROUP),
	LINK (JNStyleConstants.J_ACTION_LINK_GROUP),
	MENU (JNStyleConstants.J_ACTION_MENU_GROUP);
	
	private final String style;

	private JNActionGroupType(final String style) {
		this.style = style;
	}

	public String getStyle() {
		return style;
	}
}
