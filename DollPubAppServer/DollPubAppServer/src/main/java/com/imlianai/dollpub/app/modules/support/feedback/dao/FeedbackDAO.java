package com.imlianai.dollpub.app.modules.support.feedback.dao;

import java.util.List;

import com.imlianai.dollpub.domain.feedback.FeedBackStatus;
import com.imlianai.dollpub.domain.feedback.Feedback;
import com.imlianai.dollpub.domain.feedback.FeedbackNormal;
import com.imlianai.dollpub.domain.feedback.FeedbackReply;

/**
 * 用户反馈
 * 
 * @author Max
 * 
 */
public interface FeedbackDAO {

	/**
	 * 增加反馈
	 * 
	 * @param feedback
	 * @param headMap
	 * @return
	 */
	public int addFeedback(Feedback feedback);
	/**
	 * 增加回复
	 * 
	 * @param feedbackId
	 * @param replyConetent
	 * @param filename
	 * @param replyType
	 * @param uid
	 * @return
	 */
	public int addFeedbackReply(long feedbackId, String replyConetent,
			String filename, int replyType, long uid);
	/**
	 * 更新未回复状态
	 * 
	 * @param id
	 * @return
	 */
	public int updateFeedbackStatus(long id, FeedBackStatus fbs);
	
	/**
	 * 更新未回复状态
	 * 
	 * @param id
	 * @return
	 */
	public int updateFeedbackStatus(List<Integer> ids, FeedBackStatus fbs);
	
	/**
	 * 更新是否满意
	 * 
	 * @param id
	 * @param isSatisfy
	 * @return
	 */
	public int updateFeedbackSatisfy(long id, int isSatisfy);
	/**
	 * 分页获取反馈
	 * 
	 * @param uid
	 * @param pageIndex
	 * @return
	 */
	public List<Feedback> getFeedbacks(long uid, int pageIndex);
	
	/**
	 * 分页获取反馈
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Feedback> getFeedbacks(int pageIndex, int pageSize) ;
	/**
	 * 分页获取回复列表
	 * 
	 * @param feedbackId
	 * @param pageIndex
	 * @return
	 */
	public List<FeedbackReply> getFeedbackReplys(long feedbackId, int pageIndex);
	/**
	 * 通过id获取一个反馈
	 * 
	 * @param id
	 * @return
	 */
	public Feedback getFeedbackById(long id);

	/**
	 * 获取常见问题列表
	 * 
	 * @return
	 */
	public List<FeedbackNormal> getFeedbackNormals();
	/**
	 * 根据osType获取不同类型FAQ
	 * 
	 * @param osType
	 * @return
	 */
	public List<FeedbackNormal> getByOsType(int osType);
}
