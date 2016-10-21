$("#createBoardModal").on("shown.bs.modal", function(event) {
	$("input[name='boardName']").focus();
});

$(document).ready(function() {
	
	$("table#myBoardsList").bootstrapTable({
		
		striped : true,
		showHeader : true,
		showFooter : true
	});
	
	
	$("tr.eachRowInTable").click(function() {
        window.location = path.getFullContextPath() + "/board/" + $(this).find("td.eachBoardIdInTable").html();
    });
	
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