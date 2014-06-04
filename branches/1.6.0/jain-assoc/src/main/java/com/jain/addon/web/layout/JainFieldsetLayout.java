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

import com.vaadin.ui.CustomLayout;

/**
 * <code>JainFieldsetLayout<code>
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 * 
 *
 */
@SuppressWarnings("serial")
public class JainFieldsetLayout extends CustomLayout {

	public JainFieldsetLayout() {
		super("");
		setTemplateContents(getTemplate());
	}
	
	public static String getTemplate () {
		StringBuilder template = new StringBuilder();
		template.append("<fieldset>");
		template.append(" <legend> ");
		template.append(" <div location=\"caption\"></div> ");
		template.append(" </legend> ");
		template.append(" <div location=\"content\"></div> ");
		template.append("</fieldset>");
		return template.toString();
	}
	
	public void setCaption(String caption) {
		super.setCaption(caption);
	}
}
