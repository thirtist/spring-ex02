package org.zerock.service;

import java.util.List;

import org.zerock.domain.CommentVO;

public interface CommentService {

	public void register(CommentVO comment);

	public List<CommentVO> getList();

	public CommentVO get(Long cno);

	public boolean remove(Long cno);

	public boolean modify(CommentVO comment);
	
}
