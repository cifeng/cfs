package com.platform.cfs.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

    public static String sendXML(String path, String xml) throws Exception {
          String str;
          HttpURLConnection conn=null;
          InputStream ips=null;
          OutputStream outStream = null;
        try {
            byte[] data = xml.getBytes();
            URL url = new URL(path);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            outStream=conn.getOutputStream();
            outStream.write(data);
            outStream.flush();
            outStream.close();
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("请求出错:状态码：" + conn.getResponseCode());
            }
            ips = conn.getInputStream();
            str = IOUtils.toString(ips, "utf-8");
        }  catch (Exception e) {
            throw new RuntimeException("请求出错:：" + e.getMessage());
        } finally {
            close(outStream,ips,conn);
        }
        return str;

    }

    public static String sendGetRequest(String path,Map<String, String> params, String enc) throws Exception {
        long start = System.currentTimeMillis();
        logger.info("http get请求开始：path:" + path + " 请求参数：" + params + " enc"+ enc + " 当前时间:" + start);
        String str;
        HttpURLConnection conn=null;
        InputStream ips=null;
        OutputStream outStream = null;
        try {
            StringBuilder sb = new StringBuilder(path);
            sb.append('?');
            // ?method=save&title=435435435&timelength=89&
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append('=')
                        .append(URLEncoder.encode(entry.getValue(), enc))
                        .append('&');
            }
            sb.deleteCharAt(sb.length() - 1);
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("请求出错:状态码：" + conn.getResponseCode());
            }
            ips = conn.getInputStream();
            str = IOUtils.toString(ips, "utf-8");
        }catch (Exception e) {
            throw new RuntimeException("请求出错:：" + e.getMessage());
        }finally {
            close(outStream,ips,conn);
            log(path,params,enc,start);
        }
        return str;
    }

    /**
     *
     * @Title sendPostRequest
     * @Description post方式调用restful接口
     * @author 刘非
     * @param path 访问路径
     * @param params 参数map
     * @param header 特别设置的header
     * @param enc   编码格式
     * @return java.lang.String
     */
    public static String sendPostRequest(String path,Map<String, String> params,Map<String, String> header, String enc) throws Exception {
        long start = System.currentTimeMillis();
        logger.info("[http请求] 开始：path:" + path + " 请求参数：" + params + " enc"+ enc + " 当前时间:" + start);
        String str;
        HttpURLConnection conn=null;
        InputStream ips=null;
        OutputStream outStream = null;
        try {
            StringBuilder sb = new StringBuilder();
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    sb.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), enc)).append('&');
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            byte[] entitydata = sb.toString().getBytes();// 得到实体的二进制数据
            URL url = new URL(path);
            conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);// 如果通过post提交数据，必须设置允许对外输出数据
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            if(null != header){
                for(String key :header.keySet()){
                    conn.setRequestProperty(key,header.get(key));
                }
            }
            conn.setRequestProperty("Content-Length",String.valueOf(entitydata.length));
            outStream=conn.getOutputStream();
            outStream.write(entitydata);
            outStream.flush();
            outStream.close();
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("请求出错:状态码：" + conn.getResponseCode());
            }
            ips = conn.getInputStream();
            str = IOUtils.toString(ips, "utf-8");
        }catch (Exception e) {
            throw new RuntimeException("请求出错:：" + e.getMessage());
        }finally {
            close(outStream,ips,conn);
            log(path,params,enc,start);
        }
        return str;

    }


    private static void close(OutputStream outStream,InputStream ips,HttpURLConnection conn){
        try {

            if(null!=outStream){
                outStream.close();
            }
            if(null!=ips){
                ips.close();
            }
            if(null!=conn){
                conn.disconnect();
            }
        } catch (IOException e) {
            logger.info("关闭连接时异常:" +e.getMessage());
            e.printStackTrace();
        }
    }

    private static void log(String path,Map<String, String> params,String enc,Long start){
        long end = System.currentTimeMillis();
        logger.info("[http请求] 结束：path:" + path + " 请求参数：" + params+ " enc" + enc + " 当前时间:" + end);
        logger.info("[http请求] 当前http接口请求耗时:" + (end - start));
    }


    public static void main(String[] args) throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("name", "1111");


        String s = sendPostRequest("http://localhost/api/user/v1/save", param,null,"utf-8");
        System.out.println(s);

        System.out.println("-------------");
    }

}

