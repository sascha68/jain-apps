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

import com.vaadin.Application;
import com.vaadin.RootRequiresMoreInformationException;
import com.vaadin.terminal.WrappedRequest;
import com.vaadin.ui.Root;

public class RootProvider extends Application {
	private static final long serialVersionUID = 45678369L;

	@Inject
	private ApplicationRoot rootProvider;

	protected Root getRoot(WrappedRequest request) throws RootRequiresMoreInformationException {
		return rootProvider;
	}
}