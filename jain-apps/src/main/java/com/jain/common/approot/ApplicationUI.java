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
package com.jain.common.approot;

import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.jain.addon.cdi.CDIComponent;
import com.jain.addon.i18N.component.I18NUI;
import com.jain.addon.resource.I18NProvider;
import com.jain.addon.resource.DefaultI18NResourceProvider;
import com.jain.common.authenticate.AuthenticatedUser;
import com.jain.common.header.Header;
import com.jain.common.header.WelcomeBar;
import com.jain.theme.ApplicationTheme;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme(ApplicationTheme.THEME_NAME)
@SessionScoped
public class ApplicationUI extends I18NUI {
	@Inject AuthenticatedUser user;
	
	protected void initialize (VaadinRequest request) {
		System.setProperty("view.action.icon", "images/icons/view.png");
		System.setProperty("add.action.icon", "images/icons/add.png");
		System.setProperty("edit.action.icon", "images/icons/edit.png");
		System.setProperty("delete.action.icon", "images/icons/delete.png");
		System.setProperty("help.action.icon", "images/icons/help.png");
		System.setProperty("login.action.icon", "images/icons/login.png");
		System.setProperty("logout.action.icon", "images/icons/logout.png");
		System.setProperty("save.action.icon", "images/icons/save.png");
		System.setProperty("cancel.action.icon", "images/icons/cancel.png");
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
		I18NProvider provider = DefaultI18NResourceProvider.instance();
		getPage().setTitle(provider.getText(getLocale(), "application.title.name"));
	}

	private void createWelcomebar(VerticalLayout view) {
		WelcomeBar welcomebar = CDIComponent.getInstance(WelcomeBar.class);
		view.addComponent(welcomebar);
	}
}