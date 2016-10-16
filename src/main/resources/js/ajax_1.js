var jqAjax = {

	updateBoardDB : function(boardId, editorContent) {
		$.ajax({
			url : '../ajax/board/updateBoardDB',
			type : 'GET',
			data : {
				'boardId' : boardId,
				'editorContent' : editorContent
			},
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
				notify.notify('Ajax 통신 성공 code : ' + status, '저장 프로세스 : ' + data.resultMessage);
			},
			error : function(request, status, error) {
				notify.notify('Ajax 통신 실패 code : ' + request.status + '\n error : ' + error);
			}
		});
	},
		
	updateList : function() {
		$.ajax({
			url : 'test/boardUpdateAjaxTest',
			type : 'GET',
			contentType : "application/json; charset=utf-8",
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
				notify.notify('업데이트 성공 : ' + status, '리스트 수 : ' + data.boardList.length);
				
				var boardList = data.boardList;
				var content = "";
				
				$.each(boardList, function(index, value) {
					content += '<div class="panel panel-default">';
					content += '    <div id="heading' + (index + 1) + '" class="panel-heading">';
					content += '        <h4 class="panel-title">';
					content += '            <a class="collapsed" data-toggle="collapse" data-parent="#listAccordion" href="#content' + (index + 1) + '">' + value.title + ' (' + value.member.username + ')</a>';
					content += '            <button type="button" class="close">';
					content += '                <span class="glyphicon glyphicon-remove-sign"></span>';
					content += '            </button>';
					content += '            <span class="close">&nbsp;</span>';
					content += '            <button type="button" class="close">';
					content += '                <span class="glyphicon glyphicon-edit"></span>';
					content += '            </button>';
					content += '        </h4>';
					content += '    </div>';
					content += '    <div id="content' + (index + 1) + '" class="panel-collapse collapse">';
					content += '	    <div class="panel-body">' + value.content + '</div>';
					content += '    </div>';
					content += '</div>';
				});
				
				$('#listAccordion').children('.panel').remove();
				$('#listAccordion').append(content);
			},
			error : function(request, status, error) {
				notify.notify('실패\ncode : ' + request.status + '\n error : ' + error);
			}
		});
	},

	deleteAllBoards : function() {
		$.ajax({
			url : 'test/boardDeleteAjaxTest',
			type : 'GET',
			contentType : "application/json; charset=utf-8",
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
				notify.notify('삭제 성공', status);
				notify.notify('리스트 수', data.boardList.length);
				
				$('#listAccordion').children('.panel').remove();
			},
			error : function(request, status, error) {
				notify.notify('실패\ncode : ' + request.status + '\n error : ' + error);
			}
		});
	},
		
	insertRefresh : function() {
		$.ajax({
			url : 'test/ajaxTest1',
			type : 'GET',
			data : {'nickname' : session.nickname},
			contentType : "application/json; charset=utf-8",
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
				notify.notify('title', '성공\n');
				notify.notify('title', 'status : ' + status + '\n');
				notify.notify('title', 'data : ' + data + '\n');
				notify.notify('title', 'list : ' + data.list + '\n');
				notify.notify('title', 'size : ' + data.size + '\n');
				notify.notify('title', 'id : ' + data.id + '\n');
				
				session.id = data.id;
				
				// remove tr
				$('#memberListTBody').find('tr').remove();
				
				// refresh tr
				$.each(data.list, function(index, value) {
					content += '<tr>';
					content += '<td><span name="userNumber">' + value.id + '</span></td>';
				    content += '<td><span name="userName">' + value.username + '</span></td>';
				    content += '<td><span name="userEmail">' + value.email + '</span></td>';
				    content += '<td><span name="userAuth">' + '*' + '</span></td>';
				    content += '<td><span name="userPart">' + '*' + '</span></td>';
				    content += '<td><span name="test1">' + '*' + '</span></td>';
				    content += '<td><span name="test1">' + '*' + '</span></td>';
				    content += '</tr>';
				});
				$('#memberListTBody').append(content);
			},
			error : function(request, status, error) {
				notify.notify('실패\ncode : ' + request.status + '\n error : ' + error);
			}
		});
	},

	deleteRefresh : function() {
		$.ajax({
			url : 'test/ajaxTest2',
			type : 'GET',
			data : {'id' : session.id},
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
				notify.notify('title', '성공\n');
				notify.notify('title', 'status : ' + status + '\n');
				notify.notify('title', 'data : ' + data + '\n');
				notify.notify('title', 'list : ' + data.list + '\n');
				notify.notify('title', 'size : ' + data.size + '\n');
				
				session.id = null;
				
				// remove tr
				$('#memberListTBody').find('tr').remove();
				
				// refresh tr
				$.each(data.list, function(index, value) {
					content += '<tr>';
					content += '<td><span name="userNumber">' + value.id + '</span></td>';
				    content += '<td><span name="userName">' + value.username + '</span></td>';
				    content += '<td><span name="userEmail">' + value.email + '</span></td>';
				    content += '<td><span name="userAuth">' + '*' + '</span></td>';
				    content += '<td><span name="userPart">' + '*' + '</span></td>';
				    content += '<td><span name="test1">' + '*' + '</span></td>';
				    content += '<td><span name="test1">' + '*' + '</span></td>';
				    content += '</tr>';
				});
				$('#memberListTBody').append(content);
			},
			error : function(request, status, error) {
				notify.notify('실패\ncode : ' + request.status + '\n error : ' + error);
			}
		});
	},
	
	test1 : function() {
		/*
		$.ajax({
			url : 'test/test2',
			type : 'GET',
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
				notify.notify('성공\n');
				notify.notify('status : ' + status + '\n');
				notify.notify('data : ' + data + '\n');
				notify.notify('list : ' + data.list + '\n');
			},
			error : function(request, status, error) {
				notify.notify('실패\ncode : ' + request.status + '\n error : ' + error);
			}
		});
		*/
		
		$.ajax({
			url : 'test/test1',
			type : 'GET',
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			success : function(data, status) {
				utils.chatAppend('성공\n');
				utils.chatAppend('status : ' + status);
				utils.chatAppend('data : ' + data);
				utils.chatAppend('members : ' + data.members);
				utils.chatAppend('member : ' + data.members[0].username);
				utils.chatAppend('boards : ' + data.members[0].boards);
				utils.chatAppend('board : ' + data.members[0].boards[0].title);
				utils.chatAppend('comment content : ' + data.members[0].boards[0].comments[0].content);
				// utils.chatAppend('username : ' + data.username);
				// utils.chatAppend('password : ' + data.password);
			},
			error : function(request, status, error) {
				notify.notify('실패\ncode : ' + request.status + '\n error : ' + error);
			}
		});
	}
};