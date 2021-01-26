package org.zerock.controller;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@WebAppConfiguration //dispatcherServlet도 일하게함
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
//컨트롤러는 servlet-context.xml에서 bean이 만들어지니 추가해야함
@Log4j
public class BoardControllerTests {
	
	@Setter(onMethod_ = @Autowired)
	private WebApplicationContext ctx; //어플리케이션도 필요함(?)
	
	private MockMvc mockMvc; //서버를 실행시키지 않고도 요청을 날릴 수 있는 클래스
	@Before //아래 메소드를 다른메소드보다 먼저실행시키는 어노테이션(junit꺼)
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	} //요렇게 webApplicationContext를 넣고 빌더로 만듦
	
	@Setter(onMethod_ =@Autowired)
	private BoardMapper mapper;
	
	@Test
	public void testExist() {
		assertNotNull(ctx);
		assertNotNull(mockMvc);
	}
	
	@Test
	public void testList() throws Exception {
		
//		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/board/list"));
		//dispatcherServlet에게 서버없이 일시키기
		//브라우저로 주소입력하면 만들어지는 헤더등의 리퀘스트를 만들어서넣어줘야함
//		MvcResult rs = result.andReturn();
//		ModelAndView mv = rs.getModelAndView();
//		log.info(mv.getView());
//		log.info(mv.getModel().get("list"));
		
		//위에걸 한줄로
		Object o = mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
		.andReturn()
		.getModelAndView()
		.getModel()
		.get("list");
		
		assertNotNull(o);
		assertTrue(o instanceof List);
		assertNotEquals(((List)o).size(), 0);
	}
	
	@Test
	public void testRegister() throws Exception {
		int before = mapper.getList().size();
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
				.param("title", "테스트 새글 제목")
				.param("content", "테스트 새글 내용")
				.param("writer", "user00"))
			.andReturn();
		
		int after = mapper.getList().size();
		
		assertEquals(before +1 , after);
		
		ModelAndView mv = result.getModelAndView();
		FlashMap map = result.getFlashMap();
		
		assertEquals("redirect:/board/list", mv.getViewName());
		assertNotNull(map.get("result"));
		
		log.info(map.get("result") + "*************************");
	}
	
	@Test
	public void testGet() throws Exception {
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/board/get")
				.param("bno", "1"))
				.andReturn();
		String viewName = result.getModelAndView().getViewName();
		Map<String, Object> modelMap = result.getModelAndView().getModelMap();
		
		assertEquals("board/get", viewName);
		assertNotNull(modelMap.get("board"));
		assertEquals(new Long(1), ( (BoardVO) modelMap.get("board")).getBno());		
	}
	
	@Test
	public void testModify() throws Exception {
		BoardVO board = new BoardVO();
		board.setContent("새 게시물");
		board.setTitle("새 제목");
		board.setWriter("user00");
		
		mapper.insertSelectKey(board);
		
		Long key = board.getBno();
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")				
				.param("bno", key +"")
				.param("title", "수정된게시물111")
				.param("content", "수정된본문1111")
				.param("writer", "user00"))
				.andReturn();

		BoardVO mod = mapper.read(key);
		assertEquals("수정된게시물111",mod.getTitle());
		assertEquals("수정된본문1111",mod.getContent());
		
		FlashMap map = result.getFlashMap();
		assertEquals("success", map.get("result"));
		
		String viewName = result.getModelAndView().getViewName();
		assertEquals("redirect:/board/list", viewName);
	}
	
	@Test
	public void testRemove() throws Exception {
		BoardVO board = new BoardVO();
		board.setContent("새 게시물");
		board.setTitle("새 제목");
		board.setWriter("user00");
		
		mapper.insertSelectKey(board);
		
		Long key = board.getBno();

		int before = mapper.getList().size();
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")				
				.param("bno", key +""))
				.andReturn();
		
		int after = mapper.getList().size();
		
		assertEquals(before -1 , after);
		
		String viewName = result.getModelAndView().getViewName();
		assertEquals("redirect:/board/list", viewName);
		
		assertEquals("success", result.getFlashMap().get("result"));
	}
	
	@Test
	public void testListPaging() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/board/list")
									.param("pageNum", "2")
									.param("amount", "10"))
								.andReturn();
		Map<String, Object> model = result.getModelAndView().getModel();
		List<BoardVO> list = (List<BoardVO>) model.get("list");
		assertEquals(10, list.size());
	}
	
}

