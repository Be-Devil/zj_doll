package com.imlianai.dollpub.app.modules.support.machine.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 机器初始化请求对象
 * @author wurui
 * @create 2018-02-27 11:49
 **/
@ApiModel(value = "机器初始化请求对象")
public class MachineInitReqVO extends BaseReqVO{

    @ApiModelProperty(value = "查询类型[0:初始化所有机器数据,1:初始化单条数据] 默认:0")
    private Integer type = 0;

    @ApiModelProperty(value = "机器id(type=1时必填)",required = true)
    private Integer busId;

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
}
