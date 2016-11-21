bootstrapTable = {

	memoList : $("table#memoList"),
		
	tableSetting : function() {
		
		/* Memo list */
		$("table#memoList").bootstrapTable({
			columns: [
				{
            		field: "id",
            		title: "",
            		class: "hidden-xs",
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
            		title: "",
            		class: "",
            		formatter: function(value, row, index) {
            			var memo;
            			var string;
            			
            			string += '<div id="memoHeading' + '   ' +'" class="panel-heading" role="tab">' +
            					  '    <h4 class="panel-title">' +
            					  '        <a class="collapsed" data-toggle="collapse" data-parent="#memoListAccordion" href="#mainModal_tabContent3_memoListContent1">' +
            					  '            <span>Memo title 1</span>' +
            					  '        </a>' +
            					  '        <button type="button" class="close">' +
            					  '            <span class="glyphicon glyphicon-remove-sign"></span>' +
            					  '        </button>' +
            					  '        <span class="close">&nbsp;</span>' +
            					  '            <button type="button" class="close">' +
            					  '                <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>' +
            					  '        </button>' +
            					  '    </h4>' +
            					  '</div>' +
            					  '<div id="memoContent' + '   ' +'" class="panel-collapse collapse" role="tabpanel">' +
            					  '    <div class="panel-body">Accordion test 1</div>' +
            					  '</div>';
            		}
            	}
            ],
			
			url : path.getContextPath() + "/ajax/memo/getAllMemosOfBoard",
			sidePagination : "server",
			contentType	: 'application/json; charset=utf-8',
			method : "GET",
			dataType : 'json',
			queryParamsType : "limit",
			queryParams : function(params) {
				return params;
			},

			classes : "table table-no-bordered",

			idField : "id",
			uniqueId : "id",
			
			showRefresh : true,
			showExport : true,

			search : true,
			searchOnEnterKey : true,
			searchAlign : "left",
			trimOnSearch : true,
			searchTimeOut : 500,
			
			pagination : true,
			paginationLoop : true,
			pageList : [10, 20, 30, 50, 80, 100, "ALL"]
		});
		
	}
};

$(document).ready(function() {
	
	bootstrapTable.tableSetting();
	
});