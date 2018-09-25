package com.imlianai.zjdoll.app.modules.support.cnf.service;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.cnf.GlobalCnf;
import com.imlianai.zjdoll.app.modules.support.cnf.dao.GlobalCnfDao;
@Service
public class GlobalCnfServiceImpl implements GlobalCnfService {

	private static List<GlobalCnf> cnfs = null;

	@Resource
	private GlobalCnfDao globalCnfDao;

//	@PostConstruct
	@Override
	public void init() {
		cnfs = globalCnfDao.getAll();
	}

	@Override
	public List<GlobalCnf> getAll() {
		return cnfs;
	}
}
