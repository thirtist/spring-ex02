package org.zerock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;
import org.zerock.domain.Rest1;
import org.zerock.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import oracle.net.ano.Service;

@RestController
@RequestMapping("/replies/")
@Log4j
@AllArgsConstructor
public class ReplyController {
	
	private ReplyService service;
	
	@PostMapping(value="/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	//consumes가 JSON이므로 header에 content-type이 json으로 되어있어야지만 동작하게됨 (postman에서 body(raw)로 보낼때 header쪽에 확인)
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
		log.info("vo: " + vo);
		
		int insertCount = service.register(vo);
		
		log.info("count: " + insertCount);
		
		if (insertCount == 1) {
			return new ResponseEntity<>("success",HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping(value = "/pages/{bno}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ReplyVO>> getList(
		@PathVariable("page") int page,
		@PathVariable("bno") Long bno) {

		Criteria cri = new Criteria(page,10);
		
		
		List<ReplyVO> list = service.getList(cri, bno);
		log.info(list);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	@GetMapping(value="/{rno}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno) {
		ReplyVO vo = service.get(rno);
		
		log.info(vo);
		
		return new ResponseEntity<ReplyVO>(vo, HttpStatus.OK);
	}
	
	//@RequestMapping(method = RequestMethod.DELETE, value="/{rno}", produces = MediaType.TEXT_PLAIN_VALUE)
	@DeleteMapping(value="/{rno}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
		
		int cnt = service.remove(rno);
		
		log.info(cnt);
		
		if (cnt == 1) {
			return new ResponseEntity<>("success", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
	}
	
	@RequestMapping(value = "/{rno}",
			method = {RequestMethod.PUT, RequestMethod.PATCH},
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable Long rno) {
		
		vo.setRno(rno);
		
		int cnt = service.modify(vo);
		
		log.info(cnt);
		
		if (cnt ==1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
}
