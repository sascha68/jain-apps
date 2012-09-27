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

import com.jain.addon.JNINamed;

/**
 * <code>JNIGroup<code> is a generic interface for the group definition <br/>
 * Every Group used for the layouting should implement this interface.
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
public interface JNIGroup extends JNINamed  {
	public JNIGroup getParent();
	public int getColumns();
	public int getColSpan();
	public void setColumns(int columns);
	public void setColSpan(int columnSpan);
}
