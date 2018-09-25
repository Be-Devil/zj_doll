package com.imlianai.dollpub.app.modules.support.yy.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wurui
 * @create 2018-07-18 15:20
 **/
@Service
public class YYService {

    public String handleLogin(String username,String pwd,String token,String isRemMe,String hiido) throws IOException {

        HttpPost httpPost = new HttpPost("https://lgn.yy.com/lgn/oauth/x2/s/login_asyn.do");
        CloseableHttpClient client = HttpClients.createDefault();

//        json方式
//        JSONObject jsonParam = new JSONObject();
//        jsonParam.put("embed", "true");
//        StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题
//        entity.setContentEncoding("UTF-8");
//        entity.setContentType("application/json");
//        httpPost.setEntity(entity);

        //表单方式
        List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
        pairList.add(new BasicNameValuePair("username", username));
        pairList.add(new BasicNameValuePair("pwdencrypt", pwd));
        pairList.add(new BasicNameValuePair("oauth_token", token));
        pairList.add(new BasicNameValuePair("isRemMe", isRemMe));
        pairList.add(new BasicNameValuePair("hiido", hiido));
        httpPost.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));

        HttpResponse resp = client.execute(httpPost);
        if(resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            return EntityUtils.toString(he,"UTF-8");
        }
        return null;
    }



}
