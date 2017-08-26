var a = ({
	maxWidth : 600,
	maxHeight : 400,
	gimmeMax : function() {
		return this.maxWidth + " x " + this.maxHeight;
	},
	init : function() {
		console.log(this.gimmeMax());
		return this;
	}
}.init());
console.log(a);