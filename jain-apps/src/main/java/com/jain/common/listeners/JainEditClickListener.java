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
import com.jain.addon.web.marker.JNIEditLocal;
import com.jain.common.JAction;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public final class JainEditClickListener implements ClickListener {
	private JNIEditLocal listener;

	public JainEditClickListener(JNIEditLocal listener) {
		this.listener = listener;
	}

	public void buttonClick(ClickEvent event) {
		JAction action = JAction.getDisplayNameEquivlent(I18NHelper.getKey(event.getButton()));
		if(action != null) {
			switch (action) {
			case SAVE:
				listener.save();
				break;
			case CANCEL:
				listener.cancel();
				break;
			default:
				break;
			}
		}
	}
}
