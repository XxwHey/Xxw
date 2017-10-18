package common.utils;

import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by xiexw on 2017/8/31.
 */
public class CommonUtils {

    /**
     * GET请求
     *
     * @param url 地址
     * @param param 参数
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立连接
            connection.connect();
            //读取URL响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("GET请求异常：" + e);
            e.printStackTrace();
        }
        //关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * POST请求
     *
     * @param url 地址
     * @param param 参数
     */
    public static String sendPost(String url, String param) {
        PrintWriter writer = null;
        BufferedReader reader = null;
        String result = "";
        try {
            URLConnection connection = new URL(url).openConnection();
            //设置通用属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //POST请求需满足
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //获取输出流
            writer = new PrintWriter(connection.getOutputStream());
            //发送参数
            writer.print(param);
            writer.flush();
            //读取URL响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            //响应接收
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("POST请求异常：" + e.getMessage());
            e.printStackTrace();
        }
        //关闭输入、输出流
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * GET请求的参数字符串转为JSON对象
     *
     * @param param 参数字符串
     */
    public static JSONObject parseParam(String param) {
        JSONObject json = new JSONObject();
        //以"&"字符分割参数
        String[] params = param.split("&");
        //以"="字符区分key、value
        String[] single;
        for (String p : params) {
            single = p.split("=");
            json.put(single[0], single[1]);
        }
        return json;
    }

    /**
     * 重定向POST请求
     *
     * @param url 地址
     * @param paramMap 参数
     */
    public static void redirectPost(HttpServletResponse response, String url, Map<String, String> paramMap) {
        response.setContentType("text/html");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println("<HTML>");
            out.println("<HEAD>" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />" +
                    "<TITLE>loading...</TITLE>" +
                    "</HEAD>");
            out.println("<BODY>");
            out.println("<form name=\"submitForm\" method=\"post\" action=\"" + url + "\">");
            for (String key : paramMap.keySet()) {
                out.println("<input type=\"hidden\" name=\"" + key + "\" value=\"" + paramMap.get(key) + "\"/>");
            }
            out.println("</from>");
            out.println("<script>window.document.submitForm.submit();</script> ");
            out.println("</BODY>");
            out.println("</HTML>");
            out.flush();
        } catch (Exception e) {
            System.out.println("重定向POST异常：" + e.getMessage());
            e.printStackTrace();
        }
        //关闭输出流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
