package com.imlianai.zjdoll.app.modules.support.busowner.enm;

/**
 * 奖励比例
 * @author cls
 *
 */
public enum RewardRatioEnm {
	
	FIRST_RATIO(1, 0.08),
	SECOND_RATIO(2, 0.05),
	THIRD_RATIO(3, 0.03);

	private int ranking;
	
	private double ratio;

	private RewardRatioEnm(int ranking, double ratio) {
		this.ranking = ranking;
		this.ratio = ratio;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	
	public static RewardRatioEnm getRewardRatioEnm(int ranking) {
		for(RewardRatioEnm rewardRatioEnm : RewardRatioEnm.values()) {
			if(rewardRatioEnm.getRanking() == ranking) {
				return rewardRatioEnm;
			}
		}
		return null;
	}
}
