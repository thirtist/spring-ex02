package org.zerock.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Rest1;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/rest3")
@Log4j
public class RestControllerEx3 {
	@RequestMapping("/ex1")
	public String method1(String name) {
		log.info("name:" + name);

		return "spring";
	}

	@RequestMapping("/ex2/{val}")
	public String method2(@PathVariable("val") String value) {
		log.info("method2");
		log.info(value);

		return "method2";
	}

	@RequestMapping("/ex3/{val}") // 이름이 같으면 ()생략가능
	public String method3(@PathVariable String val) {
		log.info("method3");

		return val;
	}

	@RequestMapping("/ex4/{val}/other/{age}") // 여러개 사용가능
	public String method4(@PathVariable String val, @PathVariable int age) {
		log.info("method4");

		return val + age;
	}

	@RequestMapping("/ex5") // text데이타
	public String method5(@RequestBody String b) {

		log.info(b);

		return "method5";
	}

	@RequestMapping("/ex6") // text데이타
	public String method6(@RequestBody Rest1 body) {

		log.info(body);

		return "method6";
	}

	@RequestMapping(path = "/ex7", consumes = "text/plain") // path = 와 value = 같은역할임
	// consumes에 들어가는 건은 MIME타입(http media type)
	// consumes을 지정하면 body에 들어가는 타입이 저 지정타입일경우에만
	// 이 메소드가 실행됨
	public String method7(@RequestBody String body) {

		log.info(body);

		return "method7";
	}

	@RequestMapping(path = "/ex8", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String method8(@RequestBody String body) {

		log.info(body);

		return "method8";
	}
	
	// consumers는 request header (Content-Type)과 연관있음
	@RequestMapping(path = "/ex9", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
	public String method9(@RequestBody String body) {

		log.info(body);

		return "method9";
	}
}