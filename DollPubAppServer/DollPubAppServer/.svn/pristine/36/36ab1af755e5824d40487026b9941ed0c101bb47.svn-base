package com.imlianai.dollpub.app.modules.publics.sms.ali;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 短信发送相关请求
 * @author wurui
 * @create 2018-02-23 11:50
 **/
public class AliSmsSendRequest {

    /**
     * 必填
     *
     * 说明：短信接收号码,支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
     * 样例取值:15000000000
     */
    private String phoneNumbers;

    /**
     * 必填
     *
     * 说明：短信签名
     * 样例取值:云通信
     */
    private String signName;

    /**
     * 必填
     *
     * 说明：短信模板ID
     * 样例取值:SMS_0000
     */
    private String templateCode;

    /**
     * 可选
     *
     * 说明：短信模板变量替换JSON串,友情提示:如果JSON中需要带换行符,请参照标准的JSON协议。
     * 样例取值:{"code":"1234","product":"ytx"}
     */
    private String templateParam;

    /**
     * 可选
     *
     * 说明：上行短信扩展码,无特殊需要此字段的用户请忽略此字段
     * 样例取值:90999
     */
    private String smsUpExtendCode;

    /**
     * 可选
     *
     * 说明：外部流水扩展字段
     * 样例取值:abcdefgh
     */
    private String outId;


    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    public String getSmsUpExtendCode() {
        return smsUpExtendCode;
    }

    public void setSmsUpExtendCode(String smsUpExtendCode) {
        this.smsUpExtendCode = smsUpExtendCode;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public AliSmsSendRequest(String phoneNumbers, String signName, String templateCode) {
        this.phoneNumbers = phoneNumbers;
        this.signName = signName;
        this.templateCode = templateCode;
    }

    public  AliSmsSendRequest(String phoneNumbers, String signName, String templateCode,String code) {
        this.phoneNumbers = phoneNumbers;
        this.signName = signName;
        this.templateCode = templateCode;
        Map<String, String> codeMap=new HashMap<String, String>();
        codeMap.put("code", code);
        this.templateParam=JSON.toJSONString(codeMap);
    }
    public AliSmsSendRequest() {
    }

}
