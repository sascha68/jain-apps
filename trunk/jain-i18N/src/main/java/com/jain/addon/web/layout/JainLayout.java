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
package com.jain.addon.web.layout;

import com.jain.addon.StringHelper;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * <code>JainLayout<code> is a dynamic column based layout generated using {@link HorizontalLayout} and {@link VerticalLayout}. <br/> 
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JainLayout extends VerticalLayout {
	private String alternateStyleName;
	private boolean spacing;
	private int columns;
	private HorizontalLayout currentLayout;
	private int hCount;

	/**
	 * Create a instance with spacing, margin and number of columns
	 * @param spacing
	 * @param margin
	 * @param columns
	 */
	public JainLayout(boolean spacing, MarginInfo margin, int columns) {
		setSpacing(spacing);
		setMargin(margin);
		this.spacing = spacing;
		this.columns = columns;
		hCount = 0;
	}

	/**
	 * Create a instance with number of columns <br/>
	 * Default spacing and margin will be enabled
	 * @param columns
	 */
	public JainLayout(int columns) {
		this(true, new MarginInfo(true), columns);
	}

	@Override
	public void addComponent(Component component) {
		addComponent(component, 1);
	}

	/**
	 * Add a column with column span for current component 
	 * @param @link Component}
	 * @param colSpan
	 */
	public void addComponent(Component component, int colSpan) {
		HorizontalLayout hLayout = getHLayout(colSpan);
		hLayout.addComponent(component);
		hLayout.setExpandRatio(component, 1.0f);
	}

	private HorizontalLayout getHLayout(int colSpan) {
		if(currentLayout == null || hCount >= columns || hCount + colSpan > columns) {
			currentLayout = new HorizontalLayout();
			currentLayout.setWidth("100%");
			currentLayout.setSpacing(spacing);

			if(StringHelper.isNotEmptyWithTrim(alternateStyleName)) {
				currentLayout.setStyleName(alternateStyleName);
				currentLayout.setMargin(getMargin());
			}

			super.addComponent(currentLayout);
			hCount = 0;
		}
		hCount  = colSpan == 0 ? hCount + 1 : hCount + colSpan;
		return currentLayout;
	}

	/**
	 * Get the alternative layout style name
	 */
	public String getAlternateStyleName() {
		return alternateStyleName;
	}

	/**
	 * Set the alternative layout style name
	 */
	public void setAlternateStyleName(String alternateStyleName) {
		this.alternateStyleName = alternateStyleName;
	}
}
