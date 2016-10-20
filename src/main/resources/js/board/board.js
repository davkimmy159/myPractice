// <![CDATA[
// ]]>

var jqObj = {
	
	editorContainer : $('#editorContainer'),
	chatContainer : $('#chatContainer'),
	
	mobileEditor : $('#mobileEditor'),
	
	chatArea : $('#chatArea'),
};

// basicEditor settings (CKEditor)
var basicEditor = CKEDITOR.replace('basicEditor', {
	contentsCss  : 'body {white-space: nowrap;}',
	customConfig : 'config2.js',
	toolbarCanCollapse: true,
	resize_enabled: false,
	removePlugins: 'elementspath',
	enterMode: CKEDITOR.ENTER_BR
//	shiftEnterMode: CKEDITOR.ENTER_P
	,
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
var memoEditor = CKEDITOR.inline('memoEditor');
$('#memoEditor').height($(window).height() * 0.2);

// ===================================================================================================


var session = {
	
	socket : null,
	stompClient : null,
	nickname : null,
	boardId : null,
	chatHandler: "/dest/chat/",
	editorHandler: "/dest/editor/",
	dbUpdateAlarmHandler: "/dest/chat/db_update_alarm/",
	
	chatSubscribe: "/subscribe/chat/",
	editorSubscribe: "/subscribe/editor/",
	dbUpdateAlarmSubscribe: "/subscribe/chat/db_update_alarm/",
	
	
	connect : function() {

		// Sets path of destination and subscribe point for each board
		this.chatHandler += $("input#boardId").val();
		this.editorHandler += $("input#boardId").val();
		this.dbUpdateAlarmHandler += $("input#boardId").val();
		
		this.chatSubscribe += $("input#boardId").val();
		this.editorSubscribe += $("input#boardId").val();
		this.dbUpdateAlarmSubscribe += $("input#boardId").val();
		
		// Sets nickname
		this.nickname = $("#loginIdDisplayDesktop").text();
		
		// Sets board id
		this.boardId = $("#boardId").val();
		
		var websocketUrl = path.getFullContextPath() + '/websocket/board';
		this.socket = new SockJS(websocketUrl);
		this.stompClient = Stomp.over(this.socket);
		
		this.stompClient.connect({},
			function(frame) {
				notify.notify("Stomp connection for board", frame);
				
				// chat subscribe 1
				session.stompClient.subscribe(session.chatSubscribe, function(messageData) {
					var chatMessage = JSON.parse(messageData.body);

					utils.chatAppend(chatMessage.chatAreaMessage);
//					notify.notify(chatMessage.username, chatMessage.messageBody);
					
					if(session.nickname != chatMessage.username) {
						notify.notify(chatMessage.username, chatMessage.messageBody);
					}
				});
				
				// chat subscribe 2
				session.stompClient.subscribe(session.dbUpdateAlarmSubscribe, function(alarmMessage) {
					var alarmMsgBody = JSON.parse(alarmMessage.body);
					
					utils.chatAppend(alarmMsgBody.chatAreaMessage);
//					notify.notify(alarmMsgBody.messageBody, ' by \'' + alarmMsgBody.username + '\'');
					
					if(session.nickname != alarmMsgBody.username) {
						notify.notify(alarmMsgBody.messageBody, " by '" + alarmMsgBody.username + "\'", "success");
					}
					
					// Updates html page of DB list
//					jqAjax.updateList();
				});
				
				// editor subscribe 1
				session.stompClient.subscribe(session.editorSubscribe, function(contentData) {
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
					
					utils.chatAppend(contentDataBody.chatAreaMessage);
//					notify.notify("Editor update", " by '" + contentDataBody.username + "'");
					
					if(session.nickname != contentDataBody.username) {
						notify.notify("Editor update", " by '" + contentDataBody.username + "'", "success");
					}
				});
				
				var joinMsg = JSON.stringify({
					username : session.nickname,
					messageBody : ' 님이 접속했습니다.'
				});
				
				session.stompClient.send(session.chatHandler, {}, joinMsg);
			},
			function(error) {
				
				var joinMsg = JSON.stringify({
					username : session.nickname,
					messageBody : ' test'
				});
				session.stompClient.send(session.chatHandler, {}, joinMsg);
				
				utils.chatAppend("Error : Stomp  connection failed :");
				utils.chatAppend(error);
				notify.notify("Error", "Stomp connection failed", error);
				session.disconnect();
			}
		);
	},
	
	disconnect : function() {
		
		var exitMsg = JSON.stringify({
			username : session.nickname,
			messageBody : ' 님이 나갔습니다.'
		});
		
		session.stompClient.send(session.chatHandler, {}, exitMsg);
		
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
		utils.chatAppend("Stomp reconnecting...");
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
	
	editorContentSend : function() {
		
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

		session.stompClient.send(session.editorHandler, {}, JSON.stringify(editorContent));
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

var utils = {
	
	// chatArea append function
	chatAppend : function(str) {
		$('#chatArea').append(str + '\n');
		$('#chatArea').scrollTop($('#chatArea')[0].scrollHeight);
	},
	
	// Checks whether session is alive
	isSessionAlive : function() {
		return session.socket && session.stompClient;
	},
	
	isDesktopSize : function() {
		return matchMedia("screen and (min-width: 768px)").matches;
	},
	
	// focus move
	focusToEditor : function() {
		if (utils.isDesktopSize()) {
			basicEditor.focus();
		} else {
			mobileEditor.focus();
		}	
	},
	
	// detects sizes
	editorSizeDetectFunc : function(basicEditorFunction, mobileEditorFunction) {
		if(((typeof basicEditorFunction) == 'function') && ((typeof mobileEditorFunction) == 'function')) {
			if (utils.isDesktopSize()) {
				basicEditorFunction();
			} else {
				mobileEditorFunction();
			}	
		} else {
			notify.notify("editorSizeDetectFunc error", "parameter is not function!");
		}
	},
	
	// session check v1
	workWithSession : function(functionWithSession, functionWithoutSession) {
		if(((typeof functionWithSession) == 'function') && ((typeof functionWithoutSession) == 'function')) {
			if (utils.isSessionAlive()) {
				functionWithSession();
			} else {
				functionWithoutSession();
			}	
		} else {
			notify.notify("workWithSession error", "parameter is not function!");
		}
	},

	// session check v2
	workWithSession2 : function() {
		if(!(session.socket || session.stompClient)) {
			return notify.notify("workWithSession error", "parameter is not function!");	
		}	
	},
	
	toggleClassArrayFunc : function(array, str) {
		for ( var ctr in array)
			array[ctr].toggleClass(str);
	},
	
	addClassFunc : function(array, str) {
		for ( var ctr in array)
			array[ctr].addClass(str);
	},
	
	removeClassFunc : function(array, str) {
		for ( var ctr in array)
			array[ctr].removeClass(str);
	},
		
	// Check if string is not both null and empty
	checkStr : function(str) {
		// <![CDATA[
		if (((typeof str != "undefined") && (typeof str.valueOf() == "string")) && (str.length > 0))
		// ]]>
			return true;
		else
			return false;
	},
	
	// veryfy chat input
	verifyChatInput : function() {
		if (utils.isSessionAlive()) {
			if (this.checkStr($('#chatInput').val())) {
				session.chatMsgSend();
				$('#chatInput').focus();
			} else {
				$('#chatInput').focus();
			}
		} else {
			notify.notify('Join required', 'You need to join first!');
//			$('#nicknameInput').focus();
		}
	},
	
	/*
	// Verify and control Join condition
	verifyJoin : function() {
		if (!(session.socket) || !(session.stompClient)) {
			if (this.checkStr($('#nicknameInput').val())) {
				session.nickname = $('#nicknameInput').val();
				$('#nicknameInput').val("");
				
				session.connect();
				
				$('#joinToggleBtn').html('Exit');
				$('#joinToggleBtn').removeClass('btn-success');
				$('#joinToggleBtn').addClass('btn-danger');
				
				$('#nicknameInput').val('');
				$('#nicknameInput').prop("disabled", true);
				$('#nicknameInput').attr('placeholder', session.nickname);
				
				// Hide navbar collapsible menus in mobile
				if (!(utils.isDesktopSize())) {
					if($('.navbar-toggle').css('display') !='none'){
						$(".navbar-toggle").trigger( "click" );
					}
		        }
				
				// AJAX
				// Sends nickname to server to save in DB and refresh member table
				// jqAjax.insertRefresh();
				
				// Focus back to chat input
				$('#chatInput').focus();
			} else {
				$('#nicknameInput').attr('placeholder', 'Worng! type again!');
				$('#nicknameInput').focus();
			}
		} else {
			if(this.nickname) {
				this.nickname = null;
			};
			
			session.disconnect();
			
			$('#joinToggleBtn').html('Join');
			$('#joinToggleBtn').removeClass('btn-danger');
			$('#joinToggleBtn').addClass('btn-success');
			
			$('#nicknameInput').val('');
			$('#nicknameInput').prop("disabled", false);
			$('#nicknameInput').attr('placeholder', 'Type nickname');
			
			// Hide navbar content (in effect only in mobile mode)
			if (!(utils.isDesktopSize())) {
				if($('.navbar-toggle').css('display') !='none'){
					$(".navbar-toggle").trigger( "click" );
				}
	        }
			
			// jqAjax.deleteRefresh();
			
			// Focus back to nickname input
			$('#nicknameInput').focus();
		}
	},
	*/
	
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

/*
eventFunc.collapseBtnFocusMove();
chatInputKeyEvent();
badgeEvent();
toggleChatNotifyEvent();
toggleJoinEvent();
*/
var eventObj = {
	
	mobileEditorToolbarHide : function() {
		$('#editorSaveBtn').click(function() {
			
		});
	},
		
	
	/*
	// For focus on nickname input on mobile view
	collapseBtnFocusMove : function() {
		$('#collapseBtn').click(function() {
			// <![CDATA[
			if (!(utils.isDesktopSize()) && !(session.socket)) {
			// ]]>
				setTimeout(function() {
					$('#nicknameInput').focus();
				}, 500);
			}
		});
	},
	*/
	

	
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
//			notify.toggle ? notify.toggle = false : notify.toggle = true;
			this.blur();
		});
	},
	
	/*
	toggleJoinEvent : function() {
		// Toggle join (button)
		$('#joinToggleBtn').click(function() {
			utils.verifyJoin();
		});
		// Toggle join (input text)
		$('#nicknameInput').keypress(function(event) {
			if (event.keyCode === 13)
				utils.verifyJoin();
		});
	},
	*/
	
	/*
	windowOutEvent : function() {
		// Logout, close event with 'esc' key on browser window
		$(window).keydown(function(event) {
			if (event.keyCode == 27) {
				utils.chatAppend('esc!!');
				if (session.socket) {
					// utils.chatAppend('Exit now!');
					$('#joinToggleBtn').html('Join');
					$('#joinToggleBtn').removeClass('btn-danger');
					$('#joinToggleBtn').addClass('btn-success');
					$('#nicknameInput').val('');
					$('#nicknameInput').attr('placeholder', 'Type nickname');
					session.disconnect();
					session.nickname = null;
					$('#nicknameInput').focus();
				} else {
					// 창 닫는 함수 (작동 안 함)
					// window.open("about:blank","_self").close();
				}
			}
		});
	},
	*/
	
	showHeightsEvent : function() {
		$('#showHeights').click(function() {
			utils.showHeight();
		});
	},
	
	editorInputSendEvent : function() {
		$('#editorInputBtn').click(function() {
			if (utils.isSessionAlive()) {
				session.editorContentSend();
				
				utils.focusToEditor();
			} else {
				notify.notify('Join required', 'You need to join first!');
//				$('#nicknameInput').focus();
			}
		});
	},
	
	editorSaveEvent : function() {
		$('#editorSaveBtn').click(function() {
			if (utils.isSessionAlive()) {
				
				// desktop
				if (utils.isDesktopSize()) {
					jqAjax.updateBoardDB(session.boardId, basicEditor.getData());
					
				// mobile
				} else {
					jqAjax.updateBoardDB(session.boardId, mobileEditor.getData());
				}
//				jqAjax.saveBoard(basicEditor.document.getBody().getText());

				var dbUpdateMsg = JSON.stringify({
					username : session.nickname,
					messageBody : "DB content update"
				});
				
				session.stompClient.send(session.dbUpdateAlarmHandler, {}, dbUpdateMsg);
				
				utils.focusToEditor();
			} else {
				notify.notify('Join required', 'You need to join first!');
//				$('#nicknameInput').focus();
			}
			
			/*
			Array.prototype
				 .map
				 .call(basicEditor.document.getBody().$.childNodes, function(item) {
					 return item.innerText;
				 })
				 .join("\n");
			*/
		});
	},
	
	editorDelEvent : function() {
		$('#listDelBtn').click(function() {
//			jqAjax.deleteAllBoards();
			
			var dbUpdateMsg = JSON.stringify({
				username : session.nickname,
				messageBody : "All contents have been deleted"
			});
			
			session.stompClient.send(session.dbUpdateAlarm, {}, dbUpdateMsg);
			
		});
	},
	
	chatInputKey : function() {
		
		// for CTRL + SHIFT
		var isCtrl = false;
		
		// Focus back to chatInput on pressing enter key on chatInput
		$('#chatInput').keydown(function(event) {
			
			// ENTER
			if (event.which === 13) {
				utils.verifyChatInput();
				
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
			
//			utils.chatAppend("Ctrl : " + isCtrl);
		});
		
		// Toggle isCtrl false
		$('#chatInput').keyup(function(event) {
			if(event.which == 17) {
				isCtrl=false;
			}
		});
		
		// Focus back to chatInput on clicking chatInputBtn
		$('#chatInputBtn').click(function() {
			utils.verifyChatInput();
		});
	},
	
	ctrlAlt : CKEDITOR.CTRL + CKEDITOR.ALT + 18,
	ctrlEnter : CKEDITOR.CTRL + 13,
	
	editorEvent : function() {

	},
	
	editorResizeCollapseEvent : function() {
		/*
		basicEditor.on('contentDom', function() {
		    basicEditor.document.on('click', function(event) {
		        notify.notify("test", "test");
		        notify.notify("test", CKEDITOR.CTRL);
		    });
		});
		*/
	},
	
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
var pnotifyFunc = {
		
	toggleChatNotify : function() {
		
		var str = 'data-original-title';
		
		notify.toggle = !notify.toggle;
		$('#chatNotifyToggle').toggleClass('glyphicon-eye-close');
		$('#chatNotifyToggle').toggleClass('glyphicon-eye-open');
		if(notify.toggle) {
			$('#chatNotifyToggleBtn').attr(str, 'Hide chat notification!').tooltip('hide');			        
		}
		else {
			$('#chatNotifyToggleBtn').attr(str, 'Show chat notification!').tooltip('hide');
		}
	}
};
*/

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

/*
var tooltipFunc = {

	tooltipApply : function() {
		// $('#example').tooltip(options)
		
		$('#joinToggleBtn').tooltip({
			title : "Type your name and Join",
			placement : "bottom",
			container : "body"
		});

		$('#basicStatus').tooltip({
			title : "Hide toolbar!",
			placement : "bottom"
		});
		
		$('#mobileStatus').tooltip({
			title : "Hide toolbar!", 
			placement : "bottom"
		});
		
		$('#editorInputBtn').tooltip({
			title : '"CTRL + ENTER" or "CTRL + SHIFT"',
			placement : "top",
			container : "body"
		});
		
		$('#toolbarToggleBtn').tooltip({
			title : "Hide toolbar!",
			placement : "top",
			container : "body"
		});
		
		$('#chatNotifyToggleBtn').tooltip({
			title : "Hide chat notification!",  
			placement : "top",
			container : "body"
		});

	},
		
	tooltipActivate : function() {
		$('[data-toggle="tooltip"]').tooltip();
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

// =============================================================================================

// $(function() {
$(document).ready(function() {
	
	// Connects to STOMP server after 1 second
	// Immediate connection didn't work
	setTimeout(function() {
		session.connect();
	}, 1000);

	$.each(eventObj, function(index, element) {
		if((typeof element) == 'function') {
			element();
		}
	});
	
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