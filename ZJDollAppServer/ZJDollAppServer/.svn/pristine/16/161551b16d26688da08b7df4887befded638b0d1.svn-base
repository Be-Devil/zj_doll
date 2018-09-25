package com.imlianai.zjdoll.app.modules.support.feedback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.feedback.FeedBackStatus;
import com.imlianai.zjdoll.domain.feedback.Feedback;
import com.imlianai.zjdoll.domain.feedback.FeedbackNormal;
import com.imlianai.zjdoll.domain.feedback.FeedbackReply;
import com.imlianai.zjdoll.app.modules.support.feedback.dao.FeedbackDAO;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackDAO feedbackDAO;

	@Override
	public int addFeedback(Feedback feedback) {
		return feedbackDAO.addFeedback(feedback);
	}

	@Override
	public int addFeedbackReply(long feedbackId, String replyConetent,
			String filename, int replyType, long uid) {
		return feedbackDAO.addFeedbackReply(feedbackId, replyConetent,
				filename, replyType, uid);
	}

	@Override
	public int updateFeedbackStatus(long id, FeedBackStatus fbs) {
		return feedbackDAO.updateFeedbackStatus(id, fbs);
	}
	
	@Override
	public int updateFeedbackStatus(List<Integer> ids, FeedBackStatus fbs){
		return feedbackDAO.updateFeedbackStatus(ids, fbs);
	}
	@Override
	public int updateFeedbackSatisfy(long id, int isSatisfy) {
		return feedbackDAO.updateFeedbackSatisfy(id, isSatisfy);
	}

	@Override
	public List<Feedback> getFeedbacks(long uid, int pageIndex) {
		return feedbackDAO.getFeedbacks(uid, pageIndex);
	}

	@Override
	public List<FeedbackReply> getFeedbackReplys(long feedbackId, int pageIndex) {
		return feedbackDAO.getFeedbackReplys(feedbackId, pageIndex);
	}

	@Override
	public Feedback getFeedbackById(long id) {
		return feedbackDAO.getFeedbackById(id);
	}

	@Override
	public List<FeedbackNormal> getFeedbackNormals() {
		return feedbackDAO.getFeedbackNormals();
	}

	@Override
	public List<FeedbackNormal> getByOsType(int osType) {
		return feedbackDAO.getByOsType(osType);
	}

	@Override
	public List<Feedback> getFeedbacks(int pageIndex, int pageSize) {
		return feedbackDAO.getFeedbacks(pageIndex, pageSize);
	}
}
