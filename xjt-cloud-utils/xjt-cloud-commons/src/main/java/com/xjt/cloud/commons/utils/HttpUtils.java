package com.xjt.cloud.commons.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http工具类
 *
 * @author wangzhiwen
 * @date 2019/4/24 17:51
 */
public class HttpUtils {

    private static volatile RestTemplate restTemplate = restTemplateInit();

    /**
     * 功能描述:解决反回请求json格式字符串的TEXT_HTML类型问题
     *
     * @return RestTemplate
     * @author wangzhiwen
     */
    private static RestTemplate restTemplateInit() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        // 先获取到converter列表
        RestTemplate build = builder.build();
        List<HttpMessageConverter<?>> converters = build.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            // 因为我们只想要jsonConverter支持对text/html的解析
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                try {
                    // 先将原先支持的MediaType列表拷出
                    List<MediaType> mediaTypeList = new ArrayList<>(converter.getSupportedMediaTypes());
                    // 加入对text/html的支持
                    mediaTypeList.add(MediaType.TEXT_HTML);
                    mediaTypeList.add(MediaType.TEXT_PLAIN);
                    // 将已经加入了text/html的MediaType支持列表设置为其支持的媒体类型列表
                    ((MappingJackson2HttpMessageConverter) converter).setSupportedMediaTypes(mediaTypeList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        build.setMessageConverters(converters);
        return build;
    }

    /**
     * 功能描述: http get请求
     *
     * @param url 请求路径
     * @param t   传递的参数对象
     * @return net.sf.json.JSONObject
     * @author wangzhiwen
     * @date 2019-04-26 10:20
     */
    public static <T> JSONObject httpGet(String url, T t) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>(1);
        params.put("data", JSON.toJSONString(t));
        return restTemplate.getForObject(url + "?data={data}", JSONObject.class, params);
    }

    /**
     * 功能描述: http get请求
     *
     * @param url 请求路径
     * @return net.sf.json.JSONObject
     * @author huanggc
     * @date 2021-03-02
     */
    public static <T> String httpGetString(String url) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>(1);
        return restTemplate.getForObject(url, String.class, params);
    }

    /**
     * 功能描述: http get请求
     *
     * @param url       请求路径
     * @param param     传递的参数对象
     * @param paramName 参数名称
     * @return net.sf.json.JSONObject
     * @author wangzhiwen
     * @date 2019-04-26 10:20
     */
    public static <T> JSONObject httpGetRestTemplate(String url, String param, String paramName) {
        Map<String, String> params = new HashMap<>(1);
        params.put(paramName, param);
        return restTemplate.getForObject(url + "?" + paramName + "={" + paramName + "}", JSONObject.class, params);
    }

    /**
     * 功能描述: http get请求
     *
     * @param url       请求路径
     * @param param     传递的参数对象
     * @param paramName 参数名称
     * @return net.sf.json.JSONObject
     * @author wangzhiwen
     * @date 2019-04-26 10:20
     */
    public static <T> JSONObject httpGet(String url, String param, String paramName) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>(1);
        params.put(paramName, param);
        return restTemplate.getForObject(url + "?" + paramName + "={" + paramName + "}", JSONObject.class, params);
    }

    /**
     * httpGetToken
     *
     * @param url       url
     * @param param     参数
     * @param paramName 参数
     * @param token     token
     * @return JSONObject
     */
    public static <T> JSONObject httpGetToken(String url, String param, String paramName, String token) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>(1);
        params.put(paramName, param);
        return restTemplate.getForObject(url + "?access_token=" + token + "&" + paramName + "={" + paramName + "}", JSONObject.class, params);
    }

    /**
     * 功能描述: http get请求
     *
     * @param url       请求路径
     * @param param     传递的参数对象
     * @param paramName 参数名称
     * @return List<JSONObject>
     * @author zhangzaifa
     * @date 2019-10-29 10:20
     */
    public static <T> List<JSONObject> httpGets(String url, String param, String paramName) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>(1);
        params.put(paramName, param);
        return restTemplate.getForObject(url + "?" + paramName + "={" + paramName + "}", List.class, params);
    }

    /**
     * 功能描述: http get请求
     *
     * @param url 请求路径
     * @param t   传递的参数对象
     * @return List<JSONObject>
     * @author zhangzaifa
     * @date 2019-10-29 10:20
     */
    public static <T> List<JSONObject> httpGets(String url, T t) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>(1);
        params.put("data", JSON.toJSONString(t));
        return restTemplate.getForObject(url + "?data={data}", List.class, params);
    }


    /**
     * 功能描述: http get请求
     *
     * @param url 请求路径
     * @return net.sf.json.JSONObject
     * @author wangzhiwen
     * @date 2019-04-26 10:20
     */
    public static JSONObject httpGet(String url) {
        SysLog.logger.info("请求url:" + url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        URI uri = builder.build(true).encode().toUri();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<JSONObject> response = restTemplate.exchange(uri, HttpMethod.GET, entity, JSONObject.class);
        if (null == response || null == response.getBody()) {
            return null;
        }
        JSONObject jsonObject = response.getBody();
        SysLog.logger.info("请求返回结果:" + jsonObject.toJSONString());
        return jsonObject;
    }

    /**
     * 功能描述:
     *
     * @param url         url
     * @param file        文件
     * @param packageName 包名
     * @return void
     * @author wangzhiwen
     * @date 2020/4/12 11:14
     */
    public static String restTemplateTransferFile(String url, File file, String packageName) {
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);

        // 设置请求体，注意是LinkedMultiValueMap
        FileSystemResource fileSystemResource = new FileSystemResource(file);

        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>(2);
        form.add("file", fileSystemResource);
        form.add("packageName", packageName);
        // 用HttpEntity封装整个请求报文
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);

        return restTemplate.postForObject(url, files, String.class);
    }

    /**
     * 功能描述: http get请求
     *
     * @param url 请求路径
     * @return net.sf.json.JSONObject
     * @author zhangzaifa
     * @date 2019-04-26 10:20
     */
    public static JSONObject httpGets(String url) {
        SysLog.logger.info("请求url:" + url);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_OCTET_STREAM_VALUE);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build(true).encode().toUri();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        if (null == response || null == response.getBody()) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(response.getBody());
        SysLog.logger.info("请求返回结果:" + jsonObject.toJSONString());
        return jsonObject;
    }

    /**
     * POST请求
     *
     * @param url    url
     * @param params 参数
     * @return java.lang.String
     * @author zhangZaiFa
     * @date 2020/3/31 16:34
     **/
    public static String httpPost(String url, Map<String, String> params) {
        URL u;
        HttpURLConnection con = null;
        // 构建请求参数
        StringBuffer sb = new StringBuffer();
        if (params != null) {
            for (Map.Entry<String, String> e : params.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
            sb.substring(0, sb.length() - 1);
        }
        SysLog.info("send_url:" + url);
        SysLog.info("send_data:" + sb.toString());
        // 尝试发送请求
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            // POST 只能为大写，严格限制，post会不识别
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            osw.write(sb.toString());
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        try {
            // 一定要有返回值，否则无法把请求发送给server端。
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    /**
     * POST请求
     *
     * @param url    url
     * @param params 参数
     * @return java.lang.String
     * @author zhangZaiFa
     * @date 2020/3/31 16:34
     **/
    public static String httpPosts(String url, Map<String, Object> params) {
        URL u;
        HttpURLConnection con = null;
        // 构建请求参数
        StringBuffer sb = new StringBuffer();
        if (params != null) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
            sb.substring(0, sb.length() - 1);
        }
        SysLog.info("send_url:" + url);
        SysLog.info("send_data:" + sb.toString());
        // 尝试发送请求
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            // POST 只能为大写，严格限制，post会不识别
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            osw.write(sb.toString());
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        try {
            // 一定要有返回值，否则无法把请求发送给server端。
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    /**
     * 功能描述: http post请求
     *
     * @param url  url
     * @param json 参数
     * @return net.sf.json.JSONObject
     * @author wangzhiwen
     * @date 2019-04-26 10:30
     */
    public static JSONObject httpPost(String url, String json) {
        SysLog.logger.info("url:" + url);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        // 定义请求参数类型，这里用json所以是MediaType.APPLICATION_JSON
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> formEntity = new HttpEntity<String>(json.toString(), headers);
        ResponseEntity<JSONObject> entity = restTemplate.postForEntity(url, formEntity, JSONObject.class);
        if (null == entity || null == entity.getBody()) {
            return null;
        }
        // 获取3方接口返回的数据通过entity.getBody();它返回的是一个JsonObject对象；
        JSONObject jsonObject = entity.getBody();
        SysLog.logger.info("请求返回结果:" + jsonObject.toJSONString());
        return jsonObject;
    }

    /**
     * 功能描述: http post请求
     *
     * @param url  url
     * @param json 参数
     * @return net.sf.json.JSONObject
     * @author hunaggc
     * @date 2020/08/26
     */
    public static JSONObject httpPostByJson(String url, String json) {
        SysLog.logger.info("url:" + url);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("json", json);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> entity = restTemplate.postForEntity(url, request, String.class);
        if (null == entity || null == entity.getBody()) {
            return null;
        }

        // 获取3方接口返回的数据通过entity.getBody(); 它返回的是一个JsonObject对象；
        JSONObject jsonObject = new JSONObject();
        SysLog.logger.info("请求返回结果:" + entity.toString());
        return jsonObject;
    }

    /**
     * 功能描述: http post请求
     *
     * @param url url
     * @return net.sf.json.JSONObject
     * @author wangzhiwen
     * @date 2019-04-26 10:30
     */
    public static JSONObject httpPost(String url) {
        SysLog.info("url:" + url);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        // 定义请求参数类型，这里用json所以是MediaType.APPLICATION_JSON
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> formEntity = new HttpEntity<>(headers);
        ResponseEntity<JSONObject> entity = restTemplate.postForEntity(url, formEntity, JSONObject.class);
        if (null == entity || null == entity.getBody()) {
            return null;
        }
        // 获取3方接口返回的数据通过entity.getBody();它返回的是一个JsonObject对象；
        return entity.getBody();
    }

    /**
     * 设定session属性
     *
     * @param session  httpSession
     * @param attrName 属性名称
     * @author yaoyuan
     * @date 2010-3-18
     */
    public static void setAttribute(HttpSession session, String attrName, Object attrObject) {
        session.setAttribute(attrName, attrObject);
    }

    /**
     * 取得Session的信息?
     *
     * @param session  httpSession
     * @param attrName 属性名
     * @return object 取得session的对象
     * @author yaoyuan
     * @date 2010-3-18
     */
    public static Object getAttribute(HttpSession session, String attrName) {
        return session.getAttribute(attrName);
    }

    /**
     * 设置cookie
     *
     * @param cookieName  cookie名
     * @param cookieValue cookie值
     * @param expiry      cookie生存时间
     * @author yaoyuan
     * @date 2011-2-9
     */
    public static void addCookie(String cookieName, String cookieValue, int expiry, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(expiry);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
    }

    /**
     * 根据cookie名称取得cookie的�?
     *
     * @param cookieName cookie名称
     * @param request    HttpServletRequest
     * @return string cookie的值,当取不到cookie的值时返回null
     * @author yaoyuan
     * @date 2011-1-18
     */
    public static String getCookieValue(String cookieName, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookieName.equals(cookies[i].getName())) {
                    return cookies[i].getValue();
                }
            }
        }
        return null;
    }

    /**
     * 发送数据
     *
     * @param url url
     * @param param 参数
     * @return String
     * @author wangzhiwe
     * @date 2014-7-23
     */
    public static String sendHttpPostData(String url, String param) {
        URL u;
        HttpURLConnection con = null;
        // 尝试发送请求
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setConnectTimeout(50000);//设置连接超时  
            // 如果在建立连接之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
            con.setReadTimeout(50000);//设置读取超时  
            // 如果在数据可读取之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
            con.setRequestMethod("POST");
            // http.setRequestProperty("Content-Type","text/xml; charset=UTF-8");  
            // con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            // 设置通用的请求属性
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("user-agent", "mozilla/4.0 (compatible; msie 8.0; windows nt 5.1; trident/4.0)");
            con.connect();

            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "utf-8");
            if (StringUtils.isNotEmpty(param)) {
                osw.write(param);
            }
            osw.flush();
            osw.close();
        } catch (Exception e) {
            SysLog.logger.error("utl:" + url + "\nparam" + param + "\n请求错误:");
            SysLog.error(e);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
        } catch (Exception e) {
            SysLog.error(e);
        }
        return buffer.toString();
    }

    /**
     * 发送get数据
     *
     * @param url url
     * @param param 参数
     * @return String
     * @author wangzhiwe
     * @date 2014-7-23
     */
    public static String sendGet(String url, String param) {
        URL u;
        HttpURLConnection con = null;
        // 尝试发送请求
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            // 设置连接超时
            con.setConnectTimeout(50000);
            // 如果在建立连接之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
            con.setReadTimeout(50000);//设置读取超时  
            // 如果在数据可读取之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
            con.setRequestMethod("GET");
            // http.setRequestProperty("Content-Type","text/xml; charset=UTF-8");  
            // con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            // 设置通用的请求属性
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("user-agent", "mozilla/4.0 (compatible; msie 8.0; windows nt 5.1; trident/4.0)");
            con.connect();

            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "utf-8");
            if (StringUtils.isNotEmpty(param)) {
                osw.write(param);
            }
            osw.flush();
            osw.close();
        } catch (Exception e) {
            SysLog.logger.error("utl:" + url + "\nparam" + param + "\n请求错误:");
            SysLog.error(e);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
        } catch (Exception e) {
            SysLog.error(e);
        }
        return buffer.toString();
    }

    /**
     * 远程下载文件
     *
     * @param remoteFilePath 文件路径
     * @return String
     * @author wangzhiwen
     * @date 2016-3-30 下午04:16:38
     */
    public static String downloadFile(String remoteFilePath) {
        URL urlfile;
        HttpURLConnection httpUrl;
        BufferedInputStream bis = null;
        StringBuffer buffer = new StringBuffer();
        try {
            urlfile = new URL(remoteFilePath);
            httpUrl = (HttpURLConnection) urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());

            int len = 2048;
            byte[] b = new byte[len];
            boolean isCode = true;
            while ((len = bis.read(b)) != -1) {
                if (isCode && (new String(b)).indexOf("errcode") != -1) {
                    isCode = false;
                    return "errcode";
                }
                buffer.append(StringUtils.byte2hex(b, len));
            }
            bis.close();
            httpUrl.disconnect();
        } catch (Exception e) {
            SysLog.logger.error("微信语音文件下载错误");
            SysLog.error(e);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                SysLog.logger.error("微信语音文件下载关闭错误", e.fillInStackTrace());
                SysLog.error(e);
            }
        }
        return buffer.toString();
    }

    /**
     * @param url url
     * @param t T
     * @param paraName 参数
     * @return JSONObject
     * @author dwt
     * @date 2020-09-03 11:52
     */
    public static <T> JSONObject httpPostParaJson(String url, T t, String paraName) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put(paraName, JSON.toJSONString(t));
        return restTemplate.getForObject(url + "?" + paraName + "={" + paraName + "}", JSONObject.class, params);
    }

    public static void main(String[] args) {
        System.out.println(httpGet("http://127.0.0.1:9081/user/getUser?json=%7B%22loginName%22%3A%22admin%22%7D"));
    }
}
