package com.imlianai.dollpub.app.modules.support.shopkeeper.dao.impl;

import com.google.common.collect.Lists;
import com.imlianai.dollpub.app.modules.support.shopkeeper.dao.ShopkeeperWithdrawRecordDao;
import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperWithdrawRecord;
import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperWithdrawRecord.WithdrawStatusEnum;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.rpc.support.utils.StringUtil;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import java.util.List;

/**
 * @author wurui
 * @create 2018-04-26 21:19
 **/
@Repository
public class ShopkeeperWithdrawRecordDaoImpl implements ShopkeeperWithdrawRecordDao {

    @Resource
    private JdbcHandler jdbcHandler;

    @Override
    public int addRecord(ShopkeeperWithdrawRecord record) {
        return jdbcHandler.executeSql("INSERT INTO shopkeeper_withdraw_record(uid,cMoney,aMoney,rMoney,status,remark,createTime) VALUES(?,?,?,?,?,?,NOW())",
                record.getUid(),record.getcMoney(),record.getaMoney(),record.getrMoney(),record.getStatus(),record.getRemark());
    }
    @Override
    public ShopkeeperWithdrawRecord getRecordToPay(int size){
    	return jdbcHandler.queryOneBySql(ShopkeeperWithdrawRecord.class, "select * from shopkeeper_withdraw_record where status =? and payFlag=0 order by updateTime asc limit ? ",WithdrawStatusEnum.S_NOT_ACCOUNT.status,size);
    }
    
    @Override
    public int updateStatus(int id, int status,String remark) {
        return jdbcHandler.executeSql("UPDATE shopkeeper_withdraw_record SET `status`=?,remark=?,updateTime=NOW() WHERE id=? AND `status`<>?",status,remark,id,status);
    }

    @Override
    public List<ShopkeeperWithdrawRecord> getRecord(long uid) {
        return jdbcHandler.queryBySql(ShopkeeperWithdrawRecord.class,"SELECT * FROM shopkeeper_withdraw_record WHERE uid=?",uid);
    }

    @Override
    public List<ShopkeeperWithdrawRecord> getMonthRecord(long uid) {
        return jdbcHandler.queryBySql(ShopkeeperWithdrawRecord.class,"SELECT * FROM shopkeeper_withdraw_record WHERE uid=? and date_format(createTime,'%Y%m')=date_format(curdate(),'%Y%m') and `status`=5",uid);
    }

    @Override
    public List<ShopkeeperWithdrawRecord> getDayRecord(long uid, String date) {
        if (!StringUtil.isNullOrEmpty(date)){
            String start = date + " 00:00:00";
            String end = date + " 23:59:59";
            return jdbcHandler.queryBySql(ShopkeeperWithdrawRecord.class,"SELECT * FROM shopkeeper_withdraw_record WHERE uid=? AND createTime>=? AND createTime<=?",uid,start,end);
        }
        return Lists.newArrayList();
    }
    
	@Override
	public int updatePayState(long id, int payState,int status) {
		return jdbcHandler.executeSql("update shopkeeper_withdraw_record set payFlag=?,status =?,payTime=now() where id=? limit 1", payState,status,id);
	}
	@Override
	public int updateReason(long id, String remark,String wechatPaymentId) {
		return jdbcHandler.executeSql("update shopkeeper_withdraw_record set reason=?,wechatPaymentId=? where id=? limit 1", remark,wechatPaymentId,id);
	}
}
