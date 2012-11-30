/* 
 * Copyright 2012 Lokesh Jain.
 * 
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
package com.jain.addon.action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jain.addon.JNIComponentInit;
import com.jain.addon.JNStyleConstants;
import com.jain.addon.StringHelper;
import com.jain.addon.action.listener.JNButtonClickListener;
import com.jain.addon.i18N.I18NHelper;
import com.jain.addon.security.JNISecured;
import com.jain.addon.web.marker.authentication.JNILoginListner;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

/**
 * <code>ActionBar<code> is a horizontal segment component for actions.<br/>
 * @author Lokesh Jain
 * @since November 25, 2012
 * @version 1.0.3
 */
@SuppressWarnings("serial")
public class ActionBar <T> extends HorizontalLayout implements JNILoginListner {
	private List<JNAction> actions;
	private Map<JNAction, String> actionsToName;
	private final String firstButtonStyle;
	private final String lastButtonStyle;
	private final String buttonStyle;
	private final JNISecured secured; 
	private final JNButtonClickListener<T> listener;
	private boolean initialized = false;

	/**
	 * Create a segment instance having {@link Button} style for all the buttons
	 * @param secured -- {@link JNISecured}
	 * @param actionHandler
	 */
	public ActionBar(JNISecured secured, T actionHandler) {
		this(secured, actionHandler, JNStyleConstants.J_FIRST_ACTION, JNStyleConstants.J_LAST_ACTION, JNStyleConstants.J_ACTION);
	}
	
	/**
	 * Create a segment instance having {@link Button} style for all the buttons
	 * @param secured -- {@link JNISecured}
	 * @param actionHandler
	 * @param buttonStyle
	 */
	public ActionBar(JNISecured secured, T actionHandler, String buttonStyle) {
		this(secured, actionHandler, buttonStyle, buttonStyle, buttonStyle);
	}

	/**
	 * Create a segment instance having first {@link Button} and last {@link Button} styles
	 * @param secured -- {@link JNISecured}
	 * @param actionHandler
	 * @param firstButtonStyle
	 * @param lastButtonStyle
	 */
	public ActionBar(JNISecured secured, T actionHandler, String firstButtonStyle, String lastButtonStyle) {
		this(secured, actionHandler, firstButtonStyle, lastButtonStyle, null);
	}

	/**
	 * Create a segment instance having first {@link Button}, last {@link Button} and {@link Button} styles
	 * @param secured -- {@link JNISecured}
	 * @param actionHandler
	 * @param firstButtonStyle
	 * @param lastButtonStyle
	 */
	public ActionBar(JNISecured secured, T actionHandler, String firstButtonStyle, String lastButtonStyle, String buttonStyle) {
		this.firstButtonStyle = firstButtonStyle;
		this.lastButtonStyle = lastButtonStyle;
		this.buttonStyle = buttonStyle;
		this.secured = secured;
		this.listener = new JNButtonClickListener<T>(false, actionHandler); 
		this.actions = new ArrayList<JNAction>();
		this.actionsToName = new HashMap<JNAction, String> (); 
		setStyleName(JNStyleConstants.J_ACTION_BAR);
	}

	/**
	 * Method to initialize component
	 */
	@JNIComponentInit
	public void initActions() {
		if (!initialized) {
			findActions ();

			int i = 0;
			Button actionButton = null;
			for (JNAction action : actions) {
				actionButton = new Button(actionsToName.get(action), listener);

				if (StringHelper.isNotEmptyWithTrim(action.description()))
					actionButton.setDescription(action.description());
				else
					actionButton.setDescription(actionsToName.get(action));

				actionButton.setVisible(validatePermission (action));

				findNAddIcon(action, actionButton);
				
				if (StringHelper.isNotEmptyWithTrim(buttonStyle))
					actionButton.setStyleName(buttonStyle);

				if(i == 0)
					actionButton.addStyleName(firstButtonStyle);

				i ++;
				addComponent(actionButton);
				setComponentAlignment(actionButton, Alignment.TOP_RIGHT);
			}
			if (actionButton != null)
				actionButton.addStyleName(lastButtonStyle);
			initialized = true;
		} else {
			throw new IllegalArgumentException ("Please add action before adding component into container");
		}
	}


	private void findNAddIcon(JNAction action, Button actionButton) {
		String iconPath = System.getProperty(action.icon());
		
		if (StringHelper.isNotEmptyWithTrim(iconPath)) {
			ThemeResource icon = new ThemeResource(iconPath);
			actionButton.setIcon(icon);
		}
	}

	private void findActions() {
		if (listener.getActionHandler() != null) {
			Method[]  methods = listener.getActionHandler().getClass().getMethods();
			for (Method method : methods) {
				JNAction action = method.getAnnotation(JNAction.class);
				if(action != null) {
					String actionName = listener.addAction(action, method);
					actionsToName.put(action, actionName);
					int position = findPosition(action);
					actions.add(position, action);
				}
			}
		}
	}

	private int findPosition(JNAction action) {
		int i = 0;
		for (JNAction act : this.actions) {
			if (act.tabIndex() < action.tabIndex())
				i ++;
		}
		return i;
	}

	/**
	 * Reinitialize a Action Bar by updating resources {@link JNAction} visibility after login.<br/> 
	 */
	public void onLogin() {
		for (JNAction action : actions) {
			for (Iterator<Component> iterator = getComponentIterator(); iterator.hasNext();) {
				Component component = iterator.next();
				if (actionsToName.get(action).equalsIgnoreCase(I18NHelper.getKey(component))) {
					component.setVisible(validatePermission (action));
				}
			}
		}
		markAsDirty();
	}

	private boolean validatePermission(JNAction action) {
		if (secured != null) {
			return secured.hasPermission(action.permission());
		}
		return true;
	}

	public boolean isShowSelectedAction() {
		return listener.isShowSelectedAction();
	}

	public void setShowSelectedAction(boolean showSelectedAction) {
		this.listener.setShowSelectedAction(showSelectedAction);
	}
}
