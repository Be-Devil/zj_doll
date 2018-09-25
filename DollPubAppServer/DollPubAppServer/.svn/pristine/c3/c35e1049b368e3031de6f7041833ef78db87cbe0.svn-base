package com.imlianai.dollpub.app.modules.publics.kws.service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.configs.AppUtils;
import com.imlianai.dollpub.app.modules.publics.kws.dao.KeyWordDao;
import com.imlianai.dollpub.app.modules.publics.kws.mr.Matcher;
import com.imlianai.dollpub.app.modules.publics.kws.mr.Result;
import com.imlianai.dollpub.app.modules.publics.kws.mr.ac.ACMatcher;
import com.imlianai.dollpub.domain.kws.KWType;
@Service
public class KeyWordServiceImpl implements KeyWordService {

	private static volatile Set<String> kwA = null;
	private static volatile Set<String> kwB = null;
	private static volatile Set<String> kwS = null;
	private static Matcher matchA = null;
	private static Matcher matchB = null;
	private static Matcher matchS = null;

	@Resource
	private KeyWordDao keyWordDao;

//	@PostConstruct
	@Override
	public void init() {
		kwA = new HashSet<String>();
		kwB = new HashSet<String>();
		kwS = new HashSet<String>();
		List<String> result = keyWordDao.getAll(KWType.A);
		if (result != null) {
			kwA.addAll(result);
			matchA = new ACMatcher(kwA);
		}
		result = keyWordDao.getAll(KWType.B);
		if (result != null) {
			kwB.addAll(result);
			matchB = new ACMatcher(kwB);
		}
		result = keyWordDao.getAll(KWType.S);
		if (result != null) {
			kwS.addAll(result);
			matchS = new ACMatcher(kwS);
		}
	}

	@Override
	public Result matchA(String target) {
		if (StringUtils.isNotBlank(target) && matchA != null)
			return matchA.matchOnce(AppUtils.removeSymbol(target));
		return null;
	}

	@Override
	public Result matchB(String target) {
		if (StringUtils.isNotBlank(target) && matchB != null)
			return matchB.matchOnce(AppUtils.removeSymbol(target));
		return null;
	}

	@Override
	public Result matchS(String target) {
		if (StringUtils.isNotBlank(target) && matchS != null)
			return matchS.matchOnce(AppUtils.removeSymbol(target));
		return null;
	}
}
