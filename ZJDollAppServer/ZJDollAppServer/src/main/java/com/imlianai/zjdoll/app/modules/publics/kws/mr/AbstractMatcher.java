package com.imlianai.zjdoll.app.modules.publics.kws.mr;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractMatcher implements Matcher {

	private char defaultCover = '*';

	public void setDefaultCover(char defaultCover) {
		this.defaultCover = defaultCover;
	}

	protected Collection<String> loadKeywords() {
		Set<String> keywords = new HashSet<String>();
		return keywords;
	}

	@Override
	public String cover(String target) {
		return cover(target, defaultCover);
	}

	public abstract void init();
}
