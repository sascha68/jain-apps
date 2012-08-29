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
package com.jain.addon.web.layout.segment;

import java.util.Iterator;

import com.jain.addon.StringHelper;
import com.jain.addon.i18N.I18NHelper;
import com.jain.addon.web.JNINamedResourceVisible;
import com.jain.addon.web.marker.authentication.JNILoginListner;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

/**
 * <code>ButtonSegment<code> is a horizontal segment component.<br/>
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class ButtonSegment extends HorizontalLayout implements JNILoginListner {
	private JNINamedResourceVisible[] namedResources;
	private final String firstButtonStyle;
	private final String lastButtonStyle;
	private final String buttonStyle;

	/**
	 * Create a segment instance having {@link Button} style for all the buttons
	 * @param buttonStyle
	 */
	public ButtonSegment(String buttonStyle) {
		this(buttonStyle, buttonStyle, buttonStyle);
	}

	/**
	 * Create a segment instance having first {@link Button} and last {@link Button} styles
	 * @param firstButtonStyle
	 * @param lastButtonStyle
	 */
	public ButtonSegment(String firstButtonStyle, String lastButtonStyle) {
		this(firstButtonStyle, lastButtonStyle, null);
	}

	/**
	 * Create a segment instance having first {@link Button}, last {@link Button} and {@link Button} styles
	 * @param firstButtonStyle
	 * @param lastButtonStyle
	 */
	public ButtonSegment(String firstButtonStyle, String lastButtonStyle, String buttonStyle) {
		this.firstButtonStyle = firstButtonStyle;
		this.lastButtonStyle = lastButtonStyle;
		this.buttonStyle = buttonStyle;
	}

	/**
	 * Initialize a button segment by adding resources {@link JNINamedResourceVisible} and {@link ClickListener}. <br/> 
	 * Every {@link JNINamedResourceVisible} will be converted into button having this {@link ClickListener}.
	 * @param listener -  {@link ClickListener} for the button
	 * @param namedResources - {@link JNINamedResourceVisible} to create button and there visibility
	 */
	public void createSegment (ClickListener listener, JNINamedResourceVisible ... namedResources) {
		if (namedResources != null) {
			this.namedResources = namedResources;

			int i = 0;
			Button action = null;
			for (JNINamedResourceVisible named : namedResources) {
				action = new Button(named.getDisplayName(), listener);
				action.setDescription(named.getDescription());
				action.setVisible(named.isVisible());

				if (StringHelper.isNotEmptyWithTrim(buttonStyle))
					action.setStyleName(firstButtonStyle);

				if(i == 0)
					action.addStyleName(firstButtonStyle);

				i ++;
				addComponent(action);
				setComponentAlignment(action, Alignment.TOP_RIGHT);
			}

			if (action != null)
				action.addStyleName(lastButtonStyle);
		}
	}

	/**
	 * Reinitialize a button segment by updating resources {@link JNINamedResourceVisible} visibility after login.<br/> 
	 */
	public void onLogin() {
		if (namedResources != null) {
			for (JNINamedResourceVisible named : namedResources) {
				for (Iterator<Component> iterator = getComponentIterator(); iterator.hasNext();) {
					Component component = iterator.next();
					if (named.getDisplayName().equalsIgnoreCase(I18NHelper.getKey(component))) {
						component.setVisible(named.isVisible());
					}
				}
			}
		}
		requestRepaint();
	}
}
