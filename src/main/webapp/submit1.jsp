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
	$("#outside").click(function(){
		$("#my-form").submit();   /*form바깥쪽 버튼으로 submit하는법 */
	});
	
	$("#inside").click(function(e){
		e.preventDefault();    /*원래 하는일을 안함=>button하는일이 submit인데 안함*/
		console.log("인싸버튼클릭")/*그래서 submit하기전에하고싶은일을넣을수있음 */
		$("#my-form").submit(); /*마지막으로 submit하는거 넣기*/
	});
});
</script>

<title>Insert title here</title>
</head>
<body>
<h1>SUBMIT 예제 페이지</h1>
<h1>name : ${param.name }</h1>
<form id="my-form" action="">
	<input type="text" name="name" value="java"> <br>
	<input type="submit" value="전송"> <br>
	<button id="inside">또 다른 전송</button> 
</form>

<button id="outside">밖에 있는 버튼</button>

</body>
</html>