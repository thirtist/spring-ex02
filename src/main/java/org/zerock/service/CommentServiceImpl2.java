package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.CommentVO;
import org.zerock.mapper.CommentMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Service
@AllArgsConstructor
public class CommentServiceImpl2 implements CommentService{
	
	//@Setter(onMethod_ = @Autowired)
	private CommentMapper commentMapper;
	
	@Override
	public CommentVO get(Long cno) {
		return commentMapper.read(cno);
	}
	@Override
	public List<CommentVO> getList() {
		return commentMapper.getList();
	}
	@Override
	public boolean modify(CommentVO comment) {
		return commentMapper.update(comment) == 1;
	}
	@Override
	public void register(CommentVO comment) {
		commentMapper.insertSelectKey(comment);
	}
	@Override
	public boolean remove(Long cno) {
		return commentMapper.remove(cno) == 1;
	}
}
