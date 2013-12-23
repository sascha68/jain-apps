package com.jain.addon.web.bean.container.lazy;

import java.util.Collection;
import java.util.List;

public interface JNILazyList<E> extends Collection<E> {
	void sort(Object[] propertyId, boolean[] ascending);
	void ensureCapacity(int capacity);
	void add (int position, E bean);
	E get (int index);
	int indexOf(Object item);
	List<E> subList(int fromIndex, int toIndex);
}
