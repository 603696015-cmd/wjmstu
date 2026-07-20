package com.sopia.openmeetings;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MD5 {
	private static final Log logger = LogFactory.getLog(MD5.class);
    private static String toHexString(byte b) {
        int value = (b & 0x7F) + (b < 0 ? 128 : 0);
     
         String ret = (value < 16 ? "0" : "");
         ret += Integer.toHexString(value).toLowerCase();
     
     return ret;
    }
   
    public static String do_checksum(String data) throws NoSuchAlgorithmException {
    	MessageDigest md5 = MessageDigest.getInstance("MD5");
		StringBuffer strbuf = new StringBuffer();

		md5.update(data.getBytes(), 0, data.length());
		byte[] digest = md5.digest();

		for (int i = 0; i < digest.length; i++) {
			strbuf.append(toHexString(digest[i]));
		}

		return strbuf.toString();
	}
    public static String createPassPhrase(String userGivenPass) {
		String passPhrase = null;
		try {
			passPhrase = MD5.do_checksum(userGivenPass);
		} catch (NoSuchAlgorithmException e) {
			logger.error("md5¼ÓÃÜ´íÎó",e);
		}
		return passPhrase;
	}
}
