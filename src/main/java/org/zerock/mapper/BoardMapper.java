package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	
	public int getTotalCount(Criteria cri);
	// SELECT count(*) FROM tbl_board
	
	/* @Select("select * from tbl_board where bno > 0") */
	public List<BoardVO> getList();
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	public void insert(BoardVO board);
	
	public void insertSelectKey(BoardVO board);
	//1. seq_board의 nextval을 먼저 조회(select)
	//2. 조회된 nextval을 insert에서 사용
	
	public BoardVO read(Long bno);
	
	public int delete(Long bno);
	
	public int update(BoardVO board);
	
	//reply수가 업데이트 될때 변경
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
}
