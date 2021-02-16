package com.moliang.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @Use 解析客户端信息工具
 * @Author Chui moliang
 * @Date 2021/2/14 1:25
 * @Version 1.0
 */
@Slf4j
public class AgentUtil {

    private static final String UNKNOWN = "unknown";

    private final static String preUrl = "http://whois.pconline.com.cn/ip.jsp?ip=";
    private final static String rearUrl = "&json=true";

    /**
     * 根据ip获取详细地址
     */
    public static String getLocalCityInfo(String ip) {
        StringBuilder sb = new StringBuilder();
        InputStream is = null;
        BufferedReader br = null;
        String ans = null;
        try {
            //接口地址
            String url = preUrl + ip + rearUrl;
            log.info(url);
            URL uri = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(500);
            connection.setConnectTimeout(1000);
            connection.setRequestProperty("accept", "*/*");
            connection.setDoInput(true);
            connection.connect();
            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "GBK"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            ans = sb.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                if(is!=null) is.close();
                if(br!=null) br.close();
            } catch (Exception r) {
                log.error(r.getMessage());
            }
        }
        return ans;
    }

    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    public static String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }
}
