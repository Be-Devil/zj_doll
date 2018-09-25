package com.imlianai.dollpub.app.pvd;

import com.alibaba.dubbo.config.annotation.Service;
import com.imlianai.dollpub.app.iface.IAppPushCoinRemoteService;
import com.imlianai.dollpub.app.modules.core.coinfactory.service.PushCoinMachineService;
import com.imlianai.dollpub.app.modules.core.coinfactory.vo.PushCoinBusQueryRespVO;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.domain.coinfactory.MachinePushCoin;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import javax.annotation.Resource;

/**
 * 推币服务rpc
 * @author wurui
 * @create 2018-05-09 11:55
 **/

@Service(interfaceClass = IAppPushCoinRemoteService.class)
public class IAppPushCoinRemoteServiceImpl implements IAppPushCoinRemoteService {

    @Resource
    private PushCoinMachineService pushCoinMachineService;

    @Resource
    private CustomerService customerService;


    @Override
    public BaseRespVO apply(long uid, int busId, int customerId) {
        if (customerId != 0){
            Customer customer = customerService.getCustomerById(customerId);
            if (customer == null) {
                return new BaseRespVO(-1, false, "上机失败：商户不存在=>" + customerId);
            }
            return pushCoinMachineService.apply(uid,busId,customer);
        }
        return new BaseRespVO(0,false,"商户为空");
    }

    @Override
    public BaseRespVO putCoin(long uid, int busId, int customerId) {
        if (customerId != 0){
            Customer customer = customerService.getCustomerById(customerId);
            if (customer == null) {
                return new BaseRespVO(-1, false, "推币失败：商户不存在=>" + customerId);
            }
            return pushCoinMachineService.putCoin(uid,busId,customer);
        }
        return new BaseRespVO(0,false,"商户为空");
    }

    @Override
    public BaseRespVO operate(long uid, int busId) {
        return pushCoinMachineService.operate(uid,busId);
    }

    @Override
    public BaseRespVO finish(long uid, int busId,int customerId) {
        if (customerId != 0){
            Customer customer = customerService.getCustomerById(customerId);
            if (customer == null) {
                return new BaseRespVO(-1, false, "推币失败：商户不存在=>" + customerId);
            }
            return pushCoinMachineService.finish(uid,busId);
        }
        return new BaseRespVO(0,false,"商户为空");
    }

    @Override
    public BaseRespVO query(long optId, int customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer != null){
            MachinePushCoin machinePushCoin = pushCoinMachineService.getOptRecordRouter(customer.getGroupId(),optId);
            if (machinePushCoin != null){
                PushCoinBusQueryRespVO pushCoinBusQueryRespVO = new PushCoinBusQueryRespVO(machinePushCoin);
                pushCoinBusQueryRespVO.setResult(100);
                pushCoinBusQueryRespVO.setState(true);
                pushCoinBusQueryRespVO.setMsg("数据请求成功!");
                return pushCoinBusQueryRespVO;
            }
        }
        return new BaseRespVO(0,false,"无操作记录!");
    }
}
