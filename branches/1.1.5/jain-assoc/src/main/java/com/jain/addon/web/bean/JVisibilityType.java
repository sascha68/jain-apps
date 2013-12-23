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
package com.jain.addon.web.bean;

import com.jain.addon.web.table.JTable;

/**
 * <code>JVisibilityType<code> are the values for property visibility.
 * <pre>
 * VISIBLE - Visible in both form and table.
 * HIDDEN  - Hidden in both form and table. 
 * COLLAPSED  - Collapsed in {@link JTable} and visible in all other forms and tables.
 * NO_COLLAPSING - Not Collapsed in {@link JTable} and visible in all other forms and tables.
 * </pre>
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
public enum JVisibilityType {
	VISIBLE,
	HIDDEN,
	COLLAPSED,
	NO_COLLAPSING;
}