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

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Container.ItemSetChangeNotifier;
import com.vaadin.data.Container.Sortable;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Property.ValueChangeNotifier;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.DefaultItemSorter;
import com.vaadin.data.util.ItemSorter;
import com.vaadin.data.util.ListSet;
import com.vaadin.data.util.filter.UnsupportedFilterException;

/**
 * <code>AbstractJainBeanItemContainer<code> bean Item container
 * @see {@link Indexed} {@link Sortable} {@link Filterable} {@link ItemSetChangeListener} {@link ValueChangeListener}
 * @param <BT>
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class AbstractJainBeanItemContainer<BT> implements Indexed, Sortable, Filterable, ItemSetChangeNotifier, ValueChangeListener {
	protected ListSet<BT> filteredItems;
	protected ListSet<BT> allItems;
	protected final Map<BT, BeanItem<BT>> beanToItem;
	private ItemSorter itemSorter;
	protected Set<Filter> filters;
	protected transient LinkedHashMap<String, PropertyDescriptor> model;
	protected List<ItemSetChangeListener> itemSetChangeListeners;

	public AbstractJainBeanItemContainer() {
		this.filteredItems = new ListSet<BT>();
		this.allItems = new ListSet<BT>();
		this.beanToItem = new HashMap<BT, BeanItem<BT>>();
		this.itemSorter = new DefaultItemSorter();
		this.filters = new HashSet<Filter>();
	}

	public void addAll(Collection<? extends BT> collection) {
		allItems.ensureCapacity(allItems.size() + collection.size());

		int idx = size();
		for (BT bean : collection) {
			if (internalAddAt(idx, bean) != null) {
				idx++;
			}
		}
		filterAll();
	}

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

	public BeanItem<BT> addItemAt(int index, Object newItemId) throws UnsupportedOperationException {
		if (index < 0 || index > size()) 
			return null;
		if (index == 0) 
			return addItemAtInternalIndex(0, newItemId);
		return addItemAfter(getIdByIndex(index - 1), newItemId);
	}

	private BeanItem<BT> addItemAtInternalIndex(int index, Object newItemId) {
		BeanItem<BT> beanItem = internalAddAt(index, (BT) newItemId);
		if (beanItem != null) 
			filterAll();
		return beanItem;
	}

	public BT getIdByIndex(int index) {
		return filteredItems.get(index);
	}

	public int indexOfId(Object itemId) {
		return filteredItems.indexOf(itemId);
	}

	public BeanItem<BT> addItemAfter(Object previousItemId, Object newItemId) throws UnsupportedOperationException {
		if (previousItemId == null) 
			return addItemAtInternalIndex(0, newItemId);
		if (containsId(previousItemId))
			return addItemAtInternalIndex(allItems.indexOf(previousItemId) + 1, newItemId);
		return null;
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

	public BeanItem<BT> addBean(BT bean) {
		return addItem(bean);
	}

	public BeanItem<BT> addItem(Object itemId) throws UnsupportedOperationException {
		if (size() > 0) {
			int lastIndex = allItems.indexOf(lastItemId());
			return addItemAtInternalIndex(lastIndex + 1, itemId);
		} 
		return addItemAtInternalIndex(0, itemId);
	}

	public boolean containsId(Object itemId) {
		return filteredItems.contains(itemId);
	}

	public Property<?> getContainerProperty(Object itemId, Object propertyId) {
		return getItem(itemId).getItemProperty(propertyId);
	}

	public Collection<String> getContainerPropertyIds() {
		return model.keySet();
	}

	public BeanItem<BT> getItem(Object itemId) {
		return beanToItem.get(itemId);
	}

	public Collection<BT> getItemIds() {
		return (Collection<BT>) filteredItems.clone();
	}

	public Class<?> getType(Object propertyId) {
		PropertyDescriptor descriptor = model.get(propertyId);
		if(descriptor != null)
			return descriptor.getPropertyType();
		else
			throw new IllegalArgumentException("Property " + propertyId + " is not available in given type");
	}

	public boolean removeAllItems() throws UnsupportedOperationException {
		allItems.clear();
		filteredItems.clear();

		for (BeanItem<BT> item : beanToItem.values()) {
			removeAllValueChangeListeners(item);
		}

		beanToItem.clear();
		fireItemSetChange();
		return true;
	}

	public boolean removeItem(Object itemId) throws UnsupportedOperationException {
		if (!allItems.remove(itemId))
			return false;

		removeAllValueChangeListeners(getItem(itemId));
		beanToItem.remove(itemId);
		filteredItems.remove(itemId);
		fireItemSetChange();
		return true;
	}

	protected void addValueChangeListener(BeanItem<BT> beanItem, Object propertyId) {
		Property<?> property = beanItem.getItemProperty(propertyId);
		if (property instanceof ValueChangeNotifier) {
			// Avoid multiple notifications for the same property if multiple filters are in use
			ValueChangeNotifier notifier = (ValueChangeNotifier) property;
			notifier.removeListener(this);
			notifier.addListener(this);
		}
	}

	protected void removeValueChangeListener(BeanItem<BT> item, Object propertyId) {
		Property<?> property = item.getItemProperty(propertyId);
		if (property instanceof ValueChangeNotifier) {
			((ValueChangeNotifier) property).removeListener(this);
		}
	}

	protected void removeAllValueChangeListeners(BeanItem<BT> item) {
		for (Object propertyId : item.getItemPropertyIds()) {
			removeValueChangeListener(item, propertyId);
		}
	}

	public int size() {
		return filteredItems.size();
	}

	public Collection<Object> getSortableContainerPropertyIds() {
		LinkedList<Object> sortables = new LinkedList<Object>();
		for (Object propertyId : getContainerPropertyIds()) {
			Class<?> propertyType = getType(propertyId);
			if (Comparable.class.isAssignableFrom(propertyType)
					|| propertyType.isPrimitive()) {
				sortables.add(propertyId);
			}
		}
		return sortables;
	}

	public void sort(Object[] propertyId, boolean[] ascending) {
		itemSorter.setSortProperties(this, propertyId, ascending);

		doSort();

		// notifies if anything changes in the filtered list, including order
		filterAll();
	}

	protected void doSort() {
		Collections.sort(allItems, getItemSorter());
	}

	public void addListener(ItemSetChangeListener listener) {
		if (itemSetChangeListeners == null) {
			itemSetChangeListeners = new LinkedList<ItemSetChangeListener>();
		}
		itemSetChangeListeners.add(listener);
	}

	public void removeListener(ItemSetChangeListener listener) {
		if (itemSetChangeListeners != null) {
			itemSetChangeListeners.remove(listener);
		}
	}

	protected void fireItemSetChange() {
		if (itemSetChangeListeners != null) {
			final Container.ItemSetChangeEvent event = new Container.ItemSetChangeEvent() {
				public Container getContainer() {
					return AbstractJainBeanItemContainer.this;
				}
			};
			for (ItemSetChangeListener listener : itemSetChangeListeners) {
				listener.containerItemSetChange(event);
			}
		}
	}

	public void valueChange(ValueChangeEvent event) {
		// if a property that is used in a filter is changed, refresh filtering
		filterAll();
	}

	protected void filterAll() {
		// avoid notification if the filtering had no effect
		List<BT> originalItems = filteredItems;

		// it is somewhat inefficient to do a (shallow) clone() every time
		filteredItems = (ListSet<BT>) allItems.clone();
		for (Filter f : filters) {
			//TODO: Check this Again
			filter(null, f);
		}
		// check if exactly the same items are there after filtering to avoid unnecessary notifications this may be slow in some cases as it uses BT.equals()
		if (!originalItems.equals(filteredItems)) {
			fireItemSetChange();
		}
	}

	protected void filter(Object propertyId, Filter f) {
		Iterator<BT> iterator = filteredItems.iterator();
		while (iterator.hasNext()) {
			BT bean = iterator.next();

			if (!f.passesFilter(propertyId, (getItem(bean)))) {
				iterator.remove();
			}
		}
	}

	public void removeAllContainerFilters() {
		if (!filters.isEmpty()) {
			filters = new HashSet<Filter>();
			// stop listening to change events for any property
			for (BeanItem<BT> item : beanToItem.values()) {
				removeAllValueChangeListeners(item);
			}
			filterAll();
		}
	}

	public void removeContainerFilters(Object propertyId) {
		if (!filters.isEmpty()) {
			for (Iterator<Filter> iterator = filters.iterator(); iterator.hasNext();) {
				Filter f = iterator.next();
				if (!f.appliesToProperty(propertyId)) {
					iterator.remove();
				}
			}
			// stop listening to change events for the property
			for (BeanItem<BT> item : beanToItem.values()) {
				removeValueChangeListener(item, propertyId);
			}
			filterAll();
		}
	}

	public void addContainerFilter(Filter filter) throws UnsupportedFilterException {
		filters.add(filter);
	}

	public void removeContainerFilter(Filter filter) {
		filters.remove(filter);
	}

	public ItemSorter getItemSorter() {
		return itemSorter;
	}

	public void setItemSorter(ItemSorter itemSorter) {
		this.itemSorter = itemSorter;
	}

	//Abstract Methods
	protected abstract BeanItem<BT> internalAddAt(int position, BT bean);
	protected abstract void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException;
}
