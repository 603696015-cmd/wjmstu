package com.sopia.common.scorm.manifest;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 * Description: Copyright (c) Department of Research and
 * Development/wenyishun110@163.com. All Rights Reserved.
 * 
 * @version 1.0 2012-7-23 下午11:02:29 by 闻益舜（wenyishun110@163.com）创建
 */
public class Organization {

	private String _Title;

	private final Collection _Items;
	private int _Itemssize;
	/**
	 * 
	 */
	public Organization() {
		_Items = new Vector();
		// TODO figure out how to instantiate this object using the logger from
		// the parent (it can be done)
	}

	/**
	 * @return
	 */
	public String getTitle() {
		return _Title;
	}

	/**
	 * @param string
	 */
	public void setTitle(String string) {
		_Title = string;
	}

	/**
	 * @param o
	 * @return
	 */
	public boolean addItem(Item item) {
		// item.setLogger(getLogger());
		_Itemssize ++;
		return _Items.add(item);
	}

	/**
	 * @return
	 */
	public Iterator iterator() {
		return _Items.iterator();
	}
	public int getItemssize(){
		
		return _Itemssize;
	}
}
