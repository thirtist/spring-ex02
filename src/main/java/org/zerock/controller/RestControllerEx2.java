package org.zerock.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Rest1;
import org.zerock.domain.Rest2;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/rest2")
@Log4j
public class RestControllerEx2 {

	@RequestMapping("/ex1")
	public String method1() {
		return "hello";
	}
	
	@RequestMapping("/ex2")
	public Rest1 method2() {
		// 전송, 수신방법이 http (HyperText transper Protocol)
		// 따라서 객체를 전송하려면 에러남
		log.info("method2");
		
		Rest1 r = new Rest1();
		r.setName("donald");
		r.setAge(33);
		return r;
	}
	
	@RequestMapping("/ex3")
	public String method3() {
		// 전송, 수신방법이 http (HyperText transper Protocol)
		// 따라서 이렇게 String으로 객체를 풀어서 넣어줘야함(http에서는 객체를바로전달못함!위에ex2)
		// JSON방법을 많이 사용함 (JavaScript Object Notation)
		log.info("method3");
		
		Rest1 r = new Rest1();
		r.setName("donald");
		r.setAge(33);
		
//		String res = "이름:" + r.getName() + "," + "나이" + r.getAge(); 이건그냥 편의대로 작성한것
//		String res = "{name:\"" + r.getName() + "\", age:" + r.getAge() + "}"; //JS방법으로 수동으로나타내본것
//		=> 결과는  {name:"donald", age:33}
		String res = "{\"name\":\"" + r.getName() + "\", \"age\":" + r.getAge() + "}"; //JS방법으로 수동으로나타내본것
//		=> 결과 {"name":"donald", "age":33}
		return res;
	}
	
	@RequestMapping("/ex4")
	public Rest1 method4() {
		// 전송, 수신방법이 http (HyperText transper Protocol)
		// pom.xml에서 api추가후 바로객체로 전달가능(Jackson data-bind / Jackson Dataformat XML / gson)
		log.info("method4");
		
		Rest1 r = new Rest1();
		r.setName("donald");
		r.setAge(33);
		
		return r;
// localhost:8080/controller/rest2/ex4.json  =>  {"name": "donald", "age": 33} //JSON으로 리턴됨
// 끝에 .json을 붙이면 json으로 넘어감 (안붙이면 xml형식으로-gson때문에?)
	}
	
	@RequestMapping("/ex5")
	public Rest2 method5() {
		Rest2 r2 = new Rest2();
		r2.setAddress("seoul");
		
		Rest1 r1 = new Rest1();
		r1.setName("jeju");
		r1.setAge(100);
		r1.setVote(true);
		
		r2.setRest1(r1);
		
		return r2;
	}
	
	@RequestMapping("/ex6")
	public String[] method6() {
		String[] arr = {"java", "json", "xml"};
		
		return arr;
	}
	
	@RequestMapping("/ex7")
	public List<String> method7() {
		List<String> list = new ArrayList<>();
		list.add("hello");
		list.add("world");
		list.add("spring");
		
		return list;
	}
	
	@RequestMapping("/ex8")
	public Map<String, String> method8() {
		Map<String, String> map = new HashMap<>();
		map.put("java", "script");
		map.put("hello", "world");
		map.put("spring", "boot");
		return map;
	}
	
	@RequestMapping("/ex9")
	public List<Rest1> method9() {
		List<Rest1> list = new ArrayList<>();
		
		Rest1 r1 = new Rest1();
		r1.setName("trump");
		r1.setAge(33);
		r1.setVote(true);
		
		list.add(r1);
		
		Rest1 r2 = new Rest1();
		r2.setName("donald");
		r2.setAge(22);
		r2.setVote(false);
		
		list.add(r2);
		return list;
	}
	
	// 특정 status code로 응답할때
	@RequestMapping("/ex10")
	public ResponseEntity<String> method10() {
		return ResponseEntity.status(200).body("hello");
	}
	
	@RequestMapping("/ex11")
	public ResponseEntity<String> method11(int num) {
		if (num > 0) {
			return ResponseEntity.status(200).body("spring");
		} else {
			return ResponseEntity.status(404).body("");
		}
	}
}