package org.zerock.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
	
	private Long[] bnoArr = {147L, 144L, 141L, 130L, 129L}; //존재하는것 찾아서 완성하기
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	@Test
	public void testExist() {
		assertNotNull(mapper);
	}
	
	@Test //책에서 insert테스트(개수가 늘어나게끔)
	public void testCreate() {
		//IntStream.rangeClosed(1, 10); //1~10까지의 스트림을 리턴함(배열이나 리스트랑 비슷한개념)
		// 저거랑 람다식으로 i지정하면 앞으로 그냥 i를 쓸 수있네??
		IntStream.rangeClosed(1, 10).forEach(i -> {
			log.warn(i + "," + (i% 5));
			
			ReplyVO vo = new ReplyVO();
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("댓글 테스트"+i);
			vo.setReplyer("replyer"+i);

			mapper.insert(vo);
			});
		
	}
	
	@Test //강사님이한것
	public void testCreate2() {
		ReplyVO vo = new ReplyVO();
		// vo.setRno(rno);
		vo.setBno(188L); // tbl_board 테이블에 있는 값으로 확인하기위해서찾아서넣음
		vo.setReply("댓글 테스트");
		vo.setReplyer("user00");
		
		mapper.insert(vo);
		
		try {
			vo.setBno(131L); //없는 번호는 실패함
			mapper.insert(vo);
			fail();
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testRead() {
		Long rno = 2L;
		
		ReplyVO vo = mapper.read(rno);
		
		assertEquals("댓글 테스트1", vo.getReply());
	}
	
	@Test
	public void testDelete() {
		Long rno = 1L; //있는 리플번호넣기(한번만테스트)
		
		mapper.delete(rno);
	}
	
	@Test
	public void testUpdate() {
		ReplyVO vo = new ReplyVO();
		vo.setRno(12L);
		vo.setReply("수정된 댓글");
		mapper.update(vo);
		
		vo = mapper.read(12L);
		
		assertEquals("수정된 댓글", vo.getReply());
	}
	
	@Test
	public void testList() {
		List<ReplyVO> list = mapper.getListWithPaging(null, 188L);
		assertNotEquals(0, list.size());
	}
}
