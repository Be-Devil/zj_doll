package com.imlianai.dollpub.app.modules.publics.kws.mr;
import java.util.List;
public interface Matcher {

	/**
	 * 对目标串执行匹配
	 * 
	 * @param target
	 *            目标串
	 * @return 匹配结果集
	 */
	public List<Result> match(String target);

	/**
	 * 对目标串执行一次匹配
	 * 
	 * @param target
	 *            目标串
	 * @return 匹配结果
	 */
	public Result matchOnce(String target);

	/**
	 * 掩盖目标串中的关键字（使用预设字符）
	 * 
	 * @param target
	 * @return
	 */
	public String cover(String target);

	/**
	 * 掩盖目标串中的关键字
	 * 
	 * @param target
	 *            目标串
	 * @param cover
	 *            掩盖字符
	 * @return
	 */
	public String cover(String target, char cover);

	/**
	 * 检查目标串是否含有关键字
	 * 
	 * @param target
	 *            目标串
	 * @return
	 */
	public boolean test(String target);

}
