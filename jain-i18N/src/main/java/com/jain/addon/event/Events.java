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
package com.jain.addon.event;

import java.io.Serializable;
import java.util.Collection;

import com.jain.addon.web.marker.authentication.JLoginHandler;
import com.vaadin.ui.Root;
/**
 * <pre> 
 * <code>Events<code> class to raise events for handler.
 * After observing events this calls out all the observer methods register for this event. 
 * </pre>
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public final class Events implements Serializable {
	private static Events instance;
	
	private Events () {}
	
	/**
	 * Returns instance of the event handler as this class is singleton service.  
	 * @return {@link Events}
	 */
	public static Events instance() {
		if (instance == null) {
			synchronized (Events.class) {
				if (instance == null) {
					instance = new Events();
				}
			}
		}
		return instance;
	}
	
	
	/**
	 * Raise an event, so that {@link EventHandler} can observe this event and call proper observer methods in the application.
	 * @param EventName
	 * @param parameters needs to passed in observer method
	 */
	public void raiseEvent(String event, Object ... parameters) {
		Collection<MethodExpression> expressions = EventHandler.instance().getMethodExpressions(event);
		
		for (MethodExpression expression : expressions) {
			expression.invoke(parameters);
		}
	}
	
	/**
	 * Raise an Login event, so that {@link EventHandler} can observe this event and call proper onLogin methods in the application.
	 * @param {@link Root}
	 */
	public void raiseLoginEvent(Root root) {
		JLoginHandler.onLogin(root);
	}

	/**
	 * Raise an Logout event, so that {@link EventHandler} can observe this event and call proper Logout methods in the application.
	 * @param {@link Root}
	 */
	public void raiseLogoutEvent(Root root) {
		JLoginHandler.onLogin(root);
	}
}
