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
	enterMode: CKEDITOR.ENTER_BR
//	shiftEnterMode: CKEDITOR.ENTER_P
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

basicEditor.config.resize_enabled = false;
basicEditor.config.removePlugins = 'elementspath';

// mobileEditor settings (inline CKEditor)
var mobileEditor = CKEDITOR.inline('mobileEditor');

// memoEditor settings (inline CKEditor)
var memoEditor = CKEDITOR.inline('memoEditor');
$('#memoEditor').height($(window).height() * 0.2);

// ===================================================================================================

var session = {
	
	socket : null,
	stompChat : null,
	stompEditor : null,
	nickname : null,
	id : null,
	
	connect : function() {
		var urlForChat = path.getFullContextPath() + '/websocket/chat';
		var urlForEditor = path.getFullContextPath() + '/websocket/editor';
		this.socket = new SockJS(urlForChat);
		this.socket2 = new SockJS(urlForEditor);
		this.stompChat = Stomp.over(this.socket);
		this.stompEditor = Stomp.over(this.socket2);
		
		this.stompChat.connect({},
			function(frame) {
				notify.notify("Stomp chat connection", frame);
				
				session.stompChat.subscribe('/subscribe/chat', function(message) {
					var msgBody = JSON.parse(message.body);
					
					if(msgBody.message) {
						utils.chatAppend(msgBody.username + ' : ' + msgBody.message);
						
						if(session.nickname != msgBody.username) {
							notify.notify(msgBody.username, msgBody.message);	
						}
					} else {
						utils.chatAppend(msgBody.username);

						if(session.nickname != msgBody.username) {
							notify.notify(msgBody.username, '');
						}
					}
				});
				
				session.stompChat.subscribe('/subscribe/chat/update_alarm', function(alarmMsg) {
					var msgBody = JSON.parse(alarmMsg.body);
					utils.chatAppend("*** Content update by " + msgBody.username);
					notify.notify(msgBody.message, ' by \'' + msgBody.username + '\'');
				});
				
				var tmpMsg = JSON.stringify({
					username : session.nickname + ' 님이 접속했습니다.'
				});
				
				session.stompChat.send('/dest/chat', {}, tmpMsg);
			},
			function(error) {
				notify.notify("Stomp chat connection failed", error);
				session.disconnect();
			}
		);
		
		this.stompEditor.connect({},
			function(frame) {
				notify.notify("Stomp editor connection successful", frame);
				
				session.stompEditor.subscribe('/subscribe/editor', function(content) {
					var contentBody = JSON.parse(content.body);
					
					// desktop
					if (matchMedia("screen and (min-width: 768px)").matches) {
						var ranges = basicEditor.getSelection().getRanges();
						basicEditor.setData(contentBody.content);
						basicEditor.getSelection().selectRanges( ranges );
					// mobile
					} else {
						mobileEditor.setData(contentBody.content);
					}
				});
			},
			function(error) {
				notify.notify("Stomp editor connection failed", error);
			}
		);
		
		/*
		// Unexpected disconnection
		if ($('#joinToggleBtn').html() == ('Exit')) {
			notify.notify('title', 'Chatting is disconnected accidentaly!', 'error');
			notify.notify('title', 'Join again please!', 'error');
			$('#joinToggleBtn').html('Join');
			$('#joinToggleBtn').removeClass('btn-danger');
			$('#joinToggleBtn').addClass('btn-success');
			// jqAjax.deleteRefresh();
		}
		*/		
	},
	
	disconnect : function() {
		
		var tmpMsg = JSON.stringify({
			username : session.nickname + ' 님이 나갔습니다.'
		});
		
		session.stompChat.send('/dest/chat', {}, tmpMsg);
		
		// Close chat stomp
		if(this.stompChat) {
			this.stompChat.disconnect(function() {
				notify.notify("Disconnection", "Stomp chat disconnected");
			});
			this.stompChat = null;
		};
		
		// Close editor stomp
		if(this.stompEditor) {
			this.stompEditor.disconnect(function() {
				notify.notify("Disconnection", "Stomp editor disconnected");
			});
			this.stompEditor = null;
		};
		
		// Close socket
		if(this.socket) {
			this.socket.close();
			this.socket = null;
		}
		if(this.socket2) {
			this.socket2.close();
			this.socket2 = null;
		}
		
		notify.notify("Disconnection", "fully disconnected");
	},
	
	reconnect : function() {
		this.disconnect();
		this.connect();
	},
	
	// Send data to socket
	msgSend : function() {
		var msg = JSON.stringify({
			username : session.nickname,
			message : $('#chatInput').val()
		});
		
		this.stompChat.send('/dest/chat', {}, msg);
		
		$('#chatInput').val('');
		msg = null;
	},
	
	contentSend : function() {
		// when editorContentSocket is connected
		if (this.stompEditor) {
			// desktop
			if (matchMedia("screen and (min-width: 768px)").matches) {
				var basicEditorContent = JSON.stringify({
					content : basicEditor.getData()
				});
				session.stompEditor.send('/dest/editor', {}, basicEditorContent);
				basicEditor.focus();

			// mobile
			} else {
				var mobileEditorContent = JSON.stringify({
					content : mobileEditor.getData()
				});
				session.stompEditor.send('/dest/editor', {}, mobileEditorContent);
				
				mobileEditor.focus();
			}
			
			var updateMsg = JSON.stringify({
				username : '컨텐츠 갱신 by ' + session.nickname 
			});
			
			this.stompChat.send('/dest/chat', {}, updateMsg);
			
		} else {
			$('#editorInputBtn').blur();
		}
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
		if (!session.socket) {
			notify.notify('title', 'You need to join first!');
			$('#nicknameInput').focus();
		} else if (!(this.checkStr($('#chatInput').val()))) {
			$('#chatInput').focus();
		} else {
			session.msgSend();
			$('#chatInput').focus();
		}
	},
	
	// Verify and control Join condition
	verifyJoin : function() {
		if (!session.socket) {
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
				if (!(matchMedia("screen and (min-width: 768px)").matches)) {
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
			if (!(matchMedia("screen and (min-width: 768px)").matches)) {
				if($('.navbar-toggle').css('display') !='none'){
					$(".navbar-toggle").trigger( "click" );
				}
	        }
			
			// jqAjax.deleteRefresh();
			
			// Focus back to nickname input
			$('#nicknameInput').focus();
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

/*
eventFunc.collapseBtnFocusMove();
chatInputKeyEvent();
badgeEvent();
toggleChatNotifyEvent();
toggleJoinEvent();
*/
var eventObj = {
	
	collapseBtnFocusMove : function() {
		$('#collapseBtn').click(function() {
			// <![CDATA[
			if (!(matchMedia("screen and (min-width: 768px)").matches) && !(session.socket)) {
			// ]]>
				setTimeout(function() {
					$('#nicknameInput').focus();
				}, 500);
			}
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
			
			// CTRL + SHIFT
			// <![CDATA[
			if (event.which == 16 && isCtrl) {
			// ]]>
				notify.notify('title', 'CTRL + SHIFT clicked! (chatInput)');
				
				// Focus to basic editor
				if(matchMedia("screen and (min-width: 768px)").matches) {
					basicEditor.focus();
					
				// Focus to mobile editor
				} else {
					mobileEditor.focus();
				}
			}
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
	
	
	
	// Toggle toolbar
	toggleToolbarEvent : function() {
		$('#toolbarToggleBtn').click(function() {
			func.toggleToolbarFunc();
		});
		$('#basicStatus').click(function() {
			func.toggleToolbarFunc();
			badge.reset();
		});
		$('#mobileStatus').click(function() {
			func.toggleToolbarFunc();
		});
	},
	
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
	
	showHeightsEvent : function() {
		$('#showHeights').click(function() {
			utils.showHeight();
		});
	},
	
	editorInputSendEvent : function() {
		$('#editorInputBtn').click(function() {
			session.contentSend();
		});
	},
	
	editorSaveEvent : function() {
		$('#editorSaveBtn').click(function() {
			// desktop
			if (matchMedia("screen and (min-width: 768px)").matches) {
//				jqAjax.saveBoard(basicEditor.document.getBody().getText());
				jqAjax.saveBoard(basicEditor.getData());
				
				var alarmMsg = JSON.stringify({
					username : session.nickname,
					message : "Content update"
				});
				
				session.stompChat.send("/dest/chat/update_alarm", {}, alarmMsg);
				
			// mobile
			} else {
//				jqAjax.saveBoard(mobileEditor.document.getBody().getText());
				jqAjax.saveBoard(mobileEditor.getData());
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
			jqAjax.deleteAllBoards();
		});
	},
	
	editorKeyEvent : function() {
		
		basicEditor.on('key', function(event){
			editorKeyEventAssistant(event);
		});
		
		mobileEditor.on('key', function(event){
			editorKeyEventAssistant(event);
		});
		
		function editorKeyEventAssistant(event) {
			var keyCode = event.data.keyCode; 

			// CTRL + SHIFT
			if(keyCode == 3342352) {
//				notify.notify('title', 'CTRL + SHIFT clicked! (editor)');
				$('#chatInput').focus();
				
			// ENTER
			} else if(keyCode == 1114125) {
//				notify.notify('title', 'ENTER clicked! (editor)');
				$('#editorInputBtn').trigger('click'); 
			}
			keyCode = null;
		};
		
		// <![CDATA[
		// ]]>

		// 예비 함수
		
		/*
		 * basicEditor.on('contentDom', function() {
		 * basicEditor.document.on('keydown', function(event) { //
		 * notify.notify(event.data.getKey()); if(event.data.getKey() ==
		 * 13) { notify.notify('enter clicked!');
		 * $('#editorInputBtn').trigger('click'); } }); });
		 * 
		 * mobileEditor.on('contentDom', function() {
		 * mobileEditor.document.on('keydown', function(event) { //
		 * notify.notify(event.data.getKey()); if(event.data.getKey() ==
		 * 13) { notify.notify('enter clicked!');
		 * $('#editorInputBtn').trigger('click'); } }); });
		 */
		/*
		 * basicEditor.on('key', function(event){ // code });
		 * 
		 * basicEditor.on('key', function (evt) { var kc =
		 * evt.data.keyCode, csa = ~(CKEDITOR.CTRL | CKEDITOR.SHIFT |
		 * CKEDITOR.ALT); //<![CDATA[ console.log(kc, kc & csa); // kc &
		 * csa is what you need //]]> });
		 * 
		 * basicEditor.on('contentDom', function() {
		 * basicEditor.editable().attachListener('keyup',
		 * basicEditor.document, function( event ) { // code }); });
		 * 
		 * basicEditor.document.on('keydown', function(evt) {
		 * notify.notify(evt.data.getKeystroke() + ', ' +
		 * evt.data.getKey()); // code });
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

var func = {
	// Toggle toolbar function
	toggleToolbarFunc : function() {
		setTimeout(function() {
//			this.toggle = !this.toggle;
			
//			$('#cke_3_top').toggle();
//			$('#cke_4_top').toggle();
			$('#toolbarToggle').toggleClass('glyphicon-ice-lolly-tasted');
			$('#toolbarToggle').toggleClass('glyphicon-ice-lolly');
			if (matchMedia("screen and (min-width: 768px)").matches) {
				resizeFuncs.setBasicHeight();
				basicEditor.focus();
			} else {
				$('#mobileEditor').focus();
			}
		}, 135);
		
		/*
		setTimeout(function() {
			
			var str = 'data-original-title';
			
			if($('#toolbarToggle').hasClass('glyphicon-ice-lolly-tasted')) {
				$('#toolbarToggleBtn').attr(str, 'Hide toolbar!').tooltip('hide');
				$('#basicStatus').attr(str, 'Hide toolbar!').tooltip('hide');
				$('#mobileStatus').attr(str, 'Hide toolbar!').tooltip('hide');
			} else {
				$('#toolbarToggleBtn').attr(str, 'Show toolbar!').tooltip('hide');
				$('#basicStatus').attr(str, 'Show toolbar!').tooltip('hide');
				$('#mobileStatus').attr(str, 'Show toolbar!').tooltip('hide');
			}
		}, 135);
		*/
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
	
	// There was a problem that editor's height is not compatible with
	// chatArea's one at some point
	// I think it was from CKEditor's toolbar height changes.
	// So I've deicded to use an if statement to ensure it's compatible
	// when resizing
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
			// keyword 'this' in 'on' method of CKEditor instance means
			// basicEditor itself, so I specified 'resizeFuncs' object
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

//test block
$('#test1').click(function() {
	// jqAjax.test1();
});

// $(document).ready
// ======================================================================================

// $(function() {
$(document).ready(function() {

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
	$('#nicknameInput').focus();
	
	// Initial setting
	if (matchMedia("screen and (min-width: 768px)").matches) {
		// utils.chatAppend("큰 화면 전용");
		resizeFuncs.setInitialBasicHeight();
	} else {
		// utils.chatAppend("작은 화면 전용");
		resizeFuncs.setGapByTogglingClass();
		resizeFuncs.setMobileHeight();
	}
	
	// resize height of both calendar in modal
	resizeFuncs.setBothCalendarHeight();
	
	$(window).resize(function() {
		
		// To basic from mobile
		if (matchMedia("screen and (min-width: 768px)").matches) {
			
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