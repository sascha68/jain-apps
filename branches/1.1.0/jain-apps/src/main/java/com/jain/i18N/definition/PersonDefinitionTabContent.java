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
package com.jain.i18N.definition;

import com.jain.addon.JNIComponentInit;
import com.jain.addon.JNINamed;
import com.jain.addon.cdi.CDIComponent;
import com.jain.common.JAction;
import com.jain.common.VaadinHelper;
import com.jain.common.listeners.JNICrudLocal;
import com.jain.common.listeners.JainCrudClickListener;
import com.jain.theme.ApplicationTheme;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PersonDefinitionTabContent extends VerticalLayout implements JNICrudLocal {
	private PersonDefinitionGrid grid;

	@JNIComponentInit
	public void init () {
		setSpacing(false);
		setMargin(false);
		setStyleName(ApplicationTheme.ALTERNATE_VIEW);

		JainCrudClickListener clickListner = new JainCrudClickListener(this);
		
		HorizontalLayout hLayout = VaadinHelper.createButtonSegment (clickListner, JAction.VIEW, JAction.ADD, JAction.EDIT, JAction.DELETE);
		
		addComponent(hLayout);
		setComponentAlignment(hLayout, Alignment.TOP_RIGHT);
		setExpandRatio(hLayout, 1);

		grid = CDIComponent.getInstance(PersonDefinitionGrid.class);
		addComponent(grid);
		setExpandRatio(grid, 3);
	}

	public void view() {
		PersonDefinitionForm form = CDIComponent.getInstance(PersonDefinitionForm.class);
		form.setViewOnly(true);
		form.setPerson(grid.getSelected());
		form.setCaption(JAction.VIEW.getDisplayName(getLocale(), grid.getSelected().getDisplayName()));
		getUI().addWindow(form);
	}

	public void create() {
		PersonDefinitionForm form = CDIComponent.getInstance(PersonDefinitionForm.class);
		form.setCaption(JAction.ADD.getDisplayName(getLocale(), "person.name"));
		getUI().addWindow(form);
	}

	public void update() {
		PersonDefinitionForm form = CDIComponent.getInstance(PersonDefinitionForm.class);
		form.setPerson(grid.getSelected());
		form.setCaption(JAction.EDIT.getDisplayName(getLocale(), grid.getSelected().getDisplayName()));
		getUI().addWindow(form);
	}

	public void delete() {
		grid.delete();
	}

	public JNINamed getSelected() {
		return grid.getSelected();
	}
}
