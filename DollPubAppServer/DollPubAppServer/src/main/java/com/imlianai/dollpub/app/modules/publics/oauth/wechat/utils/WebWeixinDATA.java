package com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils;

public enum WebWeixinDATA {
	
	ToUserName("ToUserName"),
	
	FromUserName("FromUserName"),
	
	MsgType("MsgType"),
	
	Content("Content"),
	
	Text("text"),
	
	Image("image"),
	
	Location("location"),//消息里面的位置
	
	Label("label"),
	
	Event("event"),
	
	Event_View("VIEW"),
	
	Event_Click("CLICK"),
	
	Event_Subscribe("subscribe"),//订阅
	
	Event_Unsubscribe("unsubscribe"),//取消订阅
	
	Event_Location("LOCATION"),//事件位置--用于实时位置上报
	
	EventKey("EventKey"),
	
	Loc_Longitude("Longitude"),
	
	Loc_Latitude("Latitude"),
	
	Encrypt("Encrypt"),
	
	MsgSignature("MsgSignature"),
	
	Nonce("Nonce"),
	
	TimeStamp("TimeStamp"),
	
	InfoType("InfoType"),
	
	InfoType_Component_Verify_Ticket("component_verify_ticket"),
	
	InfoType_Unauthorized("unauthorized"),
	
	ComponentVerifyTicket("ComponentVerifyTicket"),
	
	AuthorizerAppid("AuthorizerAppid"),
	
	AppId("AppId"),
	
	//----------消息类型-------------//
	TextType("1"),
	
	ImageType("2"),
	
	ViewType("3"),
	
	ClickType("4"),
	
	SubscribeType("5"),//订阅
	
	UnsubscribeType("6"),//取消订阅
	
	LocationType("7"),//坐标
	;
	
	public String DATA;

	private WebWeixinDATA(String dATA) {
		DATA = dATA;
	}
}
