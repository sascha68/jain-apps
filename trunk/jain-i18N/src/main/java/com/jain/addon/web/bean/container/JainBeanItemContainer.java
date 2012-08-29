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
package com.jain.addon.web.bean.container;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Collection;

import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.annotation.JNIAttribute;
import com.jain.addon.web.bean.annotation.JNIConstraint;
import com.jain.addon.web.bean.annotation.JNIEmbaded;
import com.jain.addon.web.bean.annotation.processor.JAnnotationProcessor;
import com.jain.addon.web.bean.filter.JainFilter;
import com.jain.addon.web.bean.helper.JainBeanPropertyHelper;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ListSet;

/**
 * <code>JainBeanItemContainer<code> is an extension of {@link BeanItemContainer} to support POJO association.
 * @see {@link JainBeanItem}
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 * @param <BT>
 */
@SuppressWarnings({"serial", "unchecked"})
public class JainBeanItemContainer<BT> extends AbstractJainBeanItemContainer<BT> {
    private Collection<? extends JNIProperty> propertyIds;
    private final Class<? extends BT> type;

    /**
     * Create a bean Item container by passing only bean type.
     * In this case bean type should be annotated by the annotations
     * @see {@link JNIAttribute} {@link JNIEmbaded} {@link JNIConstraint}
     * @param type
     */
    public JainBeanItemContainer(Class<? extends BT> type) {
		this(type, JAnnotationProcessor.instance().getProperties(type).getProperties());
	}
    
    /**
     * Create a bean Item container by passing only bean type and {@link Collection} of {@link JNIProperty}.
     * @param type
     * @param propertyIds
     */
    public JainBeanItemContainer(Class<? extends BT> type, Collection<? extends JNIProperty> propertyIds) {
        if (type == null || propertyIds == null  || propertyIds.isEmpty()) {
            throw new IllegalArgumentException("The bean type, propertyIds passed to AnishBeanItemContainer must not be null or empty");
        }
        
        this.type = type;
        this.propertyIds = propertyIds;
        JainBeanPropertyHelper helper = new JainBeanPropertyHelper();
        model = helper.getPropertyDescriptors(type, this.propertyIds);
    }
    
    /**
     * Create a bean Item container by passing only bean type and array of {@link JNIProperty}.
     * @param type
     * @param propertyIds
     */
    public JainBeanItemContainer(Class<? extends BT> type, JNIProperty[] propertyIds) {
        this(type, Arrays.asList(propertyIds));
    }

    /**
     * Create a bean Item container by passing {@link Collection} of bean objects and {@link Collection} of {@link JNIProperty}.
     * @param type
     * @param propertyIds
     */
    public JainBeanItemContainer(Collection<? extends BT> collection, Collection<? extends JNIProperty> propertyIds) throws IllegalArgumentException {
        if (propertyIds == null || propertyIds.isEmpty() || collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("The bean collection Or propertyIds passed to BeanItemContainer must not be null or empty");
        }

        JainBeanPropertyHelper helper = new JainBeanPropertyHelper();
        type = (Class<? extends BT>) collection.iterator().next().getClass();
        model = helper.getPropertyDescriptors(type, propertyIds);
        addAll(collection);
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
        beanToItem.put(bean, beanItem);

        //Add listeners to be able to update filtering on property changes
        for (Filter filter : filters) {
            //AddValueChangeListener avoids adding duplicates
            addValueChangeListener(beanItem, ((JainFilter)filter).getPropertyId());
        }
        return beanItem;
    }

    /**
     * Add container filters for a property id
     * @param propertyId
     * @param filterString
     * @param ignoreCase
     * @param onlyMatchPrefix
     */
    public void addContainerFilter(Object propertyId, String filterString, boolean ignoreCase, boolean onlyMatchPrefix) {
        if (filters.isEmpty()) {
            filteredItems = (ListSet<BT>) allItems.clone();
        }
        // listen to change events to be able to update filtering
        for (BeanItem<BT> item : beanToItem.values()) {
            addValueChangeListener(item, propertyId);
        }
        
        Filter f = new JainFilter(propertyId, filterString, ignoreCase, onlyMatchPrefix);
        filter(propertyId, f);
        addContainerFilter(f);
        fireItemSetChange();
    }

    //------------- Implemented Abstract method ---------------------------------
	protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        JainBeanPropertyHelper helper = new JainBeanPropertyHelper();
        model = helper.getPropertyDescriptors(type, propertyIds);
    }
}
