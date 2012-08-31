package com.jain.common.approot;

import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.jain.addon.cdi.CDIComponent;
import com.jain.addon.i18N.component.I18NRoot;
import com.jain.addon.resource.I18NProvider;
import com.jain.addon.resource.I18NResourceProvider;
import com.jain.common.authenticate.AuthenticatedUser;
import com.jain.common.header.Header;
import com.jain.common.header.WelcomeBar;
import com.jain.theme.ApplicationTheme;
import com.vaadin.annotations.Theme;
import com.vaadin.terminal.WrappedRequest;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme(ApplicationTheme.THEME_NAME)
@SessionScoped
public class ApplicationRoot extends I18NRoot {
	@Inject AuthenticatedUser user;
	
	protected void initialize (WrappedRequest request) {
		initialize (Locale.getDefault());
	}

	public void initialize (Locale locale) {
		user.setLocale(locale);
		
		addApplicationTitle();
		
		VerticalLayout view = new VerticalLayout();
		setContent(view);
		
		view.setStyleName(ApplicationTheme.VIEW);
		view.setWidth("100%");
		view.setHeight("100%");
		view.setSpacing(false);
		view.setMargin(false);

		createWelcomebar(view);

		Header header = CDIComponent.getInstance(Header.class);
		view.addComponent(header);
		header.addDefaultTab();
	}

	private void addApplicationTitle() {
		I18NProvider provider = I18NResourceProvider.instance();
		getPage().setTitle(provider.getText(getLocale(), "application.title.name"));
	}

	private void createWelcomebar(VerticalLayout view) {
		WelcomeBar welcomebar = CDIComponent.getInstance(WelcomeBar.class);
		view.addComponent(welcomebar);
	}
}