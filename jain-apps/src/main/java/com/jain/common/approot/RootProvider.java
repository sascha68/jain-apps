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
package com.jain.common.approot;

import javax.inject.Inject;

import com.vaadin.server.AbstractUIProvider;
import com.vaadin.server.WrappedRequest;
import com.vaadin.ui.UI;

public class RootProvider extends AbstractUIProvider {
	@Inject
	private ApplicationUI rootProvider;

	@Override
	public UI createInstance(WrappedRequest request, Class<? extends UI> type) {
		return rootProvider;
	}
	
	public Class<? extends UI> getUIClass(WrappedRequest request) {
		return ApplicationUI.class;
	}
}