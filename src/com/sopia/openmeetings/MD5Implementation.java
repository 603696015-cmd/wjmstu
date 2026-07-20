package com.sopia.openmeetings;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MD5Implementation{
	private static final Log logger = LogFactory.getLog(MD5Implementation.class);
	public String createPassPhrase(String userGivenPass) {
		String passPhrase = null;
		try {
			passPhrase = MD5.do_checksum(userGivenPass);
		} catch (NoSuchAlgorithmException e) {
			logger.error("md5º”√‹£® µœ÷£©¥ÌŒÛ",e);
		}
		return passPhrase;
	}
}
