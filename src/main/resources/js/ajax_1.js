var jqAjax = {

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