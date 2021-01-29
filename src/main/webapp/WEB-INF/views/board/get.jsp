<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="u"  tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>

<script>
var appRoot = '${root}';
var bno = ${board.bno};
</script>

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

<script src="${root }/resources/js/reply.js"></script>

<script>
// console.log(replyService.name);
// replyService.add("my reply");
/*
replyService.add({
		bno:188, 
		reply:"new reply 댓글", 
		replyer:"tester"
	}, function(result){
		console.log(result);
	}, function(err){
		console.log(err);
	});
*/
/*
replyService.getList(
		{bno:188, page:1},
		function(data){
			console.log(data);
		},
		function(){
			console.log("error");
		});
*/
/*
replyService.remove(51, function(data) {
	console.log(data);
}, function(err) {
	console.log(err);
})
*/
/*
replyService.update({rno:52, reply:'수정된본문'},
		function(data) {
			console.log(data);
		},
		function(err){
			console.log(err);
		})
*/
/*
replyService.get(52,
		function(data) {
			console.log(data)
		})
*/
</script>

<script>

$(document).ready(function(){
	//날짜형식
	function dateString(date){
		var d = new Date(date);
		return d.toISOString().split("T")[0];
		// date형식에서 .toISOString을하면 T가 중간에 끼어있음
		//.split("T")로하면 배열로 쪼개서 들어감 -> 그중에 앞인 0을 선택함
	}
	
	//
	function showList(){
	
		replyService.getList({bno:bno},function(list){
			console.log(list);
			
			var replyUL = $("#reply-ul");
			replyUL.empty();  /*#reply-ul 아래의 기존 태그들을 비움 */
			for (var i = 0; i < list.length; i++) {
				var replyLI = '<li class="media" data-rno="'
				+ list[i].rno + '"><div class="media-body"><h5>'
				+ list[i].replyer + '<small class="float-right">'
				+ dateString(list[i].replyDate) + "</small></h5>"
				+ list[i].reply + "<hr></div></li>";
				
				replyUL.append(replyLI);
			}
		});
	
	};
	
	// 새 댓글 버튼 클릭 이벤트 처리
	$("#new-reply-button").click(function(){
		console.log("new reply button clicked....")
		$("#new-reply-modal").modal("show")
	});
	
	// 새 댓글 등록 버튼 클릭 이벤트 처리
	$("#reply-submit-button").click(function() {
		
		// input에서 value 가져와서 변수에 저장
		var reply = $("#reply-input").val();
		var replyer = $("#replyer-input").val();
		
		// ajax 요청을 위한 데이터 만들기
		var data = {bno: bno, reply:reply , replyer:replyer};
		
		
		replyService.add(data,
				function() {
					// 성공했을때 댓글 목록 가져오기 실행
					
					showList();
					alert("댓글 등록에 성공하였습니다.");
				},
				function() {
					alert("댓글 등록에 실패하였습니다.");
				});
		
		// 모달창 닫기
		$("#new-reply-modal").modal("hide");
		// 모달창 내의 input 요소들 value를 초기화
		$("#new-reply-modal input").val("");

	});
	
	// reply-ul (작성한댓글)클릭 이벤트 처리 - 수정,삭제
	// on(events [,selector]-자손을선택 [,data]-여기선생략, handler)
	$("#reply-ul").on("click","li", function(){
		//console.log("reply-ul clicked......");
		console.log($(this).attr("data-rno"));
		
		//하나의 댓글 읽어오기
		var rno = $(this).attr("data-rno");
		replyService.get(rno, function(data){       //reply.js에 있는 get을 사용
			$("#rno-input2").val(rno); //아래 수정버튼 이벤트 처리에 넘겨주기 위해서 만듦
			$("#reply-input2").val(data.reply);
			$("#replyer-input2").val(data.replyer);
			$("#modify-reply-modal").modal("show"); //성공시modal창 띄우기		
		});
		
	});
	
	//수정 버튼 이벤트 처리
	$("#reply-modify-button").click(function(){
		
		var rno = $("#rno-input2").val(); // 위에 모달창에서 쓴걸 받음
		var reply = $("#reply-input2").val();
		var data = {rno: rno, reply: reply };
		
		replyService.update(data, function(){
			alert("댓글을 수정하였습니다.");
			$("#modify-reply-modal").modal('hide');
			showList();
		});
		
	});
	
	//삭제 버튼 이벤트 처리
	$("#reply-delete-button").click(function(){
		
		var rno = $("#rno-input2").val(); // 위에 모달창에서 쓴걸 받음
		replyService.remove(rno, function(){
			alert("댓글을 삭제하였습니다.");
			$("#modify-reply-modal").modal('hide');
			showList();
		});
		
	});
	
	
	// 댓글 목록 가져오기 실행
	showList();
	
});

</script>

<title>Insert title here</title>
</head>
<body>

	<u:navbar></u:navbar>	

	<div class="container-sm">
		<div class="row">
			<div class="col-12 col-lg-6 offset-lg-3">
				<h1>게시물 보기</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-12 col-lg-6 offset-lg-3">

					<div class="form-group">
						<label for="input3">번호</label> <input readonly value='<c:out value="${board.bno }"/>' type="text"
							class="form-control" id="input3">
					</div>
		
					<div class="form-group">
						<label for="input1">제목</label> <input readonly value='<c:out value="${board.title }"/>' type="text"
							class="form-control" id="input1">
					</div>

					<div class="form-group">
						<label for="textarea1">내용</label>
						<textarea readonly class="form-control" id="textarea1"
							rows="3"><c:out value="${board.content }"/></textarea>
					</div>

					<div class="form-group">
						<label for="input1">작성자</label> <input readonly value='<c:out value="${board.writer }"/>' type="text"
							class="form-control" id="input2">
					</div>
					
					<!-- "수정" -> 수정,삭제
					책 : 254page 위쪽 코드
					수정 button 요소를 만들고
					button을 눌렀을 때 이동하는 js 작성
					 -->
					<!-- 우리:
					a요소로 만듦 -->
					<c:url value="/board/modify" var="modifyLink">
						<c:param name="bno" value="${board.bno }"></c:param>
						<c:param name="pageNum" value="${cri.pageNum }"></c:param>
						<c:param name="amount" value="${cri.amount }"></c:param>
						<c:param name="type" value="${cri.type }"></c:param>
						<c:param name="keyword" value="${cri.keyword }"></c:param>
					</c:url>
					
					<a href="${modifyLink}" class="btn btn-primary">
					 수정
					</a>

			</div>
		</div>
	</div>
	
	<!-- 댓글 목록 container -->
	<div class="container-sm mt-3">
		<div class="row">
			<div class="col-12 col-lg-6 offset-lg-3">
				
				<div class="card">
					<div class="card-header d-flex justify-content-between align-items-center">
						<span>
						댓글 목록
						</span>
						<button class="btn btn-info" id="new-reply-button">댓글 쓰기</button>
					</div>
				</div>
				
				<div class="card-body">
					<ul class="list-unstyled" id="reply-ul">
					
					<!-- 이걸 토대로 위에만듦
						하나의 댓글이 하나의 li
						<li class="media" data-rno="21"> data-* =>나중에 쓰려고 정의해둠
							<div class="media-body">
								<h5>user00 <small>2021/01/29</small></h5>
								댓글 본문..........!~!~!@~!~!
								<hr>
							</div>
						</li>
					 -->
						
					</ul>
				</div>
			</div>
		</div>
	</div>
	
	<%-- modal 새 댓글 form --%>
	<div class="modal fade" id="new-reply-modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">
						새 댓글
					</h5>		
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>		
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="reply-input" class="col-form-label">
							댓글
						</label>
						<input type="text" class="form-control" id="reply-input">
					</div>
					<div class="form-group">
						<label for="replyer-input" class="col-form-label">
							작성자
						</label>
						<input type="text" class="form-control" id="replyer-input">
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
					<button id="reply-submit-button" type="button" class="btn btn-primary">등록</button>				
				</div>
			</div>
		</div>
	</div>
	
	
	<%-- modal 수정 form --%>
	<div class="modal fade" id="modify-reply-modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">
						수정 / 삭제
					</h5>		
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>		
				</div>
				<div class="modal-body">
					<input id="rno-input2" type="hidden">
					<div class="form-group">
						<label for="reply-input2" class="col-form-label">
							댓글
						</label>
						<input type="text" class="form-control" id="reply-input2">
					</div>
					<div class="form-group">
						<label for="replyer-input2" class="col-form-label">
							작성자
						</label>
						<input readonly="readonly" type="text" class="form-control" id="replyer-input2">
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
					<button id="reply-modify-button" type="button" class="btn btn-primary">수정</button>				
					<button id="reply-delete-button" type="button" class="btn btn-danger">삭제</button>				
				</div>
			</div>
		</div>
	</div>
	
	

</body>
</html>