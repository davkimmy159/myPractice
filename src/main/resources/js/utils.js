var path = {
	
	getContextPath : function() {
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	},
	
	getFullContextPath : function() {
		return window.location.protocol + '//' + window.location.host + this.getContextPath();
	}
};