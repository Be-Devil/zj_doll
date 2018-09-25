package com.imlianai.dollpub.app.modules.support.machine.vo;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 机器相关设置查询请求对象
 * @author wurui
 * @create 2018-02-27 10:29
 **/
@ApiModel(value = "机器相关设置查询请求对象")
public class MachineSettingQueryReqVO extends BaseSignReqVO {

    @ApiModelProperty(value = "查询类型[1:普通数据查询，2:爪力电压查询，3:机器马达速度]",required = true)
    private Integer type = 0;

    @ApiModelProperty(value = "机器id",required = true)
    private Integer busId =0 ;

    private Integer customerId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
