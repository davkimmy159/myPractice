// <![CDATA[
// ]]>

PNotify.prototype.options.styling = "bootstrap3";

var notify = {
		
	/*
		notify
		info
		success
		error
	*/
		
	toggle : true,
	
	notify : function(title, msg, type) {
		if(!type) {
			type = "info";
		}
		
		if (this.toggle) {
			new PNotify({
	            title: title,
	            text: msg,
	            type: type
	        });
		}
	}
};

var utils = {
	
	// Check if string is not both null and empty
	checkStr : function(str) {
		// <![CDATA[
		if (((typeof str != "undefined") && (typeof str.valueOf() == "string")) && (str.length > 0)) {
		// ]]>
			if("" != utils.trim(str)) {
				return true;				
			} else {
				return false;
			}
		} else {
			return false;			
		}
	},
	
	
	trim : function(str) {
		return str.replace(/^\s*|\s*$/g, '');
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
	
	isDesktopSize : function() {
		return matchMedia("screen and (min-width: 768px)").matches;
	},
	
	executeAllFunctionMembers : function(object) {
		$.each(object, function(index, element) {
			if((typeof element) == 'function') {
				element();
			}
		});
	},
	
	clientValidatorBtnEvent : function($form) {
		
		// Signals when input is wrong and submit button clicked on client side
		$form.validator().on('submit', function(e) {
			if (e.isDefaultPrevented()) {
				notify.notify('Wrong input', '잘못된 입력이 있습니다.\n수정 후 다시 시도해주세요', 'error');
			} else {
				
			}
		});
	}
};

var setting = {
	logoutFormSubmitEvent : function() {

		// Checks and registers submit event to login, logout button
		if($("form#logoutForm").length > 0) {
			
//			notify.notify("Logout button", "logout button exist");
			
			$("a#logoutBtnMobile").click(function() {
				$("form#logoutForm").submit();
			});
			
			$("a#logoutBtnDesktop").click(function() {
				$("form#logoutForm").submit();
			});	
		} else {
//			notify.notify("Warning", "logout form doesn't exist!", "error");
		}
	},
		
	focusToInputOnCreateBoardModal : {
		
		focusToInput : function() {
			if($("div#createBoardModal").length > 0) {
				$("#createBoardModal").on("shown.bs.modal", function(event) {
					$("input[name='boardName']").focus();
				});
			}
		},

		ifWrongInputOpenModal : function() {
			if($("div#createBoardModal").length > 0) {
				// Shows 'create board' modal if board isn't created
				if($(".error_container").find("span").text()) {
					$("#createBoardModal").modal("show");
				}
			}
		}
	},
	
	focusToInputOnCreateMemoTab : function() {
		if($("a#memoTab").length > 0 && $("a#memoWriteTab").length > 0) {
			$("a#memoTab").on("shown.bs.tab", function (e) {
				if($("li#memoTabLi").hasClass("active")) {
					setFocusOnMemoWriter();
				}
			});
			
			$("a#memoWriteTab").on("shown.bs.tab", function (e) {
				setFocusOnMemoWriter();
			});
		}
		
		function setFocusOnMemoWriter() {
			var memoTitle = $('input[name="memoTitle"]').val();
			var memoContent = memoEditor.getData();
			
			if(!(utils.checkStr(memoTitle))) {
				$('input[name="memoTitle"]').focus();
			} else if (!(utils.checkStr(memoContent))) {
				memoEditor.focus();
			}
		}
	},
	
	focusToEditorOnUpdateMemoModal : function() {
		if($("#memoUpdateSubModal.modal").length > 0) {
			$("#memoUpdateSubModal.modal").on("shown.bs.modal", function (e) {
				memoUpdateEditor.focus();
			});
		}
	}
};

var path = {

	getFullContextPath : function() {
		return window.location.protocol + '//' + window.location.host + this.getContextPath();
	},
		
	getContextPath : function() {
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	},
	
	getCurrentRelativePath : function() {
		return window.location.pathname.substring(window.location.pathname.indexOf("/",2), window.location.pathname.length);
//		document.getElementsByName("currentRelativePath")[0].value = window.location.pathname.substring(window.location.pathname.indexOf("/",2), window.location.pathname.length);
	}
};

$(document).ready(function() {
	
	utils.executeAllFunctionMembers(setting);
	utils.executeAllFunctionMembers(setting.focusToInputOnCreateBoardModal);
	
});