package com.sopia.common;

/**
 * html 标记转换（消除掉标记功能）
 * @author Administrator
 *
 */
public class CheckHtml {
	public static String getString(String oldstring) {
		if(oldstring==null) return "";
		if (oldstring.length() < 1) {
			return oldstring;
		}
		int left[] = new int[oldstring.length()];
		int right[] = new int[oldstring.length()];
		int js1[] = new int[oldstring.length()];
		int js2[] = new int[oldstring.length()];
		left[0] = 1;
		int j = 0;
		String newstring = "";
		String jsstring = "";
		int checkjs = 0;
		int jsstate = 0;

		// 屏蔽js
		for (int i = 0; i < oldstring.length(); i++) {
			if (oldstring.substring(i, i + 1).equals("<")) {
				if (i + 7 <= oldstring.length()) {
					jsstring = oldstring.substring(i, i + 7);
				}
				checkjs = jsstring.compareToIgnoreCase("<script");
				if (checkjs == 0) {
					js1[j] = i;
					jsstate = 1;
				}
			}
			if (oldstring.substring(i, i + 1).equals(">")) {
				if (i > 8) {
					jsstring = oldstring.substring(i - 8, i + 1);
				}
				checkjs = jsstring.compareToIgnoreCase("</script>");
				if (checkjs == 0) {
					js2[j] = i;
				}
				if (js2[j] != 0 && jsstate == 1) {
					j++;
					jsstate = 0;
				}
			}
		}
		for (int i = 0; i < j; i++) {
			if (i == 0) {
				newstring = oldstring.substring(0, js1[i]);
			} else {
				newstring = newstring
						+ oldstring.substring(js2[i - 1] + 1, js1[i]);
			}
		}
		if (j == 0) {
			newstring = oldstring;
		} else if (js2[j - 1] + 1 <= oldstring.length()) {
			newstring = newstring
					+ oldstring.substring(js2[j - 1] + 1, oldstring.length());
		}
		oldstring = newstring;

		// 记录html标签的<和>的位置，转换转意符
		j = 0;
		for (int i = 0; i < oldstring.length(); i++) {
			// 记录下<的位置
			if (i + 1 <= oldstring.length()) {
				if (oldstring.substring(i, i + 1).equals("<")) {
					left[j] = i;
				}
				// 记录下>的位置
				if (oldstring.substring(i, i + 1).equals(">")) {
					right[j] = i;
				}
				// 将&nbsp;转为空格，&lt;转为<，&gt;转为>
				if (oldstring.substring(i, i + 1).equals("&")) {
					if (i + 6 <= oldstring.length()) {
						if (oldstring.substring(i, i + 6).equals("&nbsp;")) {
							oldstring = oldstring.substring(0, i)
									+ " "
									+ oldstring.substring(i + 6, oldstring
											.length());
						}
						if (oldstring.substring(i, i + 4).equals("&lt;")) {
							oldstring = oldstring.substring(0, i)
									+ "<"
									+ oldstring.substring(i + 4, oldstring
											.length());
						}
						if (oldstring.substring(i, i + 4).equals("&gt;")) {
							oldstring = oldstring.substring(0, i)
									+ ">"
									+ oldstring.substring(i + 4, oldstring
											.length());
						}
					}
				}
			}
			if (right[j] != 0) {
				j++;
			}
		}

		// 取出指定长度的去除html代码后的字符串
		newstring = oldstring.substring(0, 0);
		for (int i = 0; i < j; i++) {
			if (i == 0) {
				newstring = oldstring.substring(0, left[i]);
			} else {
				newstring = newstring
						+ oldstring.substring(right[i - 1] + 1, left[i]);
			}
		}
		if (j == 0) {
			newstring = oldstring;
		} else if (right[j - 1] + 1 <= oldstring.length()) {
			newstring = newstring
					+ oldstring.substring(right[j - 1] + 1, oldstring.length());
		}
		return newstring;
	}
}
