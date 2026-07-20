package com.sopia.common.scorm.manifest;

/**
 * Description: Copyright (c) Department of Research and
 * Development/wenyishun110@163.com. All Rights Reserved.
 * 
 * @version 1.0 2012-7-23 下午10:59:45 by 闻益舜（wenyishun110@163.com）创建
 */
public class Item {

	private String _Title;
	private String _Identifierref;
	private String _;
	/**
	 * 
	 */
	public Item() {
	}

	/**
	 * @return
	 */
	public String getIdentifierref() {
		return _Identifierref;
	}

	/**
	 * @param string
	 */
	public void setIdentifierref(String string) {
		_Identifierref = string;
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

}
