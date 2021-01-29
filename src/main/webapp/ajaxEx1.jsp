<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
  href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
  src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
  src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
  src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/a076d05399.js"></script>

<script>
$(document).ready(function(){
	$("#btn-1").click(function(){
		$.ajax({
			method: "POST",
			url: "/controller/replies/new",
			data: '{"bno":188, "reply":"new reply","replyer":"user00"}', /*postman에서 body(raw)에 해당  */
			contentType: "application/json"   /*postman에서 header에서 content-Type에해당  */
		}); 		/* api에선 Jquary.ajax()라고 되어있는데 Jquary가 $임 => $.ajax() 제이쿼리에서 이함수를사용*/
					/* 파라미터로 url,settings 또는 settings 를 받음-api사전활용(jquary사이트)  */
					/* settings는 PlainObject 의 형식을 취함 => {} / 프로퍼티 : 밸류형식으로 넣음  */
	});
	
	$("#btn-2").click(function(){
		$.ajax({
			method: "GET",
			url: "/controller/replies/pages/188/1"
		});
	});
	
	$("#btn-3").click(function(){
		$.ajax({
			method: "DELETE",
			url: "/controller/replies/48"
		});
	});
	
	$("#btn-4").click(function(){
		$.ajax({
			method: "PUT",
			url: "/controller/replies/14",
			data: '{"bno":188, "reply":"modify! reply"}',
			contentType: "application/json"
		});
	});
	
	$("#btn-5").click(function(){
		$.ajax({
			method: "GET",
			url: "/controller/replies/14",
		});
	});
					
});
</script>

<title>Insert title here</title>
</head>
<body>
<h1>AJAX ex1</h1>
<button id="btn-1">새 댓글</button>

<button id="btn-2">댓글 목록</button>

<button id="btn-3">댓글 삭제</button>

<button id="btn-4">댓글 수정</button>

<button id="btn-5">댓글 하나조회</button>

</body>
</html>