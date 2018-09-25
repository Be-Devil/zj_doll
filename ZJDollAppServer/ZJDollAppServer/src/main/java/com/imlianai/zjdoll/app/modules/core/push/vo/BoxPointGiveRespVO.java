package com.imlianai.zjdoll.app.modules.core.push.vo;


import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.core.push.dto.UserPointGiveRecordDTO;
import com.imlianai.zjdoll.domain.doll.DollInfo;

import java.util.List;
import java.util.Map;

/**
 * @author wurui
 * @create 2018-06-21 11:23
 **/
public class BoxPointGiveRespVO extends BaseRespVO {

    private List<UserPointGiveRecordDTO> list;
    private Map<String,String> dollInfoMaps;

    public List<UserPointGiveRecordDTO> getList() {
        return list;
    }

    public void setList(List<UserPointGiveRecordDTO> list) {
        this.list = list;
    }


    public Map<String, String> getDollInfoMaps() {
        return dollInfoMaps;
    }

    public void setDollInfoMaps(Map<String, String> dollInfoMaps) {
        this.dollInfoMaps = dollInfoMaps;
    }

    public BoxPointGiveRespVO() {
    }
}
