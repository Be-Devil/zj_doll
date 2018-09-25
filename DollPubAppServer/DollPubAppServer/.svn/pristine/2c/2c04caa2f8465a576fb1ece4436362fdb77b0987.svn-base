package com.imlianai.dollpub.app.pvd;
import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Service;
import com.imlianai.dollpub.app.iface.IAppCusRemoteService;
import com.imlianai.dollpub.app.modules.core.user.customer.opt.router.CusOptRecordRouter;
import com.imlianai.rpc.support.common.BaseLogger;

@Service(interfaceClass = IAppCusRemoteService.class)
public class AppCusRemoteServiceImpl implements IAppCusRemoteService {
	
	private final BaseLogger logger = BaseLogger.getLogger(this.getClass());
	
	@Resource
	private CusOptRecordRouter cusOptRecordRouter;
	
	/**
	 * 根据商户id创建对应的操作记录表
	 */
	@Override
	public String createOptRecordTable(int groupId) {
		logger.info("创建组=>" + groupId);
		return cusOptRecordRouter.getTableName(groupId);
	}
}
