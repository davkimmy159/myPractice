var jqObj = {
	
	editorContainer : $('#editorContainer'),
	chatContainer : $('#chatContainer'),
	
	mobileEditor : $('#mobileEditor'),
	memoEditor : $('#memoEditor'),
	basicCalendarInputEditor : $('#basicCalendarInputEditor'),
	basicCalendarEventContentViewer : $('#basicCalendarEventContentViewer'),
	yearlyCalendarInputEditor : $('#yearlyCalendarInputEditor'),
	
	chatArea : $('#chatArea'),
	
	chatInput : $('#chatInput'),
	nicknameInput : $('#nicknameInput'),
	
	IMColla : $('#IMColla'),
	
	editorBtnGroupDiv : $('#editorBtnGroupDiv'),
	
	editorInputBtn : $('#editorInputBtn'),
	chatInputBtn : $('#chatInputBtn'),
	joinToggleBtn : $('#joinToggleBtn'),
	toolbarToggleBtn : $('#toolbarToggleBtn'),
	toolbarToggle : $('#toolbarToggle'),
	chatNotifyToggleBtn : $('#chatNotifyToggleBtn'),
	chatNotifyToggle : $('#chatNotifyToggle'),
	collapseBtn : $('#collapseBtn'),

	btnGroup1 : $('#btnGroup1'),
	btnGroup2 : $('#btnGroup2'),

	formGroup1 : $('#formGroup1'),
	formGroup2 : $('#formGroup2'),
	formGroup3 : $('#formGroup3'),
	
	basicStatusBadge : $('#basicStatusBadge'),
	mobileStatusBadge : $('#mobileStatusBadge'),
	basicStatus : $('#basicStatus'),
	mobileStatus : $('#mobileStatus'),

	basicCalendar : $('#basicCalendar'),
	yearlyCalendar : $('#yearlyCalendar'),

	mainModal : $('#mailModal'),
	basicCalendarModal : $('#basicCalendarModal'),
	BCEventViewerModal : $('#BCEventViewerModal'),
	
	yearlyCalendarModal : $('#yearlyCalendarModal'),
	
	joinTable : $('#joinTable')
	
};

// basicEditor settings (CKEditor)
var basicEditor = CKEDITOR.replace('basicEditor', {
	contentsCss  : 'body {white-space: nowrap;}'
});
basicEditor.config.resize_enabled = false;
basicEditor.config.removePlugins = 'elementspath';

// mobileEditor settings (inline CKEditor)
var mobileEditor = CKEDITOR.inline('mobileEditor');

// memoEditor settings (inline CKEditor)
var memoEditor = CKEDITOR.inline('memoEditor');
$('#memoEditor').height($(window).height() * 0.2);

// BCInputEditor settings (inline CKEditor)
var basicCalendarInputEditor = CKEDITOR.inline('basicCalendarInputEditor');
jqObj.basicCalendarInputEditor.height($(window).height() * 0.2);

// BCEventContentViewer settings (inline CKEditor)
var basicCalendarEventContentViewer = CKEDITOR.inline('basicCalendarEventContentViewer', {
    removePlugins: 'toolbar'
});
jqObj.basicCalendarEventContentViewer.height($(window).height() * 0.2);

// YCInputEditor settings (inline CKEditor)
var yearlyCalendarInputEditor = CKEDITOR.inline('yearlyCalendarInputEditor');
jqObj.yearlyCalendarInputEditor.height($(window).height() * 0.2);



// FullCalendar (in modal)
var basicCalendar = $("#basicCalendar").fullCalendar({
	header: {
		left : 'prev,next,today',
		center : 'title',
		right : 'month,agendaWeek,agendaDay'
	},
	selectable : true,
	selectHelper : true,
	editable : true,
	timezone : 'local',
	displayEventEnd : true,
	timeFormat : 'A hh : mm',
	
	eventLimit : 3,
	
	
	// initial height set for resizing later(removing vertical scroll
	// bar)
	contentHeight: 100,
	
	views: {
		month : {
			titleFormat : 'YYYY년 M월'
		},
		agenda: {
			titleFormat : 'M월 D일'	
		},
		week : {
		
		},
		day : {

		}
	},
	
	// fired when its calendar cell is clicked or dragged
	select: function(start, end) {
		BCModalFunc.select(start, end);
	},
	
	eventClick: function(event, jsEvent, view) {
		BCEventViewerModalFunc.clickEventFor(event, jsEvent, view);
    },
    
	events: [
		{
			id : 999,
			title : 'Click for Google',
			start : '2016-05-01',
			end : '2016-05-03',
			editable : true,
			allDay : true
		},
		{
			id : 9998,
			title : 'test',
			start : '2016-05-01',
			end : '2016-05-04',
			editable : true,
			allDay : true
		},
		{
			id : 997,
			title : 'test2',
			start : '2016-05-01',
			end : '2016-05-05',
			editable : true
		},
		{
			id : 996,
			title : 'test3',
			start : '2016-05-01',
			end : '2016-05-06',
			editable : true
		}
	]
});

// must be called right after the declaration of fullCalendar
$('#mainModal').on('shown.bs.modal', function () {
	basicCalendar.fullCalendar('render');
});

var BCModalFunc = {
	
	BCEventVO : {},
	
	BCEventIdCnt : 1,
	
	BCEventId : jqObj.basicCalendarModal.find('input[name="BCEventId"]'),
	BCEventTitle : jqObj.basicCalendarModal.find('input[name="BCEventTitle"]'),
	BCEventContentEditor : basicCalendarInputEditor,
	
	BCEventAllDayToggle : jqObj.basicCalendarModal.find('input[name="BCEventAllDayToggle"]'),
	BCEventStartDate : jqObj.basicCalendarModal.find('input[name="BCEventStartDate"]'),
	BCEventEndDate : jqObj.basicCalendarModal.find('input[name="BCEventEndDate"]'),
	
	BCModalSaveBtn : jqObj.basicCalendarModal.find('button[name="BCModalSaveBtn"]'),
	BCModalResetBtn : jqObj.basicCalendarModal.find('button[name="BCModalResetBtn"]'),
	BCModalCloseBtn : jqObj.basicCalendarModal.find('button[name="BCModalCloseBtn"]'),

	BCInitialSettings : function() {
		allDayFormatStr = 'YYYY.MM.DD ddd';
		nonAllDayFormatStr = 'YYYY.MM.DD ddd A hh:mm';
		formatStr = this.BCEventAllDayToggle.prop('checked') ? allDayFormatStr : nonAllDayFormatStr;
		
		this.BCEventStartDate.datetimepicker({
			format : formatStr,
			stepping : 5,
			sideBySide : true,
			calendarWeeks : true,
			showTodayButton : true,
			showClear : true,
			showClose : true,
			toolbarPlacement : 'bottom',
			widgetPositioning : {
				horizontal: 'right',
				vertical: 'top'
			}
		});
		this.BCEventEndDate.datetimepicker({
			useCurrent: false,
			format : formatStr,
			stepping : 5,
			sideBySide : true,
			calendarWeeks : true,
			showTodayButton : true,
			showClear : true,
			showClose : true,
			toolbarPlacement : 'top',
			widgetPositioning : {
				horizontal: 'right',
				vertical: 'bottom'
			}
		});
		this.BCEventAllDayToggle.bootstrapToggle({
			on : 'Yes',
			off : 'No',
			width : 75
		});
		
		this.BCEventStartDate.on("dp.change", function (e) {
			BCModalFunc.BCEventEndDate.data("DateTimePicker").minDate(e.date);
        });
		this.BCEventEndDate.on("dp.change", function (e) {
			BCModalFunc.BCEventStartDate.data("DateTimePicker").maxDate(e.date);
        });
		
		this.BCEventAllDayToggle.change(function() {
			if (BCModalFunc.BCEventAllDayToggle.prop("checked")) {
				BCModalFunc.BCEventStartDate.data("DateTimePicker").format(allDayFormatStr);
				BCModalFunc.BCEventEndDate.data("DateTimePicker").format(allDayFormatStr);
			} else {
				BCModalFunc.BCEventStartDate.data("DateTimePicker").format(nonAllDayFormatStr);
				BCModalFunc.BCEventEndDate.data("DateTimePicker").format(nonAllDayFormatStr);
			}
		});
	},
	
	select : function(start, end) {
		start.tz("Asia/Seoul");
		end.tz("Asia/Seoul");
		end.subtract(16, 'hours');
		
		this.BCEventId.val(BCModalFunc.BCEventIdCnt);
		
		this.BCEventStartDate.data("DateTimePicker").date(start);
		this.BCEventEndDate.data("DateTimePicker").date(end);
		
		jqObj.basicCalendarModal.modal('show');
		
		$("#basicCalendar").fullCalendar('unselect');
	},
	
	BCModalSaveBtnFunc : function() {
		this.BCModalSaveBtn.click(function() {
			BCModalFunc.BCEventVO.id = BCModalFunc.BCEventId.val();
			BCModalFunc.BCEventVO.title = BCModalFunc.BCEventTitle.val();
			BCModalFunc.BCEventVO.content = BCModalFunc.BCEventContentEditor.getData();
			
			BCModalFunc.BCEventVO.allDay = BCModalFunc.BCEventAllDayToggle.prop("checked");

			if(BCModalFunc.BCEventVO.allDay) {
				BCModalFunc.BCEventVO.startDate = BCModalFunc.BCEventStartDate.data("DateTimePicker").date();
				BCModalFunc.BCEventVO.endDate = BCModalFunc.BCEventEndDate.data("DateTimePicker").date().add(1, 'days');
			} else {
				BCModalFunc.BCEventVO.startDate = BCModalFunc.BCEventStartDate.data("DateTimePicker").date();
				BCModalFunc.BCEventVO.endDate = BCModalFunc.BCEventEndDate.data("DateTimePicker").date();
			}
			
			BCModalFunc.BCEventVO.displayEventTime = !BCModalFunc.BCEventVO.allDay;
			BCModalFunc.BCEventVO.editable = true;
			
			if(BCModalFunc.BCEventVO.title) {
				if(BCModalFunc.BCEventVO.content) {
					basicCalendar.fullCalendar('renderEvent', {
							id : BCModalFunc.BCEventVO.id, 
							title : BCModalFunc.BCEventVO.title,
							content : BCModalFunc.BCEventVO.content,
							
							allDay : BCModalFunc.BCEventVO.allDay,
							start : BCModalFunc.BCEventVO.startDate,
							end : BCModalFunc.BCEventVO.endDate,
							
							displayEventTime : BCModalFunc.BCEventVO.dispalyEventTime,
							editable : BCModalFunc.BCEventVO.editable
						},
						true // make the event "stick"
					);
					
					BCModalFunc.BCEventIdCnt++;
					
					BCModalFunc.clearInputData();
					
					jqObj.basicCalendarModal.modal('hide');
				} else {
					notify.notify('input content!', 'error');
				}
			} else {
				notify.notify('input title!', 'error');
			}
		});
	},
	
	BCModalResetBtnFunc : function() {
		this.BCModalResetBtn.click(function() {
			BCModalFunc.clearInputData();
		});
	},
	
	BCModalCloseBtnFunc : function() {
		this.BCModalCloseBtn.click(function() {
			jqObj.basicCalendarModal.modal('hide');
		});
	},
	
	clearInputData : function() {
		this.BCEventTitle.val('');
		basicCalendarInputEditor.setData('');
	},
	
	addAllEventsAndInitSet : function() {
		this.BCInitialSettings();
		this.BCModalSaveBtnFunc();
		this.BCModalResetBtnFunc();
		this.BCModalCloseBtnFunc();
	}
};

var BCEventViewerModalFunc = {
	
	BCEventId : jqObj.BCEventViewerModal.find('input[name="BCEventId"]'),
	BCEventTitle : jqObj.BCEventViewerModal.find('input[name="BCEventTitle"]'),
	BCEventContentViewer : basicCalendarEventContentViewer,
	
	BCEventAllDayToggle : jqObj.BCEventViewerModal.find('input[name="BCEventAllDayToggle"]'),
	BCEventStartDate : jqObj.BCEventViewerModal.find('input[name="BCEventStartDate"]'),
	BCEventEndDate : jqObj.BCEventViewerModal.find('input[name="BCEventEndDate"]'),
	
	BCModalModifyBtn : jqObj.BCEventViewerModal.find('button[name="BCModalModifyBtn"]'),
	BCModalDeleteBtn : jqObj.BCEventViewerModal.find('button[name="BCModalDeleteBtn"]'),
	BCModalCloseBtn : jqObj.BCEventViewerModal.find('button[name="BCModalCloseBtn"]'),
	
	BCInitialSettings : function() {
		/*
		 * allDayFormatStr = 'YYYY.MM.DD ddd'; nonAllDayFormatStr =
		 * 'YYYY.MM.DD ddd A hh:mm'; formatStr =
		 * this.BCEventAllDayToggle.prop('checked') ? allDayFormatStr :
		 * nonAllDayFormatStr;
		 */
		
		this.BCEventStartDate.datetimepicker({
		// format : formatStr
		});
		this.BCEventEndDate.datetimepicker({
		// format : formatStr,
		// useCurrent: false
		});
		this.BCEventAllDayToggle.bootstrapToggle({
			on : 'Yes',
			off : 'No',
			width : 75
		});
		this.BCEventAllDayToggle.bootstrapToggle('disable');
	},
	
	clickEventFor : function(event, jsEvent, view) {
		notify.notify('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
		notify.notify('View: ' + view.name);
		notify.notify('End: ' + event.end.toDate());
		notify.notify('Start: ' + event.start.toDate());
		notify.notify('AllDay: ' + event.allDay);
		notify.notify('Event: ' + event.title);

		this.BCEventId.val(event.id);
		this.BCEventTitle.val(event.title);
		this.BCEventContentViewer.setData(event.content);
		
		this.BCEventAllDayToggle.bootstrapToggle('enable');
		this.BCEventAllDayToggle.bootstrapToggle(event.allDay ? 'on' : 'off');
		this.BCEventAllDayToggle.bootstrapToggle('disable');
		
		if(event.allDay) {
			this.BCEventStartDate.data("DateTimePicker").format('YYYY.MM.DD ddd');
			this.BCEventEndDate.data("DateTimePicker").format('YYYY.MM.DD ddd');
			this.BCEventStartDate.data("DateTimePicker").date(event.start);
			this.BCEventEndDate.data("DateTimePicker").date(event.end.clone().subtract(1, 'days'));
		} else {
			event.start.tz('Asia/Seoul');
			event.end.tz('Asia/Seoul');
			this.BCEventStartDate.data("DateTimePicker").format('YYYY.MM.DD ddd A hh:mm');
			this.BCEventEndDate.data("DateTimePicker").format('YYYY.MM.DD ddd A hh:mm');
			this.BCEventStartDate.data("DateTimePicker").date(event.start);
			this.BCEventEndDate.data("DateTimePicker").date(event.end);
		}
		
		jqObj.BCEventViewerModal.modal('show');
	},
	
	BCModalModifyBtnFunc : function() {
		this.BCModalModifyBtn.click(function() {
			notify.notify('modify clicked!', 'primary');
		});
	},
	
	BCModalDeleteBtnFunc : function() {
		this.BCModalDeleteBtn.click(function() {
			notify.notify('delete clicked!', 'error');
		});
	},
	
	BCModalCloseBtnFunc : function() {
		this.BCModalCloseBtn.click(function() {
			jqObj.BCEventViewerModal.modal('hide');
		});
	},
	
	addAllEventsAndInitSet : function() {
		this.BCInitialSettings();
		this.BCModalModifyBtnFunc();
		this.BCModalDeleteBtnFunc();
		this.BCModalCloseBtnFunc();
	}	

};


/*
 * // bootstrap-year-calendar in modal // var yearCalendar =
 * $('#yearlyCalendar').calendar();
 * 
 * function editEvent(event) { $('#event-modal
 * input[name="event-index"]').val(event ? event.id : '');
 * $('#event-modal input[name="event-name"]').val(event ? event.name :
 * ''); $('#event-modal input[name="event-location"]').val(event ?
 * event.location : ''); $('#event-modal
 * input[name="event-start-date"]').datepicker('update', event ?
 * event.startDate : ''); $('#event-modal
 * input[name="event-end-date"]').datepicker('update', event ?
 * event.endDate : ''); $('#event-modal').modal(); };
 * 
 * function deleteEvent(event) { var dataSource =
 * $('#yearlyCalendar').data('calendar').getDataSource();
 * 
 * for(var i in dataSource) { if(dataSource[i].id == event.id) {
 * dataSource.splice(i, 1); break; } }
 * 
 * $('#yearlyCalendar').data('calendar').setDataSource(dataSource); };
 * 
 * function saveEvent() { var event = { id: $('#event-modal
 * input[name="event-index"]').val(), name: $('#event-modal
 * input[name="event-name"]').val(), location: $('#event-modal
 * input[name="event-location"]').val(), startDate: $('#event-modal
 * input[name="event-start-date"]').datepicker('getDate'), endDate:
 * $('#event-modal input[name="event-end-date"]').datepicker('getDate') }
 * 
 * var dataSource =
 * $('#yearlyCalendar').data('calendar').getDataSource();
 * 
 * if(event.id) { for(var i in dataSource) { if(dataSource[i].id ==
 * event.id) { dataSource[i].name = event.name; dataSource[i].location =
 * event.location; dataSource[i].startDate = event.startDate;
 * dataSource[i].endDate = event.endDate; } } } else { var newId = 0;
 * for(var i in dataSource) { if(dataSource[i].id > newId) { newId =
 * dataSource[i].id; } }
 * 
 * newId++; event.id = newId;
 * 
 * dataSource.push(event); }
 * 
 * $('#yearlyCalendar').data('calendar').setDataSource(dataSource);
 * $('#event-modal').modal('hide'); };
 * 
 * var currentYear = new Date().getFullYear();
 * 
 */

/*
 * $('#yearlyCalendar').calendar({ enableContextMenu: true,
 * enableRangeSelection: true, contextMenuItems:[ { text: 'Update',
 * click: editEvent }, { text: 'Delete', click: deleteEvent } ],
 * selectRange: function(e) { editEvent({ startDate: e.startDate,
 * endDate: e.endDate }); }, mouseOnDay: function(e) {
 * if(e.events.length > 0) { var content = '';
 * 
 * for(var i in e.events) { content += '<div
 * class="event-tooltip-content">' + '<div class="event-name"
 * style="color:' + e.events[i].color + '">' + e.events[i].name + '</div>' + '<div
 * class="event-location">' + e.events[i].location + '</div>' + '</div>'; }
 * 
 * $(e.element).popover({ trigger: 'manual', container: 'body',
 * html:true, content: content });
 * 
 * $(e.element).popover('show'); } }, mouseOutDay: function(e) {
 * if(e.events.length > 0) { $(e.element).popover('hide'); } },
 * dayContextMenu: function(e) { $(e.element).popover('hide'); },
 * dataSource: [ { id: 0, name: 'Google I/O', location: 'San Francisco,
 * CA', startDate: new Date(currentYear, 4, 1), endDate: new
 * Date(currentYear, 4, 29) }, { id: 1, name: 'Microsoft Convergence',
 * location: 'New Orleans, LA', startDate: new Date(currentYear, 2, 16),
 * endDate: new Date(currentYear, 2, 19) } ] });
 * 
 * $('#save-event').click(function() { saveEvent(); });
 */

var currentYear = new Date().getFullYear();

$('#yearlyCalendar').calendar({
	enableContextMenu : true,
	enableRangeSelection: true,
	displayWeekNumber : true,
	roundRangeLimits : true,
	
	contextMenuItems : [
		{
			text : 'Update',
			// click : notify.notify('Update clicked!')
		},
		{
			text : 'Delete',
			// click : notify.notify('Delete clicked!')
		}
	],
	
	dataSource: [
		{
			id: 0,
			name: 'Google I/O',
			location: 'San Francisco, CA',
			startDate: new Date(currentYear, 1, 1),
			endDate: new Date(currentYear, 1, 15)
		},
		{
			id: 1,
			name: 'Microsoft Convergence',
			location: 'New Orleans, LA',
			startDate: new Date(currentYear, 2, 16),
			endDate: new Date(currentYear, 2, 19)
		}
	],
	
	
	/* events */
	
	clickDay : function(e) {
		YCModalFunc.clickDay(e);
	},
	
	selectRange : function(e) {
		YCModalFunc.selectRange(e);
	},
	
    dayContextMenu : function(e) {
    	notify.notify('element : ' + e.element);
    	notify.notify('date : ' + e.date);
    	notify.notify('events : ' + e.events);
    },
	
    mouseOnDay : function(e) {
        if(e.events.length > 0) {
            var content = '';
            
            for(var i in e.events) {
                content += '<div class="event-tooltip-content">'
                         + '<div class="event-name" style="color:' + e.events[i].color + '">' + e.events[i].name + '</div>'
                         + '<div class="event-location">' + e.events[i].location + '</div>'
                         + '</div>';
            }
        
            $(e.element).popover({
                trigger: 'manual',
                container: 'body',
                html:true,
                content: content
            });
            
            $(e.element).popover('show');
        }
    },
    
    mouseOutDay : function(e) {
        if(e.events.length > 0) {
            $(e.element).popover('hide');
        }
    }
});

var YCModalFunc = {
	YCEventVO : {},
	
	YCEventIdCnt : 1,
	
	YCEventId : jqObj.yearlyCalendarModal.find('input[name="YCEventId"]'),
	YCEventTitle : jqObj.yearlyCalendarModal.find('input[name="YCEventTitle"]'),
	YCEventContentEditor : yearlyCalendarInputEditor,
	
	YCEventAllDayToggle : jqObj.yearlyCalendarModal.find('input[name="YCEventAllDayToggle"]'),
	YCEventStartDate : jqObj.yearlyCalendarModal.find('input[name="YCEventStartDate"]'),
	YCEventEndDate : jqObj.yearlyCalendarModal.find('input[name="YCEventEndDate"]'),
	
	YCModalSaveBtn : jqObj.yearlyCalendarModal.find('button[name="YCModalSaveBtn"]'),
	YCModalResetBtn : jqObj.yearlyCalendarModal.find('button[name="YCModalResetBtn"]'),
	YCModalCloseBtn : jqObj.yearlyCalendarModal.find('button[name="YCModalCloseBtn"]'),

	
	YCInitialSettings : function() {
		allDayFormatStr = 'YYYY.MM.DD ddd';
		nonAllDayFormatStr = 'YYYY.MM.DD ddd A hh:mm';
		formatStr = this.YCEventAllDayToggle.prop('checked') ? allDayFormatStr : nonAllDayFormatStr;
		
		this.YCEventStartDate.datetimepicker({
			format : formatStr,
			stepping : 5,
			sideBySide : true,
			calendarWeeks : true,
			showTodayButton : true,
			showClear : true,
			showClose : true,
			toolbarPlacement : 'bottom',
			widgetPositioning : {
				horizontal: 'right',
				vertical: 'top'
			}
		});
		this.YCEventEndDate.datetimepicker({
			useCurrent: false,
			format : formatStr,
			stepping : 5,
			sideBySide : true,
			calendarWeeks : true,
			showTodayButton : true,
			showClear : true,
			showClose : true,
			toolbarPlacement : 'top',
			widgetPositioning : {
				horizontal: 'right',
				vertical: 'bottom'
			}
		});
		
		this.YCEventAllDayToggle.bootstrapToggle({
			on : 'Yes',
			off : 'No',
			width : 75
		});
		
		this.YCEventStartDate.on("dp.change", function (e) {
			YCModalFunc.YCEventEndDate.data("DateTimePicker").minDate(e.date);
        });
		this.YCEventEndDate.on("dp.change", function (e) {
			YCModalFunc.YCEventStartDate.data("DateTimePicker").maxDate(e.date);
        });
		
		this.YCEventAllDayToggle.change(function() {
			if (YCModalFunc.YCEventAllDayToggle.prop("checked")) {
				YCModalFunc.YCEventStartDate.data("DateTimePicker").format(allDayFormatStr);
				YCModalFunc.YCEventEndDate.data("DateTimePicker").format(allDayFormatStr);
			} else {
				YCModalFunc.YCEventStartDate.data("DateTimePicker").format(nonAllDayFormatStr);
				YCModalFunc.YCEventEndDate.data("DateTimePicker").format(nonAllDayFormatStr);
			}
		});
	},

	/*
	 * editEvent : function(event) { this.YCEventId.val(event ? event.id :
	 * ''); this.YCEventTitle.val(event ? event.name : '');
	 * this.YCEventContentEditor.val(event ? event.location : '');
	 * //this.YCEventStartDate.datepicker('update', event ?
	 * event.startDate : ''); //this.YCEventEndDate.datepicker('update',
	 * event ? event.endDate : ''); $('#event-modal').modal(); },
	 */

	clickDay : function(e) {
		
		this.YCEventStartDate.data("DateTimePicker").date(e.date);
		this.YCEventEndDate.data("DateTimePicker").date(e.date);
		
		jqObj.yearlyCalendarModal.modal('show');
	},
	
	selectRange : function(e) {
		notify.notify('endDate  : ' + e.endDate);
		notify.notify('startDate : ' + e.startDate);
		notify.notify('location: ' + e.location);
		notify.notify('name : ' + e.name);
		notify.notify('id : ' + e.id);
		
		this.YCEventStartDate.data("DateTimePicker").date(e.startDate);
		this.YCEventEndDate.data("DateTimePicker").date(e.endDate);

		jqObj.yearlyCalendarModal.modal('show');
	},
	
	YCModalSaveBtnFunc : function() {
		
	},
	
	YCModalResetBtnFunc : function() {
		this.YCModalResetBtn.click(function() {
			YCModalFunc.clearInputData();
		});
	},
	
	YCModalCloseBtnFunc : function() {
		this.YCModalCloseBtn.click(function() {
			jqObj.yearlyCalendarModal.modal('hide');
		});
	},
	
	clearInputData : function() {
		this.YCEventTitle.val('');
		yearlyCalendarInputEditor.setData('');
	},
	
	addAllEventsAndInitSet : function() {
		this.YCInitialSettings();
		this.YCModalSaveBtnFunc();
		this.YCModalResetBtnFunc();
		this.YCModalCloseBtnFunc();
	}
};

// ===================================================================================================

// socket(SockJS)
var session = {
	
	socket : null,
	editorContentSocket : null,
	nickname : null,
	id : null,
	
	connect : function() {
		var URL = location.protocol + '//' + location.host;
		this.socket = new SockJS(URL + '/myPractice/socket');
		this.socket.onopen = function(event) {
			notify.notify('메신저 연결 성공', 'success');
		};
		this.socket.onclose = function(event) {
			notify.notify('메신저 연결 종료', 'success');
			
			// Unespected disconnection
			if (jqObj.joinToggleBtn.html() == ('Exit')) {
				notify.notify('Chatting is disconnected accidentaly!', 'error');
				notify.notify('Join again please!', 'error');
				jqObj.joinToggleBtn.html('Join');
				jqObj.joinToggleBtn.removeClass('btn-danger');
				jqObj.joinToggleBtn.addClass('btn-success');
				jqAjax.deleteRefresh();
			}
		};
		
		this.socket.onmessage = function(event) {
			utils.chatAppend(event.data);
			notify.notify(event.data);
			badge.add();
		};
		this.socket.onerror = function(event) {
			alert('메신저 에러 발생 : ' + event.data);
		};
		
		// URL = null;
		
		// Editor content socket
		this.editorContentSocket = new SockJS(URL + '/myPractice/editorContentSocket');
		
		this.editorContentSocket.onopen = function(event) {
			notify.notify('에디터 연결 성공', 'success');
		};
		this.editorContentSocket.onclose = function(event) {
			notify.notify('에디터 연결 종료', 'success');
			
			// Unespected disconnection
			if (jqObj.joinToggleBtn.html() == ('Exit')) {
				notify.notify('Editor is disconnected accidentaly!', 'error');
				notify.notify('Reconnect please!', 'error');
			}
		};
		
		this.editorContentSocket.onmessage = function(event) {
			var ranges = basicEditor.getSelection().getRanges();
			
			basicEditor.setData(event.data);
			mobileEditor.setData(event.data);
			// notify.notify('컨텐츠 갱신');
			
			
			basicEditor.getSelection().selectRanges( ranges );
		};
		
		this.socket.onerror = function(event) {
			alert('에디터 에러 발생 : ' + event.data);
		};
		
		URL = null;
	},
	
	disconnect : function() {
		// Close socket
		if(this.socket) {
			this.socket.send(this.nickname + ' 님이 퇴장했습니다.');
			this.socket.close();
			this.socket = null;
		};
		
		// Close editor content socket
		if(this.editorContentSocket) {
			this.editorContentSocket.close();
			this.editorContentSocket = null;
		};
		
		if(this.nickname) {
			this.nickname = null;
		};
	},
	
	reconnect : function() {
		this.disconnect();
		this.connect();
	},
	
	// Send data to socket
	msgSend : function() {
		var msg = jqObj.chatInput.val();
		utils.chatAppend('[' + this.nickname + '] : ' + msg);
		this.socket.send(this.nickname + ' : ' + msg);
		jqObj.chatInput.val('');
		msg = null;
	},
	
	contentSend : function() {
		// when editorContentSocket is connected
		if (this.editorContentSocket) {
			// basic size
			if (matchMedia("screen and (min-width: 768px)").matches) {
				this.editorContentSocket.send(basicEditor.getData());
				basicEditor.focus();

			// mobile size
			} else {
				this.editorContentSocket.send(mobileEditor.getData());
				mobileEditor.focus();
			}
			
			this.socket.send('컨텐츠 갱신 by ' + this.nickname);
			
		} else {
			jqObj.editorInputBtn.blur();
		}
	},
	
	sendJoinCheckMsg : function() {
		// <![CDATA[
		if(this.socket && this.nickname) {
			// ]]>
			this.socket.send(this.nickname + ' 님이 입장했습니다.');
		} else {
			notify.notify('Sending join check msg has failed');
		}
	}
};

var badge = {
	
	msgCnt : 0,
	badgeArray : $('.badge'),
	badgeBtnArray : [ jqObj.basicStatus, jqObj.mobileStatus ],
	
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

var notify = {
		
	toggle : true,
	
	notify : function(msg, type) {
		if(!type)
			type = 'info'; 
		if (this.toggle)
			$.notify(msg, type);				
	},
	
	toggleChatNotify : function() {
		
		var str = 'data-original-title';
		
		this.toggle = !this.toggle;
		jqObj.chatNotifyToggle.toggleClass('glyphicon-eye-close');
		jqObj.chatNotifyToggle.toggleClass('glyphicon-eye-open');
		if(this.toggle) {
			jqObj.chatNotifyToggleBtn.attr(str, 'Hide chat notification!').tooltip('hide');			        
		}
		else {
			jqObj.chatNotifyToggleBtn.attr(str, 'Show chat notification!').tooltip('hide');
		}
	}
};

var utils = {
	
	// chatArea append function
	chatAppend : function(str) {
		jqObj.chatArea.append(str + '\n');
		jqObj.chatArea.scrollTop(jqObj.chatArea[0].scrollHeight);
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
			notify.notify('You need to join first!');
			jqObj.nicknameInput.focus();
		} else if (!(this.checkStr(jqObj.chatInput.val()))) {
			jqObj.chatInput.focus();
		} else {
			session.msgSend();
			jqObj.chatInput.focus();
		}
	},
	
	// Verify and control Join condition
	verifyJoin : function() {
		if (!session.socket) {
			if (this.checkStr(jqObj.nicknameInput.val())) {
				// this.chatAppend('Join now!');
				jqObj.joinToggleBtn.html('Exit');
				jqObj.joinToggleBtn.removeClass('btn-success');
				jqObj.joinToggleBtn.addClass('btn-danger');
				session.nickname = jqObj.nicknameInput.val();
				jqObj.nicknameInput.val('');
				jqObj.nicknameInput.attr('placeholder', 'Login is successful!');
				session.connect();
				
				setTimeout(function(){
					session.sendJoinCheckMsg();	
				}, 100);

				// Hide navbar collapsible menus in mobile
				if (!(matchMedia("screen and (min-width: 768px)").matches)) {
					if($('.navbar-toggle').css('display') !='none'){
						$(".navbar-toggle").trigger( "click" );
					}
		        }
				
				
				// AJAX
				// Send nickname to server to save in DB and refersh
				// table
				jqAjax.insertRefresh();
				
				// Focus back to chat input
				jqObj.chatInput.focus();
			} else {
				jqObj.nicknameInput.attr('placeholder', 'Worng! type again!');
				jqObj.nicknameInput.focus();
			}
		} else {
			// this.chatAppend('Exit now!');
			jqObj.joinToggleBtn.html('Join');
			jqObj.joinToggleBtn.removeClass('btn-danger');
			jqObj.joinToggleBtn.addClass('btn-success');
			jqObj.nicknameInput.val('');
			jqObj.nicknameInput.attr('placeholder', 'Type nickname');
			session.disconnect();
			
			// Hide navbar content (in effect only in mobile mode)
			if (!(matchMedia("screen and (min-width: 768px)").matches)) {
				if($('.navbar-toggle').css('display') !='none'){
					$(".navbar-toggle").trigger( "click" );
				}
	        }
			
			jqAjax.deleteRefresh();
			
			// Focus back to nickname input
			jqObj.nicknameInput.focus();
		}
	},
	
	showHeight : function() {
		// Each height
		this.chatAppend('#window : ' + $(window).height());
		this.chatAppend('#container : ' + $('#container').height());
		this.chatAppend('#navBarContainer: ' + $('#navBarContainer').height());
		this.chatAppend('#mainRow : ' + $('#mainRow').height());
		this.chatAppend('#editorContainer : ' + jqObj.editorContainer.height());
		this.chatAppend('#chatContainer : ' + jqObj.chatContainer.height());
		this.chatAppend('#basicEditorDiv : ' + $('#basicEditorDiv').height());
		this.chatAppend('#mobileEditorDiv : ' + $('#mobileEditorDiv').height());
		this.chatAppend('#basicEditor : ' + $('#basicEditor').height());
		this.chatAppend("#basicEditor.config.height : " + basicEditor.config.height);
		this.chatAppend('#mobileEditor : ' + jqObj.mobileEditor.height());
		this.chatAppend('#chatArea : ' + jqObj.chatArea.height());
		this.chatAppend('#chatContainer - editorContainer : ' + (jqObj.chatContainer.height() - jqObj.editorContainer.height()));
	}
};

var eventFunc = {
	
	collapseBtnFocusMove : function() {
		jqObj.collapseBtn.click(function() {
			// <![CDATA[
			if (!(matchMedia("screen and (min-width: 768px)").matches) && !(session.socket)) {
			// ]]>
				setTimeout(function() {
					jqObj.nicknameInput.focus();
				}, 500);
			}
		});
	},
	
	chatInputKeyEvent : function() {
		
		// for CTRL + SHIFT
		var isCtrl = false;
		
		// Focus back to chatInput on pressing enter key on chatInput
		jqObj.chatInput.keydown(function(event) {
			
			// ENTER
			if (event.which === 13) {
				utils.verifyChatInput();
				
			// CTRL
			} else if (event.which == 17) {
				// notify.notify('CTRL clicked!');
				isCtrl = true;
				// notify.notify('keyDown! : ' + isCtrl);
			}
			
			// CTRL + SHIFT
			// <![CDATA[
			if (event.which == 16 && isCtrl) {
			// ]]>
				notify.notify('CTRL + SHIFT clicked! (chatInput)');
				
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
		jqObj.chatInput.keyup(function(event) {
			if(event.which == 17) {
				isCtrl=false;
				// notify.notify('keyUp! : ' + isCtrl)
			}
		});
		
		// Focus back to chatInput on clicking chatInputBtn
		jqObj.chatInputBtn.click(function() {
			utils.verifyChatInput();
		});
	},
	
	badgeEvent : function() {
		// Badge event function
		jqObj.chatArea.focus(function(event) {
			badge.reset();
		});
		jqObj.chatInput.focus(function(event) {
			badge.reset();
		});
		jqObj.basicStatus.click(function() {
			badge.reset();
		});
		jqObj.mobileStatus.click(function() {
			badge.reset();
		});
	},
	
	// Toggle chat notification
	toggleChatNotifyEvent : function() {
		jqObj.chatNotifyToggleBtn.click(function() {
			notify.toggleChatNotify();
			this.blur();
		});
	},
	
	toggleJoinEvent : function() {
		// Toggle join (button)
		jqObj.joinToggleBtn.click(function() {
			utils.verifyJoin();
		});
		// Toggle join (input text)
		jqObj.nicknameInput.keypress(function(event) {
			if (event.keyCode === 13)
				utils.verifyJoin();
		});
	},
	
	// Toggle toolbar function
	toggleToolbarFunc : function() {
		setTimeout(function() {
			this.toggle = !this.toggle;
			$('#cke_1_top').toggle();
			$('#cke_2_top').toggle();
			jqObj.toolbarToggle.toggleClass('glyphicon-ice-lolly-tasted');
			jqObj.toolbarToggle.toggleClass('glyphicon-ice-lolly');
			if (matchMedia("screen and (min-width: 768px)").matches) {
				resizeFuncs.setBasicHeight();
				basicEditor.focus();
			} else {
				jqObj.mobileEditor.focus();
			}
		}, 135);
		
		setTimeout(function() {
			
			var str = 'data-original-title';
			
			if(jqObj.toolbarToggle.hasClass('glyphicon-ice-lolly-tasted')) {
				jqObj.toolbarToggleBtn.attr(str, 'Hide toolbar!').tooltip('hide');
				jqObj.basicStatus.attr(str, 'Hide toolbar!').tooltip('hide');
				jqObj.mobileStatus.attr(str, 'Hide toolbar!').tooltip('hide');
			} else {
				jqObj.toolbarToggleBtn.attr(str, 'Show toolbar!').tooltip('hide');
				jqObj.basicStatus.attr(str, 'Show toolbar!').tooltip('hide');
				jqObj.mobileStatus.attr(str, 'Show toolbar!').tooltip('hide');
			}
		}, 135);
	},
	
	// Toggle toolbar
	toggleToolbarEvent : function() {
		jqObj.toolbarToggleBtn.click(function() {
			eventFunc.toggleToolbarFunc();
		});
		jqObj.basicStatus.click(function() {
			eventFunc.toggleToolbarFunc();
			badge.reset();
		});
		jqObj.mobileStatus.click(function() {
			eventFunc.toggleToolbarFunc();
		});
	},
	
	windowOutEvent : function() {
		// Logout, close event with 'esc' key on browser window
		$(window).keydown(function(event) {
			if (event.keyCode == 27) {
				utils.chatAppend('esc!!');
				if (session.socket) {
					// utils.chatAppend('Exit now!');
					jqObj.joinToggleBtn.html('Join');
					jqObj.joinToggleBtn.removeClass('btn-danger');
					jqObj.joinToggleBtn.addClass('btn-success');
					jqObj.nicknameInput.val('');
					jqObj.nicknameInput.attr('placeholder', 'Type nickname');
					session.disconnect();
					session.nickname = null;
					jqObj.nicknameInput.focus();
				} else {
					// 창 닫는 함수 (작동 안 함)
					// window.open("about:blank","_self").close();
				}
			}
		});
	},
	
	showHeights : function() {
		$('#showHeights').click(function() {
			utils.showHeight();
		});
	},
	
	editorInputSend : function() {
		jqObj.editorInputBtn.click(function() {
			session.contentSend();
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
				notify.notify('CTRL + SHIFT clicked! (editor)');
				jqObj.chatInput.focus();
				
			// ENTER
			} else if(keyCode == 1114125) {
				notify.notify('ENTER clicked! (editor)');
				jqObj.editorInputBtn.trigger('click'); 
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
		 * jqObj.editorInputBtn.trigger('click'); } }); });
		 * 
		 * mobileEditor.on('contentDom', function() {
		 * mobileEditor.document.on('keydown', function(event) { //
		 * notify.notify(event.data.getKey()); if(event.data.getKey() ==
		 * 13) { notify.notify('enter clicked!');
		 * jqObj.editorInputBtn.trigger('click'); } }); });
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
	
	
	addAllEvents() {
		this.chatInputKeyEvent();
		this.badgeEvent();
		this.toggleJoinEvent();
		this.toggleToolbarEvent();
		this.windowOutEvent();
		this.showHeights();
		this.toggleChatNotifyEvent();
		modalFunc.addAllEventsAndInitSet();
		calendarFunc.addAllEventsAndInitSet();
		datetimepickerFunc.addAllEventsAndInitSet();
		pnotifyFunc.addAllEventsAndInitSet();
		xeditableFunc.addAllEventsAndInitSet();
		BCModalFunc.addAllEventsAndInitSet();
		BCEventViewerModalFunc.addAllEventsAndInitSet();
		YCModalFunc.addAllEventsAndInitSet();
		// this.tooltipActivate();
		tooltipFunc.addAllEventsAndInitSet();
		
		this.editorInputSend();
		this.editorKeyEvent();
		this.collapseBtnFocusMove();
	}
	
};

var modalFunc = {
		
	basicSettings : {
		// necessary for nested modal scroll focus (!!!important!!!)
		resolveNestedModalScrollbarProblem : function() {
			$('.modal').on('hidden.bs.modal', function (e) {
				if($('.modal').hasClass('in')) {
					$('body').addClass('modal-open');
				}
			});
		}
	}, 
	
	basicCalendarModal : {
		dateClearOnExit : function() {
			jqObj.basicCalendarModal.on('hide.bs.modal', function(e) {
				BCModalFunc.BCEventStartDate.data("DateTimePicker").date(null);
				BCModalFunc.BCEventEndDate.data("DateTimePicker").date(null);
			});	
		}
	}, 
	
	addAllEventsAndInitSet : function() {
		this.basicSettings.resolveNestedModalScrollbarProblem();
		this.basicCalendarModal.dateClearOnExit();
	}
};
	
var calendarFunc = {
	

		
	addAllEventsAndInitSet : function() {
		
	}
	
};

var datetimepickerFunc = {
		

	
	addAllEventsAndInitSet : function() {
		
	}
};

var pnotifyFunc = {
	
	stack_modal : {
		"dir1": "top",
		"dir2": "left",
		"push": "top",
		"modal": true,
		"overlay_close": true
	},
		
	addAllEventsAndInitSet : function() {
		// PNotify initial setting
		PNotify.prototype.options.styling = "bootstrap3";
		
	}
	
};

var xeditableFunc = {
	
	userNumbers : jqObj.joinTable.find('td span[name="userNumber"]'),
	userNames : jqObj.joinTable.find('td span[name="userName"]'),
	userEmails : jqObj.joinTable.find('td span[name="userEmail"]'),
	userAuths : jqObj.joinTable.find('td span[name="userAuth"]'),
	userParts : jqObj.joinTable.find('td span[name="userPart"]'),
	test1 : jqObj.joinTable.find('td span[name="test1"]'),
	test2 : jqObj.joinTable.find('td span[name="test2"]'),
	
	joinTableSet : function() {
		var userNumbers = jqObj.joinTable.find('td span[name="userNumber"]');
		var userNames = jqObj.joinTable.find('td span[name="userName"]');
		var userEmails = jqObj.joinTable.find('td span[name="userEmail"]');
		var userAuths = jqObj.joinTable.find('td span[name="userAuth"]');
		var userParts = jqObj.joinTable.find('td span[name="userPart"]');
		var test1 = jqObj.joinTable.find('td span[name="test1"]');
		var test2 = jqObj.joinTable.find('td span[name="test2"]');
		
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
		
		/*
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
		 */
	},
		
	addAllEventsAndInitSet : function() {
		// X-editable initial setting
		this.joinTableSet();
	}
};

var tooltipFunc = {

	tooltipApply : function() {
		// $('#example').tooltip(options)
		jqObj.joinToggleBtn.tooltip({
			title : "Type your name and Join",
			placement : "bottom",
			container : "body"
		});

		
		jqObj.basicStatus.tooltip({
			title : "Hide toolbar!",
			placement : "bottom"
		});
		
		jqObj.mobileStatus.tooltip({
			title : "Hide toolbar!", 
			placement : "bottom"
		});
		
		jqObj.editorInputBtn.tooltip({
			title : '"CTRL + ENTER" or "CTRL + SHIFT"',
			placement : "top",
			container : "body"
		});
		
		jqObj.toolbarToggleBtn.tooltip({
			title : "Hide toolbar!",
			placement : "top",
			container : "body"
		});
		
		jqObj.chatNotifyToggleBtn.tooltip({
			title : "Hide chat notification!",  
			placement : "top",
			container : "body"
		});
	},
		
	tooltipActivate : function() {
		$('[data-toggle="tooltip"]').tooltip();
	},
	
	addAllEventsAndInitSet : function() {
		this.tooltipActivate();
		this.tooltipApply();
	}
};

var resizeFuncs = {
	
	// arrays to set different sets between basic and mobile
	formGroupArray : [ jqObj.formGroup1, jqObj.formGroup2, jqObj.formGroup3 ],
	formControlArray : [ jqObj.chatInputBtn ],
	btnGroupArray : [ jqObj.btnGroup1, jqObj.btnGroup2 ],
	
	setGapByTogglingClass : function() {
		utils.toggleClassArrayFunc(this.formGroupArray, 'form-group');
		utils.toggleClassArrayFunc(this.formControlArray, 'form-control');
		utils.toggleClassArrayFunc(this.btnGroupArray, 'btn-group-justified');
	},
		
	setBothCalendarHeight : function() {
		// resize height of basicCalendar in modal
		basicCalendar.fullCalendar('option', 'contentHeight', $(window).height() * 0.48);
		
		// resize height of yearlyCalendar in modal
		jqObj.yearlyCalendar.height($(window).height() * 0.48);
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

// AJAX
// ===================================================================================================

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

// $(document).ready
// ======================================================================================

// $(function() {
$(document).ready(function() {
	
	// this is for only testing
	jqObj.nicknameInput.focus();
	
	// add element events and initial settings of frameworks
	eventFunc.addAllEvents();
	
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