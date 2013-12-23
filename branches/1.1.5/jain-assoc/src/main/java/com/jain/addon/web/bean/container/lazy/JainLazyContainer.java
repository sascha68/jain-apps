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
package com.jain.addon.web.bean.container.lazy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Collection;

import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.annotation.JNIAttribute;
import com.jain.addon.web.bean.annotation.JNIConstraint;
import com.jain.addon.web.bean.annotation.JNIEmbedded;
import com.jain.addon.web.bean.annotation.processor.JAnnotationProcessor;
import com.jain.addon.web.bean.container.JainBeanItem;
import com.jain.addon.web.bean.helper.JainBeanPropertyHelper;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;

/**
 * <code>JainLazyContainer<code> is an extension of {@link BeanItemContainer} to support POJO association.
 * @see {@link JainBeanItem}
 * @since Aug 27, 2012
 * @version 1.0.0
 * @param <BT>
 */
@SuppressWarnings({"serial", "unchecked"})
public class JainLazyContainer<BT> extends AbstractLazyContainer<BT> {
    private Collection<? extends JNIProperty> propertyIds;
    private final Class<? extends BT> type;

    /**
     * Create a bean Item container by passing only bean type.
     * In this case bean type should be annotated by the annotations
     * @see {@link JNIAttribute} {@link JNIEmbedded} {@link JNIConstraint}
     * @param type
     */
    public JainLazyContainer(Class<? extends BT> type) {
		this(type, JAnnotationProcessor.instance().getProperties(type).getProperties());
	}
    
    /**
     * Create a bean Item container by passing only bean type and {@link Collection} of {@link JNIProperty}.
     * @param type
     * @param propertyIds
     */
    public JainLazyContainer(Class<? extends BT> type, Collection<? extends JNIProperty> propertyIds) {
        if (type == null || propertyIds == null  || propertyIds.isEmpty()) {
            throw new IllegalArgumentException("The bean type, propertyIds passed to AnishBeanItemContainer must not be null or empty");
        }
        
        this.type = type;
        this.propertyIds = propertyIds;
        JainBeanPropertyHelper helper = new JainBeanPropertyHelper();
        model = helper.getPropertyDescriptors(type, this.propertyIds);
        this.allItems = new JNLazyList<BT>();
    }
    
    /**
     * Create a bean Item container by passing only bean type and array of {@link JNIProperty}.
     * @param type
     * @param propertyIds
     */
    public JainLazyContainer(Class<? extends BT> type, JNIProperty[] propertyIds) {
        this(type, Arrays.asList(propertyIds));
    }

    /**
     * Create a bean Item container by passing {@link JNILazyList} of bean objects and {@link Collection} of {@link JNIProperty}.
     * @param type
     * @param propertyIds
     */
    public JainLazyContainer(JNILazyList<BT> lazyList, Collection<? extends JNIProperty> propertyIds) throws IllegalArgumentException {
        if (propertyIds == null || propertyIds.isEmpty() || lazyList == null || lazyList.isEmpty()) {
            throw new IllegalArgumentException("The bean collection Or propertyIds passed to BeanItemContainer must not be null or empty");
        }

        JainBeanPropertyHelper helper = new JainBeanPropertyHelper();
        type = (Class<? extends BT>) lazyList.iterator().next().getClass();
        model = helper.getPropertyDescriptors(type, propertyIds);
        this.allItems = lazyList;
    }
    
    protected BeanItem<BT> internalAddAt(int position, BT bean) {
        //Make sure that the item has not been added previously
        if (allItems.contains(bean)) {
            return null;
        }

        if (!type.isAssignableFrom(bean.getClass())) {
            return null;
        }

        // "filteredList" will be updated in filterAll() which should be invoked
        // by the caller after calling this method.
        allItems.add(position, bean);
        JainBeanItem<BT> beanItem = new JainBeanItem<BT>(bean, propertyIds);
        return beanItem;
    }

    //------------- Implemented Abstract method ---------------------------------
	protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        JainBeanPropertyHelper helper = new JainBeanPropertyHelper();
        model = helper.getPropertyDescriptors(type, propertyIds);
    }

	protected Collection<? extends JNIProperty> getPropertyIds() {
		return propertyIds;
	}

	@Override
	public Collection<Filter> getContainerFilters() {
		// TODO Auto-generated method stub
		return null;
	}
}
