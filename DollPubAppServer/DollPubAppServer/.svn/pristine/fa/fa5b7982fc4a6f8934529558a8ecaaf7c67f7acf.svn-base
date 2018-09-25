package com.imlianai.dollpub.app.modules.publics.sms.ali;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * 阿里短信发送
 *
 * @author wurui
 * @create 2018-02-23 11:36
 **/
public class AliSMSApi {

    /**
     * OK	                                请求成功
     * isp.RAM_PERMISSION_DENY	            RAM权限DENY
     * isv.OUT_OF_SERVICE	                业务停机
     * isv.PRODUCT_UN_SUBSCRIPT	            未开通云通信产品的阿里云客户
     * isv.PRODUCT_UNSUBSCRIBE	            产品未开通
     * isv.ACCOUNT_NOT_EXISTS	            账户不存在
     * isv.ACCOUNT_ABNORMAL	                账户异常
     * isv.SMS_TEMPLATE_ILLEGAL	            短信模板不合法
     * isv.SMS_SIGNATURE_ILLEGAL	        短信签名不合法
     * isv.INVALID_PARAMETERS	            参数异常
     * isp.SYSTEM_ERROR	                    系统错误
     * isv.MOBILE_NUMBER_ILLEGAL	        非法手机号
     * isv.MOBILE_COUNT_OVER_LIMIT	        手机号码数量超过限制
     * isv.TEMPLATE_MISSING_PARAMETERS	    模板缺少变量
     * isv.BUSINESS_LIMIT_CONTROL	        业务限流
     * isv.INVALID_JSON_PARAM	JSON        参数不合法，只接受字符串值
     * isv.BLACK_KEY_CONTROL_LIMIT	        黑名单管控
     * isv.PARAM_LENGTH_LIMIT	            参数超出长度限制
     * isv.PARAM_NOT_SUPPORT_URL	        不支持URL
     * isv.AMOUNT_NOT_ENOUGH	            账户余额不足
     */


    private final static String PRODUCT = "Dysmsapi";
    private final static String DOMAIN = "dysmsapi.aliyuncs.com";
    private final static String ACCESS_KEY_ID = "LTAI9f0eZ2TLmQLg";
    private final static String ACCESS_KEY_SECRET = "ZJBa4N8CpI2eeeZZwktr9YM7MqeHsK";
    private final static String TIME_OUT = "10000";

    private static IAcsClient acsClient = null;

    static {
        //超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", TIME_OUT);
        System.setProperty("sun.net.client.defaultReadTimeout", TIME_OUT);

        //初始化acsClient,暂不支持region化
        try {
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
            acsClient = new DefaultAcsClient(profile);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public static int sendCheckCodeSMS(long phone,int checkCode){
    	AliSmsSendRequest sendSmsReq = new AliSmsSendRequest(phone+"", "萌趣抓娃娃", "SMS_126280143",checkCode+"");
    	try {
			sendSMS(sendSmsReq);
		} catch (ClientException e) {
		}
    	return 1;
    }
    
    public static int sendCheckCodeSMS(long phone,int checkCode,String name){
    	AliSmsSendRequest sendSmsReq = new AliSmsSendRequest(phone+"",name, "SMS_126280143",checkCode+"");
    	try {
			sendSMS(sendSmsReq);
		} catch (ClientException e) {
		}
    	return 1;
    }
    /**
     * 发送短信接口
     *
     * @param aliSmsSendRequest
     * @return
     * @throws ClientException
     */
    public static SendSmsResponse sendSMS(AliSmsSendRequest aliSmsSendRequest) throws ClientException {
        if (acsClient != null) {
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();

            //必填参数
            request.setPhoneNumbers(aliSmsSendRequest.getPhoneNumbers());
            request.setSignName(aliSmsSendRequest.getSignName());
            request.setTemplateCode(aliSmsSendRequest.getTemplateCode());

            //选填参数
            if (aliSmsSendRequest.getTemplateParam() != null) {
                request.setTemplateParam(aliSmsSendRequest.getTemplateParam());
            }
            if (aliSmsSendRequest.getSmsUpExtendCode() != null) {
                request.setSmsUpExtendCode(aliSmsSendRequest.getSmsUpExtendCode());
            }
            if (aliSmsSendRequest.getOutId() != null) {
                request.setOutId(aliSmsSendRequest.getOutId());
            }
            return acsClient.getAcsResponse(request);
        }
        throw new NullPointerException("acsClient is null!");
    }

    /**
     * 短信发送记录查询接口
     * 用于查询短信发送的状态，是否成功到达终端用户手机
     *
     * @param aliSMSQueryRequest
     * @return
     * @throws ClientException
     */
    public static QuerySendDetailsResponse querySendDetails(AliSMSQueryRequest aliSMSQueryRequest) throws ClientException {
        if (acsClient != null) {
            //组装请求对象
            QuerySendDetailsRequest request = new QuerySendDetailsRequest();

            //必填参数
            request.setPhoneNumber(aliSMSQueryRequest.getPhoneNumbers());

            SimpleDateFormat ft = new SimpleDateFormat(aliSMSQueryRequest.getSendDate());
            request.setSendDate(ft.format(new Date()));

            request.setPageSize(aliSMSQueryRequest.getPageSize());
            request.setCurrentPage(aliSMSQueryRequest.getCurrentPage());

            //选填参数
            if (aliSMSQueryRequest.getBizId() != null) {
                request.setBizId(aliSMSQueryRequest.getBizId());
            }

            return acsClient.getAcsResponse(request);
        }
        throw new NullPointerException("acsClient is null!");
    }

}
