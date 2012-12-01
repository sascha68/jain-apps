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
package com.jain.addon.component.crud;

import java.util.ArrayList;
import java.util.Collection;

import com.jain.addon.JNIComponentInit;
import com.jain.addon.JNINamed;
import com.jain.addon.JNStyleConstants;
import com.jain.addon.action.ActionBar;
import com.jain.addon.action.JNAction;
import com.jain.addon.helper.JNHelper;
import com.jain.addon.security.JNISecured;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

/**
 * <code>JCrud<code> Is a crud component
 * @author Lokesh Jain
 * @since Nov 27, 2012
 * @version 1.0.3
 * @param <T>
 */
@SuppressWarnings("serial")
public class JCrud <T> extends VerticalLayout implements JNISecured {
	private static final String SELECTION_REQUIRED_MESSAGE = "selection.required";
	private Collection <T> values;
	private JCrudGrid<T> grid;
	private JCrudObject<T> type;
	private JNISecured secured;  
	private String displayName = "";
	private int pageLength;
	private int columns;

	@JNIComponentInit
	public void init () {
		setSpacing(false);
		setMargin(false);
		setStyleName(JNStyleConstants.J_CRUD);

		if (values == null) {
			values = new ArrayList<T> ();
		}

		ActionBar <JCrud<T>> hLayout = new ActionBar <JCrud<T>> (this, this);
		addComponent(hLayout);
		setComponentAlignment(hLayout, Alignment.TOP_RIGHT);
		setExpandRatio(hLayout, 1);

		grid = new JCrudGrid<T>();
		grid.setValues(values);
		grid.setReadOnly(isReadOnly());
		grid.setType(type.getType());
		grid.setPageLength(pageLength);
		addComponent(grid);
		setExpandRatio(grid, 3);
	}

	public Collection<T> getValues() {
		return values;
	}

	public void setValues(Collection<T> values) {
		this.values = values;
	}

	public JCrudObject<T> getType() {
		return type;
	}

	public void setType(JCrudObject<T> type) {
		this.type = type;
	}

	public T getSelected() {
		return grid.getSelected();
	}

	//Actions
	@JNAction (value = "view.action.name", tabIndex = 1, permission = VIEW_ACTION_PERMISSION, 
			description = "view.action.description", icon="view.action.icon")
	public void view() {
		if (grid.getSelected() != null) {
			JCrudWindow<T> form = new JCrudWindow<T>();
			form.setViewOnly(true);
			form.setColumns(columns);
			form.setGrid(grid);
			form.setValue(grid.getSelected());
			form.setCaption(JNHelper.getResourceValue(getUI(), "view.action.plus", getDisplayName(), getDisplayName()));
			getUI().addWindow(form);		
		} else {
			JNHelper.showNotification(getUI(), SELECTION_REQUIRED_MESSAGE, SELECTION_REQUIRED_MESSAGE, "view.action.name", getDisplayName());
		}
	}

	@JNAction (value = "add.action.name", tabIndex = 2, permission = ADD_ACTION_PERMISSION, 
			description = "add.action.description",  icon="add.action.icon")
	public void create() {
		JCrudWindow<T> form = new JCrudWindow<T>();
		T value = type.newInstance();
		form.setColumns(columns);
		form.setValue(value);
		form.setGrid(grid);
		form.setCaption(JNHelper.getResourceValue(getUI(), "add.action.plus", getDisplayName(), getDisplayName()));
		getUI().addWindow(form);
	}

	@JNAction (value = "edit.action.name", tabIndex = 3,  permission = EDIT_ACTION_PERMISSION, 
			description = "edit.action.description",  icon="edit.action.icon")
	public void update() {
		if (grid.getSelected() != null) {
			JCrudWindow<T> form = new JCrudWindow<T>();
			form.setValue(grid.getSelected());
			form.setColumns(columns);
			form.setGrid(grid);
			form.setCaption(JNHelper.getResourceValue(getUI(), "edit.action.plus", getDisplayName(), getDisplayName()));
			getUI().addWindow(form);
		} else {
			JNHelper.showNotification(getUI(), SELECTION_REQUIRED_MESSAGE, SELECTION_REQUIRED_MESSAGE, "edit.action.name", getDisplayName());
		}
	}

	@JNAction (value = "delete.action.name", tabIndex = 4, permission = DELETE_ACTION_PERMISSION, 
			description = "delete.action.description",  icon="delete.action.icon")
	public void delete() {
		if (grid.getSelected() != null) {
			grid.delete();
		} else {
			JNHelper.showNotification(getUI(), SELECTION_REQUIRED_MESSAGE, SELECTION_REQUIRED_MESSAGE, "delete.action.name", getDisplayName());
		}
	}

	//Getter Setter
	private String getDisplayName() {
		T value = grid.getSelected();
		if(value != null) {
			if (value instanceof JNINamed)
				return ((JNINamed) value).getDisplayName();
			return value.toString();
		}
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getPageLength() {
		return pageLength;
	}

	public void setPageLength(int pageLength) {
		this.pageLength = pageLength;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public JNISecured getSecured() {
		return secured;
	}

	public void setSecured(JNISecured secured) {
		this.secured = secured;
	}

	public boolean hasPermission(String permission) {
		if ((permission.equals(VIEW_ACTION_PERMISSION) && isReadOnly()) || !isReadOnly()) {
			if (secured != null)
				return secured.hasPermission(permission);

			return true;			
		}
		return false;
	}
}
