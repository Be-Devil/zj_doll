package com.imlianai.dollpub.app.modules.publics.kws.mr.ac;
import java.util.Collection;
public interface State {

	/**
	 * 根据输入转跳
	 * 
	 * @param input
	 * @return
	 */
	public State go(char input);

	/**
	 * 失效
	 * 
	 * @return 失效转向状态
	 */
	public State fail();

	/**
	 * 是否为空结果状态
	 * 
	 * @return
	 */
	public boolean isEmpty();

	/**
	 * 设置失败状态转向
	 * 
	 * @param failState
	 */
	public void setFailState(State failState);

	/**
	 * 设置跳转状态
	 * 
	 * @param input
	 * @param state
	 * @return
	 */
	public State setNextState(char input, State state);

	/**
	 * 返回跳转输入集合
	 * 
	 * @return
	 */
	public Collection<Character> getNextInputs();

	/**
	 * 返回跳转状态集合
	 * 
	 * @return
	 */
	public Collection<State> getNextStates();

	/**
	 * 返回最长结果
	 * 
	 * @return
	 */
	public String getLongestResult();

	/**
	 * 返回最短结果
	 * 
	 * @return
	 */
	public String getShortestResult();

	/**
	 * 返回结果集
	 * 
	 * @return
	 */
	public Collection<String> getResults();

	/**
	 * 添加结果集
	 * 
	 * @param results
	 */
	public void addAllResults(Collection<String> results);

	/**
	 * 添加结果
	 * 
	 * @param result
	 */
	public void addResult(String result);

}
