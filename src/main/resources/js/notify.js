PNotify.prototype.options.styling = "bootstrap3";

var notify = {
		
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