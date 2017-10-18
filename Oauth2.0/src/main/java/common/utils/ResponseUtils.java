package common.utils;


import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/27.
 */
public class ResponseUtils {
    public static void setWrite(HttpServletResponse response, String dataName, Object data) {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            JSONObject json = new JSONObject();
            json.put("result", true);
            json.put(dataName, data);
            writer.print(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            if (writer != null) {
                returnErrorMsg(writer, e.toString());
            }
        }
    }

    public static void setWrite(HttpServletResponse response, Map<String, Object> dataMap) {
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            JSONObject json = new JSONObject();
            for (String key : dataMap.keySet()) {
                json.put(key, dataMap.get(key));
            }
            json.put("result", true);
            writer.print(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            if (writer != null) {
                returnErrorMsg(writer, e.toString());
            }
        }
    }

    public static void returnErrorMsg(PrintWriter writer, String errorMsg) {
        JSONObject json = new JSONObject();
        json.put("result", false);
        json.put("msg", errorMsg);
        writer.flush();
        writer.close();
    }

}