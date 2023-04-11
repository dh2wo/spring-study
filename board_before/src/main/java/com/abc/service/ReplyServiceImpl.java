package com.abc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.dao.ReplyDAO;
import com.abc.vo.Reply;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Autowired
	private ReplyDAO rDAO;
	
	@Override
	public int insertReply(Reply r) {
		return rDAO.insertReply(r);
	}

	@Override
	public List<Reply> getAllReply(int boardNum) {
		return rDAO.findReplyByBoardNum(boardNum);
	}

	@Override
	public Reply getOneReply(int replyNum) {
		return rDAO.findReplyByReplyNum(replyNum);
	}

	@Override
	public int updateReply(Reply r) {
		return rDAO.updateReply(r);
	}

	@Override
	public int deleteReply(int replyNum) {
		return rDAO.deleteReply(replyNum);
	}
}
