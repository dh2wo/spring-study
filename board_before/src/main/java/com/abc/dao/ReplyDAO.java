package com.abc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.abc.vo.Reply;

@Mapper
public interface ReplyDAO {
	
	public int insertReply(Reply r);
	public List<Reply> findReplyByBoardNum(int boardNum);
	public Reply findReplyByReplyNum(int replyNum);
	public int updateReply(Reply r);
	public int deleteReply(int replyNum);
}
