package com.atonm.core.interceptor.xss;

/**
 * SQL Filter class
 *
 * @author jangjaeyoung
 *
 */
public class XssFilter {
    public static String cleanXSS(String value) {
        if(value == null) {
            return null;
        }

        //You'll need to remove the spaces from the html entities below
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }

    public static String cleanBoardXSS(String value) {
        if(value == null) {
            return null;
        }

        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        value = value.replaceAll("object", "");
        value = value.replaceAll("applet", "");
        value = value.replaceAll("iframe", "");
        value = value.replaceAll("frame", "");
        value = value.replaceAll("base", "");
        value = value.replaceAll("meta", "");
        return value;
    }
}
