/* 
 * Copyright 2012
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
package com.jain.addon.cdi;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * 
 * <pre> 
 * <code>CDIComponent</code> is a helping API to inject CDI resources form bean container in a POJO class.
 * </pre>
 *
 */
@SuppressWarnings("unchecked")
public final class CDIComponent {
	private CDIComponent () {}
	
	/**
	 * <p>
	 * This method is used to find out the instance of <code>Class</code> from the CDI container.
	 * </p>
	 * @param clazz
	 * @return Instance - from the CDI container
	 */
	public static <T> T getInstance(Class<T> clazz) {
		final BeanManager beanManager = getBeanManager();
		final Bean<?> bean = beanManager.getBeans(clazz).iterator().next();
		final CreationalContext<?> ctx = beanManager.createCreationalContext(bean);
		return (T) beanManager.getReference(bean, bean.getClass(), ctx);
	}

	/**
	 * <p>
	 * This method is used to find out the instance of <strong>provided Component name</strong> from the CDI container.
	 * </p>
	 * @param clazz
	 * @return Instance - from the CDI container
	 */
	public static <T> T getInstance(String name) {
		final BeanManager beanManager = getBeanManager();
		final Bean<?> bean = beanManager.getBeans(name).iterator().next();
		final CreationalContext<?> ctx = beanManager.createCreationalContext(bean);
		return (T) beanManager.getReference(bean, bean.getClass(), ctx);
	}

	private static BeanManager getBeanManager() {
		Context jndiCtx = null;
		BeanManager beanManager = null;
		try {
			jndiCtx = new InitialContext();
			beanManager = (BeanManager) jndiCtx.lookup("java:comp/BeanManager");
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			if (jndiCtx != null) {
				try {
					jndiCtx.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		}
		return beanManager;
	}
}