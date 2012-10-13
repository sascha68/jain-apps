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

import java.util.HashMap;
import java.util.Map;

import com.jain.addon.StringHelper;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 * <code>JainGroupLayout<code> is a layout component for a group of fields.<br/>
 * All the styles needs to be added before adding components.
 * @see {@link JainLayout} 
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JainGroupLayout extends JainLayout {
	private static final String DEFAULT_GROUP_NAME = "jain.default.layout.name"; 
	private String styleName;
	private String alternateStyleName;
	private boolean spacing;
	private MarginInfo margin;
	private int columns;
	private Map<String, JainLayout> groupLayoutMap;

	/**
	 * Create a instance with spacing, margin and number of columns
	 * @param spacing
	 * @param margin
	 * @param columns
	 */
	public JainGroupLayout(boolean spacing, MarginInfo margin, int columns) {
		super(spacing, margin, columns);
		this.spacing = spacing;
		this.columns = columns;
		this.margin = margin;
		groupLayoutMap = new HashMap<String, JainLayout>();
	}

	/**
	 * Create a instance with number of columns <br/>
	 * Default spacing and margin will be enabled
	 * @param columns
	 */
	public JainGroupLayout(int columns) {
		this(true, new MarginInfo(true), columns);
	}

	/**
	 * Add a component to {@link JainGroupLayout} for a calculated space usnig {@link JNILayout}
	 * @see {@link JNILayout}, {@link JNIGroup}
	 * @param component
	 * @param layout
	 */
	public void addComponent(Component component, JNILayout layout) {
		JainLayout currentLayout = getLayout(layout);
		currentLayout.addComponent(component, layout.getColSpan());
	}

	private JainLayout getLayout(JNILayout layout) {
		String groupName = getGroupName(layout);

		JainLayout currentLayout = groupLayoutMap.get(groupName);  

		if(currentLayout == null) {
			throw new NullPointerException("Layout creation Error");
		}
		return currentLayout;
	}

	private String getGroupName(JNILayout layout) {
		String groupName = layout.getGroup() == null ? DEFAULT_GROUP_NAME : layout.getGroup().getName();

		if(StringHelper.isEmptyWithTrim(groupName))
			throw new NullPointerException("Group name should be available for a Group");

		JainLayout currentLayout = groupLayoutMap.get(groupName);
		if(currentLayout == null) {
			createOrUpdateCurrentLayout(layout.getGroup(), groupName);
		}
		return groupName;
	}

	private JainLayout createOrUpdateCurrentLayout(JNIGroup group, String groupName) {
		if(group != null && group.getParent() != null) {
			JainLayout parentLayout = groupLayoutMap.get(group.getParent().getName());
			if(parentLayout == null) {
				parentLayout = createOrUpdateCurrentLayout(group.getParent(), group.getParent().getName());
			}

			if(parentLayout != null) {
				JainLayout layout = new JainLayout(spacing, margin, group.getParent() == null ? group.getColumns() : group.getParent().getColumns());

				if(StringHelper.isNotEmptyWithTrim(alternateStyleName))
					layout.setStyleName(alternateStyleName);

				VerticalLayout groupLayout = new VerticalLayout();

				if(StringHelper.isNotEmptyWithTrim(styleName))
					groupLayout.setStyleName(styleName);

				if (StringHelper.isNotEmptyWithTrim(group.getDisplayName())) {
					groupLayout.setCaption(group.getDisplayName());
				} 

				groupLayout.setSpacing(true);
				groupLayout.setMargin(true);
				groupLayout.setWidth("100%");
				groupLayout.addComponent(layout);
				parentLayout.addComponent(groupLayout, group.getColSpan());
				
				groupLayoutMap.put(groupName, layout);
				return layout;
			}
		}

		String groupDisplayName =  group == null ? "" : group.getDisplayName();
		JainLayout layout = new JainLayout(spacing, margin, group == null ? columns : group.getColumns());

		if(StringHelper.isNotEmptyWithTrim(alternateStyleName))
			layout.setStyleName(alternateStyleName);

		VerticalLayout groupLayout = new VerticalLayout();

		if(StringHelper.isNotEmptyWithTrim(styleName))
			groupLayout.setStyleName(styleName);

		if(StringHelper.isNotEmptyWithTrim(groupDisplayName)) {
			groupLayout.setCaption(group.getDisplayName());
		} 

		groupLayout.setSpacing(true);
		groupLayout.setMargin(true);
		groupLayout.setWidth("100%");
		groupLayout.addComponent(layout);
		super.addComponent(groupLayout);
		
		groupLayoutMap.put(groupName, layout);
		return layout;
	}

	/**
	 * Get the layout style name
	 */
	public String getStyleName() {
		return styleName;
	}

	/**
	 * Set the layout style name
	 */

	public void setStyleName(String styleName) {
		this.styleName = styleName;
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
