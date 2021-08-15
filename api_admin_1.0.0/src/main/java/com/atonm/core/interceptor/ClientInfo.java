package com.atonm.core.interceptor;

import com.atonm.kblease.api.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ClientInfo {
    private static final Logger logger = LoggerFactory.getLogger(ClientInfo.class);
    private HttpServletRequest request;

    public ClientInfo(HttpServletRequest _request) {
        request = _request;
    }

    public void printClientInfo() {
        final String referer = getReferer();
        final String fullURL = getFullURL();
        final String clientIpAddr = getClientIpAddr();
        final String clientOS = getClientOS();
        final String clientBrowser = getClientBrowser();
        final String userAgent = getUserAgent();

        logger.info("\n" +
                "User Agent \t" + userAgent + "\n" +
                "Operating System\t" + clientOS + "\n" +
                "Browser Name\t" + clientBrowser + "\n" +
                "IP Address\t" + clientIpAddr + "\n" +
                "Full URL\t" + fullURL + "\n" +
                "Referrer\t" + referer);

        System.out.println("========================================================");
        System.out.println("User Agent \t" + userAgent + "\n" +
                "Operating System\t" + clientOS + "\n" +
                "Browser Name\t" + clientBrowser + "\n" +
                "IP Address\t" + clientIpAddr + "\n" +
                "Full URL\t" + fullURL + "\n" +
                "Referrer\t" + referer);
        System.out.println("========================================================");
    }

    public String getReferer() {
        final String referer = this.request.getHeader("_referer") != null ? this.request.getHeader("referer") : this.request.getHeader("_referer");
        return referer;
    }

    public String getFullURL() {
        final StringBuffer requestURL = this.request.getRequestURL();
        final String queryString = this.request.getQueryString();

        final String result = queryString == null ? requestURL.toString() : requestURL.append('?')
                .append(queryString)
                .toString();

        return result;
    }

    public String getPathInfo() {
        return this.request.getRequestURI();
    }

    public String getRequestMethod() {
        return this.request.getMethod();
    }

    public String getMenuUrl() throws UnsupportedEncodingException {
        return splitQuery() != null ? (splitQuery().get("menu-url") == null ? "" : splitQuery().get("menu-url").get(0)) : "";
    }

    public Map<String, List<String>> splitQuery() throws UnsupportedEncodingException {
        if(this.request.getQueryString() == null) {
            return null;
        }
        final Map<String, List<String>> query_pairs = new LinkedHashMap<>();
        final String[] pairs = this.request.getQueryString().split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
            if (!query_pairs.containsKey(key)) {
                query_pairs.put(key, new LinkedList<String>());
            }
            String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
            value = !StringUtil.isEmpty(value) && (value.split("&")).length > 0 ? value.split("&")[0] : value;
            query_pairs.get(key).add(value);
        }
        return query_pairs;
    }

    //http://stackoverflow.com/a/18030465/1845894
    public String getClientIpAddr() {
        String ip = this.request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = this.request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = this.request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = this.request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = this.request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = this.request.getRemoteAddr();
        }
        return ip;
    }

    //http://stackoverflow.com/a/18030465/1845894
    public String getClientOS() {
        final String browserDetails = this.request.getHeader("_User-Agent") == null ? this.request.getHeader("User-Agent") : this.request.getHeader("_User-Agent");

        //=================OS=======================
        try {
        	final String lowerCaseBrowser = browserDetails.toLowerCase();
        	if (lowerCaseBrowser.contains("windows")) {
        		return "Windows";
        	} else if (lowerCaseBrowser.contains("mac")) {
        		return "Mac";
        	} else if (lowerCaseBrowser.contains("x11")) {
        		return "Unix";
        	} else if (lowerCaseBrowser.contains("android")) {
        		return "Android";
        	} else if (lowerCaseBrowser.contains("iphone")) {
        		return "IPhone";
        	} else {
        		return "UnKnown, More-Info: " + browserDetails;
        	}
		} catch (Exception e) {
			return "Windows";
		}
    }

    //http://stackoverflow.com/a/18030465/1845894
    public String getClientBrowser() {
        final String browserDetails = this.request.getHeader("_User-Agent") == null ? this.request.getHeader("User-Agent") : this.request.getHeader("_User-Agent");
        
        if(browserDetails == null) return "api";
        
        final String user = browserDetails.toLowerCase();

        String browser = "";

        //===============Browser===========================
        if (user.contains("msie")) {
            String substring = browserDetails.substring(browserDetails.indexOf("MSIE")).split(";")[0];
            browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version")) {
            browser = (browserDetails.substring(browserDetails.indexOf("Safari")).split(" ")[0]).split(
                    "/")[0] + "-" + (browserDetails.substring(
                    browserDetails.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if (user.contains("opr") || user.contains("opera")) {
            if (user.contains("opera"))
                browser = (browserDetails.substring(browserDetails.indexOf("Opera")).split(" ")[0]).split(
                        "/")[0] + "-" + (browserDetails.substring(
                        browserDetails.indexOf("Version")).split(" ")[0]).split("/")[1];
            else if (user.contains("opr"))
                browser = ((browserDetails.substring(browserDetails.indexOf("OPR")).split(" ")[0]).replace("/",
                        "-")).replace(
                        "OPR", "Opera");
        } else if (user.contains("chrome")) {
            browser = (browserDetails.substring(browserDetails.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } /*else if ((user.contains("mozilla/7.0"))
                || (user.contains("netscape6"))
                || (user.contains("mozilla/4.7"))
                || (user.contains("mozilla/4.78"))
                || (user.contains("mozilla/4.08"))
                || (user.contains("mozilla/3"))) {
            browser = "Netscape-?";
        }*/ else if (user.contains("firefox")) {
            browser = (browserDetails.substring(browserDetails.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if (user.contains("rv")) {
            browser = "IE";
        } else {
            browser = "UnKnown, More-Info: " + browserDetails;
        }

        return browser;
    }

    public String getUserAgent() {
        return this.request.getHeader("_User-Agent") == null ? this.request.getHeader("User-Agent") : this.request.getHeader("_User-Agent");
    }
}
