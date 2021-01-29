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
			type: "post",
			url: "/controller/replies/new",
			contentType: "application/json",
			data: '{"bno":188, "reply":"새 댓글~", "replyer":"user01"}',
			complete: function(jqXHR, status) {
				console.log("complete");
				console.log(status);
			}
		});
	});
	
	$("#btn-2").click(function(){
		$.ajax({
			type: "post",
			url: "/controller/replies/new",
			contentType: "application/json",
			data: '{"reply":"새 댓글~", "replyer":"user01"}', /*bno가 not null이라서 없으면 실패  */
			complete: function(jqXHR, status) {
				console.log("complete");
				console.log(status);
			}
		});
	});
	
	$("#btn-3").click(function() {
		$.ajax({
			type: "post",
			url: "/controller/replies/new",
			contentType: "application/json",
			data: '{"bno":188,"reply":"새 댓글~","replyer":"user01"}',
			complete: function(jqXHR, status) {
				if (status === "success") {
					console.log("등록 성공");
					console.log(jqXHR.responseText);
				} else if (status === "error") {
					console.log("등록 실패");
				}
			}
		});
	});
	
	$("#btn-4").click(function() {
		$.ajax({
			type: "get",
			url: "/controller/replies/pages/188/1",
			complete: function(jqXHR, status) {
				if (status === "success") {
					console.log(jqXHR.responseText);
				}
			}
		});
	});
	
	$("#btn-5").click(function() {
		$.ajax({
			type: "PUT",
			url: "/controller/replies/49",
			contentType: "application/json",
			data: '{"bno":188,"reply":"수정댓글!!@~~"}',
			complete: function(jqXHR, status) {
				if (status === "success") {
					console.log("수정 성공");
					console.log(jqXHR.responseText);
				} else if (status === "error") {
					console.log("수정 실패");
				}
			}
		});
	});
	
	$("#btn-6").click(function() {
		$.ajax({
			type: "DELETE",
			url: "/controller/replies/49",
			complete: function(jqXHR, status) {
				if (status === "success") {
					console.log("삭제 성공");
					console.log(jqXHR.responseText);
				} else if (status === "error") {
					console.log("삭제 실패");
				}
			}
		});
	});
	
});

</script>

<title>Insert title here</title>
</head>
<body>
<h1>AJAX ex2</h1>
<div>
	<button id="btn-1">댓글 등록 성공</button>

	<button id="btn-2">댓글 등록 실패</button>

	<button id="btn-3">댓글 등록 성공 또는 실패</button>

	<button id="btn-4">댓글 리스트 가져오기</button>
	
	<button id="btn-5">댓글 수정</button>

	<button id="btn-6">댓글 삭제</button>
	
	
	
</div>
</body>
</html>