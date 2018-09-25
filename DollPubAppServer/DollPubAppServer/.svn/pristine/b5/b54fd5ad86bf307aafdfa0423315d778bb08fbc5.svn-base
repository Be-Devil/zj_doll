package com.imlianai.dollpub.app.modules.core.user.customer.dto;

import com.imlianai.dollpub.domain.customer.Customer;

/**
 * @author wurui
 * @create 2018-06-13 10:20
 **/
public class CustomerDTO {
    private String appId;
    private int groupId; //分组名
    private String name; //商户名字

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CustomerDTO adapter(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        if (customer != null){
            customerDTO.setAppId(customer.getAppId());
            customerDTO.setGroupId(customer.getGroupId());
            customerDTO.setName(customer.getName());
        }
        return customerDTO;
    }
}
