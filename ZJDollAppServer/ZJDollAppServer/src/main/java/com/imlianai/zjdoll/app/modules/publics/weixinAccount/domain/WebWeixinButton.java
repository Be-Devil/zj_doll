package com.imlianai.zjdoll.app.modules.publics.weixinAccount.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.zjdoll.app.modules.publics.oauth.weixin.config.OauthWeiXinConfig;


public class WebWeixinButton {
	
//	private static final String APP_SECRET = "93d65424ac74b99b9829c822b52427ca";
	// 需要在验证的时候记录下来--考虑数据库或缓存存储可能性
//	private static final String APP_ACCESS_TOKEN = "zkp31021";
	
	private String type;
	private String name;
	private String url;
	private String key;
	private List<WebWeixinButton> sub_button;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
		public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public void setView(String name, String url) {
		this.type = "view";
		this.name = name;
		this.url = url;
	}
	
	public void setClick(String name, String key){
		this.type = "click";
		this.name = name;
		this.key = key;
	}
	
	public void setParent(String name, List<WebWeixinButton> subbuttonlist){
		this.name = name;
		this.sub_button=subbuttonlist;
	}
	
	public static void main(String[] args) {
		handleMengquZhushou();
	}
	
	public static void getSuanguoMenu(){
		String App_ACCESS_TOKEN="fYTWWs500U_yXlidcV17ljLoh7A2QgRnwuSztby6YVuYuodiNx_hJPBqcYbbeIWqEFFSjCA2fWMLkI-f-Y4Xuev31T2IlbieZf7qcKPaFto4kYtWBSihA0nOHMvizejtLCYgCBARIE";
		System.out.println(HttpUtil.Get("https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+App_ACCESS_TOKEN).getHtml());
		
	}
	
	public static void handleMengquZhushou(){
		WebWeixinButton button = new WebWeixinButton();
		List<WebWeixinButton>  buttonlist=new ArrayList<WebWeixinButton>();
		List<WebWeixinButton> subbuttonlist=new ArrayList<WebWeixinButton>();
		List<WebWeixinButton> eventsubbuttonlist=new ArrayList<WebWeixinButton>();
		//新建子菜单
		button = new WebWeixinButton();
		button.setView("宇宙无敌送豪礼","http://www.mengquww.com/event/e20180420/index_h.html");
		eventsubbuttonlist.add(button);
		button = new WebWeixinButton();
		button.setClick("邀请有奖","invite_code");
		eventsubbuttonlist.add(button);
		button = new WebWeixinButton();
		button.setView("微博福利","https://m.weibo.cn/5997839722/4218264428718947");
		eventsubbuttonlist.add(button);
		button = new WebWeixinButton();
		button.setView("免费领币","http://www.nuannuanzhibo.com/mobile/oauth/wx_auth.do?eventtarget=weixin_live_test&userInfoMark=userInfoSave&appid=wx72f7d35891f70172&authBefore=authBefore&channel=gw");
		eventsubbuttonlist.add(button);
		
		button = new WebWeixinButton();
		button.setView("星途恋人","http://www.mishuoapp.com/xtlr/index.html?hmsr=inside&hmpl=neibu");
		eventsubbuttonlist.add(button);
		
/*		button = new WebWeixinButton();
		button.setParent("活动福利", eventsubbuttonlist);
		//buttonlist.add(button);
		button = new WebWeixinButton();
		button.setClick("红包绑定","boundredpacket");
		subbuttonlist.add(button);
		button = new WebWeixinButton();
		button.setView("联运合作", "http://www.realgamecloud.com/doc/contact.html");
		subbuttonlist.add(button);
		button = new WebWeixinButton();
		button.setParent("红包绑定", subbuttonlist);*/
		
		button = new WebWeixinButton();
		button.setClick("红包绑定","boundredpacket");
		buttonlist.add(button);
		button = new WebWeixinButton();
		button.setView("下载APP","http://a.app.qq.com/o/simple.jsp?pkgname=os.zjwy.zengjing.doll");
		buttonlist.add(button);
		button = new WebWeixinButton();
		button.setClick("商务合作", "consult");
		buttonlist.add(button);
		Map<Object,Object> obj =new HashMap<Object, Object>();
		obj.put("button", buttonlist);
		System.out.println(JSON.toJSONString(obj));
		//HttpEntity httpEntity = HttpUtil.Get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx72f7d35891f70172&secret=a7d29dce0e2f741b5559960ea4b2e725");
		//System.out.println(httpEntity.getHtml());
		String App_ACCESS_TOKEN="12_bkGbLcmvNhHKjA7GUmi6LSyK0L7odlQyuQ-v5OKdZT2gdJ3BuC2GxcH-G0IDvBc7x5ADsMKUst3dD7Y90q9Z0IZdOXPdz68t8x0s0T3iLA5DCPrmYYhEA2skivbU1YN0ZtP1sE9LxiRbPr2ZJTFbAFARBN";
		//App_ACCESS_TOKEN="7awsnzIeEhPjvinpp2n7-rOmG-CP1knys1ruVIOef7L3ki5tIFqgZi_7eEGeGJUX2ZI0uNDwwHPzJNsP9xWFdFxCTD1NRLyMhfoOOJODfeCq3tqrpn22vz_27Mz_b3E9PNYjCFAAYJ";
		//System.out.println(App_ACCESS_TOKEN);
		com.imlianai.rpc.support.common.entity.HttpEntity HttpEntity=HttpUtil.Post("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+App_ACCESS_TOKEN, JSON.toJSONString(obj));
		System.out.println(HttpEntity.getHtml());
	}
	
	public static void handleMengqu(){
		WebWeixinButton button = new WebWeixinButton();
		List<WebWeixinButton>  buttonlist=new ArrayList<WebWeixinButton>();
		List<WebWeixinButton> subbuttonlist=new ArrayList<WebWeixinButton>();
		List<WebWeixinButton> eventsubbuttonlist=new ArrayList<WebWeixinButton>();
		//新建子菜单
		button = new WebWeixinButton();
		button.setView("开始游戏","http://www.realgamecloud.com/api/oauth/wxAuth/84/doll?appid=wx8c5308361b165de9&userInfoMark=userInfoSave&authBefore=authBefore&agentId=1");
		buttonlist.add(button);
		button = new WebWeixinButton();
		button.setClick("每日礼包", "day_sign");
		subbuttonlist.add(button);
		button = new WebWeixinButton();
		button.setClick("邀请有奖", "invite_reward");
		subbuttonlist.add(button);
		button = new WebWeixinButton();
		button.setParent("签到领币", subbuttonlist);
		buttonlist.add(button);
		
		button = new WebWeixinButton();
		button.setClick("5元红包", "red_5_pic");
		eventsubbuttonlist.add(button);
		button = new WebWeixinButton();
		button.setClick("商务合作", "consult");
		eventsubbuttonlist.add(button);
		button = new WebWeixinButton();
		button.setParent("活动福利", eventsubbuttonlist);
		buttonlist.add(button);
		
		Map<Object,Object> obj =new HashMap<Object, Object>();
		obj.put("button", buttonlist);
		System.out.println(JSON.toJSONString(obj));
		//HttpEntity httpEntity = HttpUtil.Get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+OauthWeiXinConfig.ACCOUNT_APP_ID+"&secret="+OauthWeiXinConfig.ACCOUNT_SECRET);
		//System.out.println(httpEntity.getHtml());
		String App_ACCESS_TOKEN="7_YOaGmPAMhi_Pkp8X2h-tFnNmjzQNPgqFDdzhOCuR8lfyqou9NsJkDac6_kpD8XCWr-KRHHS5F8hrO4D1vlxI_Y72udxSkPokoBlSgHuRo33CaMZOsXvciRmpC6utTi-iS7Sy7HMAmWL0Me6sZUPeAEASNI";
		//App_ACCESS_TOKEN="7awsnzIeEhPjvinpp2n7-rOmG-CP1knys1ruVIOef7L3ki5tIFqgZi_7eEGeGJUX2ZI0uNDwwHPzJNsP9xWFdFxCTD1NRLyMhfoOOJODfeCq3tqrpn22vz_27Mz_b3E9PNYjCFAAYJ";
		//System.out.println(App_ACCESS_TOKEN);
		com.imlianai.rpc.support.common.entity.HttpEntity HttpEntity=HttpUtil.Post("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+App_ACCESS_TOKEN, JSON.toJSONString(obj));
		System.out.println(HttpEntity.getHtml());
	}
	
	public static void handeSuanguozhibo(){
		WebWeixinButton button = new WebWeixinButton();
		List<WebWeixinButton>  buttonlist=new ArrayList<WebWeixinButton>();
		List<WebWeixinButton> subbuttonlist=new ArrayList<WebWeixinButton>();
		List<WebWeixinButton> eventsubbuttonlist=new ArrayList<WebWeixinButton>();
		//新建子菜单
		button = new WebWeixinButton();
		button.setView("下载直播", "http://www.nuannuanzhibo.com/");
		buttonlist.add(button);
		button = new WebWeixinButton();
		button.setView("一秒抓住宋小宝","http://t.xiehou360.com/LiveWebServer/event/e20160830test3/sxbIndex.html");
		subbuttonlist.add(button);
		button = new WebWeixinButton();
		button.setView("装逼神器","http://t.xiehou360.com/LiveWebServer/event/e20160907test/orderIndex.html");
		/*subbuttonlist.add(button);
		button = new WebWeixinButton();
		button.setParent(""+ChannelConstants.getByThread().simpleName()+"游戏", subbuttonlist);*/
		//buttonlist.add(button);
		
		button = new WebWeixinButton();
		button.setView("关于直播","http://mp.weixin.qq.com/s?__biz=MzI4NzE4MDIxOQ==&mid=521561135&idx=1&sn=ef7587f03acad253fd5d72ce36f550a9&scene=18#wechat_redirect");
		eventsubbuttonlist.add(button);
		button = new WebWeixinButton();
		button.setView("直播充值","http://mp.weixin.qq.com/s?__biz=MzI4NzE4MDIxOQ==&mid=521561242&idx=1&sn=43e9f9f09a7864f4336c57c249776875&scene=18#wechat_redirect");
		eventsubbuttonlist.add(button);
		button = new WebWeixinButton();
		button.setView("直播提现","http://mp.weixin.qq.com/s?__biz=MzI4NzE4MDIxOQ==&mid=521561242&idx=2&sn=1fb40515ee11f2134fd46a797174e332&scene=18#wechat_redirect");
		eventsubbuttonlist.add(button);
		button = new WebWeixinButton();
		button.setView("免费流量","https://hd.faisco.cn/9292902/41/load.html?style=0");
		subbuttonlist.add(button);
		button = new WebWeixinButton();
		button.setParent("玩法攻略", eventsubbuttonlist);
		buttonlist.add(button);
		Map<Object,Object> obj =new HashMap<Object, Object>();
		obj.put("button", buttonlist);
		System.out.println(JSON.toJSONString(obj));
		//String APP_ACCESS_TOKEN = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx45686a461704e848&secret=498c9394b57881d91d9eb73cdd03d4c5", "");
		//System.out.println(APP_ACCESS_TOKEN);
		String App_ACCESS_TOKEN="AUyWI1uwK8-yKR9gcNMMbKP7DwHPtLYB04o7YzJOVs28B5iBh933U-2rPprSiY6viYkLiNyHDqMjc-ROVxEh-5TJzi3rXK6q8ITDGiW5fOCNhx-JcAUCcqpkKVp_Hwv0ZZPiCBAVVL";//APP_ACCESS_TOKEN;//webWeixinService.getWebTokenByAppid(senderUser).get(WebWeixinTokenHandler.ACCESS_TOKEN_TYPE).getToken_ticket();
		//App_ACCESS_TOKEN="CgI6lDrcEjtRgXBpaJdZIhj9uZYO1-boaGdXoWJwuu0vPYioNLAD7Jb_C26-_Yk06RIVHINUE9t6NndAS7edg3pIOw8DYN_evU8HfTyzZVZxfMthiqDK-wrfLzb7eW4VUFViAAAPAQ";
		System.out.println(App_ACCESS_TOKEN);
		System.out.println(HttpUtil.Get("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+App_ACCESS_TOKEN, JSON.toJSONString(obj)));
	
	}
	public List<WebWeixinButton> getSub_button() {
		return sub_button;
	}
	public void setSub_button(List<WebWeixinButton> sub_button) {
		this.sub_button = sub_button;
	}
	
}