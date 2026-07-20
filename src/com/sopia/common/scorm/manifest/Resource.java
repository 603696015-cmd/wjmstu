package com.sopia.common.scorm.manifest;

/**
 * Description: Copyright (c) Department of Research and
 * Development/wenyishun110@163.com. All Rights Reserved.
 * 
 * @version 1.0 2012-7-23 下午11:02:16 by 闻益舜（wenyishun110@163.com）创建
 */
public class Resource {

	private String _Identifier;

	private String _Href;

	/**
	 * 
	 */
	public Resource() {
		// TODO figure out how to instantiate this object using the logger from
		// the parent (it can be done)
	}

	/**
	 * @return
	 */
	public String getIdentifier() {
		return _Identifier;
	}

	/**
	 * @param string
	 */
	public void setIdentifier(String string) {
		_Identifier = string;
	}

	/**
	 * @return
	 */
	public String getHref() {
		return _Href;
	}

	/**
	 * @param string
	 */
	public void setHref(String string) {
		_Href = string;
	}

}
