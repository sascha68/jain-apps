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

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.container.JainBeanItem;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Container.ItemSetChangeNotifier;
import com.vaadin.data.Container.Sortable;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Property.ValueChangeNotifier;
import com.vaadin.data.util.AbstractContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.filter.UnsupportedFilterException;

/**
 * <code>AbstractLazyContainer<code> bean Item container
 * @see {@link Indexed} {@link Sortable} {@link Filterable} {@link ItemSetChangeListener} {@link ValueChangeListener}
 * @param <BT>
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class AbstractLazyContainer<BT> extends AbstractContainer implements Indexed, Sortable, Filterable, ItemSetChangeNotifier, ValueChangeListener {
	protected JNILazyList<BT> allItems;
	protected transient LinkedHashMap<String, PropertyDescriptor> model;

	public void addAll(Collection<? extends BT> collection) {
		allItems.ensureCapacity(allItems.size() + collection.size());

		int idx = size();
		for (BT bean : collection) {
			if (internalAddAt(idx, bean) != null) {
				idx++;
			}
		}
		fireItemSetChange();
	}
	
	public BeanItem<BT> addItemAt(int index, Object newItemId) throws UnsupportedOperationException {
		if (index < 0 || index > size()) 
			return null;
		if (index == 0) 
			return addItemAtInternalIndex(0, newItemId);
		return addItemAfter(getIdByIndex(index - 1), newItemId);
	}

	private BeanItem<BT> addItemAtInternalIndex(int index, Object newItemId) {
		BeanItem<BT> beanItem = internalAddAt(index, (BT) newItemId);
		return beanItem;
	}

	public BeanItem<BT> addItemAfter(Object previousItemId, Object newItemId) throws UnsupportedOperationException {
		if (previousItemId == null) 
			return addItemAtInternalIndex(0, newItemId);
		if (containsId(previousItemId))
			return addItemAtInternalIndex(allItems.indexOf(previousItemId) + 1, newItemId);
		return null;
	}


	public BT getIdByIndex(int index) {
		return allItems.get(index);
	}

	public int indexOfId(Object itemId) {
		return allItems.indexOf(itemId);
	}

	public BT firstItemId() {
		if (size() > 0)
			return getIdByIndex(0);
		return null;
	}

	public boolean isFirstId(Object itemId) {
		return firstItemId() == itemId;
	}

	public boolean isLastId(Object itemId) {
		return lastItemId() == itemId;
	}

	public BT lastItemId() {
		if (size() > 0)
			return getIdByIndex(size() - 1);
		return null;
	}

	public BT nextItemId(Object itemId) {
		int index = indexOfId(itemId);
		if (index >= 0 && index < size() - 1)
			return getIdByIndex(index + 1);
		return null;
	}

	public BT prevItemId(Object itemId) {
		int index = indexOfId(itemId);
		if (index > 0) 
			return getIdByIndex(index - 1);
		return null;
	}

	public int size() {
		return allItems.size();
	}

	//Method is taken from Vaadin implementation
	public List<BT> getItemIds(int startIndex, int numberOfIds) {
		if (startIndex < 0) {
			throw new IndexOutOfBoundsException("Start index cannot be negative! startIndex=" + startIndex);
		}

		if (startIndex > allItems.size()) {
			throw new IndexOutOfBoundsException("Start index exceeds container size! startIndex = "
					+ startIndex + " containerLastItemIndex = " + (allItems.size() - 1));
		}

		if (numberOfIds < 1) {
			if (numberOfIds == 0) {
				return Collections.emptyList();
			}
			throw new IllegalArgumentException("Cannot get negative amount of items! numberOfItems = " + numberOfIds);
		}

		int endIndex = startIndex + numberOfIds;

		if (endIndex > allItems.size()) {
			endIndex = allItems.size();
		}
		return Collections.unmodifiableList(allItems.subList(startIndex, endIndex));
	}

	public BeanItem<BT> getItem(Object itemId) {
		JainBeanItem<BT> beanItem = new JainBeanItem<BT>((BT)itemId, getPropertyIds());
		return beanItem;
	}

	public Collection<BT> getItemIds() {
		return allItems;
	}

	public boolean removeAllItems() throws UnsupportedOperationException {
		for (Object item : allItems) {
			removeAllValueChangeListeners(getItem(item));
		}
		allItems.clear();
		fireItemSetChange();
		return true;
	}

	public boolean removeItem(Object itemId) throws UnsupportedOperationException {
		if (!allItems.remove(itemId))
			return false;

		removeAllValueChangeListeners(getItem(itemId));
		fireItemSetChange();
		return true;
	}

	protected void removeValueChangeListener(BeanItem<BT> item, Object propertyId) {
		Property<?> property = item.getItemProperty(propertyId);
		if (property instanceof ValueChangeNotifier) {
			((ValueChangeNotifier) property).removeValueChangeListener(this);
		}
	}

	protected void removeAllValueChangeListeners(BeanItem<BT> item) {
		for (Object propertyId : item.getItemPropertyIds()) {
			removeValueChangeListener(item, propertyId);
		}
	}

	public Collection<String> getContainerPropertyIds() {
		return model.keySet();
	}

	public Class<?> getType(Object propertyId) {
		PropertyDescriptor descriptor = model.get(propertyId);
		if(descriptor != null)
			return descriptor.getPropertyType();
		else
			throw new IllegalArgumentException("Property " + propertyId + " is not available in given type");
	}
	
	public Property<?> getContainerProperty(Object itemId, Object propertyId) {
		return getItem(itemId).getItemProperty(propertyId);
	}

	public boolean containsId(Object itemId) {
		return allItems.contains(itemId);
	}

	public BeanItem<BT> addItem(Object itemId) throws UnsupportedOperationException {
		if (size() > 0) {
			int lastIndex = allItems.indexOf(lastItemId());
			return addItemAtInternalIndex(lastIndex + 1, itemId);
		} 
		return addItemAtInternalIndex(0, itemId);
	}

	public void valueChange(ValueChangeEvent event) {
		fireItemSetChange();		
	}

	public void sort(Object[] propertyId, boolean[] ascending) {
		allItems.sort (propertyId, ascending);
		fireItemSetChange();
	}

	public Collection<Object> getSortableContainerPropertyIds() {
		LinkedList<Object> sortables = new LinkedList<Object>();
		for (Object propertyId : getContainerPropertyIds()) {
			Class<?> propertyType = getType(propertyId);
			if (Comparable.class.isAssignableFrom(propertyType) || propertyType.isPrimitive()) {
				sortables.add(propertyId);
			}
		}
		return sortables;
	}

	//Adding listeners
	public void addItemSetChangeListener(ItemSetChangeListener listener) {
		super.addItemSetChangeListener(listener);
	}

	public void removeItemSetChangeListener(ItemSetChangeListener listener) {
		super.removeItemSetChangeListener(listener);
	}

	public void addListener(ItemSetChangeListener listener) {
		super.addItemSetChangeListener(listener);
	}

	public void removeListener(ItemSetChangeListener listener) {
		super.removeItemSetChangeListener(listener);
	}
	

	//Unsupported Methods
	public Object addItemAt(int index) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public Object addItemAfter(Object previousItemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public Object addItem() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	public void addContainerFilter(Filter filter) throws UnsupportedFilterException {
		throw new UnsupportedOperationException();
	}

	public void removeContainerFilter(Filter filter) {
		throw new UnsupportedOperationException();
	}

	public void removeAllContainerFilters() {
		throw new UnsupportedOperationException();
	}

	//Abstract Methods
	protected abstract BeanItem<BT> internalAddAt(int position, BT bean);
	protected abstract void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException;
	protected abstract  Collection<? extends JNIProperty> getPropertyIds();
}
