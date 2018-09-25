package com.imlianai.dollpub.app.modules.publics.msg.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.dollpub.domain.msg.MsgNotice;
import com.imlianai.dollpub.domain.msg.MsgRoom;

public class MsgJumpDemo {

	@ApiModelProperty("leancloud类型，固定是-30")
	private String lctype = "-1";

	@ApiModelProperty("leancloud消息主体")
	private LcTextJumpDemo lctext;

	public String getLctype() {
		return lctype;
	}


	public void setLctype(String lctype) {
		this.lctype = lctype;
	}


	public LcTextJumpDemo getLctext() {
		return lctext;
	}


	public void setLctext(LcTextJumpDemo lctext) {
		this.lctext = lctext;
	}


	public class LcTextJumpDemo {
		@ApiModelProperty("带跳转系统消息类型,固定是50")
		private int type;
		@ApiModelProperty("消息内容主体")
		private MsgNotice body;

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public MsgNotice getBody() {
			return body;
		}

		public void setBody(MsgNotice body) {
			this.body = body;
		}


	}
}
