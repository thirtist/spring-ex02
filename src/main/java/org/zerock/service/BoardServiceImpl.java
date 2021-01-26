package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;

//@Component
@Service
@AllArgsConstructor //생성자를 이용해서 필드 mapper에 주입함 이거하나로
public class BoardServiceImpl implements BoardService {
	
	private BoardMapper mapper;
	
	/* 생성자를 사용해서 주입 --위의 @AllArgsConstructor로 대체됨
	//@Autowired  //버전에따라서 @Autowired를 꼭 붙여야하는 버전도 있음
	//따라서 @Autowired를 안넣어도 되는버전이면 위의 @AllArgsConstructor만으로끝남
	public BoardServiceImpl(BoardMapper mapper) {
		this.mapper = mapper;
	}
	*/
	
	@Override
	public void register(BoardVO board) {
		mapper.insertSelectKey(board);
	}
	
	/*
	@Override
	public List<BoardVO> getList() {
		return mapper.getList();
	}
	*/
	
	@Override
	public List<BoardVO> getList(Criteria cri) {
		return mapper.getListWithPaging(cri);
	}
	
	@Override
	public BoardVO get(Long bno) {
		return mapper.read(bno);
	}
	
	@Override
	public boolean remove(Long bno) {
		return mapper.delete(bno) == 1;
	}
	
	@Override
	public boolean modify(BoardVO board) {
		return mapper.update(board) ==1;
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
	
}
