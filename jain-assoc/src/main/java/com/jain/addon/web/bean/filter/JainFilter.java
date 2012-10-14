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
package com.jain.addon.web.bean.filter;

import java.io.Serializable;

import com.jain.addon.web.bean.container.JainBeanItemContainer;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

/**
 * <code>JainFilter<code> is a filter class for {@link JainBeanItemContainer}
 */
@SuppressWarnings("serial")
public class JainFilter implements Serializable, Filter {
	private final Object propertyId;
	private final String filterString;
	private final boolean ignoreCase;
	private final boolean onlyMatchPrefix;

	public JainFilter(Object propertyId, String filterString, boolean ignoreCase, boolean onlyMatchPrefix) {
		this.propertyId = propertyId;
		this.filterString = ignoreCase ? filterString.toLowerCase() : filterString;
		this.ignoreCase = ignoreCase;
		this.onlyMatchPrefix = onlyMatchPrefix;
	}

	public Object getPropertyId() {
		return propertyId;
	}

	public String getFilterString() {
		return filterString;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public boolean isOnlyMatchPrefix() {
		return onlyMatchPrefix;
	}

	public boolean passesFilter(Item item) {
		final Property<?> p = item.getItemProperty(propertyId);
		if (p == null || p.toString() == null) {
			return false;
		}
		final String value = ignoreCase ? p.toString().toLowerCase() : p.toString();
		if (onlyMatchPrefix) {
			if (!value.startsWith(filterString)) {
				return false;
			}
		} else {
			if (!value.contains(filterString)) {
				return false;
			}
		}
		return true;
	}

	public boolean passesFilter(Object itemId, Item item) throws UnsupportedOperationException {
		return passesFilter(item);
	}

	public boolean appliesToProperty(Object propertyId) {
		return this.getPropertyId().equals(propertyId);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;

		// Only ones of the objects of the same class can be equal
		if (!(obj instanceof JainFilter)) {
			return false;
		}
		final JainFilter o = (JainFilter) obj;

		// Checks the properties one by one
		if (propertyId != o.propertyId && o.propertyId != null && !o.propertyId.equals(propertyId)) {
			return false;
		}

		if (filterString != o.filterString && o.filterString != null && !o.filterString.equals(filterString)) {
			return false;
		}

		if (ignoreCase != o.ignoreCase) {
			return false;
		}

		if (onlyMatchPrefix != o.onlyMatchPrefix) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return (propertyId != null ? propertyId.hashCode() : 0) ^ (filterString != null ? filterString.hashCode() : 0);
	}
}
