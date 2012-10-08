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
package com.jain.addon.i18N.component;

import com.jain.addon.I18N.listners.JAttachDetachListner;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * <code>I18NUI<code> is an abstract root component with some basic 18n configuration.<br/>
 * Every application should implement this class to create there root instance 
 * because this include some configuration related to window attachment, 
 * detachment and listeners required for the i18N support.    
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public abstract class I18NUI extends UI {

	protected void init(VaadinRequest request) {
		JAttachDetachListner attachListener = new JAttachDetachListner();
		addComponentAttachListener(attachListener);
		addComponentDetachListener(attachListener);

		initialize (request);
	}

	public void addWindow(Window window) throws IllegalArgumentException, NullPointerException {
		super.addWindow(window);
		fireComponentAttachEvent(window);
	}
	
	public boolean removeWindow(Window window) {
		boolean removed = super.removeWindow(window);
		fireComponentDetachEvent(window);
		return removed;
	}

	protected abstract void initialize (VaadinRequest request);
}
