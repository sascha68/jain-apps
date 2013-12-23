package com.jain.addon.web.bean.container.lazy;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class JNLazyList <E> extends ArrayList<E> implements JNILazyList<E> {
	
	public void sort(Object[] propertyId, boolean[] ascending) {
		//Collections.sort(this, itemSorter);
		System.out.println("Sort clicked " + propertyId + " on " + ascending);
	}
	
	public List<E> subList(int fromIndex, int toIndex) {
		List<E> subList = new ArrayList<E>();
		
		for (int i = fromIndex; i < toIndex; i++) {
			if( i < super.size()) {
				subList.add(super.get(i));
			} else {
				
			}
		}
		
		return subList;
	}
	
	public void add (int position, E bean) {
		if (position > super.size()) {
			position = super.size();
		}
		
		super.add(position, bean);
	}
	
	public int size () {
		return 50;
	} 
}
