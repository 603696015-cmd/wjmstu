package com.sopia.common.scorm.manifest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CoursePageDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;

public class ManifestDigester {

	private static final Log logger = LogFactory.getLog(ManifestDigester.class);
	private static final String S_IDENTIFIER = "identifier";
	private static final String S_IDENTIFIERREF = "identifierref";
	private static final String S_MANIFEST = "manifest";
	private static final String S_ORGANIZATION = "organization";
	private static final String S_RESOURCE = "resource";
	private static final String S_ITEM = "item";
	private static final String S_TITLE = "title";

	private static final String S_PATH_ORGANIZATION = S_MANIFEST + "/"
			+ S_ORGANIZATION + "s/" + S_ORGANIZATION;
	private static final String S_PATH_ORGANIZATION_TITLE = S_PATH_ORGANIZATION
			+ "/" + S_TITLE;
	private static final String S_PATH_ITEM = S_PATH_ORGANIZATION + "/"
			+ S_ITEM;
	private static final String S_PATH_ITEM_TITLE = S_PATH_ITEM + "/" + S_TITLE;
	private static final String S_PATH_RESOURCE = S_MANIFEST + "/" + S_RESOURCE
			+ "s/" + S_RESOURCE;

	private Manifest _Manifest;
	private String basepath;

	/**
	 * 
	 */
	public ManifestDigester() {
	}

	public ManifestDigester(String basepath) {
		this.basepath = basepath;
	}

	public Collection digestCourseManifest(File manifestFile, int courseid)
			throws IOException, SAXException, Exception {
		Digester digester = new Digester();
		initializeDigester(digester);
		// This method starts the parsing of the document.
		Object parsed = digester.parse(manifestFile);

		if (parsed == this) {
			Manifest manifest = getManifest();
			return processManifest(courseid, manifest);
		} else
			throw new Exception("CourseManifestDigester: logic error");
	}

	public Collection digestCourseManifest(URL manifestURL, int courseid)
			throws IOException, SAXException, Exception {

		Digester digester = new Digester();
		initializeDigester(digester);

		// This method starts the parsing of the document.
		Object parsed = digester.parse(manifestURL.openStream());

		if (parsed == this) {
			Manifest manifest = getManifest();
			return processManifest(courseid, manifest);
		} else
			throw new Exception("CourseManifestDigester: logic error");
	}

	private void initializeDigester(Digester digester) {
		digester.setValidating(false);

		// This method pushes this (CourseManifestDigester) class to the
		// Digesters
		// object stack making its methods available to processing rules.
		digester.push(this);

		digester.addObjectCreate(S_MANIFEST, Manifest.class);
		digester.addObjectCreate(S_PATH_ORGANIZATION, Organization.class);
		digester.addObjectCreate(S_PATH_ITEM, Item.class);
		digester.addObjectCreate(S_PATH_RESOURCE, Resource.class);

		digester.addSetNext(S_MANIFEST, "setManifest",
				"com.sopia.common.scorm.manifest.Manifest");
		digester.addSetNext(S_PATH_ORGANIZATION, "addOrganization",
				"com.sopia.common.scorm.manifest.Organization");
		digester.addSetNext(S_PATH_RESOURCE, "addResource",
				"com.sopia.common.scorm.manifest.Resource");
		digester.addSetNext(S_PATH_ITEM, "addItem",
				"com.sopia.common.scorm.manifest.Item");

		digester.addBeanPropertySetter(S_PATH_ORGANIZATION_TITLE, S_TITLE);
		digester.addBeanPropertySetter(S_PATH_ITEM_TITLE, S_TITLE);

		digester
				.addSetProperties(S_PATH_ITEM, S_IDENTIFIERREF, S_IDENTIFIERREF);
		digester.addSetProperties(S_PATH_RESOURCE, S_IDENTIFIER, S_IDENTIFIER);
	}

	public Collection processManifest(int courseid, Manifest manifest) {
		return addCoursesFromManifest(courseid, manifest);
	}

	private Collection addCoursesFromManifest(int courseid, Manifest manifest) {
		Collection courses = new Vector();
		for (Iterator iter = manifest.iterator(); iter.hasNext();) {
			Organization course = (Organization) iter.next();
			addCourse(course, manifest, courseid);
		}
		return courses;
	}

	private String getLocation(String URL) throws MalformedURLException {
		String location;

		// TODO do we have to worry about decoding here?
		// If it's external, don't concatenate to the local context.
		if ((URL.startsWith("http://")) || (URL.startsWith("https://"))) {
			location = URL.toString();
		} else {
			String uri = basepath;
			location = uri + "/" + URL.toString();
		}
		return location;
	}

	private void addCourse(Organization course, Manifest manifest, int courseID) {
		try {
			int sequence = 0;
			if (course.getItemssize() == 1) {
				CourseDao cpd = ((CourseDao) SpringContextUtil
						.getBean("courseDao"));
				Item item = (Item) course.iterator().next();
				String identifierRef = item.getIdentifierref();
				if (identifierRef != null) {
					Resource resource = manifest.getResource(identifierRef);
					if (resource != null) {
						String URL = resource.getHref();
						String location = getLocation(URL);
						Course c = new Course();
						c.setId(courseID);
						c.setExurl(location);
						c.setDuring(45);
						cpd.alterCourse_S(c);
					}
				}
			} else {
				CoursePageDao cpd = ((CoursePageDao) SpringContextUtil
						.getBean("coursePageDao"));
				for (Iterator iter = course.iterator(); iter.hasNext();) {
					Item item = (Item) iter.next();
					String identifierRef = item.getIdentifierref();
					if (identifierRef != null) {
						Resource resource = manifest.getResource(identifierRef);
						if (resource != null) {
							String URL = resource.getHref();
							String title = item.getTitle();
							String location = getLocation(URL);
							sequence++;
							CoursePage ci = new CoursePage();
							ci.setCourse(new Course(courseID));
							// ci.setIdentifier(item.getIdentifier());
							ci.setTitle(item.getTitle());
							ci.setPage_url(location);
							// ci.setDuring_s(resource.getMaxTimeAllowed());
							// ci.setPrerequisites(resource.getPrerequisites());
							// ci.setDuring(SopiaScoDataManager.time2int(resource
							// .getMaxTimeAllowed()));
							ci.setSortid(sequence);
							cpd.addCoursePage2(ci);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("µ¼Èë¿Î³ÌÊ§°Ü", e);
		}
	}

	/**
	 * @return
	 */
	public Manifest getManifest() {
		return _Manifest;
	}

	/**
	 * @param manifest
	 */
	public void setManifest(Manifest manifest) {
		_Manifest = manifest;
	}

}
