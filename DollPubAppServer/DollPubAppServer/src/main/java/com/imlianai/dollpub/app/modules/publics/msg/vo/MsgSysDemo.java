package com.imlianai.dollpub.app.modules.publics.msg.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.dollpub.domain.msg.Msg;

public class MsgSysDemo {

	@ApiModelProperty("leancloud类型，固定是-1")
	private String lctype = "-1";

	@ApiModelProperty("leancloud消息主体")
	private LcTextDemo lctext;


	public String getLctype() {
		return lctype;
	}


	public void setLctype(String lctype) {
		this.lctype = lctype;
	}


	public LcTextDemo getLctext() {
		return lctext;
	}


	public void setLctext(LcTextDemo lctext) {
		this.lctext = lctext;
	}


	public class LcTextDemo {
		@ApiModelProperty("系统消息类型")
		private int type;
		@ApiModelProperty("消息内容主体")
		private Msg body;
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public Msg getBody() {
			return body;
		}
		public void setBody(Msg body) {
			this.body = body;
		}
		
	}
}
