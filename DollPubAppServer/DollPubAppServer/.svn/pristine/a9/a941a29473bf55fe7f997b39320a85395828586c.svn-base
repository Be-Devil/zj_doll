package com.imlianai.dollpub.app.modules.publics.sms.tuojin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class JavaHttpConnection {

    public static String doGetWithReturn(String httpUrl) throws IOException {
        URL url = new URL(httpUrl);
        HttpURLConnection httpUrlCon = (HttpURLConnection) url.openConnection();
        httpUrlCon.setRequestMethod("GET");
        httpUrlCon.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpUrlCon.getInputStream()));
        String sCurrentLine = "";
        String sTotalString = "";
        while ((sCurrentLine = reader.readLine()) != null) {
            sTotalString += sCurrentLine;
        }
        reader.close();
        httpUrlCon.disconnect();
/*
        int beginIndex = sTotalString.indexOf(matcherStrStart);
        int endIndex = sTotalString.indexOf(matcherStrEnd);
        sTotalString = sTotalString.substring(beginIndex, endIndex);
        int beginIndex2 = sTotalString.indexOf(checkValidStart);
        sTotalString = sTotalString.substring(beginIndex2 + 31);
        int endIndex2 = sTotalString.indexOf(checkValidEnd);
        sTotalString = sTotalString.substring(0, endIndex2);
*/
        return sTotalString;
    }

    public static String doPostWithoutReturn(String httpUrl, String param)
            throws IOException {
        String result = "";
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Charset", "UTF-8");
            PrintWriter out = new PrintWriter(httpConn.getOutputStream());
            out.write(param);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
//            InputStreamReader in = new InputStreamReader(httpConn.getInputStream(),"utf-8");
            String line;
//            char[] cbuf = new char[ httpConn.getContentLength()];
//            in.read(cbuf);
            while ((line = in.readLine()) != null) {
                result += line;
            }
//            line = new String(cbuf);
            //result=line;
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
    
    public static void main(String[] args) throws IOException, ParseException {
    	String urlStr = new String("http://t.xiehou360.com/call.do?cmd=service.getReplyList");
    	Map<String, Object> map = new HashMap<String, Object>();
//    	map.put("uid", "1000");
//    	map.put("type", 1);
//    	map.put("filename", "abctest");
//    	map.put("content", "http://192.168.1.99:8081");//service.submitFeedback
    	
//    	map.put("uid", "1000");
//    	map.put("feedbackId", 1);
//    	map.put("reply_content", "googogooogogogogo");//service.submitReply
    	
//    	map.put("uid", "1000");
//    	map.put("pageIndex", 1);//service.getFeedbackList
    	
//    	map.put("id", 1);
//    	map.put("satisfy", 1);//service.submitSatisfy
    	
    	map.put("feedbackId", 1);
    	map.put("pageIndex", 1);//service.getReplyList
    	String param = JSON.toJSONString(map);
    	System.out.println(param);
    	System.out.println(param);
    	System.out.println(doPostWithoutReturn(urlStr,param));
    }
}
