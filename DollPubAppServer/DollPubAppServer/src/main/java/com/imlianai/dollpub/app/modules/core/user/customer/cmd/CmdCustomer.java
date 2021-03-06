package com.imlianai.dollpub.app.modules.core.user.customer.cmd;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusDao;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.core.user.customer.dto.CustomerDTO;
import com.imlianai.dollpub.app.modules.core.user.customer.dto.DollBusDto;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.app.modules.core.user.customer.vo.CustomerReqVO;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.info.PackageMsg;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.utils.StringUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wurui
 * @create 2018-06-13 9:54
 **/
@Component("cus")
public class CmdCustomer extends RootCmd {

    @Resource
    private CustomerService customerService;

    @Resource
    private DollBusDao dollBusDao;

    @Resource
    private DollBusService dollBusService;

    //提供给视频页
    @ApiHandle
    @Path("api/cus/getAllCustomer")
    public BaseRespVO getAllCustomer(BaseReqVO vo){
        if (!vo.getLoginKey().equals("c10f91de68d240b1ad5ef41b056735b1")){
            return new BaseRespVO(0,false,"校验错误");
        }
        Map<String, Object> map = Maps.newHashMap();
        List<Customer> customerList = customerService.getAllCustomer();
        List<CustomerDTO> customerDTOS = Lists.newArrayList();
        if (!StringUtil.isNullOrEmpty(customerList)){
            for (Customer customer : customerList){
                if (customer.getState() == 1){
                    customerDTOS.add(CustomerDTO.adapter(customer));
                }
            }
        }
        map.put("customer",customerDTOS);
        return new BaseRespVO(map);
    }


    @ApiHandle
    @Path("api/cus/getCustomerBusList")
    public BaseRespVO getCustomerBusList(CustomerReqVO vo){

        Map<String, Object> map = Maps.newHashMap();
        List<Customer> customerList = customerService.getAllCustomer();
        if (!StringUtil.isNullOrEmpty(customerList) && !StringUtil.isNullOrEmpty(vo.getAppIds())){
            Set<Integer> groupList = Sets.newHashSet();
            for (String appId : vo.getAppIds()){
                for (Customer customer : customerList){
                    if (null != customer.getAppId() && customer.getAppId().equals(appId)){
                        groupList.add(customer.getGroupId());
                    }
                }
            }
            List<DollBus> dollBusList = Lists.newArrayList();
            List<DollBusDto> dollBusDtoList = Lists.newArrayList();
            if (!StringUtil.isNullOrEmpty(groupList)){
                for (int groupId : Lists.newArrayList(groupList)){
                    List<DollBus> dollBuses = dollBusService.getDollBusByGroupId(groupId,0,1000);
                    if (!StringUtil.isNullOrEmpty(dollBuses)){
                        dollBusList.addAll(dollBuses);
                    }

                }
                if (!StringUtil.isNullOrEmpty(dollBusList)){
                    for (DollBus dollBus : dollBusList){
                        dollBusDtoList.add(DollBusDto.adapter(dollBus));
                    }
                }
            }
            map.put("busList",dollBusDtoList);
            return new BaseRespVO(map);
        }
        return new BaseRespVO(0,false,"商户列表为空");
    }


}
