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
package com.jain.addon.action.listener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.jain.addon.StringHelper;
import com.jain.addon.action.JNAction;
import com.jain.addon.event.MethodExpression;
import com.jain.addon.i18N.I18NHelper;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * <code>JNButtonClickListener<code> Default click listener for all the actions
 * @author Lokesh Jain
 * @since Nov 27, 2012
 * @version 1.0.3
 * @param <T>
 *
 */
@SuppressWarnings("serial")
public final class JNButtonClickListener <T> implements ClickListener {
	private final T actionHandler;
	private Map<String, MethodExpression> actions;
	private Map<String, Object []> actionParams;

	/**
	 * @param actionHandler
	 * @param params
	 */
	public JNButtonClickListener(T actionHandler, Object ... params) {
		this(true, actionHandler, params);
	}

	/**
	 * @param populate - Do we need to process annotations by default
	 * @param actionHandler - Object should have some methods annotated with {@link JNAction}
	 * @param params 
	 */
	public JNButtonClickListener(boolean populate, T actionHandler, Object ... params) {
		this.actionHandler = actionHandler;
		this.actions = new HashMap <String, MethodExpression> ();
		this.actionParams = new HashMap<String, Object []>();

		if (populate)
			processActionHandler(params);
	}

	private void processActionHandler(Object ... params) {
		if (actionHandler != null) {
			Method[]  methods = actionHandler.getClass().getMethods();
			for (Method method : methods) {
				JNAction action = method.getAnnotation(JNAction.class);
				if(action != null) {
					addAction(action, method, params);
				}
			}
		}
	}

	/**
	 * Add an action with method and parameters
	 * @param action
	 * @param method
	 * @param params
	 * @return action Name
	 */
	public String addAction(JNAction action, Method method, Object... params) {
		String actionName = null;
		if (StringHelper.isNotEmptyWithTrim(action.value())) {
			actionName = action.value();
		} else {
			actionName = StringHelper.methodToPropertyName(method.getName());
		}
		actions.put(actionName, new MethodExpression(actionHandler, method));
		processParameters (actionName, method, params);
		return actionName;
	}

	private void processParameters(String actionName, Method method, Object ... params) {
		if (params != null) {
			Class <?> [] paramTypes = method.getParameterTypes();
			if (paramTypes.length > 0) {
				Object [] actionParams = new Object [paramTypes.length];
				for (int i = 0; i < paramTypes.length; i ++) {
					Class <?> paramType = paramTypes [i];
					for (Object param : params) {
						if (param != null && paramType.isAssignableFrom(param.getClass())) {
							actionParams [i] = param;
							break;
						}
					}
				}
				this.actionParams.put(actionName, actionParams);
			}
		}
	}

	public void buttonClick(ClickEvent event) {
		String actionName = I18NHelper.getKey(event.getButton());
		MethodExpression action = actions.get(actionName);
		if (action != null) {
			action.invoke(this.actionParams.get(actionName));
		}
	}

	/**
	 * Add Action parameters
	 * @param action
	 * @param actionParams
	 */
	public void addActionParams (String action, Object ... actionParams) {
		this.actionParams.put(action, actionParams);
	}

	/**
	 * Remove action parameters
	 * @param action
	 */
	public void removeActionParams (String action) {
		this.actionParams.remove(action);
	}

	public T getActionHandler() {
		return actionHandler;
	}
}
