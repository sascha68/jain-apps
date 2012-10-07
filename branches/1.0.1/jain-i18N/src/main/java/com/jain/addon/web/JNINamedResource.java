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
package com.jain.addon.web;

import com.jain.addon.JNINamed;

/**
 * <code>JNINamedResource<code> indicates framework to use 
 * display name instead of toString for an component value along with resource url.
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
public interface JNINamedResource extends JNINamed {
	String getResource ();
}
