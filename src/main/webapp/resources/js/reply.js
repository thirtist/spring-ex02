//console.log("reply module...");

//{root}

var replyService = (function(){
	
	function add(reply, callback, error){
		//console.log("add1 method");
		console.log(reply);
		
		$.ajax({
			type: "post",
			url: appRoot + "/replies/new", //context root로 변경
			data: JSON.stringify(reply),    //form data를 json
			contentType: "application/json; charset=utf-8",
			success: function(result, status, xhr) {
				if (callback !== undefined){
					callback(result);
				}
			},
			error: function(xhr, status, er) {
				if (error){
					// !== undefined는 생략가능
					error(er);
				}
			}
		});
		
	}
	
	function getList(param, callback, error){
		var bno = param.bno;
		var page = param.page || 1;
		// javascript에서 || 연산 => 앞이 true면 그게 들어가고 false면 뒤에것이 들어감
		// boolean false : 0, "", null, undefined
	
		$.getJSON(appRoot + "/replies/pages/" + bno + "/" + page, function(data){
			if (callback){
				callback(data);
			}
		}).fail(function(xhr, status, err){
			if (error) {
				error();
			}
		});
	}
	
	function remove(rno, callback, error) {
		$.ajax({
			type: 'delete',
			url: appRoot + '/replies/' + rno,
			success: function(result, status, xhr) {
				if (callback) {
					callback(result);
				}
			},
			error: function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		});
	}
	
	function update(reply, callback, error) {
		$.ajax({
			type: 'put',
			url: appRoot + '/replies/' + reply.rno,
			data: JSON.stringify(reply),
			//자바스크립트 객체를 JSON으로 바꿔주는 메소드
			contentType: 'application/json; charset=utf-8',
			success: function(result, status, xhr) {
				if(callback) {
					callback(result);
				}
			},
			error: function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		});
	}
	
	function get(rno, callback, error){
		$.get(appRoot + '/replies/'+rno,function(data){
			if (callback) {
				callback(data);
			}
		}).fail(function(){
			if (error){
				error();
			}
		});
	}
	
	return {
//		name:"AAAA",
		add: add,       //add라는 객체가 파라미터로 내장함수 add를 가지고있음
		getList: getList,
		remove: remove,
		update: update,
		get: get
	};
})();