var path = {
	
	getContextPath : function() {
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	},
	
	getFullContextPath : function() {
		return window.location.protocol + '//' + window.location.host + this.getContextPath();
	}
};

$(document).ready(function() {
	
	// Checks and registers submit event to login, logout button
	if($("form#logoutForm").length > 0) {
		
//		notify.notify("Logout button", "logout button exist");
		
		$("a#logoutBtnMobile").click(function() {
			$("form#logoutForm").submit();
		});
		
		$("a#logoutBtnDesktop").click(function() {
			$("form#logoutForm").submit();
		});	
	} else {
		notify.notify("Warning", "logout form doesn't exist!", "error");
	}
});