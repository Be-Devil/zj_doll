package com.imlianai.zjdoll.app.modules.core.trade.catalog.enm;

/**
 * 周月卡奖励
 * @author cls
 *
 */
public enum NewChargeCardRewardType {

	WEEKCARD_TYPE(2, "周卡", 20),
	
	MONTHCARD_TYPE(3, "月卡", 33);
	
	private int type;
	
	private String desc;
	
	private int coin;

	private NewChargeCardRewardType(int type, String desc, int coin) {
		this.type = type;
		this.desc = desc;
		this.coin = coin;
	}
	
	public static int getCoin(int type) {
		for(NewChargeCardRewardType rewardType : NewChargeCardRewardType.values()) {
			if(rewardType.getType() == type) {
				return rewardType.getCoin();
			}
		}
		return 0;
	}
	
	public static NewChargeCardRewardType getRewardType(int type) {
		for(NewChargeCardRewardType rewardType : NewChargeCardRewardType.values()) {
			if(rewardType.getType() == type) {
				return rewardType;
			}
		}
		return null;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}
}
