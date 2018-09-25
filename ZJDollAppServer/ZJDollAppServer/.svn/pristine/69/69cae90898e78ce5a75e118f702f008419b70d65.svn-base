package com.imlianai.zjdoll.app.modules.support.feedback.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.feedback.FeedBackStatus;
import com.imlianai.zjdoll.domain.feedback.Feedback;
import com.imlianai.zjdoll.domain.feedback.FeedbackNormal;
import com.imlianai.zjdoll.domain.feedback.FeedbackReply;
import com.imlianai.rpc.support.common.page.PageInfo;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.rpc.support.utils.StringUtil;

@Repository
public class FeedbackDAOImpl implements FeedbackDAO {

	@Resource
	private JdbcHandler jdbcHandler;

	private String addFeedback = "insert into feedback(uid,content,fileName,phone,time) values(?,?,?,?,now()) ";
	@Override
	public int addFeedback(Feedback feedback) {
		return jdbcHandler.executeSql(addFeedback, feedback.getUid(), feedback.getContent(),feedback.getPhone() ,feedback.getImgurl());
	}

	private String addFeedbackReply = "insert into feedback_reply(feedbackId,replyContent,replyType,replyTime,replyImgurl,uid) values(?,?,?,now(),?,?)";
	@Override
	public int addFeedbackReply(long feedbackId, String replyConetent, String replyImgurl, int replyType, long uid) {
		return jdbcHandler.executeSql(addFeedbackReply, feedbackId, replyConetent, replyType, replyImgurl, uid);
	}

	private String updateFeedbackStatus = "update feedback set status=? where id=?";
	@Override
	public int updateFeedbackStatus(long id, FeedBackStatus fbs) {
		return jdbcHandler.executeSql(updateFeedbackStatus, fbs.type, id);
	}

	private String updateFeedbackSatisfy = "update feedback_reply set satisfy=? where id=?";
	@Override
	public int updateFeedbackSatisfy(long id, int isSatisfy) {
		return jdbcHandler.executeSql(updateFeedbackSatisfy, isSatisfy, id);
	}

	private String getFeedbacks = "select * from feedback where uid=? order by id desc limit ?,?";
	@Override
	public List<Feedback> getFeedbacks(long uid, int pageIndex) {
		PageInfo page = new PageInfo(pageIndex, 20);
		return jdbcHandler.queryBySql(Feedback.class, getFeedbacks, uid,
				page.getStart(), page.getOffset());
	}

	private String getFeedbackReplys = "select * from feedback_reply where feedbackId=? order by replyTime asc limit ?,?";
	@Override
	public List<FeedbackReply> getFeedbackReplys(long feedbackId, int pageIndex) {
		PageInfo page = new PageInfo(pageIndex, 20);
		return jdbcHandler.queryBySql(FeedbackReply.class, getFeedbackReplys, feedbackId, page.getStart(),
				page.getOffset());
	}

	private String getFeedbackById = "select * from feedback where id=?";
	@Override
	public Feedback getFeedbackById(long id) {
		return jdbcHandler.queryOneBySql(Feedback.class, getFeedbackById, id);
	}

	private String getFeedbackNormals = "select * from feedback_normal ";
	@Override
	public List<FeedbackNormal> getFeedbackNormals() {
		return jdbcHandler.queryBySql(FeedbackNormal.class, getFeedbackNormals);
	}

	private String getByOsTypeSql = "select * from feedback_normal where osType=?";
	@Override
	public List<FeedbackNormal> getByOsType(int osType) {
		return jdbcHandler.queryBySql(FeedbackNormal.class, getByOsTypeSql, osType);
	}
	@Override
	public List<Feedback> getFeedbacks(int pageIndex, int pageSize) {
		return jdbcHandler.queryBySql(Feedback.class, "select * from feedback where status=0 order by id asc limit ?,?", (pageIndex-1)*pageSize,pageSize);
	}
	
	@Override
	public int updateFeedbackStatus(List<Integer> ids, FeedBackStatus fbs) {
		StringBuilder sbBuilder=new StringBuilder("update feedback set status=? where id in (");
		if (!StringUtil.isNullOrEmpty(ids)) {
			for (int i = 0; i < ids.size(); i++) {
				if (i>0) {
					sbBuilder.append(",");
				}
				sbBuilder.append(ids.get(i));
			}
			sbBuilder.append(")");
			return jdbcHandler.executeSql(sbBuilder.toString(), fbs.type);
		}
		return 0;
	}

}
