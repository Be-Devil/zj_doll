package com.imlianai.dollpub.app.modules.core.trade.util.ali.config;

import com.ctrip.framework.apollo.util.PropertiesUtil;
import com.imlianai.rpc.support.utils.LivePropsUtil;

/**
 * 上海赛驷网络科技有限公司的充值账户
 * @author tensloveq
 *
 */
public class AliPaySaisiConfig {

	public static String format = "json";
	
	public static String signType="RSA2";
	
	public static final String charSet = "utf-8";
	
	public static final String reqUrl = "https://openapi.alipay.com/gateway.do";
	/**
	 * 商户编号
	 */
	public static final String appId ="2017120400371408";// LivePropsUtil.getString("aliPaySaisi.appId");


	/**
	 * 回调地址
	 */
	public static final String notifyUrl =PropertiesUtil.getString("application","aliPaySaisi.notifyUrl");

	/**
	 * 支付完成跳转地址
	 */
	public static final String returnUrl = PropertiesUtil.getString("application","aliPaySaisi.returnUrl") ;
	
	/**
	 * 支付实际落地地址
	 */
	public static final String payH5Url =PropertiesUtil.getString("application","aliPaySaisi.payH5Url") ;
	/**
	 * 应用私钥
	 */
	public static final String rsaPrivate = PropertiesUtil.getString("application","aliPaySaisi.rsaPrivate");
	//"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCMNEmO7VzFpKAl8TEU5JrwftIS6IlpibpNIf//5R1g7Ld6pU7tJAUlUO5Gnt9XdTJU8Z9hmWjEd7Mu/s6R4Q4Yw3VHMIFnIzfkCOrm4ZORVHyOaXibK3/4W8Pg0MyEweuYZxyQWocwg4QXtV0Pl6+/uNbCrqEU7fvGZSYptC/BOQIS+URUOO4oHLY4VDITh9jjTyMXF7DyPSdYCJoUHCx377z+fsTBwTe9v5hNX8l0XVqc7W+852CORrOk5/Fikmv+/VSh9xXmdVFQAzGomu1b9dKNOxiTDzzyhNvXW/laWeruniorW2+8ZFbODc8ibSsjLulz8fsFiGOumbi07ATfAgMBAAECggEABpDIRQ4ugv7jlQNHdiACdXKq6cHD9ZJCjCwkbdpP1uMe8HpfSNGgABBbKCl2cgUF915dRVfGxTSJypCaIdC1NUmp60yFMsw+aMpWHbvAilS3hH/B6xpNYtcBRDzi5vCh1MA3YTcsD4SJIgcXNYy36TWBsRo2ZuGCIddIwzkx501vXty3S+7mCshq+H46XtzS86EPVABjs5rzFAFpwyqwUxEfcpOhUl97Eb+aQHR77fxbNaJS2Af3l5/gsCSgi71khevwSG0sbSMAPhu+YJVd46fdlJM+1x3BWTbtn5XIidEetTct/dcuTcUweqjeuUzexXLJO61Iau8l5PcGq95jwQKBgQDEosNIkUunbdXhI9MMoMdIrQNGdS59v57H4wWxxSnMEQD4ZxyCK/4KudOJHaYka7o/uSfo3X8voCdSUmTGQyZ/zAu4E65vX63IqdtpOmrHnPxNcHo0PnN9bUxEty+pmqyRQp91o8vyp5CICkCJnRXswtSJMM6PtkH/595JdFhIoQKBgQC2iCTJaH0rOjJVNnAOwHqCqT2vVUa7lNOziv3sA4COzRQYSAzEtjor/scbRtplA/EqPSXUwKSIDJz6DZXhGmv78Cxm477x6FbPCsE5w4ryXFDAjOCjZ1O47uJvtgkZpVi5UhVLF2/HaHOGtQIf4vdPiIMxQthWhXKHzVQdLk3dfwKBgFmxfK5vSDkZCsRlmPFbrUtjT+klro1BiOXYklJigwxIfU7Ridl+w6SU2v7ifVSDpzNF/ARmdIt+3DDa0Ij3PGpy4q2WnKzE0fqEMEcxlF91JkkUk0mdawS9hFqbzKvql+iyoRYCHxeSg4TqWEHtW4Dh5hymHLLFChn25BhRb6vBAoGATCvyVg6BtYlauc624qEWPvKLaGDI3LzVv6YwIs6vVmhki2F92pVFH1j6E/r0ZpVHjd3orKkbwB6rX3pN4ptZFtD6ZpGB/Id9unb9fbKJiGxX0pK3p9kVyiX+XqnzkD09ElEby7NjFS8UjtF4qn/Xvtv0C5memDSmP9/Cy06oddcCgYEApYEHn4kMXB4GY0tuARbriZx76pm1CVicBiHoBg2kJHJYysVVhkHTuRz0qZv0QDsG6GFOhj7A38Fa3XNiLuyRICLA9Ztk6LAEdxKO+blUieENjZr5krngFa6nGqprDHt0TWP1BEpGdUq9mCS49TwJ4WC/fNfpFp/+jIENrPAxurY=";
	
	/**
	 * 应用公钥(RSA1widthRsa)
	 */
	public static final String rsaPublic = PropertiesUtil.getString("application","aliPaySaisi.rsaPublic");
	//"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjDRJju1cxaSgJfExFOSa8H7SEuiJaYm6TSH//+UdYOy3eqVO7SQFJVDuRp7fV3UyVPGfYZloxHezLv7OkeEOGMN1RzCBZyM35Ajq5uGTkVR8jml4myt/+FvD4NDMhMHrmGcckFqHMIOEF7VdD5evv7jWwq6hFO37xmUmKbQvwTkCEvlEVDjuKBy2OFQyE4fY408jFxew8j0nWAiaFBwsd++8/n7EwcE3vb+YTV/JdF1anO1vvOdgjkazpOfxYpJr/v1UofcV5nVRUAMxqJrtW/XSjTsYkw888oTb11v5Wlnq7p4qK1tvvGRWzg3PIm0rIy7pc/H7BYhjrpm4tOwE3wIDAQAB";
	
	/**
	 * 支付宝公钥(RSA1withRSA)
	 */
	public static final String reaPublicAliPay = PropertiesUtil.getString("application","aliPaySaisi.reaPublicAliPay");
	//"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	/**
	 * 支付宝公钥(SHA256withRsa)
	 */
	public static final String rsa2PublicAliPay = PropertiesUtil.getString("application","aliPaySaisi.rsa2PublicAliPay");
}
