package com.jain.addon;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre> 
 * <code>JNIComponentInit<code> is an annotation used for component initialization.
 * All component initialization methods should be annotated by this annotation before 
 * registering for the UI component. 
 * </pre>
 * @author Lokesh Jain
 * @since Oct 27, 2012
 * @version 1.0.3
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JNIComponentInit {

}
