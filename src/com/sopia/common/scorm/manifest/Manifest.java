package com.sopia.common.scorm.manifest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Description: Copyright (c) Department of Research and
 * Development/wenyishun110@163.com. All Rights Reserved.
 * 
 * @version 1.0 2012-7-23 下午11:01:03 by 闻益舜（wenyishun110@163.com）创建
 */
public class Manifest {

	final private Collection _Organizations;

	final private Map _Resources;

	/**
	 * 
	 */
	public Manifest() {
		_Organizations = new Vector();
		_Resources = new HashMap();
	}

	/**
	 * @param o
	 * @return
	 */
	public boolean addOrganization(Organization organization) {
		// organization.setLogger(getLogger());
		return _Organizations.add(organization);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public Object addResource(Resource resource) {
		return _Resources.put(resource.getIdentifier(), resource);
	}

	/**
	 * @param key
	 * @return
	 */
	public Resource getResource(String identifier) {
		return (Resource) _Resources.get(identifier);
	}

	/**
	 * @return
	 */
	public Iterator iterator() {
		return _Organizations.iterator();
	}

}
