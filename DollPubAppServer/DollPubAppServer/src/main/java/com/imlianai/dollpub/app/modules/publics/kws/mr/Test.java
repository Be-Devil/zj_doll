package com.imlianai.dollpub.app.modules.publics.kws.mr;
import java.util.Arrays;

import com.imlianai.dollpub.app.modules.publics.kws.mr.ac.ACMatcher;
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("okkkkkkkkkkkkkkk");
		Matcher matcher = new ACMatcher(Arrays.asList("王八蛋", "王八羔子"));
		String target="我和你去上课888     王蛋";
		Result result=matcher.matchOnce(target);
		System.out.println(cover(target, result, '*'));
	}
	
	public static String cover(String target, Result result, char cover) {
		StringBuilder ret = new StringBuilder(target);
		String kw = result.getContent();
		for (int index =result.getIndex(); index <= (result.getIndex()+kw.length())-1; index++)
			ret.setCharAt(index, cover);
		return ret.toString();
	}

}
