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

import com.jain.addon.JNINamedResourceVisible;
import com.jain.addon.cdi.CDIComponent;
import com.jain.addon.resource.I18NProvider;
import com.jain.addon.resource.DefaultI18NResourceProvider;
import com.jain.addon.web.layout.segment.ButtonSegment;
import com.jain.common.authenticate.AuthenticatedUser;
import com.jain.theme.ApplicationTheme;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;


public final class VaadinHelper {
	private VaadinHelper() {
	}

	public static Notification createNotificationMessage(String title, String message, Object ... params) {
		I18NProvider provider = DefaultI18NResourceProvider.instance();
		AuthenticatedUser currentUser = CDIComponent.getInstance(AuthenticatedUser.class); 
		Notification n = new Notification(provider.getText(currentUser.getLocale(), title, params), Notification.Type.TRAY_NOTIFICATION);
		n.setPosition(Position.MIDDLE_CENTER);
		n.setDescription(provider.getText(currentUser.getLocale(), message, params));
		return n;
	}
	
	public static Notification createNotification(String title, Object ... params) {
		return createNotificationMessage(title + I18NProvider.TITLE_KEY, 
				title +  I18NProvider.MESSAGE_KEY, params);
	}
	
	public static HorizontalLayout createButtonSegment (ClickListener listener, JNINamedResourceVisible ... namedResources) {
		ButtonSegment layout = new ButtonSegment (ApplicationTheme.FIRST, ApplicationTheme.LAST);
		layout.setStyleName(ApplicationTheme.HEADER_SEGMENT_SMALL);
		layout.createSegment(listener, namedResources);
		return layout;
	}
}
