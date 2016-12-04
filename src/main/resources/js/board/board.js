var jqObj = {

	editorContainer : $('#editorContainer'),
	chatContainer : $('#chatContainer'),

	mobileEditor : $('#mobileEditor'),

	chatArea : $('#chatArea'),
};

// basicEditor settings (CKEditor)
var basicEditor = CKEDITOR.replace('basicEditor', {
	contentsCss  : 'body {white-space: nowrap;}',
//	customConfig : 'config2.js',
	toolbarCanCollapse: true,
	resize_enabled: false,
	removePlugins: 'elementspath',
	enterMode: CKEDITOR.ENTER_BR,
//	shiftEnterMode: CKEDITOR.ENTER_P
	fillEmptyBlocks : false,
	basicEntities : false,
	on: {
		instanceReady : function() {
			$("a.cke_toolbox_collapser").click(function() {
				resizeFuncs.setBasicHeight();
			});
		},

		key : function(event) {
			var keyCode = event.data.keyCode;

			// CTRL + ALT
			if(keyCode == eventObj.ctrlAlt) {
				$('#chatInput').focus();

			// CTRL + ENTER
			} else if(keyCode == eventObj.ctrlEnter) {
				$('#editorInputBtn').trigger('click');
			}
			
			keyCode = null;
		}
	}


	/*
	,
	qtRows: 20, // Count of rows
    qtColumns: 20, // Count of columns
    qtBorder: '1', // Border of inserted table
    qtWidth: '90%', // Width of inserted table
    qtStyle: { 'border-collapse' : 'collapse' },
    qtClass: 'test', // Class of table
    qtCellPadding: '0', // Cell padding table
    qtCellSpacing: '0', // Cell spacing table
    qtPreviewBorder: '4px double black', // preview table border
    qtPreviewSize: '4px', // Preview table cell size
    qtPreviewBackground: '#c8def4' // preview table background (hover)
    */
});

// mobileEditor settings (inline CKEditor)
var mobileEditor = CKEDITOR.inline('mobileEditor', {
	enterMode: CKEDITOR.ENTER_BR,
	fillEmptyBlocks : false,
	basicEntities : false,
	on: {
		key : function(event) {
			var keyCode = event.data.keyCode;

			// CTRL + ALT
			if(keyCode == eventObj.ctrlAlt) {
				$('#chatInput').focus();

			// CTRL + ENTER
			} else if(keyCode == eventObj.ctrlEnter) {
				$('#editorInputBtn').trigger('click');
			}
			keyCode = null;
		}
	}
});

// memoEditor settings (inline CKEditor)
var memoEditor = CKEDITOR.inline('memoEditor', {
	fillEmptyBlocks : false,
	basicEntities : false
});
$('#memoEditor').height($(window).height() * 0.2);

// memo update editor settings (inline CKEditor)
var memoUpdateEditor = CKEDITOR.inline('memoUpdateEditor', {
	fillEmptyBlocks : false,
	basicEntities : false,
	enterMode: CKEDITOR.ENTER_BR
});
$('#memoUpdateEditor').height($(window).height() * 0.2);


// ===================================================================================================


var session = {

	socket : null,
	stompClient : null,
	nickname : null,
	boardId : null,
	chatHandler: "/dest/board/chat/",
	editorHandler: "/dest/board/editor/",
	dbUpdateAlarmHandler: "/dest/board/chat/db_update_alarm/",
	memoUpdateAlarmHandler: "/dest/board/memo_update_alarm/",
//	joinMemberUpdateHandler: "/dest/board/join_member_update/",
	
	chatSubscribe: "/subscribe/board/chat/",
	editorSubscribe: "/subscribe/board/editor/",
	dbUpdateAlarmSubscribe: "/subscribe/board/chat/db_update_alarm/",
	memoUpdateAlarmSubscribe: "/subscribe/board/memo_update_alarm/",
	joinMemberUpdateSubscribe: "/subscribe/board/join_member_update/",
	

	connect : function() {

		// Sets nickname
		this.nickname = $("#loginUsername").text();

		// Sets board id
		this.boardId = $("#boardId").val();

		// Sets path of destination and subscribe point for each board
		this.chatHandler += this.boardId;
		this.editorHandler += this.boardId;
		this.dbUpdateAlarmHandler += this.boardId;
		this.memoUpdateAlarmHandler += this.boardId;
//		this.joinMemberUpdateHandler += $("input#boardId").val();

		this.chatSubscribe += this.boardId;
		this.editorSubscribe += this.boardId;
		this.dbUpdateAlarmSubscribe += this.boardId;
		this.memoUpdateAlarmSubscribe += this.boardId;
		this.joinMemberUpdateSubscribe += this.boardId;
		
		var websocketUrl = path.getFullContextPath() + '/websocket/board';
		this.socket = new SockJS(websocketUrl);
		this.stompClient = Stomp.over(this.socket);

		var headers = {
//			login : "myLogin",
//			passcode : "myPasscode",
			
			// additional header
			boardId : session.boardId
		};
		
		this.stompClient.connect(headers,
			function(frame) {
				notify.notify("Stomp connection for board", frame);
				
				// chat subscribe
				session.stompClient.subscribe(session.chatSubscribe, function(messageData) {
					var chatMessage = JSON.parse(messageData.body);

					boardUtils.chatAppend(chatMessage.chatAreaMessage);

					if(session.nickname != chatMessage.username) {
						notify.notify(chatMessage.username, chatMessage.messageBody);
					}
				});

				// DB update subscribe
				session.stompClient.subscribe(session.dbUpdateAlarmSubscribe, function(alarmMessage) {
					var alarmMsgBody = JSON.parse(alarmMessage.body);

					boardUtils.chatAppend(alarmMsgBody.chatAreaMessage);

					if(session.nickname != alarmMsgBody.username) {
						notify.notify(alarmMsgBody.messageBody, " by '" + alarmMsgBody.username + "\'", "success");
					}
				});

				// editor subscribe
				session.stompClient.subscribe(session.editorSubscribe, function(contentData) {
					ajax.getHistoriesOfBoard(session.boardId, $('#historyPagination-bootpag').find("li.active").find("a").text());
					
					var contentDataBody = JSON.parse(contentData.body);

					// desktop
					if (utils.isDesktopSize()) {
//						notify.notify("test", basicEditor.getSelection().getRanges().length);
//						var ranges = basicEditor.getSelection().getRanges();
						basicEditor.setData(contentDataBody.messageBody);
//						basicEditor.getSelection().selectRanges(ranges);

					// mobile
					} else {
						mobileEditor.setData(contentDataBody.messageBody);
					}

					boardUtils.chatAppend(contentDataBody.chatAreaMessage);

					if(session.nickname != contentDataBody.username) {
						notify.notify("Editor update", " by '" + contentDataBody.username + "'", "success");
					}
				});
				
				// memoUpdateAlarm subscribe
				session.stompClient.subscribe(session.memoUpdateAlarmSubscribe, function(messageData) {
					ajax.getMemosOfBoard(session.boardId, $('#memoPagination-bootpag').find("li.active").find("a").text());
					ajax.getHistoriesOfBoard(session.boardId, $('#historyPagination-bootpag').find("li.active").find("a").text());
					
					var memoUpdateMessage = JSON.parse(messageData.body);

					boardUtils.chatAppend(memoUpdateMessage.chatAreaMessage);
					
					if(session.nickname != memoUpdateMessage.username) {
						notify.notify(memoUpdateMessage.username, memoUpdateMessage.messageBody);
					}
				});
			
				// member connection and disconnection subscribe
				session.stompClient.subscribe(session.joinMemberUpdateSubscribe, function(messageData) {
					
					var message = JSON.parse(messageData.body);
					
					notify.notify(message.messageBody, message.username);
					boardUtils.chatAppend(message.messageBody);
					boardUtils.chatAppend(" - " + message.username + " - ");
					
					var memberList = message.memberList;
					
					var joinMemberRows = "";
					var role;
					
					for(var cnt in memberList) {
						role = memberList[cnt].username == $("#boardOwner").val() ? "Owner" : "Guest";
						
						joinMemberRows +=
							'<tr>' +
							'	<td>' +
							'		<span class="username">' + memberList[cnt].username + '</span>' +
							'	</td>' +
							'	<td>' +
							'		<span class="userEmail">' + memberList[cnt].email + '</span>' +
							'	</td>' +
							'	<td>' +
							'		<span class="userRole">' + role + '</span>' +
							'	</td>' +
							'</tr>';
					}
					
					$("#joinMemberTableTbody").html(joinMemberRows);
					
					/*
					$("#joinMemberTableTbody").find('span.username').editable({
						type : 'text',
//						url : '/post',
						title : 'test'
//						placement : 'left'
					});
					*/
				});
				
				if($("input#boardMemoSize").val() != 0) {
					ajax.getMemosOfBoard($("input#boardId").val(), 1);	
				}
				
				if($("input#boardHistorySize").val() != 0) {
					ajax.getHistoriesOfBoard($("input#boardId").val(), 1);	
				}
				
				ajax.updateJoinMemberTable($("input#boardId").val());
			},
			function(error) {

				var joinMsg = JSON.stringify({
					username : session.nickname,
					messageBody : ' test'
				});
				session.stompClient.send(session.chatHandler, {}, joinMsg);

				boardUtils.chatAppend("Error : Stomp  connection failed :");
				boardUtils.chatAppend(error);
				notify.notify("Error", "Stomp connection failed", error);
				session.disconnect();
			}
		);
	},

	disconnect : function() {

		// For good-bye message isn't sent
		setTimeout(function() {

			// Close stomp client
			if(session.stompClient) {
				session.stompClient.disconnect(function() {
					notify.notify("Disconnection", "Stomp disconnected");
				});
				session.stompClient = null;
			};

			// Close socket
			if(session.socket) {
				session.socket.close();
				session.socket = null;
			}

			notify.notify("Disconnection", "fully disconnected (stomp & socket)");
		}, 200);
	},

	reconnect : function() {
		boardUtils.chatAppend("Stomp reconnecting...");
		notify.notify("Stomp reconnection", "Reconnecting...");
		this.disconnect();
		this.connect();
	},

	// Send data to socket
	chatMsgSend : function() {
		var msg = JSON.stringify({
			username : session.nickname,
			messageBody : $('#chatInput').val()
		});

		$('#chatInput').val('');

		this.stompClient.send(session.chatHandler, {}, msg);

		msg = null;
	},

	editorContentSend : function(dbUpdateFlag) {

		var editorContent = {
			username : "",
			messageBody : ""
		};

		editorContent.username = session.nickname;

		// desktop
		if (utils.isDesktopSize()) {
			editorContent.messageBody = basicEditor.getData();
			basicEditor.focus();

		// mobile
		} else {
			editorContent.messageBody = mobileEditor.getData();
			mobileEditor.focus();
		}

		session.stompClient.send(session.editorHandler, {"DB_UPDATE" : dbUpdateFlag}, JSON.stringify(editorContent));
	},

	editorContentDBUpdate : function() {

		// desktop
		if (utils.isDesktopSize()) {
			ajax.updateBoardDB(session.nickname, session.boardId, basicEditor.getData());

		// mobile
		} else {
			ajax.updateBoardDB(session.nickname, session.boardId, mobileEditor.getData());
		}

		var dbUpdateMsg = JSON.stringify({
			username : session.nickname,
			messageBody : "DB content update"
		});

		session.stompClient.send(session.dbUpdateAlarmHandler, {}, dbUpdateMsg);

		boardUtils.focusToEditor();
	}
};

var badge = {

	msgCnt : 0,
	badgeArray : $('.badge'),
	badgeBtnArray : [ $('#basicStatus'), $('#mobileStatus') ],

	add : function() {
		if(this.msgCnt == 0) {
			utils.removeClassFunc(this.badgeBtnArray, 'btn-primary');
			utils.addClassFunc(this.badgeBtnArray, 'btn-warning');
		}
		this.badgeArray.text(++this.msgCnt);
	},

	reset : function() {
		this.msgCnt = 0;
		this.badgeArray.text(0);
		utils.removeClassFunc(this.badgeBtnArray, 'btn-warning');
		utils.addClassFunc(this.badgeBtnArray, 'btn-primary');
	}
};

var boardUtils = {

	// chatArea append function
	chatAppend : function(str) {
		$('#chatArea').append(str + '\n');
		$('#chatArea').scrollTop($('#chatArea')[0].scrollHeight);
	},

	// Checks whether session is alive
	isSessionAlive : function() {
		return session.socket && session.stompClient;
	},

	// focus move
	focusToEditor : function() {
		if (utils.isDesktopSize()) {
			basicEditor.focus();
		} else {
			mobileEditor.focus();
		}
	},

	// veryfy chat input
	verifyChatInput : function() {
		if (boardUtils.isSessionAlive()) {
			if (utils.checkStr($('#chatInput').val())) {
				session.chatMsgSend();
				$('#chatInput').focus();
			} else {
				$('#chatInput').focus();
			}
		} else {
			notify.notify('Join required', 'You need to join first!');
		}
	},

	showHeight : function() {
		// Each height
		this.chatAppend('#window : ' + $(window).height());
		this.chatAppend('#container : ' + $('#container').height());
		this.chatAppend('#navBarContainer: ' + $('#navBarContainer').height());
		this.chatAppend('#mainRow : ' + $('#mainRow').height());
		this.chatAppend('#editorContainer : ' + $('#editorContainer').height());
		this.chatAppend('#chatContainer : ' + $('#chatContainer').height());
		this.chatAppend('#basicEditorDiv : ' + $('#basicEditorDiv').height());
		this.chatAppend('#mobileEditorDiv : ' + $('#mobileEditorDiv').height());
		this.chatAppend('#basicEditor : ' + $('#basicEditor').height());
		this.chatAppend("#basicEditor.config.height : " + basicEditor.config.height);
		this.chatAppend('#mobileEditor : ' + $('#mobileEditor').height());
		this.chatAppend('#chatArea : ' + $('#chatArea').height());
		this.chatAppend('#chatContainer - editorContainer : ' + ($('#chatContainer').height() - $('#editorContainer').height()));
	}
};

var eventObj = {

	badgeEvent : function() {
		// Badge event function
		$('#chatArea').focus(function(event) {
			badge.reset();
		});
		$('#chatInput').focus(function(event) {
			badge.reset();
		});
		$('#basicStatus').click(function() {
			badge.reset();
		});
		$('#mobileStatus').click(function() {
			badge.reset();
		});
	},

	// Toggle chat notification
	toggleChatNotifyEvent : function() {
		$('#chatNotifyToggleBtn').click(function() {
			if($(this).hasClass("active")) {
				notify.toggle = true;
			} else {
				notify.toggle = false;
			}
		});
	},
	
	mobileToggleToolbarBtnEvent : function() {
		$("#toolbarToggleBtn").click(function() {
			if(!(utils.isDesktopSize())) {
				if($(this).hasClass("active")) {
					$(".cke_editor_mobileEditor").find(".cke_top").css("display", "block");					
				} else {
					$(".cke_editor_mobileEditor").find(".cke_top").css("display", "none");
				}
			}
		});
	},

	showHeightsEvent : function() {
		$('#showHeights').click(function() {
			boardUtils.showHeight();
		});
	},

	editorInputSendEvent : function() {
		$('#editorInputBtn').click(function() {
			if (boardUtils.isSessionAlive()) {
				session.editorContentSend(false);

				boardUtils.focusToEditor();
			} else {
				notify.notify('Join required', 'You need to join first!');
			}
		});
	},

	editorSaveEvent : function() {
		$('#editorSaveBtn').click(function() {
			if (boardUtils.isSessionAlive()) {

				session.editorContentDBUpdate();

			}
		});
	},

	memoSaveEvent : function() {
		$('#memoSaveBtn').click(function() {
			if (boardUtils.isSessionAlive()) {
				
				var memoTitleInput = $("#mainModal_tabContent3_memoContent1").find('input[name="memoTitle"]');
				var memoTitle = memoTitleInput.val();
				var memoContent = memoEditor.getData();
				
				if(!(utils.checkStr(memoTitle))) {
					notify.notify("Title", "invalid", "error");
					memoTitleInput.focus();
				} else if (!(utils.checkStr(memoContent))) {
					notify.notify("Content", "invalid", "error");
					memoEditor.focus();
				} else {
					ajax.createMemo(session.boardId, memoTitle, memoContent);
				}
				
			} else {
				notify.notify('Join required', 'You need to join first!');
			}
		});
	},
	
	memoUpdateEvent : function() {
		$('#memoUpdateSaveBtn').click(function() {
			if (boardUtils.isSessionAlive()) {
				var memoUpdateTitleInput = $("#memoUpdateDiv").find('input[name="memoTitle"]');
				var memoUpdateTitle = memoUpdateTitleInput.val();
				var memoUpdateContent = memoUpdateEditor.getData();
				
				if(!(utils.checkStr(memoUpdateTitle))) {
					notify.notify("Title", "invalid", "error");
					memoUpdateTitleInput.focus();
				} else if (!(utils.checkStr(memoUpdateContent))) {
					notify.notify("Content", "invalid", "error");
					memoUpdateEditor.focus();
				} else {
					ajax.updateMemo($("#memoUpdateSubModal").find('input[name="memoUpdateId"]').val(), memoUpdateTitle, memoUpdateContent);
				}
			} else {
				notify.notify('Join required', 'You need to join first!');
			}
		});
	},
	
	memoRefreshEvent : function() {
		$("#memoRefreshBtn").click(function() {
			ajax.getMemosOfBoard($("input#boardId").val(), $('#memoPagination-bootpag').find("li.active").find("a").text());
		});
	},
	
	chatInputKey : function() {

		// for CTRL + SHIFT
		var isCtrl = false;

		// Focus back to chatInput on pressing enter key on chatInput
		$('#chatInput').keydown(function(event) {

			// ENTER
			if (event.which === 13) {
				boardUtils.verifyChatInput();

			// CTRL
			} else if (event.which == 17) {
				isCtrl = true;
			}

			// CTRL + ALT
			// <![CDATA[
			if (event.which == 18 && isCtrl) {
			// ]]>
//				notify.notify('title', 'CTRL + ALT clicked! (chatInput)');

				// Focus to basic editor
				if(utils.isDesktopSize()) {
					basicEditor.focus();

				// Focus to mobile editor
				} else {
					mobileEditor.focus();
				}
			}

//			boardUtils.chatAppend("Ctrl : " + isCtrl);
		});

		// Toggle isCtrl false
		$('#chatInput').keyup(function(event) {
			if(event.which == 17) {
				isCtrl=false;
			}
		});

		// Focus back to chatInput on clicking chatInputBtn
		$('#chatInputBtn').click(function() {
			boardUtils.verifyChatInput();
		});
	},

	ctrlAlt : CKEDITOR.CTRL + CKEDITOR.ALT + 18,
	ctrlEnter : CKEDITOR.CTRL + 13,

	modalBasicSettings : {
		// necessary for nested modal scroll focus (!!!important!!!)
		resolveNestedModalScrollbarProblemEvent : function() {
			$('.modal').on('hidden.bs.modal', function (e) {
				if($('.modal').hasClass('in')) {
					$('body').addClass('modal-open');
				}
			});
		}
	},

	modalBasicCalendarModalEvent : {
		dateClearOnExit : function() {
			$('#basicCalendarModal').on('hide.bs.modal', function(e) {
				BCModalFunc.BCEventStartDate.data("DateTimePicker").date(null);
				BCModalFunc.BCEventEndDate.data("DateTimePicker").date(null);
			});
		}
	}
};

/*
var xeditableFunc = {

	userNumbers : $('#joinTable').find('td span[name="userNumber"]'),
	userNames : $('#joinTable').find('td span[name="userName"]'),
	userEmails : $('#joinTable').find('td span[name="userEmail"]'),
	userAuths : $('#joinTable').find('td span[name="userAuth"]'),
	userParts : $('#joinTable').find('td span[name="userPart"]'),
	test1 : $('#joinTable').find('td span[name="test1"]'),
	test2 : $('#joinTable').find('td span[name="test2"]'),

	joinTableSet : function() {
		var userNumbers = $('#joinTable').find('td span[name="userNumber"]');
		var userNames = $('#joinTable').find('td span[name="userName"]');
		var userEmails = $('#joinTable').find('td span[name="userEmail"]');
		var userAuths = $('#joinTable').find('td span[name="userAuth"]');
		var userParts = $('#joinTable').find('td span[name="userPart"]');
		var test1 = $('#joinTable').find('td span[name="test1"]');
		var test2 = $('#joinTable').find('td span[name="test2"]');

		userNumbers.editable({
		    type : 'text',
		    url : '/post',
		    title : 'test',
		});
		userNames.editable({
		    type : 'text',
		    url : '/post',
		    title : 'test',
		});
		userEmails.editable({
		    type : 'text',
		    url : '/post',
		    title : 'test',
		    placement : 'right'
		});
		userAuths.editable({
		    type : 'text',
		    url : '/post',
		    title : 'test',
		    placement : 'left'
		});
		userParts.editable({
		    type : 'text',
		    url : '/post',
		    title : 'test',
		    placement : 'left'
		});
		test1.editable({
	        type : 'textarea',
			url: '/post',
	        title: 'test',
	        rows: 6,
	        placement : 'left'
		});
		test2.editable({
	        type : 'date',
	        title : 'test',
			format: 'yyyy-mm-dd',
	        viewformat: 'dd/mm/yyyy',
	        datepicker: {
	                weekStart: 1
	       	},
	       	placement : 'left'
		});

		userNumbers = null;
		userNames = null;
		userEmails = null;
		userAuths = null;
		userParts = null;
		test1 = null;
		test2 = null;


		 * this.userNumbers.editable({ type : 'text', url : '/post',
		 * title : 'test', }); this.userNames.editable({ type : 'text',
		 * url : '/post', title : 'test', }); this.userEmails.editable({
		 * type : 'text', url : '/post', title : 'test', placement :
		 * 'right' }); this.userAuths.editable({ type : 'text', url :
		 * '/post', title : 'test', placement : 'left' });
		 * this.userParts.editable({ type : 'text', url : '/post', title :
		 * 'test', placement : 'left' }); this.test1.editable({ type :
		 * 'textarea', url: '/post', title: 'test', rows: 6, placement :
		 * 'left' }); this.test2.editable({ type : 'date', title :
		 * 'test', format: 'yyyy-mm-dd', viewformat: 'dd/mm/yyyy',
		 * datepicker: { weekStart: 1 }, placement : 'left' });

	}
};
*/

var resizeFuncs = {

	// arrays to set different sets between basic and mobile
	formGroupArray : [ $('#formGroup1'), $('#formGroup2'), $('#formGroup3') ],
	formControlArray : [ $('#chatInputBtn') ],
	btnGroupArray : [ $('#btnGroup1'), $('#btnGroup2') ],

	// For rearrangement of each component
	setGapByTogglingClass : function() {
		utils.toggleClassArrayFunc(this.formGroupArray, 'form-group');
		utils.toggleClassArrayFunc(this.formControlArray, 'form-control');
		utils.toggleClassArrayFunc(this.btnGroupArray, 'btn-group-justified');
	},

	setBothCalendarHeight : function() {
		// resize height of basicCalendar in modal
		basicCalendar.fullCalendar('option', 'contentHeight', $(window).height() * 0.48);

		// resize height of yearlyCalendar in modal
		$('#yearlyCalendar').height($(window).height() * 0.48);
	},

	setBasicEditorHeight : function() {
		basicEditor.resize('100%', jqObj.chatArea.height() + 63);
	},

	setBasicChatAreaHeight : function() {
		jqObj.chatArea.height($(window).height() - 175);
	},

	/*
		There was a problem that editor's height is not compatible with chatArea's one at some point.
		I think it was from CKEditor's toolbar height changes.
		So I've deicded to use an if statement to ensure it's compatible when resizing
	*/
	checkEditorHeightAgainAndResize : function() {
		if (jqObj.editorContainer.height() != jqObj.chatContainer.height()) {
			this.setBasicEditorHeight();
		}
	},

	setInitialBasicHeight : function() {
		this.setBasicChatAreaHeight();

		// CKEditor is loaded at the last moment, so I used 'on' method
		// from CKEditor
		basicEditor.on('instanceReady', function() {
			resizeFuncs.setBasicEditorHeight();
			resizeFuncs.checkEditorHeightAgainAndResize();
		});
	},

	setBasicHeight : function() {
		this.setBasicChatAreaHeight();
		this.setBasicEditorHeight();
		this.checkEditorHeightAgainAndResize();
	},

	setMobileHeight : function() {
		jqObj.mobileEditor.height($(window).height() - 320);
		jqObj.chatArea.height($(window).height() - jqObj.mobileEditor.height() - 192);
	}

};


// List pagination ============================================================================

// memo list
$('#memoPagination-bootpag').bootpag({
	total: $("input#boardMemoSize").val() != 0 ? $("input#boardMemoSize").val() % 10 == 0 ?  $("input#boardMemoSize").val() / 10 : $("input#boardMemoSize").val() / 10 + 1 : 0,
	page: 1,
	maxVisible: 5,
	leaps: true,
	firstLastUse: true,
	prev: "‹",
	next: "›",
	first: "«",
	last: "»"
}).on("page", function(event, num){
	ajax.getMemosOfBoard($("input#boardId").val(), num);
}); 

// history list
$('#historyPagination-bootpag').bootpag({
	total: $("input#boardHistorySize").val() != 0 ? $("input#boardHistorySize").val() % 10 == 0 ?  $("input#boardHistorySize").val() / 10 : $("input#boardHistorySize").val() / 10 + 1 : 0,
	page: 1,
	maxVisible: 5,
	leaps: true,
	firstLastUse: true,
	prev: "‹",
	next: "›",
	first: "«",
	last: "»"
}).on("page", function(event, num){
	ajax.getHistoriesOfBoard($("input#boardId").val(), num);
});

//=============================================================================================

// Ajax

var ajax = {
	updateBoardDB : function(username, boardId, editorContent) {
		$.ajax({
			url : '../ajax/board/updateBoardDB',
			type : 'GET',
			data : {
				'username' : username,
				'boardId' : boardId,
				'editorContent' : editorContent
			},
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
//				notify.notify('Ajax 통신 성공 : ' + status, '저장 프로세스 : ' + data.resultMessage);
				notify.notify('DB 저장 ', '성공');
				
				session.editorContentSend(true);
			},
			error : function(request, status, error) {
				notify.notify('Ajax 통신 실패 : ' + request.status, 'status : ' + status);
			}
		});
	},
	
	getMemosOfBoard : function(boardId, offset) {
		$.ajax({
			url : '../ajax/memo/getAllMemosOfBoard',
			type : 'GET',
			data : {
				'boardId' : boardId,
				'offset' : offset
			},
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
//				notify.notify('Ajax 통신 성공', status);
				
				$("input#boardMemoSize").val(data.total);
				
				var memoList = data.memoList;
				
				var memoListDisplay = "";

				for(var cnt in memoList) {
					memoListDisplay +=  	'<div id="memoPanel' + memoList[cnt].id + '" class="panel panel-default">' +
											'	<input name="memoId" value="' + memoList[cnt].id + '" hidden="hidden" />' +
											'	<div id="memoHeading' + memoList[cnt].id + '" class="panel-heading" role="tab">' +
											'		<h4 class="panel-title">' +
											'			<a class="collapsed" data-toggle="collapse" data-parent="#memoListAccordion" href="#memoContent' + memoList[cnt].id + '">';
					
					memoListDisplay +=  	'				<span class="memoTitleSpan hidden-xs">' + memoList[cnt].title + '</span>';
					
					memoListDisplay +=  	memoList[cnt].title.length >= 18 ? '				<span class="visible-xs-inline-block">' + memoList[cnt].title.substring(0, 18) + '...' + '</span>' : '				<span class="visible-xs-inline-block">' + memoList[cnt].title + '</span>';
					
					memoListDisplay +=		'			</a>';
					
					if(memoList[cnt].memberUsername == session.nickname) {
						memoListDisplay +=  '			<button class="close" name="memoDeleteBtn" type="button" >' +
											'				<span class="glyphicon glyphicon-remove-sign"></span>' +
											'			</button>' +
											'			<span class="close">&nbsp;</span>' +
											'			<button class="close" name="memoUpdateBtn" type="button">' +
											'				<span class="glyphicon glyphicon-edit"></span>' +
											'			</button>' +
											'			<span class="close">&nbsp;</span>';
					}
					
					memoListDisplay +=  	'			<button class="close visible-xs-block" name="usernameDisplayBtn" type="button">' +
											'				<span class="glyphicon glyphicon-exclamation-sign"></span>' +
											'			</button>' +
											'			<span class="hidden-xs memoOwnerDisplay">  [' + memoList[cnt].memberUsername + ']</span>';					
					memoListDisplay +=  	'		</h4>' +
											'	</div>' +
											'	<div id="memoContent' + memoList[cnt].id + '" class="panel-collapse collapse" role="tabpanel">' +
											'		<div class="panel-body">' + memoList[cnt].content + '</div>' +
											'	</div>' +
											'</div>';
				};

				$("#memoListAccordion").html(memoListDisplay);
				
				memoListDisplay = "";
				
				$('button[name="memoUpdateBtn"]').click(function() {
					
					var memoPanel = $(this).parents('[id^="memoPanel"]');
					var memoId = memoPanel.find('input[name="memoId"]').val();
					var memoTitle = memoPanel.find('span.memoTitleSpan').text();
					var memoContent = memoPanel.find('[id^="memoContent"]').html();
					
					$("#memoUpdateSubModal").find('input[name="memoUpdateId"]').val($(this).parents('div[id^="memoHeading"]').siblings('input[name="memoId"]').val());
					$("#memoUpdateDiv").find('input[name="memoTitle"]').val(memoTitle);
					memoUpdateEditor.setData(memoContent);
					
					$("#memoUpdateSubModal").modal("show");
				});
				
				$('button[name="memoDeleteBtn"]').click(function() {
					ajax.deleteOneMemo($(this).parents('div[id^="memoHeading"]').siblings('input[name="memoId"]').val());
				});
				
				$('button[name="usernameDisplayBtn"]').click(function() {
					var memoPanel = $(this).parents('[id^="memoPanel"]');
					var memoTitle = memoPanel.find('span.memoTitleSpan').text();
					var username = memoPanel.find("span.memoOwnerDisplay").text();
					
					notify.notify(memoTitle, username.substring(3, username.length - 1));
				});
				
				$('#memoPagination-bootpag').bootpag({
					total: $("input#boardMemoSize").val() != 0 ? $("input#boardMemoSize").val() % 10 == 0 ?  $("input#boardMemoSize").val() / 10 : $("input#boardMemoSize").val() / 10 + 1 : 0
				});
			},
			error : function(request, status, error) {
				notify.notify('Ajax 통신 실패  : ' + request.status, 'status : ' + status);
			}
		});
	},
	
	createMemo : function(boardId, memoTitle, memoContent) {
		$.ajax({
			url : '../ajax/memo/createMemo',
			type : 'GET',
			data : {
				"boardId" : boardId,
				"memoTitle" : memoTitle,
				"memoContent" : memoContent
			},
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
//				notify.notify('Ajax 통신 성공', status);
				notify.notify("저장 성공", data.result);
				
				$("#mainModal_tabContent3_memoContent1").find('input[name="memoTitle"]').val("");
				memoEditor.setData("");
				
				var memoUpdateMsg = JSON.stringify({
					username : session.nickname,
					messageBody : "Memo update",
					pageNumber : $('#memoPagination-bootpag').find("li.active").find("a").text()
				});
				
				session.stompClient.send(session.memoUpdateAlarmHandler, {}, memoUpdateMsg);
			},
			error : function(request, status, error) {
				notify.notify('Ajax 통신 실패  : ' + request.status, 'status : ' + status);
			}
		});
	},
	
	updateMemo : function(memoId, memoTitle, memoContent) {
		$.ajax({
			url : '../ajax/memo/updateMemo',
			type : 'GET',
			data : {
				"memoId" : memoId,
				"memoTitle" : memoTitle,
				"memoContent" : memoContent
			},
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
//				notify.notify('Ajax 통신 성공', status);
				notify.notify("갱신 성공", data.result);
				
				$("#memoUpdateSubModal").modal("hide");
				
				$("#memoUpdateDiv").find('input[name="memoTitle"]').val("");
				memoUpdateEditor.setData("");
				
				var memoUpdateMsg = JSON.stringify({
					username : session.nickname,
					messageBody : "Memo update",
					pageNumber : $('#memoPagination-bootpag').find("li.active").find("a").text()
				});
				
				session.stompClient.send(session.memoUpdateAlarmHandler, {}, memoUpdateMsg);
			},
			error : function(request, status, error) {
				notify.notify('Ajax 통신 실패  : ' + request.status, 'status : ' + status);
			}
		});
	},
	
	deleteOneMemo : function(memoId) {
		$.ajax({
			url : '../ajax/memo/deleteOneMemo',
			type : 'GET',
			data : {
				'memoId' : memoId
			},
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
//				notify.notify('Ajax 통신 성공', status);
				notify.notify('message', data.message);
				
				var memoUpdateMsg = JSON.stringify({
					username : session.nickname,
					messageBody : "Memo update",
					pageNumber : $('#memoPagination-bootpag').find("li.active").find("a").text()
				});
				
				session.stompClient.send(session.memoUpdateAlarmHandler, {}, memoUpdateMsg);
			},
			error : function(request, status, error) {
				notify.notify('Ajax 통신 실패  : ' + request.status, 'status : ' + status);
			}
		});
	},
	
	updateJoinMemberTable : function(boardId) {
		$.ajax({
			url : '../ajax/board/updateJoinMemberTable',
			type : 'GET',
			data : {
				"boardId" : boardId
			},
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
//				notify.notify('Ajax 통신 성공 : ', status);
				
				var memberList = data.memberList;
				
				var joinMemberRows = "";
				var role;
				
				for(var cnt in memberList) {
					role = memberList[cnt].username == $("#boardOwner").val() ? "Owner" : "Guest";
					
					joinMemberRows +=
						'<tr>' +
						'	<td>' +
						'		<span class="username">' + memberList[cnt].username + '</span>' +
						'	</td>' +
						'	<td>' +
						'		<span class="userEmail">' + memberList[cnt].email + '</span>' +
						'	</td>' +
						'	<td>' +
						'		<span class="userRole">' + role + '</span>' +
						'	</td>' +
						'</tr>';
				}
				
				$("#joinMemberTableTbody").html(joinMemberRows);
			},
			error : function(request, status, error) {
				notify.notify('Ajax 통신 실패 : ' + request.status, 'status : ' + status);
			}
		});
	},
	
	getHistoriesOfBoard : function(boardId, offset) {
		$.ajax({
			url : '../ajax/board/history/getHistoriesOfBoard',
			type : 'GET',
			data : {
				"boardId" : boardId,
				"offset" : offset
			},
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
//				notify.notify('Ajax 통신 성공 : ', status);
				
				var historyList = data.historyList;
				
				var boardHistoryRows = "";
				
				for(var cnt in historyList) {
					if(historyList[cnt].memberUsername != session.nickname) {
						boardHistoryRows +=
							'<tr>';
					} else {
						boardHistoryRows +=
							'<tr class="success">';
					}
					boardHistoryRows +=
							'	<td>' +
							'		<span class="username">' + historyList[cnt].memberUsername + '</span>' +
							'	</td>' +
							'	<td>' +
							'		<span class="content">' + historyList[cnt].content + '</span>' +
							'	</td>' +
							'	<td>' +
							'		<span class="createDate">' + utils.dateFormat(historyList[cnt].createDate) + '</span>' +
							'	</td>' +
							'</tr>';
				}
				
				$("#boardHistoryTableTbody").html(boardHistoryRows);
			},
			error : function(request, status, error) {
				notify.notify('Ajax 통신 실패 : ' + request.status, 'status : ' + status);
			}
		});
	}
}

$("#joinMemberRefreshBtn").click(function() {
	
//	ajax.updateJoinMemberTable(session.boardId);
//	ajax.getHistoriesOfBoard(session.boardId, $('#historyPagination-bootpag').find("li.active").find("a").text());
});

// =============================================================================================


// $(function() {
$(document).ready(function() {
	
	// Connects to STOMP server after 1 second because immediate connection didn't work
	session.connect();

	utils.executeAllFunctionMembers(eventObj);
	
	for(var ctr in BCModalSet) {
		if((typeof BCModalSet[ctr]) == 'function') {
			BCModalSet[ctr]();
		}
	}

	/*
	$.each(BCModalSet, function(index, element) {
		if((typeof element) == 'function') {
			element();
		}
	});
	*/

	// this is for only testing
//	$('#nicknameInput').focus();

	// Initial setting
	if (utils.isDesktopSize()) {
		resizeFuncs.setInitialBasicHeight();
		basicEditor.setData($("#boardContentFromServer").text());
	} else {
		resizeFuncs.setGapByTogglingClass();
		resizeFuncs.setMobileHeight();
		mobileEditor.setData($("#boardContentFromServer").text());
	}

	/*
		Added because of error of inline editor on Chrome
		Inline editor initialized in hidden tag on Chrome can't be used
	*/
	mobileEditor.on('instanceReady', function() {
		$("#mobileEditor").addClass("visible-xs-block");
	});

	// resize height of both calendar in modal
	resizeFuncs.setBothCalendarHeight();

	$(window).resize(function() {

		// To basic from mobile
		if (utils.isDesktopSize()) {

			// resize editor, chatArea
			resizeFuncs.setBasicHeight();

			// Applied only once after mode has been changed
			if (!(resizeFuncs.formGroupArray[0].hasClass('form-group'))) {
				resizeFuncs.setGapByTogglingClass();
				resizeFuncs.setBasicHeight();
				basicEditor.setData(mobileEditor.getData());
			}

		// To mobile from basic
		} else {

			// resize editor, chatArea
			resizeFuncs.setMobileHeight();

			// Applied only once after mode has been changed
			if (resizeFuncs.formGroupArray[0].hasClass('form-group')) {
				resizeFuncs.setGapByTogglingClass();
				resizeFuncs.setMobileHeight();
				mobileEditor.setData(basicEditor.getData());
			}
		}

		// resize height of both calendar in modal
		resizeFuncs.setBothCalendarHeight();

	}).resize();
});