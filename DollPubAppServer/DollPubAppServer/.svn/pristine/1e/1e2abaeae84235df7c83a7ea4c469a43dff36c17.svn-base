package com.imlianai.dollpub.app.modules.publics.sms.ali;

/**
 * 短信查询请求
 * @author wurui
 * @create 2018-02-23 14:25
 **/
public class AliSMSQueryRequest {

    /**
     * 必填
     *
     * 说明：短信接收号码,如果需要查询国际短信,号码前需要带上对应国家的区号,区号的获取详见国际短信支持国家信息查询API接口
     * 样例取值:15000000000
     */
    private String phoneNumbers;

    /**
     * 必填
     *
     * 发送日期 支持30天内记录查询，格式yyyyMMdd
     */
    private String sendDate;

    /**
     * 必填
     *
     * 页大小Max=50,默认10
     */
    private Long pageSize = 10L;

    /**
     *  必填
     *
     *  当前页码,默认1
     */
    private Long currentPage = 1L;

    /**
     * 可选
     *
     * 发送回执ID,可根据该ID查询具体的发送状态
     * 样例取值:134523^4351232
     */
    private String bizId;

    public AliSMSQueryRequest(String phoneNumbers, String sendDate) {
        this.phoneNumbers = phoneNumbers;
        this.sendDate = sendDate;
    }

    public AliSMSQueryRequest(String phoneNumbers, String sendDate, Long pageSize, Long currentPage) {
        this.phoneNumbers = phoneNumbers;
        this.sendDate = sendDate;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    public AliSMSQueryRequest() {
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }
}
