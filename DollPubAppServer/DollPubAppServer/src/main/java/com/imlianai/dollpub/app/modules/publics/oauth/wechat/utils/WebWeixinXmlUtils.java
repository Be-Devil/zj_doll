package com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain.WebWeixinButton;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain.WeiXinArticles;
import com.imlianai.rpc.support.utils.HttpUtil;


public class WebWeixinXmlUtils {
    
    public static String setBackPicMsg(String toUserName, String fromUserName, String title, String content, String picUrl,String url){
    	StringBuilder xmlMsg = new StringBuilder(); 
    	xmlMsg.append("<xml><ToUserName><![CDATA[");
    	xmlMsg.append(toUserName);
    	xmlMsg.append("]]></ToUserName><FromUserName><![CDATA[");
    	xmlMsg.append(fromUserName);
    	xmlMsg.append("]]></FromUserName><CreateTime>");
    	xmlMsg.append(System.currentTimeMillis());
    	xmlMsg.append("</CreateTime><MsgType><![CDATA[news]]></MsgType>");
    	xmlMsg.append("<Content><![CDATA[]]></Content><ArticleCount>1</ArticleCount>");
    	xmlMsg.append("<Articles><item><Title><![CDATA[");
    	xmlMsg.append(title);
    	xmlMsg.append("]]></Title><Description><![CDATA[");
    	xmlMsg.append(content);
    	xmlMsg.append("]]></Description><PicUrl><![CDATA[");
    	xmlMsg.append(picUrl);
    	xmlMsg.append("]]></PicUrl><Url><![CDATA[");
    	xmlMsg.append(url);
    	xmlMsg.append("]]></Url></item></Articles><FuncFlag>0</FuncFlag></xml>");
        return xmlMsg.toString();
    }
    
    public static String setBackTextMsg(String toUserName, String fromUserName, String content){
    	StringBuilder xmlMsg = new StringBuilder();
    	xmlMsg.append("<xml><ToUserName><![CDATA[");
    	xmlMsg.append(toUserName);
    	xmlMsg.append("]]></ToUserName><FromUserName><![CDATA[");
    	xmlMsg.append(fromUserName);
    	xmlMsg.append("]]></FromUserName><CreateTime>");
    	xmlMsg.append(System.currentTimeMillis());
    	xmlMsg.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
    	xmlMsg.append(content);
    	xmlMsg.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
        return xmlMsg.toString();
    }
    
    public static String setBackTextMsgNoCDATA(String toUserName, String fromUserName, String content){
    	StringBuilder xmlMsg = new StringBuilder();
    	xmlMsg.append("<xml><ToUserName><![CDATA[");
    	xmlMsg.append(toUserName);
    	xmlMsg.append("]]></ToUserName><FromUserName><![CDATA[");
    	xmlMsg.append(fromUserName);
    	xmlMsg.append("]]></FromUserName><CreateTime>");
    	xmlMsg.append(System.currentTimeMillis());
    	xmlMsg.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
    	xmlMsg.append(content);
    	xmlMsg.append("]]></Content></xml>");
        return xmlMsg.toString();
    }
    
    /**
     * 回复图片消息
     * @param toUserName
     * @param fromUserName
     * @param media_id
     * @return
     */
    public static String setBackPicMsgNoCDATA(String toUserName, String fromUserName, String media_id){
    	StringBuilder xmlMsg = new StringBuilder();
    	xmlMsg.append("<xml><ToUserName><![CDATA[");
    	xmlMsg.append(toUserName);
    	xmlMsg.append("]]></ToUserName><FromUserName><![CDATA[");
    	xmlMsg.append(fromUserName);
    	xmlMsg.append("]]></FromUserName><CreateTime>");
    	xmlMsg.append(System.currentTimeMillis());
    	xmlMsg.append("</CreateTime><MsgType><![CDATA[image]]></MsgType><Image><MediaId><![CDATA[");
    	xmlMsg.append(media_id);
    	xmlMsg.append("]]></MediaId></Image></xml>");
        return xmlMsg.toString();
    }
    /**
     * 
     * 建立多图文回复消息
     * @param toUserName
     * @param fromUserName
     * @param title
     * @param content
     * @param picUrl
     * @param url
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String setBackPicListMsg(String toUserName, String fromUserName, List<WeiXinArticles> list){
    	StringBuilder xmlMsg = new StringBuilder(); 
    	xmlMsg.append("<xml><ToUserName><![CDATA[");
    	xmlMsg.append(toUserName);
    	xmlMsg.append("]]></ToUserName>");
    	xmlMsg.append("<FromUserName><![CDATA[");
    	xmlMsg.append(fromUserName);
    	xmlMsg.append("]]></FromUserName>");
    	xmlMsg.append("<CreateTime>");
    	xmlMsg.append(System.currentTimeMillis());
    	xmlMsg.append("</CreateTime>");
    	xmlMsg.append("<MsgType><![CDATA[news]]></MsgType>");
    	xmlMsg.append("<ArticleCount>");
    	xmlMsg.append(list.size());
    	xmlMsg.append("</ArticleCount>");
    	xmlMsg.append("<Articles>");
    	for (WeiXinArticles weiXinArticles : list) {
    		xmlMsg.append("<item>");
        	xmlMsg.append("<Title><![CDATA[");
        	xmlMsg.append(weiXinArticles.getTitle());
        	xmlMsg.append("]]></Title>");
        	xmlMsg.append("<Description><![CDATA[");
        	xmlMsg.append(weiXinArticles.getDescription());
        	xmlMsg.append("]]></Description>");
        	xmlMsg.append("<PicUrl><![CDATA[");
        	xmlMsg.append(weiXinArticles.getPicUrl());
        	xmlMsg.append("]]></PicUrl>");
        	xmlMsg.append("<Url><![CDATA[");
        	xmlMsg.append(weiXinArticles.getUrl());
        	xmlMsg.append("]]></Url>");
        	xmlMsg.append("</item>");
		}
    	xmlMsg.append("</Articles></xml>");
        return xmlMsg.toString();
    }
    
    /**
	 * 生成加密后的消息
	 * 
	 * @param encrypt
	 *            加密后的消息密文
	 * @param signature
	 *            安全签名
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机字符串
	 * @return 生成的xml字符串
	 */
	public static String setEncryptMsg(String encrypt, String signature,
			String timestamp, String nonce) {
		StringBuilder xmlMsg = new StringBuilder();
    	xmlMsg.append("<xml><Encrypt><![CDATA[");
    	xmlMsg.append(encrypt);
    	xmlMsg.append("]]></Encrypt><MsgSignature><![CDATA[");
    	xmlMsg.append(signature);
    	xmlMsg.append("]]></MsgSignature><TimeStamp>");
    	xmlMsg.append(timestamp);
    	//xmlMsg.append(System.currentTimeMillis());
    	xmlMsg.append("</TimeStamp><Nonce><![CDATA[");
    	xmlMsg.append(nonce);
    	xmlMsg.append("]]></Nonce></xml>");
        return xmlMsg.toString();
	}
	
	/**
	 * 将NameValuePair列表转换成xml信息
	 * @param list
	 * @return
	 */
	public static String getXmlMsg(List<NameValuePair> list){
    	StringBuilder xmlMsg = new StringBuilder();
    	xmlMsg.append("<xml>");
    	for (NameValuePair basicNameValuePair : list) {
    		xmlMsg.append("<"+basicNameValuePair.getName()+"><![CDATA[");
    		xmlMsg.append(basicNameValuePair.getValue());
        	xmlMsg.append("]]></"+basicNameValuePair.getName()+">");
		}
    	xmlMsg.append("</xml>");
        return xmlMsg.toString();
    }
	
	public static void main(String[] args) {
		getPics();
	}
	public static void getPics(){
		Map<Object,Object> obj =new HashMap<Object, Object>();
		obj.put("type", "image");
		obj.put("offset", 0);
		obj.put("count", 20);
		//String APP_ACCESS_TOKEN = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx45686a461704e848&secret=498c9394b57881d91d9eb73cdd03d4c5", "");
		//System.out.println(APP_ACCESS_TOKEN);
		String App_ACCESS_TOKEN="10_6tQ39pHMMdUuA1tIbzx-M6PVuYXEsKVhrT25OgJ9gGQMvxOu8Zw3MyvT-_2yUN4C0DFjmtbvNx_26sx8F-pJgn108GqVZ_dPUh8wOK2q0hQxQaaSowy-sgOYmJETUythBc5VQEBrwMiPCjWIOAVdAFAHTO";
		//App_ACCESS_TOKEN="CgI6lDrcEjtRgXBpaJdZIhj9uZYO1-boaGdXoWJwuu0vPYioNLAD7Jb_C26-_Yk06RIVHINUE9t6NndAS7edg3pIOw8DYN_evU8HfTyzZVZxfMthiqDK-wrfLzb7eW4VUFViAAAPAQ";
		System.out.println(App_ACCESS_TOKEN);
		System.out.println(HttpUtil.Post("https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="+App_ACCESS_TOKEN,JSON.toJSONString(obj)).getHtml());
	
	}
}
