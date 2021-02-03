<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="u"  tagdir="/WEB-INF/tags" %>
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
		
		var result = '${result}';
		var message = '${message}';
		
		//checkModal(result);
		checkModal2(message);
		
		history.replaceState({}, null, null);

		function checkModal2(message){
			if (message && history.state == null) {
				$("#myModal .modal-body p").html(message)
				$("#myModal").modal("show");
			}			
		}

		function checkModal(result){
			if (result === '' || history.state) {
				return;
			}			
		
			if(parseInt(result) > 0){
				$("#myModal .modal-body p").html("게시글 " + result + "번이 등록되었습니다")
			}
			$("#myModal").modal("show");
		}
		
		var actionForm = $("#actionForm");
		$(".pagination a").click(function(e){
			e.preventDefault();
			
			actionForm.find("[name='pageNum']").val($(this).attr('href'));
			
			actionForm.submit();
		})
		
	});
</script>

<title>Insert title here</title>
</head>
<body>

<u:navbar></u:navbar>


<div class="container-sm">
  <div class="row">
    <table class="table table-striped table-hover">
      <thead>
        <tr>
          <th>#번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>작성일</th>
          <th>수정일</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${list}" var="board" >
          <tr>
            <td>${board.bno}</td>
            <td>
            <c:url value="/board/get" var="boardLink">
            	<c:param name="bno" value="${board.bno }"></c:param>
            	<c:param name="pageNum" value="${pageMaker.cri.pageNum }"></c:param>
            	<c:param name="amount" value="${pageMaker.cri.amount }"></c:param>
            	<c:param name="type" value="${pageMaker.cri.type }"></c:param>
	    		<c:param name="keyword" value="${pageMaker.cri.keyword }"></c:param>            	
            </c:url>
            <a href="${boardLink }">
            	<c:out value="${board.title}" />
            	<c:if test="${board.replyCnt gt 0 }">
	            	<span class="badge badge-info">${board.replyCnt }</span>
            	</c:if>
            </a>
            </td>
            <td><c:out value="${board.writer}" /></td>
            <td><fmt:formatDate value="${board.regdate}" pattern="yyyy-MM-dd"/></td>
            <td><fmt:formatDate value="${board.updateDate}" pattern="yyyy-MM-dd"/></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<!-- modal창 -->
<div id="myModal" class="modal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">알림</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>처리가완료되었습니다</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<div class="container-sm mt-3">
	<div class="row justify-content-center">
		<nav aria-label="Page navigation example">
		  <ul class="pagination">
		    
		   	<c:if test="${pageMaker.prev }">
		   		<c:url value="/board/list" var="prevLink">
		   		<!--c:url은 contextRoot를 안넣어도 자동으로 만들어진다!  -->
		   			<c:param value="${pageMaker.startPage-1 }" name="PageNum"></c:param>
		   			<c:param value="${pageMaker.cri.amount }" name="amount"></c:param>
		   		</c:url>
			    <li class="page-item">
			    	<%-- <a class="page-link" href="${prevLink }">Previous</a> --%>
			    	<a class="page-link" href="${pageMaker.startPage-1}">Previous</a>
			    </li>
		   	</c:if>
		    
		    <c:forEach var="num" begin="${pageMaker.startPage }" end="${pageMaker.endPage }">
			    
			   <c:url value="/board/list" var="pageLink" >
		    		<c:param name="pageNum" value="${num }" />
		    		<c:param name="amount" value="${pageMaker.cri.amount }" />
		    		<c:param name="type" value="${pageMaker.cri.type }"></c:param>
		    		<c:param name="keyword" value="${pageMaker.cri.keyword }"></c:param>
		    	</c:url>
		    	<li class="page-item ${pageMaker.cri.pageNum eq num ? 'active' : '' }">
		    		<%-- <a class="page-link" href="${pageLink }">${num }</a> --%>
				    <a class="page-link" href="${num }">${num }</a>
		    	</li>
			    
		    </c:forEach>
	
			<c:if test="${pageMaker.next }">
				<c:url value="/board/list" var="nextLink">
		   			<c:param value="${pageMaker.endPage+1 }" name="PageNum"></c:param>
		   			<c:param value="${pageMaker.cri.amount }" name="amount"></c:param>
		   		</c:url>
			    <li class="page-item">
			    	<%-- <a class="page-link" href="${nextLink }">Next</a> --%>
			    	<a class="page-link" href="${pageMaker.endPage+1}">Next</a>
			    </li>
			</c:if>
		  </ul>
		</nav>
	</div>
</div>

<div class="d-none" > <!-- 부트스트랩에서 class="d-none" => style="display:none;" -->
	<form id="actionForm" action="${root }/board/list">
		<input name="pageNum" value="${pageMaker.cri.pageNum }"/>
		<input name="amount" value="${pageMaker.cri.amount }"/>
		<input name="type" value="${pageMaker.cri.type }"/>
	 	<input name="keyword" value="${pageMaker.cri.keyword }"/>
		<input type="submit"/>
	</form>
</div>

</body>
</html>