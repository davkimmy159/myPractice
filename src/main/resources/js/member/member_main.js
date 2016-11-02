bootstrapTable = {

	myBoardListTable : $("table#myBoardList"),
	allBoardListTable : $("table#allBoardList"),
		
	tableSetting : function() {
		
		/* Bootstrap table plugin settings for my board list */
		$("table#myBoardList").bootstrapTable({
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
			
//			sidePagination : "server",
			
//			url : 'member/member_main',
			/*
			method : "GET",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			cache : true,
			*/
			
			/*
			ajax : function(params) {
			   
				notify.notify(params);
				
		        setTimeout(function () {
		            params.success({
		            	total : params.data.total,
		            	rows : params.data.boardList
		            });
		        }, 500);
		    },
		    */
		    
			/*
			queryParams : function() {
			    return {
			        type: 'owner',
			        sort: 'updated',
			        direction: 'desc',
			        per_page: 100,
			        page: 1
			    };
			},
			*/
			
			onPageChange : function(number, size) {
				$("span.pagination-info").remove();
				bootstrapTable.sideSetting.eachCellClickEvent();
				
				// Basic size
				if (utils.isDesktopSize()) {
					bootstrapTable.responsiveSetting.basicSize();	
					
				// Mobile size
				} else {
					bootstrapTable.responsiveSetting.mobileSize();
				}
			}
			
		});
		
		/* Bootstrap table plugin settings for my board list */
		$("table#allBoardList").bootstrapTable({
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
				$("span.pagination-info").remove();
				
				// Basic size
				if (utils.isDesktopSize()) {
					bootstrapTable.responsiveSetting.basicSize();	
					
				// Mobile size
				} else {
					bootstrapTable.responsiveSetting.mobileSize();
				}
			}
		});
		
		utils.executeAllFunctionMembers(this.sideSetting);
	},
	
	sideSetting : {
		initialSideSetting : function() {
			$("input[type='checkbox'][data-field='number']").parent().parent("li").addClass("hidden-xs");
			$("input[type='checkbox'][data-field='create']").parent().parent("li").addClass("hidden-xs hidden-sm");
			$("input[type='checkbox'][data-field='last up']").parent().parent("li").addClass("hidden-xs hidden-sm");
			
			$(".search").removeClass("pull-right").addClass("pull-left").css("width", "140px");
			
			$("span.pagination-info").remove();
		},
		
		eachCellClickEvent : function() {
			$("span.boardDownloadBtn").click(function() {
				notify.notify("test 1", $(this));
			});
			
			$("span.boardSettingBtn").click(function() {
				notify.notify("test 2", $(this));
			});
			
			$("span.boardDeleteBtn").click(function() {
				var boardId = $(this).parent().siblings(".eachBoardIdInTable").find("span").text();

				ajax.deleteOneBoardFromBtn(boardId);
				
//				$(this).parent().parent().remove();
			});
			
			$("button[name='deleteSelectedBoardBtn']").click(function() {
				var boardIds = $.map(bootstrapTable.myBoardListTable.bootstrapTable('getSelections'), function (row) {
	                notify.notify(Object.keys(row));
					return row;
	            });
				ajax.deleteSelectedBoard(boardIds);
			});
		}
	},
	
	responsiveSetting : {
		basicSize : function() {
			$("div.pagination-detail").removeAttr("style");
			$("div.pagination").removeAttr("style");
			$("div.pagination-detail").removeClass("text-center");
			$("div.pagination").removeClass("text-center");
		},
		
		mobileSize : function() {
			$("div.pagination-detail").css("width", "100%");
			$("div.pagination").css("width", "100%");
			$("div.pagination-detail").addClass("text-center");
			$("div.pagination").addClass("text-center");
		}
	},
	
	// For 'data-formatter' attribute in checkbox td in bootstrap table
	checkboxFormatter : function(value, row, index) {
		if($("span#loginUsername").text() != $.parseHTML(row.owner)[1].innerHTML) {
            return {
                disabled: true
            };
        }
	}
};

ajax = {
	deleteOneBoardFromBtn : function(boardId) {
		$.ajax({
			url : '../ajax/board/deleteOneBoardFromBtn',
			type : 'GET',
			data : {
				'boardId' : boardId
			},
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			// cache: false,
			// processData: false,
			success : function(data, status) {
				notify.notify("Ajax 통신 성공 code : " + status + " message : " + data.message);
				notify.notify(boardId, typeof boardId);
				bootstrapTable.myBoardListTable.bootstrapTable("removeByUniqueId", boardId);
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
				notify.notify("Ajax 통신 성공 code : " + status + " message : " + data.message);
				bootstrapTable.myBoardListTable.bootstrapTable("getSelections").remove();
//				bootstrapTable.myBoardListTable.bootstrapTable("remove", {field: "number", values: boardIds});
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