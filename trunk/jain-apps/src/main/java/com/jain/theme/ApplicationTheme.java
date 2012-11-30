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
package com.jain.theme;

import java.io.Serializable;

import com.jain.addon.JNStyleConstants;

public interface ApplicationTheme extends Serializable {
	String THEME_NAME = "apptheme";
	String VIEW = JNStyleConstants.J_VIEW;
	String ALTERNATE_VIEW = JNStyleConstants.J_ALTERNATE_VIEW;
	String HEADER = "header";
	String HEADER_SEGMENT = "header-segment";
	String HEADER_SEGMENT_SMALL = "header-segment-small";
	String LAST = "last";
	String FIRST = "first";
	String SELECTED = "selected";
	String UPLOADER = "uploader";
	String UPLOADER_SEGMENT = "uploader-segment";
	String SMALL_TABSHEET="small-tabsheet";
	String WELCOME_MESSAGE="welcome-message";
}
