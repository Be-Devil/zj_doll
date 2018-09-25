package com.imlianai.dollpub.app.modules.core.coinfactory.dao;

import com.imlianai.dollpub.domain.coinfactory.virtual.base.PushCoinVirtualConfig;

/**
 * 虚拟推币机默认配置
 * @author wurui
 * @create 2018-07-09 11:32
 **/
public interface PushCoinVirtualConfigDao {

    /**
     * 新增或修改虚拟推币机机器配置
     * @param virtualConfig
     * @return
     */
    int insertOrUpdateVirtualPushCoinConfig(PushCoinVirtualConfig virtualConfig);


    int initConfig(int busId);


    /**
     * 获取虚拟推币机配置
     * @param busId
     * @return
     */
    PushCoinVirtualConfig getVirtualConfigByBusId(int busId);

    /**
     *  更新节点数值，每次入币加点+1
     * @param busId
     * @param intCoin
     * @return
     */
    int updateNodeValue(int busId,int intCoin);

    /**
     * 重置节点
     * @param busId
     * @return
     */
    int resetNode(int busId);

    /**
     * 更新下一个节点并清空当前节点值
     * @param currentNode
     * @param busId
     * @return
     */
    int updateNextNode(int currentNode,int busId);

}
