package org.zerock.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;
import org.zerock.service.FileUpService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*") // 예전 list.do -> List.handler가 일하라고 했던 역할
//컨트롤러에게 요청이 오기전에 DispatcherServlet이 먼저 요청받음(web.xml에 나와있음)
@AllArgsConstructor
@Log4j
public class BoardController {
	private BoardService service;
	private FileUpService fileUpSvc;
	// 211 page 표
/*	
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@GetMapping("/list")
	// handler 메소드의 return type이 void인 경우
	// 요청경로가 view(jsp)의 이름이 됨
	// 이 메소드는 결국 (/board/list)이므로 /views/board/list.jsp파일을 읽게됨
	// spring이 제공해주는 model클래스에 정보를 담아서 3명이 주고받음
	public void list(Model model) {
		log.info("*************list**************");
		List<BoardVO> list = service.getList();
		model.addAttribute("list", list);
	}
*/
	@GetMapping("/list")
	public void list(@ModelAttribute("cri") Criteria cri, Model model) {
		List<BoardVO> list = service.getList(cri);

		int total = service.getTotal(cri);
		
		PageDTO dto = new PageDTO(cri, total);
		
		model.addAttribute("list",list);
		model.addAttribute("pageMaker",dto);
	}
	
	@GetMapping("/register")
	public void register(@ModelAttribute("cri") Criteria cri) {
	}	
	
	@PostMapping("/register")
	public String register(BoardVO board, MultipartFile file, RedirectAttributes rttr) {
		
		/* 위에 파라미터로 BoardVO board를 넣은것만으로도 아래일들을 알아서해줌
		BoardVO board = new BoardVO();
		board.setTitle(request.getParameter("title"));
		board.setContent(request.getParameter("content"));
		board.setWriter(reqeust.getParameter("writer"));
		*/
		board.setFilename("");
		service.register(board);
		
		if(file != null) {
			board.setFilename(board.getBno() + "_" + file.getOriginalFilename());
			service.modify(board);
			/* fileUpSvc.write(file,board.getFilename()); */
			try {
				fileUpSvc.transfer(file,board.getFilename());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		rttr.addFlashAttribute("result", board.getBno());
		rttr.addFlashAttribute("message",board.getBno() + "번 글이 등록되었습니다.");
		
		return "redirect:/board/list";
	}
	
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		
		/* 예전코드 (스프링 없이)
		String boardNum = request.getParameter("num");
		int num = Integer.parseInt(boardNum);
		BoardVO vo = service.get((long) num);
		request.setAttribute("board", vo);
		request.getRequestDispatcher(".jsp").forward();
		*/
		
		log.info("get method - bno:"+bno);
		BoardVO vo = service.get(bno);
		model.addAttribute("board", vo);
		//model.addAttribute("cri", cri);
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO board, Criteria cri, RedirectAttributes rttr, HttpServletRequest req) {
		
		/* 예전코드 (스프링 없이)
		BoardVO board = new BoardVO();
		board.setBno(request.getParameter("bno"));//일단long변환등은생략
		board.setTitle(request.getParameter("title"));
		board.setContent(request.getParameter("content"));
		 */
		if (service.modify(board)) {
			rttr.addFlashAttribute("result","success");
			rttr.addFlashAttribute("message",board.getBno() + "번 글이 수정되었습니다.");
						
			//rttr.addAttribute("a","b");
			//이렇게하면 쿼리스트링에 붙어서 redirect되게됨
		}
		log.info("#######################@$@#$@$@#$@$###################");
		log.info(req.getParameter("keyword"));
		log.info(req.getParameterValues("keyword").length);
		log.info(req.getParameter("type"));
		log.info(req.getParameterValues("type").length);
		log.info(cri);
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
	
		return "redirect:/board/list";
	}
	

	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, Criteria cri, RedirectAttributes rttr) {
		
		if (service.remove(bno)) {
			rttr.addFlashAttribute("result","success");
			rttr.addFlashAttribute("message",bno + "번 글이 삭제되었습니다.");
		}
		
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/board/list";
	}
	
	/*
	@GetMapping("/modify")
	public void modify(Long bno, Model model) {
		BoardVO vo = service.get(bno);
		model.addAttribute("board",vo);
	}
	*/
	
}
