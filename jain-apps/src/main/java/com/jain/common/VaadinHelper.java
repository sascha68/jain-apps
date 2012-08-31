package com.jain.common;

import com.jain.addon.cdi.CDIComponent;
import com.jain.addon.resource.I18NProvider;
import com.jain.addon.resource.I18NResourceProvider;
import com.jain.addon.web.JNINamedResourceVisible;
import com.jain.addon.web.layout.segment.ButtonSegment;
import com.jain.common.authenticate.AuthenticatedUser;
import com.jain.theme.ApplicationTheme;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;


public final class VaadinHelper {
	private VaadinHelper() {
	}

	public static Notification createNotificationMessage(String title, String message, Object ... params) {
		I18NProvider provider = I18NResourceProvider.instance();
		AuthenticatedUser currentUser = CDIComponent.getInstance(AuthenticatedUser.class); 
		Notification n = new Notification(provider.getText(currentUser.getLocale(), title, params), Notification.TYPE_TRAY_NOTIFICATION);
		n.setPosition(Notification.POSITION_CENTERED);
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
