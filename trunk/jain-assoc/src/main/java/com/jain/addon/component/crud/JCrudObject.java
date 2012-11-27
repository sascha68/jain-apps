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
package com.jain.addon.component.crud;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <code>JCrudObject<code> is a object to provide object configuration to the crud component. 
 * @author Lokesh Jain
 * @since Nov 27, 2012
 * @version 1.0.3
 * @param <T>
 *
 */
@SuppressWarnings("serial")
public class JCrudObject<T> implements Serializable {
	private Class<T> type;
	private String methodName;
	private Collection<JCrudObject <?>> subObjects;

	public JCrudObject(Class<T> type, String methodName) {
		this.type = type;
		this.methodName = methodName;
	}

	public Class<T> getType() {
		return type;
	}

	public void setType(Class<T> type) {
		this.type = type;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Collection<JCrudObject <?>> getSubObjects() {
		return subObjects;
	}

	public void setSubObjects(Collection<JCrudObject <?>> subObjects) {
		this.subObjects = subObjects;
	}

	public void addSubObject(JCrudObject <?> subObject) {
		if (this.subObjects == null) {
			this.subObjects = new ArrayList<JCrudObject<?>>();
		}
		this.subObjects.add(subObject);
	}
	
	public <E> JCrudObject<E> addSubObject(Class<E> type, String methodName) {
		JCrudObject<E> object = new JCrudObject <E> (type, methodName);
		addSubObject(object);
		return object;
	}

	public void removeSubObject(JCrudObject <?> subObject) {
		if (this.subObjects == null) {
			this.subObjects = new ArrayList<JCrudObject<?>>();
		}
		this.subObjects.remove(subObject);
	}

	public T newInstance() {
		T obj = null;
		try {
			obj = (T) type.newInstance();
			createSubObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	private void createSubObject(Object obj) {
		if(subObjects != null) {
			for (JCrudObject <?> object : subObjects) {
				try {
					Object subObject = object.newInstance();
					String methodName = object.getMethodName();
					updateObject(obj, subObject, methodName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	private void updateObject(Object obj, Object subObject, String methodName) throws Exception{
		Method  method = findBestMethod(obj.getClass(), methodName, subObject.getClass());
		if(method != null)
			method.invoke(obj, subObject);
	}

	private Method findBestMethod(Class<?> c, String name, Class<?> param) {
		Method m = null;
		try {
			m = c.getMethod(name, param);
		} catch (Exception e) {
			if(param != null){
				if(param.getName().equals(Object.class.getName()))
					return null;
				return findBestMethod(c, name, param.getSuperclass());
			}
		}
		return m;
	}
}
