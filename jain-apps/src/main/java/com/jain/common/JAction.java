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
package com.jain.common;

import java.util.Locale;

import com.jain.addon.cdi.CDIComponent;
import com.jain.addon.resource.I18NProvider;
import com.jain.addon.resource.I18NResourceProvider;
import com.jain.addon.web.JNINamedResourceVisible;
import com.jain.common.authenticate.AuthenticatedUser;

public enum JAction implements JNINamedResourceVisible {
	VIEW("view", "view", "../runo/icons/16/reload.png"),
	ADD("add", "add", "../runo/icons/16/document-add.png"),
	EDIT("edit", "edit", "../runo/icons/16/attention.png"),
	DELETE("delete", "delete", "../runo/icons/16/trash.png"),
	SAVE (null, "save", "../runo/icons/16/ok.png"),
	CANCEL(null, "cancel", "../runo/icons/16/cancel.png"),
	LOGIN("login", "login", "../runo/icons/16/cancel.png"),
	LOGOUT("logout", "logout", "../runo/icons/16/cancel.png"),
	HELP(null, "help", "../runo/icons/16/cancel.png");

	private final String displayNameKey;
	private final String displayNamePlusKey;
	private final String resource;
	private final String permission;

	private JAction(String permission, String displayName, String resource) {
		this.permission = permission;
		this.displayNameKey = displayName + ".name";
		this.displayNamePlusKey = displayName + ".plus";
		this.resource = resource;
	}

	public String getDisplayName() {
		return displayNameKey;
	}
	
	public String getDisplayName(Locale locale, Object ... params) {
		I18NProvider provider = I18NResourceProvider.instance();
		return provider.getText(locale, displayNamePlusKey, params);
	}

	public String getResource() {
		return resource;
	}

	public String getName() {
		return displayNameKey;
	}

	public String getDescription() {
		return getDisplayName();
	}
	
	public String getPermission() {
		return permission;
	}	

	public boolean isVisible() {
		AuthenticatedUser user = CDIComponent.getInstance(AuthenticatedUser.class); 
		return user.hasPermission(permission);
	}

	public static JAction getDisplayNameEquivlent(String text) {
		for (JAction action : JAction.values()) {
			if(action.getDisplayName() != null && action.getDisplayName().equals(text)) 
				return action;
		}
		return null;
	}
}
