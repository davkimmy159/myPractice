bootstrapTable = {

	myBoardListTable : $("table#myBoardList"),
	allBoardListTable : $("table#allBoardList"),
		
	tableSetting : function() {
		
		/* Bootstrap table plugin settings for my board list */
		$("table#myBoardList").bootstrapTable({
			columns: [
            	{
            		field: "state",
            		checkbox: true
            	}, {
            		field: "id",
            		title: "#",
            		class: "hidden-xs",
            		sortable: true,
            		formatter: function(value, row, index) {
            			return '<span class="eachBoardIdInTable">' + row.id + '</span>';
            		},
            		events: {
            			'click .eachBoardIdInTable': function(e, value, row, index) {
            				notify.notify(row.id);
            			}
            		}
            	}, {
            		field: "title",
            		title: "Title",
            		sortable: true,
            		formatter: function(value, row, index) {
            			return '<span class="boardTitle">' + row.title + '</span>';
            		},
            		events: {
            			'click .boardTitle': function(e, value, row, index) {
            				location.href = path.getFullContextPath() + "/board/" + row.id;
            			}
            		}
            	}, {
            		field: "hitCount",
            		title: "Hits",
            		sortable: true
            	
            		/*
                	footerFormatter: function(data) {
                        var total = 0;
                        $.each(data, function(i, row) {
                        	total += Number(row.hitCount);
                        });
                		return '<span>' + total + '</span>';
                    }
                    */
            	}, {
            		field: "updateCount",
            		title: "Up",
            		sortable: true
                	
            		/*
            		footerFormatter: function(data) {
                        var total = 0;
                        $.each(data, function(i, row) {
                        	total += Number(row.updateCount);
                        });
                        return '<span>' + total + '</span>';
                    }
                    */
            	}, {
            		field: "createDate",
            		title: "Create",
            		class: "hidden-xs hidden-sm",
            		sortable: true,
            		formatter: function(value, row, index) {
            			var date = row.createDate;
            			return [
            				'<span class="createDate">',
            				bootstrapTable.dateFormat(date),
            				'</span>'
            			].join('');
            		}
            	}, {
            		field: "lastUpdateDate",
            		title: "Last up",
            		class: "hidden-xs hidden-sm",
            		sortable: true,
            		formatter: function(value, row, index) {
            			var date = row.lastUpdateDate;
            			return [
            				'<span class="createDate">',
            				bootstrapTable.dateFormat(date),
            				'</span>'
            			].join('');
            		}
            	},  {
            		field: "Utils",
            		title: "utils",
            		formatter: function(value, row, index) {
            			return [
            				'<span class="boardDownloadBtn glyphicon glyphicon-download-alt"></span>',
							'<span class="boardSettingBtn glyphicon glyphicon-cog"></span>',
							'<span class="boardDeleteBtn glyphicon glyphicon-remove-circle" style="color: red;"></span>'
            			].join('');
            		},
            		events: {
            			'click .boardDownloadBtn': function (e, value, row, index) {
            	            notify.notify("button clicked", "download");
            	        },
            	        'click .boardSettingBtn': function (e, value, row, index) {
            	            notify.notify("button clicked", "setting");
            	        },
            	        'click .boardDeleteBtn': function (e, value, row, index) {
            	        	ajax.deleteOneBoard(row.id);
            	        }
            		}
            	}
            ],
			
			url : path.getContextPath() + "/ajax/board/getBoardOfMember",
			sidePagination : "server",
			contentType	: 'application/json; charset=utf-8',
			queryParamsType : "limit",
			queryParams : function(params) {
			    params.sort = "id";
				return params;
			},
			
			classes : "table table-no-bordered",
		
			showHeader : true,
//			showFooter : true,
		
			showRefresh : true,
			showToggle : true,
			showColumns : true,
			showExport : true,
			
			search : true,
			searchOnEnterKey : true,
			
			trimOnSearch : true,
			searchTimeOut : 500,
			
			idField : "id",
			uniqueId : "id",
			
			selectItemName : "rowCheckbox",
			
			detailView : true,
			detailFormatter : function(index, row, element) {
				var format = "<span>test!!!</span>";
				
				return format;
			},
			
			pagination : true,
			paginationLoop : true,
			pageList : [10, 20, 30, 50, 80, 100, "ALL"],
			
			method : "GET",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json'
//			cache : true,
			
//			onPageChange : bootstrapTable.common.event.onPageChange
		});
		
		/* Bootstrap table plugin settings for my board list */
		$("table#allBoardList").bootstrapTable({
			/*
			classes : "table table-no-bordered",
		
			showHeader : true,
//			showFooter : true,
		
			showRefresh : true,
			showToggle : true,
			showColumns : true,
			
			search : true,
			searchOnEnterKey : true,
			
			trimOnSearch : true,
			searchTimeOut : 500,
			
			idField : "number",
			uniqueId : "number",
			
			selectItemName : "rowCheckbox",
			
			detailView : true,
			detailFormFormatter : function(index, row, element) {
				var format = "<span>test!!!</span>";
				
				return format;
			},
			
			pagination : true,
			pageSize : 10,
			pageList : [10, 20, 30, 50, 80, 100],

			onPageChange : function(number, size) {
				bootstrapTable.common.event.onPageChange(number, size);
			}
			*/
		});
		
		utils.executeAllFunctionMembers(this.sideSetting);
	},
	
	sideSetting : {
		initialSideSetting : function() {
			$("input[type='checkbox'][data-field='id']").parent().parent("li").addClass("hidden-xs");
			$("input[type='checkbox'][data-field='createDate']").parent().parent("li").addClass("hidden-xs hidden-sm");
			$("input[type='checkbox'][data-field='lastUpdateDate']").parent().parent("li").addClass("hidden-xs hidden-sm");
			
			$(".search").removeClass("pull-right").addClass("pull-left");
		},
		
		deleteSelectedBoardButtonEvent : function() {
			$('button[name="deleteSelectedBoardBtn"]').click(function() {
				var boardIds = $.map(bootstrapTable.myBoardListTable.bootstrapTable("getSelections"), function (row) {
					return row.id;
				});
				ajax.deleteSelectedBoard(boardIds);
			});
		}
	},
	
	responsiveSetting : {
		basicSize : function() {
			$("div.pagination-detail").removeClass("text-center");
			$("div.pagination").removeClass("text-center");
		},
		
		mobileSize : function() {
			$("div.pagination-detail").addClass("text-center");
			$("div.pagination").addClass("text-center");
		}
	},
	
	common : {
		column : {
			formatter : {
				  
			},
			event : {
				
			}
		},
		
		
		event : {
			onPageChange : function(number, size) {
				// Basic size
				if (utils.isDesktopSize()) {
					bootstrapTable.responsiveSetting.basicSize();	
					
				// Mobile size
				} else {
					bootstrapTable.responsiveSetting.mobileSize();
				}
			}
		}
	},
	
	dateFormat : function(date) {
		return date.year + "." + date.monthValue + "." + date.dayOfMonth + " " + date.hour + ":" + date.minute;
	}
};

ajax = {
	deleteOneBoard : function(boardId) {
		$.ajax({
			url : '../ajax/board/deleteOneBoard',
			type : 'GET',
			data : {
				'boardId' : boardId
			},
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
				notify.notify("Ajax 통신 성공 code : " + status, " message : " + data.message);
	        	bootstrapTable.myBoardListTable.bootstrapTable("removeByUniqueId", boardId);
	        	bootstrapTable.myBoardListTable.bootstrapTable("refresh", {silent: true});
			},
			error : function(request, status, error) {
				notify.notify('Ajax 통신 실패 code : ' + request.status + '\n error : ' + error);
			}
		});
	},
	
	deleteSelectedBoard : function(boardIds) {
		$.ajax({
			url : '../ajax/board/deleteSelectedBoard',
			type : 'GET',
			data : {
				'boardIds' : boardIds
			},
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
				notify.notify("Ajax 통신 성공 code : " + status, " message : " + data.message);
				bootstrapTable.myBoardListTable.bootstrapTable("remove", {field: "id", values: boardIds});
				bootstrapTable.myBoardListTable.bootstrapTable("refresh", {silent: true});
			},
			error : function(request, status, error) {
				notify.notify('Ajax 통신 실패 code : ' + request.status + '\n error : ' + error);
			}
		});
	}
};

$(document).ready(function() {
	
	bootstrapTable.tableSetting();
	
	utils.clientValidatorBtnEvent($("form#createBoardForm"));
	
	// Initial setting
	if (utils.isDesktopSize()) {
		
	} else {
		bootstrapTable.responsiveSetting.mobileSize();
	}
	
	$(window).resize(function() {
		
		// To basic from mobile
		if (utils.isDesktopSize()) {
			if($("div.pagination-detail").hasClass("text-center")) {
				bootstrapTable.responsiveSetting.basicSize();
			}
			
		// To mobile from basic
		} else {
			if(!($("div.pagination-detail").hasClass("text-center"))) {
				bootstrapTable.responsiveSetting.mobileSize();
			}
		}
	}).resize();

});