package com.imlianai.dollpub.app.modules.core.user.phone.code;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.domain.user.UserCode;

@Service
public class UserCodeServiceImpl implements UserCodeService{

	@Resource
	private UserCodeDAO userCodeDAO;

	
	@Override
	public int updateCode(String number, String code) {
		return userCodeDAO.updateCode(0, number, code);
	}
	
	@Override
	public int updateCodeState(String number, String code) {
		return userCodeDAO.updateCodeState(0, number, code);
	}

	@Override
	public int delCode(String number) {
		return userCodeDAO.delCode(0, number);
	}
	

	@Override
	public UserCode getCode(String number) {
		UserCode code = userCodeDAO.getCode(0, number);
		if(code == null)
			code =  new UserCode(number);
		return code;
	}

}
