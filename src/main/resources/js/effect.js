effectObj = {
	
		IMCollaBtnEffect : function() {
		if($('#IMCollaBtn').length) {
			$('#IMCollaBtn').mouseover(function() {
				$(this).removeClass("animated flip")
					   .addClass('animated flip')
					   .one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
							$(this).removeClass("animated flip");
						});
			});
		}
	}
	
	
}

$(document).ready(function(){
	$.each(effectObj, function(index, element) {
		if((typeof element) == 'function') {
			element();
		}
	});
});