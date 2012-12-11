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
package com.jain.addon.web.bean.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jain.addon.web.bean.JConstraintType;
import com.jain.addon.web.bean.JNIPropertyConstraint;

/**
 * <code>JNIConstraint<code> annotation values used to calculate {@link JNIPropertyConstraint} for the attribute.
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JNIConstraint {
	public String width() default "100%";
	public JConstraintType [] types() default {JConstraintType.NONE};
	public String enumarationName () default "";
}
