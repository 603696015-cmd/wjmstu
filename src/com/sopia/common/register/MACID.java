package com.sopia.common.register;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Mac地址获取
 * @author Administrator
 *
 */
public class MACID {
	private static final Log logger = LogFactory.getLog(MACID.class);
	String IPCONFIG_COMMAND_WIN = "ipconfig /all";
	boolean realMac = true;
	String unique = "";

	public static String getMacAddress() {
		MACID hwid = new MACID();
		return hwid.getUnique().trim();
	}

	private String getUnique() {
		String os = System.getProperty("os.name");

		if (os.startsWith("Windows")) {
			return getUniqueWindows();
		} else {
			return "";
		}
	}

	private String getUniqueWindows() {
		// 运行控制台命令，返回结果字符串
		String ipConfigResponse = null;
		try {
			ipConfigResponse = runConsoleCommand(IPCONFIG_COMMAND_WIN);
		} catch (IOException e) {
			logger.error("控制台命令执行错误!",e);
		}

		// 按行分割结果字符串，并循环判断每个字符串直道找出 Mac 地址
		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();

			// 获取每行 ":" 后的字符串
			int macAddressPosition = line.indexOf(":");
			if (macAddressPosition <= 0) {
				continue;
			}
			String macAddressCandidate = line.substring(macAddressPosition + 1)
					.trim();

			// 检查 macAddressCandidate 中内容是否为真实网卡 Mac 地址，根据 Mac 地址计算出唯一标识。
			if (isMacAddWin(macAddressCandidate)) {
				if (realMac == true) {
					generateUnique(macAddressCandidate);
				} else {
					realMac = true;
				}
			}
		}

		return unique;
	}

	/**
	 * 运行控制台命令，返回结果字符串
	 * 
	 * @param command
	 *            String
	 * @return String
	 * @throws IOException
	 */
	private String runConsoleCommand(String command) throws IOException {
		Process p = Runtime.getRuntime().exec(command);
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

		StringBuffer buffer = new StringBuffer();
		while (true) {
			int c = stdoutStream.read();
			if (c == -1) {
				break;
			}
			buffer.append((char) c);
		}
		String outputText = buffer.toString();

		stdoutStream.close();

		return outputText;
	}

	/**
	 * 对输入参数进行检查，符合正则表达式的为 windows 平台下有效 Mac 地址
	 * 
	 * @param macAddressCandidate
	 *            String
	 * @return boolean
	 */
	private boolean isMacAddWin(String macAddressCandidate) {
		Pattern macPattern = Pattern
				.compile("[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}");
		Matcher m = macPattern.matcher(macAddressCandidate);
		return m.matches();
	}

	/**
	 * 对输入参数进行检查，符合长度的为 MAC OS X 下有效网卡 Mac 地址
	 * 
	 * @param macAddressCandidate
	 *            String
	 * @return boolean
	 */
	/*
	 * private boolean isMacAddOSX(String macAddressCandidate) { if
	 * (macAddressCandidate.length() != 17) { return false; } else { return
	 * true; } }
	 */
	/**
	 * 产生 Unique
	 * 
	 * @param macAddress
	 *            String
	 */
	private void generateUnique(String macAddress) {
		if ("".equals(unique) ) {
			unique += macAddress;
		} else {
			if(!macAddress.equals("00-00-00-00-00-00")) {
			unique += "#";
			unique += macAddress;
			}
		}
	}
}
