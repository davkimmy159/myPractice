bootstrapTable = {

	myBoardListTable : $("table#myBoardList"),
	allBoardListTable : $("table#allBoardList"),
		
	tableSetting : function() {
		
		/* My board list */
		$("table#myBoardList").bootstrapTable({
			columns: [
            	{
            		field: "state",
            		checkbox: true,
            		searchable: false
            	}, {
            		field: "id",
            		title: "#",
            		class: "hidden-xs",
            		sortable: true,
            		searchable: false,
            		formatter: function(value, row, index) {
            			return '<span class="eachBoardIdInTable">' + row.id + '</span>';
            		}/*,
            		events: {
            			'click .eachBoardIdInTable': function(e, value, row, index) {
            				notify.notify(row.id);
            			}
            		}*/
            	}, {
            		field: "title",
            		title: "Title",
            		sortable: true,
            		searchable: true,
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
            		sortable: true,
            		searchable: false
            	}, {
            		field: "updateCount",
            		title: "Up",
            		sortable: true,
            		searchable: false
            	}, {
            		field: "createDate",
            		title: "Create",
            		class: "hidden-xs hidden-sm",
            		sortable: true,
            		searchable: false,
            		formatter: function(value, row, index) {
            			var date = row.createDate;
            			return [
            				'<span class="createDate">',
            				utils.dateFormat(date),
            				'</span>'
            			].join('');
            		}
            	}, {
            		field: "lastUpdateDate",
            		title: "Last up",
            		class: "hidden-xs hidden-sm",
            		sortable: true,
            		searchable: false,
            		formatter: function(value, row, index) {
            			var date = row.lastUpdateDate;
            			return [
            				'<span class="createDate">',
            				utils.dateFormat(date),
            				'</span>'
            			].join('');
            		}
            	}, {
            		field: "Utils",
            		title: "utils",
            		searchable: false,
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
			
			url : path.getContextPath() + "/ajax/board/getAllBoardsOfMember",
			sidePagination : "server",
			contentType	: 'application/json; charset=utf-8',
			method : "GET",
			dataType : 'json',
			queryParamsType : "limit",
			queryParams : function(params) {
				return params;
			},

			idField : "id",
			uniqueId : "id",
			
			sortName: "id",
			sortOrder: "desc",
			
			classes : "table table-no-bordered",
		
			showHeader : true,
//			showFooter : true,
		
			showRefresh : true,
			showToggle : true,
			showColumns : true,
			showExport : true,
			
			search : true,
//			searchOnEnterKey : true,
			searchAlign : "left",
			trimOnSearch : true,
			searchTimeOut : 500,
			
			selectItemName : "rowCheckbox",
			
			detailView : true,
			detailFormatter : function(index, row, element) {
				var format = "<span>test!!!</span>";
				
				return format;
			},
			
			pagination : true,
			paginationLoop : true,
			pageList : [10, 20, 30, 50, 80, 100, "ALL"]
		});
		
		/* All board list */
		$("table#allBoardList").bootstrapTable({
			columns: [
            	{
            		field: "state",
            		checkbox: true,
            		searchable: false,
            		formatter: function(value, row, index) {
            			if (row.username != $("#loginUsername").text()) {
            	            return {
            	                disabled: true
            	            };
            	        } else {
            	        	return value;	
            	        }
            		}
            	}, {
            		field: "id",
            		title: "#",
            		class: "hidden-xs",
            		sortable: true,
            		searchable: false,
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
            		sortable: true,
            		searchable: false
            	}, {
            		field: "updateCount",
            		title: "Up",
            		sortable: true,
            		searchable: false
            	}, {
            		field: "createDate",
            		title: "Create",
            		class: "hidden-xs hidden-sm",
            		sortable: true,
            		searchable: false,
            		formatter: function(value, row, index) {
            			var date = row.createDate;
            			return [
            				'<span class="createDate">',
            				utils.dateFormat(date),
            				'</span>'
            			].join('');
            		}
            	}, {
            		field: "lastUpdateDate",
            		title: "Last up",
            		class: "hidden-xs hidden-sm",
            		sortable: true,
            		searchable: false,
            		formatter: function(value, row, index) {
            			var date = row.lastUpdateDate;
            			return [
            				'<span class="createDate">',
            				utils.dateFormat(date),
            				'</span>'
            			].join('');
            		}
            	}, {
            		field: "member",
            		title: "Owner",
            		class: "hidden-sm",
            		sortable: true,
            		searchable: false,
            		formatter: function(value, row, index) {
            			return [
            				'<span class="member">',
            				row.username,
            				'</span>'
            			].join('');
            		}
            	}, {
            		field: "Utils",
            		title: "utils",
            		searchable: false,
            		formatter: function(value, row, index) {
            			var format = ['<span class="boardDownloadBtn glyphicon glyphicon-download-alt"></span>'];
            			
            			if (row.username == $("#loginUsername").text()) {
            	            format.push('<span class="boardSettingBtn glyphicon glyphicon-cog"></span>');
            	            format.push('<span class="boardDeleteBtn glyphicon glyphicon-remove-circle" style="color: red;"></span>');
            			}
            			
            			return format.join('');
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
			
			url : path.getContextPath() + "/ajax/board/getAllBoards",
			sidePagination : "server",
			contentType	: 'application/json; charset=utf-8',
			method : "GET",
			dataType : 'json',
			queryParamsType : "limit",
			queryParams : function(params) {
				return params;
			},
			
			idField : "id",
			uniqueId : "id",
			
			sortName: "id",
			sortOrder: "desc",
			
			classes : "table table-no-bordered",
		
			showHeader : true,
//			showFooter : true,
		
			showRefresh : true,
			showToggle : true,
			showColumns : true,
			showExport : true,
			
			search : true,
//			searchOnEnterKey : true,
			searchAlign : "left",
			trimOnSearch : true,
			searchTimeOut : 500,
			
			selectItemName : "rowCheckbox",
			
			detailView : true,
			detailFormatter : function(index, row, element) {
				var format = "<span>test!!!</span>";
				
				return format;
			},
			
			pagination : true,
			paginationLoop : true,
			pageList : [10, 20, 30, 50, 80, 100, "ALL"]
		});
		
		utils.executeAllFunctionMembers(this.sideSetting);
	},
	
	sideSetting : {
		initialSideSetting : function() {
			$("input[type='checkbox'][data-field='id']").parent().parent("li").addClass("hidden-xs");
			$("input[type='checkbox'][data-field='createDate']").parent().parent("li").addClass("hidden-xs hidden-sm");
			$("input[type='checkbox'][data-field='lastUpdateDate']").parent().parent("li").addClass("hidden-xs hidden-sm");
			
			$("#colla-collapse-index").find("ul.nav:first-of-type").find("li.boardTab").click(function() {
				$('button.navbar-toggle').click();
			});
		},
		
		deleteSelectedBoardButtonEvent : function() {
			bootstrapTable.myBoardListTable.parents(".panel-body").siblings(".panel-footer").find('button[name="deleteSelectedBoardBtn"]').click(function() {
				deleteCheckedBoards(bootstrapTable.myBoardListTable);
			});
			
			bootstrapTable.allBoardListTable.parents(".panel-body").siblings(".panel-footer").find('button[name="deleteSelectedBoardBtn"]').click(function() {
				deleteCheckedBoards(bootstrapTable.allBoardListTable);
			});
			
			function deleteCheckedBoards($table) {
				rows = $table.bootstrapTable("getSelections");
				var boardIds = $.map(rows, function (row) {
					return row.id;
				});
				ajax.deleteSelectedBoard(boardIds);
				$table.bootstrapTable("refresh", {silent: true});
			}
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
	
	removeByUniqueId : function(boardId) {
		bootstrapTable.myBoardListTable.bootstrapTable("removeByUniqueId", boardId);
    	bootstrapTable.allBoardListTable.bootstrapTable("removeByUniqueId", boardId);
    	this.refresh();
	},
	
	remove : function(boardIds) {
		bootstrapTable.myBoardListTable.bootstrapTable("remove", boardIds);
    	bootstrapTable.allBoardListTable.bootstrapTable("remove", boardIds);
		this.refresh();
	},
	
	refresh : function() {
    	bootstrapTable.myBoardListTable.bootstrapTable("refresh", {silent: true});
    	bootstrapTable.allBoardListTable.bootstrapTable("refresh", {silent: true});
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
//				notify.notify("Ajax 통신 성공 : " + status, " message : " + data.message);
				notify.notify("message", data.message, "success");
				bootstrapTable.removeByUniqueId(boardId);
			},
			error : function(request, status, error) {
				notify.notify('Ajax 통신 실패 : ' + request.status, 'error : ' + error, "error");
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
//				notify.notify("Ajax 통신 성공 : " + status, " message : " + data.message);
				notify.notify("message", data.message), "success";
				bootstrapTable.remove(boardIds);
			},
			error : function(request, status, error) {
				notify.notify('Ajax 통신 실패 : ' + request.status, 'error : ' + error, "error");
				notify.notify("Warning", "You must select board", "error");
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