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
package com.jain.addon.helper;

import com.jain.addon.i18N.component.I18NUI;
import com.jain.addon.resource.DefaultI18NResourceProvider;
import com.jain.addon.resource.I18NProvider;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * <code>JNHelper<code> is a Helper class containi8ng all the helper methods
 * @author Lokesh Jain
 * @since Nov 27, 2012
 * @version 1.1.0
 */
public final class JNHelper {
	private JNHelper() {
	}

	/**
	 * Method to create Notification
	 * @param currentUI
	 * @param title
	 * @param message
	 * @param params
	 * @return Notification
	 */
	public static Notification createNotification(UI currentUI, String title, String message, Object ... params) {
		I18NProvider provider = null;
		if (currentUI instanceof I18NUI)
			provider = ((I18NUI) currentUI).getI18nProvider();
		else 
			provider = DefaultI18NResourceProvider.instance();
		
		Notification notification = new Notification(provider.getTitle(currentUI.getLocale(), title, params), Notification.Type.TRAY_NOTIFICATION);
		notification.setPosition(Position.MIDDLE_CENTER);
		notification.setDescription(provider.getMessage(currentUI.getLocale(), message, params));
		return notification;
	}

	/**
	 * Method to create and Show notification
	 * @param currentUI
	 * @param title
	 * @param message
	 * @param params
	 */
	public static void showNotification(UI currentUI, String title, String message, Object ... params) {
		Notification notification = createNotification(currentUI, title, message, params);
		notification.show(currentUI.getPage());
	}
	
	/**
	 * Method to find resource value for I18N support
	 * @param ui
	 * @param displayName
	 * @param defaultValue
	 * @param params
	 * @return resource Value
	 */
	public static String getResourceValue(UI currentUI, String resourceKey, String defaultValue, Object ... params) {
		if (currentUI instanceof I18NUI)
			return ((I18NUI) currentUI).getI18nProvider().getText(currentUI.getLocale(), resourceKey, params);
		return defaultValue;
	}
}
