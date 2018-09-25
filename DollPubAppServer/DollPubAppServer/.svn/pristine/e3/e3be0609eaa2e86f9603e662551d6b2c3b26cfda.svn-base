package com.imlianai.dollpub.app.modules.support.version.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.dollpub.domain.version.Version;
import com.imlianai.dollpub.domain.version.VersionBootimg;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@SuppressWarnings("serial")
@ApiModel(value="版本更新返回")
public class VersionRespVO extends BaseRespVO{
	
	@ApiModelProperty(value="版本信息")
	private Version versionInfo;
	@ApiModelProperty(value="闪屏")
	private VersionBootimg bootimgInfo;
	
	@ApiModelProperty(value="弹窗广告")
	private VersionBootimg ad;
	@ApiModelProperty(value="关于我们的微信公众号信息")
	private VersionAboutInfo aboutInfo;
	public VersionRespVO(){
		this.aboutInfo=new VersionAboutInfo();
	}
	public Version getVersionInfo() {
		return versionInfo;
	}
	public void setVersionInfo(Version versionInfo) {
		this.versionInfo = versionInfo;
	}
	public VersionBootimg getBootimgInfo() {
		return bootimgInfo;
	}
	public void setBootimgInfo(VersionBootimg bootimgInfo) {
		this.bootimgInfo = bootimgInfo;
	}
	public VersionBootimg getAd() {
		return ad;
	}
	public void setAd(VersionBootimg ad) {
		this.ad = ad;
	}
	
	public VersionAboutInfo getAboutInfo() {
		return aboutInfo;
	}
	public void setAboutInfo(VersionAboutInfo aboutInfo) {
		this.aboutInfo = aboutInfo;
	}
	public void setSzAboutInfo() {
		this.aboutInfo = new VersionAboutInfo();
		this.aboutInfo.setId("");
		this.aboutInfo.setName("");
		this.aboutInfo.setQrcode("http://lianai-image-sys.qiniudn.com/e20180311/images/1521121469.png");
	}
	public class VersionAboutInfo{
		
		private String qrcode="http://lianai-image-sys.qiniudn.com/zjgw/gzh.png";
		
		private String name="真景直播抓娃娃解决方案";

		private String id="gh_ee4ff4978556";
		
		public String getQrcode() {
			return qrcode;
		}

		public void setQrcode(String qrcode) {
			this.qrcode = qrcode;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
		
	}
}
