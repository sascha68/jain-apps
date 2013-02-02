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
package com.jain.common.listeners;

import com.jain.addon.i18N.I18NHelper;
import com.jain.common.JAction;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public final class JainCrudClickListener implements ClickListener {
	private JNICrudLocal listener;

	public JainCrudClickListener(JNICrudLocal listener) {
		this.listener = listener;
	}

	public void buttonClick(ClickEvent event) {
		JAction action = JAction.getDisplayNameEquivlent(I18NHelper.getKey(event.getButton()));
		if (action != null) {
			if(listener.getSelected() == null && action != JAction.ADD) {
				System.out.println("!!!!!!!!!!!!!!!!!!! Selection Required !!!!!!!!!!!!!!!!!!");
				return;
			} else { 
				switch (action) {
				case VIEW:
					listener.view();
					break;
				case ADD:
					listener.create();
					break;
				case EDIT:
					listener.update();
					break;
				case DELETE:
					listener.delete();
					break;
				default:
					break;
				}
			}
		}
	}
}
