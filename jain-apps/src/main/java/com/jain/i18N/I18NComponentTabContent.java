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
package com.jain.i18N;

import com.jain.addon.JNIComponentInit;
import com.jain.addon.cdi.CDIComponent;
import com.jain.i18N.annotation.PersonAnnotationTabContent;
import com.jain.i18N.definition.PersonDefinitionTabContent;
import com.vaadin.ui.TabSheet;

@SuppressWarnings("serial")
public class I18NComponentTabContent extends TabSheet {
	
	@JNIComponentInit
	public void init() {
		PersonAnnotationTabContent personAnnotationTabContent = CDIComponent.getInstance(PersonAnnotationTabContent.class);
		addTab(personAnnotationTabContent, "annotaion.approach.name");
		
		PersonDefinitionTabContent definitionTabContent = CDIComponent.getInstance(PersonDefinitionTabContent.class);
		addTab(definitionTabContent, "definition.approach.name");
	}
}
