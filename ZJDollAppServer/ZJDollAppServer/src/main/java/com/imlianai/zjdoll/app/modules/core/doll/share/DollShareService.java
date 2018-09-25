package com.imlianai.zjdoll.app.modules.core.doll.share;

import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollSuccessRes;

/**
 * 娃娃分享相关
 * @author tensloveq
 *
 */
public interface DollShareService {

	/**
	 * 获取捉取成功的娃娃分享信息
	 * 
	 * @param uid
	 * @param doll
	 * @param busId
	 * @return
	 */
	public DollSuccessRes getDollSuccessShare(long uid, DollInfo doll, int busId);

}
