package com.imlianai.dollpub.app.modules.publics.msg.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.dollpub.domain.msg.MsgRoom;

public class MsgRoomDemo {

	@ApiModelProperty("leancloud类型，固定是-30")
	private String lctype = "-1";

	@ApiModelProperty("leancloud消息主体")
	private LcTextRoomDemo lctext;

	public String getLctype() {
		return lctype;
	}


	public void setLctype(String lctype) {
		this.lctype = lctype;
	}


	public LcTextRoomDemo getLctext() {
		return lctext;
	}


	public void setLctext(LcTextRoomDemo lctext) {
		this.lctext = lctext;
	}


	public class LcTextRoomDemo {
		@ApiModelProperty("系统消息类型,固定是30")
		private int type;
		@ApiModelProperty("消息内容主体")
		private MsgRoom body;

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public MsgRoom getBody() {
			return body;
		}

		public void setBody(MsgRoom body) {
			this.body = body;
		}


	}
}
