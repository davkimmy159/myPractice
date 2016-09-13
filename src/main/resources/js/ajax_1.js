var jqAjax = {

	insertRefresh : function() {
		$.ajax({
			url : 'ajaxTest1',
			type : 'GET',
			data : {'nickname' : session.nickname},
			contentType: "application/json; charset=utf-8",
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
				notify.notify('성공\n');
				notify.notify('status : ' + status + '\n');
				notify.notify('data : ' + data + '\n');
				notify.notify('list : ' + data.list + '\n');
				notify.notify('size : ' + data.size + '\n');
				notify.notify('id : ' + data.id + '\n');
				
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
			url : 'ajaxTest2',
			type : 'GET',
			data : {'id' : session.id},
			contentType: "application/json; charset=utf-8",
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
				notify.notify('성공\n');
				notify.notify('status : ' + status + '\n');
				notify.notify('data : ' + data + '\n');
				notify.notify('list : ' + data.list + '\n');
				notify.notify('size : ' + data.size + '\n');
				
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
	}
};