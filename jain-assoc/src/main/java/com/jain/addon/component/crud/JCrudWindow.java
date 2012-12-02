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

import com.jain.addon.JNIComponentInit;
import com.jain.addon.JNStyleConstants;
import com.jain.addon.action.ActionBar;
import com.jain.addon.action.JNAction;
import com.jain.addon.i18N.component.I18NWindow;
import com.jain.addon.web.bean.container.JainBeanItem;
import com.jain.addon.web.field.JFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

/**
 * <code>JCrudWindow<code> is a form window for Crud component
 * @author Lokesh Jain
 * @since Nov 27, 2012
 * @version 1.0.3
 * @param <T>
 *
 */
@SuppressWarnings("serial")
public class JCrudWindow <T> extends I18NWindow {
	private T value;
	private JCrudGrid <T> grid;
	private FieldGroup fieldGroup;
	private int columns = 2;
	private boolean viewOnly = false;

	@JNIComponentInit
	public void init () {
		setModal(true);
		setSizeUndefined();
		setWidth("70%");

		VerticalLayout layout = new VerticalLayout();
		layout.setWidth("100%");
		layout.setMargin(new MarginInfo(false, false, true, false));
		layout.setStyleName(JNStyleConstants.J_ALTERNATE_VIEW);

		setContent(layout);
		createFieldGroup(layout);

		createActions(layout);
	}

	private void createActions(VerticalLayout layout) {
		if(!isViewOnly()) {
			ActionBar<JCrudWindow <T>> hLayout = new ActionBar<JCrudWindow <T>> (null, this);

			VerticalLayout vLayout = new VerticalLayout();
			vLayout.setSizeUndefined();
			vLayout.setStyleName(JNStyleConstants.J_VIEW);
			vLayout.addComponent(hLayout);

			layout.addComponent(vLayout);
			layout.setComponentAlignment(vLayout, Alignment.MIDDLE_CENTER);
			layout.setExpandRatio(vLayout, 1);
		}
	}

	private void createFieldGroup(VerticalLayout layout) {
		JainBeanItem<T> item = new JainBeanItem<T>(getValue ());
		JFieldGroup<T> jainFieldGroup = new JFieldGroup<T>(getType(), columns);
		jainFieldGroup.setViewOnly (isViewOnly());
		jainFieldGroup.setStyleName(JNStyleConstants.J_VIEW);
		jainFieldGroup.setAlternateStyleName(JNStyleConstants.J_ALTERNATE_VIEW);
		layout.addComponent(jainFieldGroup);

		fieldGroup = jainFieldGroup.createFieldGroup(getType(), item); 
	}

	@JNAction (name = "save.action.name", tabIndex = 1, description = "save.action.description", icon="save.action.icon")
	public void save() {
		try {
			fieldGroup.commit();
			grid.reload(getValue());
			getUI().removeWindow(this);
		} catch (CommitException e) {
			e.printStackTrace();
		}
	}

	@JNAction (name = "cancel.action.name", tabIndex = 2, icon="cancel.action.icon", description = "cancel.action.description")
	public void cancel() {
		fieldGroup.discard();
		getUI().removeWindow(this);
	}

	private T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	public JCrudGrid<T> getGrid() {
		return grid;
	}

	public void setGrid(JCrudGrid<T> grid) {
		this.grid = grid;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getType () {
		return (Class<T>) getValue().getClass();
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns > 0 ? columns : 2;
	}

	public boolean isViewOnly () {
		return this.viewOnly;
	}

	public void setViewOnly(boolean viewOnly) {
		this.viewOnly = viewOnly;
	}
}
