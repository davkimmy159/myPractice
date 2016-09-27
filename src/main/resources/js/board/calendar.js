// BCInputEditor settings (inline CKEditor)
var basicCalendarInputEditor = CKEDITOR.inline('basicCalendarInputEditor');
$('#basicCalendarInputEditor').height($(window).height() * 0.2);

// BCEventContentViewer settings (inline CKEditor)
var basicCalendarEventContentViewer = CKEDITOR.inline('basicCalendarEventContentViewer', {
    removePlugins: 'toolbar'
});

$('#basicCalendarEventContentViewer').height($(window).height() * 0.2);

// YCInputEditor settings (inline CKEditor)
var yearlyCalendarInputEditor = CKEDITOR.inline('yearlyCalendarInputEditor');
$('#yearlyCalendarInputEditor').height($(window).height() * 0.2);

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
	// timezone : 'local',
	displayEventEnd : true,
	timeFormat : 'A hh : mm',
	
	eventLimit : 3,
	
	
	// initial height set for resizing later(removing vertical scroll bar)
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
		}
	]
});

// must be called right after the declaration of fullCalendar
$('#mainModal').on('shown.bs.modal', function () {
	basicCalendar.fullCalendar('render');
});

var BCModalFunc = {
	
	select : function(start, end) {
		// start.tz("Asia/Seoul");
		// end.tz("Asia/Seoul");
		// end.subtract(16, 'hours');
		
		BCModalSet.BCEventId.val(BCModalSet.BCEventIdCnt);
		
		BCModalSet.BCEventStartDate.data("DateTimePicker").date(start);
		BCModalSet.BCEventEndDate.data("DateTimePicker").date(end);
		
		$('#basicCalendarModal').modal('show');
		
		$("#basicCalendar").fullCalendar('unselect');
	},
		
	clearInputData : function() {
		BCModalSet.BCEventTitle.val('');
		basicCalendarInputEditor.setData('');
	}
};

var BCModalSet = {
	
	BCEventVO : {},
	
	BCEventIdCnt : 1,
	
	BCEventId : $('#basicCalendarModal').find('input[name="BCEventId"]'),
	BCEventTitle : $('#basicCalendarModal').find('input[name="BCEventTitle"]'),
	BCEventContentEditor : basicCalendarInputEditor,
	
	BCEventAllDayToggle : $('#basicCalendarModal').find('input[name="BCEventAllDayToggle"]'),
	BCEventStartDate : $('#basicCalendarModal').find('input[name="BCEventStartDate"]'),
	BCEventEndDate : $('#basicCalendarModal').find('input[name="BCEventEndDate"]'),
	
	BCModalSaveBtn : $('#basicCalendarModal').find('button[name="BCModalSaveBtn"]'),
	BCModalResetBtn : $('#basicCalendarModal').find('button[name="BCModalResetBtn"]'),
	BCModalCloseBtn : $('#basicCalendarModal').find('button[name="BCModalCloseBtn"]'),

	BCInitialSettingsEvent : function() {
		allDayFormatStr = 'YYYY.MM.DD ddd';
		nonAllDayFormatStr = 'YYYY.MM.DD ddd A hh:mm';
		formatStr = this.BCEventAllDayToggle.prop('checked') ? allDayFormatStr : nonAllDayFormatStr;
		
		this.BCEventStartDate.datetimepicker({
			format : formatStr,
			stepping : 5,
			sideBySide : true,
			// calendarWeeks : true,
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
			// calendarWeeks : true,
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
			BCModalSet.BCEventEndDate.data("DateTimePicker").minDate(e.date);
        });
		this.BCEventEndDate.on("dp.change", function (e) {
			BCModalSet.BCEventStartDate.data("DateTimePicker").maxDate(e.date);
        });
		
		this.BCEventAllDayToggle.change(function() {
			if (BCModalSet.BCEventAllDayToggle.prop("checked")) {
				BCModalSet.BCEventStartDate.data("DateTimePicker").format(allDayFormatStr);
				BCModalSet.BCEventEndDate.data("DateTimePicker").format(allDayFormatStr);
			} else {
				BCModalSet.BCEventStartDate.data("DateTimePicker").format(nonAllDayFormatStr);
				BCModalSet.BCEventEndDate.data("DateTimePicker").format(nonAllDayFormatStr);
			}
		});
	},
	
	BCModalSaveBtnEvent : function() {
		this.BCModalSaveBtn.click(function() {
			BCModalSet.BCEventVO.id = BCModalSet.BCEventId.val();
			BCModalSet.BCEventVO.title = BCModalSet.BCEventTitle.val();
			BCModalSet.BCEventVO.content = BCModalSet.BCEventContentEditor.getData();
			
			BCModalSet.BCEventVO.allDay = BCModalSet.BCEventAllDayToggle.prop("checked");

			BCModalSet.BCEventVO.startDate = BCModalSet.BCEventStartDate.data("DateTimePicker").date();

			if(BCModalSet.BCEventVO.allDay) {
//				BCModalSet.BCEventVO.startDate = BCModalSet.BCEventStartDate.data("DateTimePicker").date();
				BCModalSet.BCEventVO.endDate = BCModalSet.BCEventEndDate.data("DateTimePicker").date(); //.add(1, 'days');
			} else {
//				BCModalSet.BCEventVO.startDate = BCModalSet.BCEventStartDate.data("DateTimePicker").date();
				BCModalSet.BCEventVO.endDate = BCModalSet.BCEventEndDate.data("DateTimePicker").date();
			}
			
			BCModalSet.BCEventVO.displayEventTime = !BCModalSet.BCEventVO.allDay;
			BCModalSet.BCEventVO.editable = true;
			
			if(BCModalSet.BCEventVO.title) {
				if(BCModalSet.BCEventVO.content) {
					basicCalendar.fullCalendar('renderEvent', {
							id : BCModalSet.BCEventVO.id, 
							title : BCModalSet.BCEventVO.title,
							content : BCModalSet.BCEventVO.content,
							
							allDay : BCModalSet.BCEventVO.allDay,
							start : BCModalSet.BCEventVO.startDate,
							end : BCModalSet.BCEventVO.endDate,
							
							displayEventTime : BCModalSet.BCEventVO.dispalyEventTime,
							editable : BCModalSet.BCEventVO.editable
						},
						true // make the event "stick"
					);
					
					BCModalSet.BCEventIdCnt++;
					
					BCModalFunc.clearInputData();
					
					$('#basicCalendarModal').modal('hide');
				} else {
					notify.notify('title', 'input content!', 'error');
				}
			} else {
				notify.notify('title', 'input title!', 'error');
			}
		});
	},
	
	BCModalResetBtnEvent : function() {
		this.BCModalResetBtn.click(function() {
			BCModalFunc.clearInputData();
		});
	},
	
	BCModalCloseBtnEvent : function() {
		this.BCModalCloseBtn.click(function() {
			$('#basicCalendarModal').modal('hide');
		});
	}
};

var BCEventViewerModalFunc = {
	
	BCEventId : $('#BCEventViewerModal').find('input[name="BCEventId"]'),
	BCEventTitle : $('#BCEventViewerModal').find('input[name="BCEventTitle"]'),
	BCEventContentViewer : basicCalendarEventContentViewer,
	
	BCEventAllDayToggle : $('#BCEventViewerModal').find('input[name="BCEventAllDayToggle"]'),
	BCEventStartDate : $('#BCEventViewerModal').find('input[name="BCEventStartDate"]'),
	BCEventEndDate : $('#BCEventViewerModal').find('input[name="BCEventEndDate"]'),
	
	BCModalModifyBtn : $('#BCEventViewerModal').find('button[name="BCModalModifyBtn"]'),
	BCModalDeleteBtn : $('#BCEventViewerModal').find('button[name="BCModalDeleteBtn"]'),
	BCModalCloseBtn : $('#BCEventViewerModal').find('button[name="BCModalCloseBtn"]'),
	
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
		
		$('#BCEventViewerModal').modal('show');
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
			$('#BCEventViewerModal').modal('hide');
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
	
	YCEventId : $('#yearlyCalendarModal').find('input[name="YCEventId"]'),
	YCEventTitle : $('#yearlyCalendarModal').find('input[name="YCEventTitle"]'),
	YCEventContentEditor : yearlyCalendarInputEditor,
	
	YCEventAllDayToggle : $('#yearlyCalendarModal').find('input[name="YCEventAllDayToggle"]'),
	YCEventStartDate : $('#yearlyCalendarModal').find('input[name="YCEventStartDate"]'),
	YCEventEndDate : $('#yearlyCalendarModal').find('input[name="YCEventEndDate"]'),
	
	YCModalSaveBtn : $('#yearlyCalendarModal').find('button[name="YCModalSaveBtn"]'),
	YCModalResetBtn : $('#yearlyCalendarModal').find('button[name="YCModalResetBtn"]'),
	YCModalCloseBtn : $('#yearlyCalendarModal').find('button[name="YCModalCloseBtn"]'),

	
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
		
		$('#yearlyCalendarModal').modal('show');
	},
	
	selectRange : function(e) {
		notify.notify('endDate  : ' + e.endDate);
		notify.notify('startDate : ' + e.startDate);
		notify.notify('location: ' + e.location);
		notify.notify('name : ' + e.name);
		notify.notify('id : ' + e.id);
		
		this.YCEventStartDate.data("DateTimePicker").date(e.startDate);
		this.YCEventEndDate.data("DateTimePicker").date(e.endDate);

		$('#yearlyCalendarModal').modal('show');
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
			$('#yearlyCalendarModal').modal('hide');
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