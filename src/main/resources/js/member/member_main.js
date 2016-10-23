$("#createBoardModal").on("shown.bs.modal", function(event) {
	$("input[name='boardName']").focus();
});

$(document).ready(function() {
	
	/* Bootstrap table plugin settings */
	$("table#myBoardsList").bootstrapTable({
		classes : "table table-no-bordered",
		showHeader : true,
//		showFooter : true,
		showRefresh : true,
		showToggle : true,
		showColumns : true,
		search : true,
		searchOnEnterKey : true,
		trimOnSearch : true,
		searchTimeOut : 500,
		idField : "number",
		detailView : true,
		detailFormFormatter : function(index, row, element) {
			var format = "test!!!";
			return format;
		},
		pagination : true,
		pageSize : 10,
		pageList : [10, 25, 50, 100],
		selectItemName : "selectedItem",
		
		onClickCell : function(field, value, row, $element) {
			if(field != "checkbox" && field != "utils") {
				window.location = path.getFullContextPath() + "/board/" + $element.siblings(".eachBoardIdInTable").html();
			}
		}
	});
	
	$("input[type='checkbox'][data-field='1']").parent().parent("li").addClass("hidden-xs");
	$("input[type='checkbox'][data-field='3']").parent().parent("li").addClass("hidden-xs");
	$("input[type='checkbox'][data-field='4']").parent().parent("li").addClass("hidden-xs");
	
	$(".search").removeClass("pull-right").addClass("pull-left").css("width", "150px");
	
	$(".pagination-info").remove();
	
	$(".fixed-table-pagination").addClass("text-center");

	
	
	/*
	$(".clickForJoin").click(function() {
        window.location = path.getFullContextPath() + "/board/" + $(".clickForJoin").html();
    });
    */
	
	
	// Shows 'create board' modal if board isn't created
	if($(".error_container").find("span").text()) {
		$("#createBoardModal").modal("show");
	}
	
	/*
	// Shows 'create board' modal if board doesn't exist (URL connection)
	if($("#boardExists").val()) {
		notify.notify("test", $("boardExists").val());
		$("#createBoardModal").modal("show");
	}
	*/
	
	// Signals when input is wrong and submit button clicked on client side
	$("form#createBoardForm").validator().on('submit', function(e) {
		if (e.isDefaultPrevented()) {
			notify.notify('Wrong input', '잘못된 입력이 있습니다.\n수정 후 다시 시도해주세요', 'error');
		} else {
			
		}
	});
});