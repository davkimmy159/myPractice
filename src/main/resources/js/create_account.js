notify.notify('title', 'test1');

$('#createAccount').click(function() {
	notify.notify('title', 'createAccount button is clicked');
});

notify.notify('title', 'test2');

$(document).ready(function() {
	$('form').validator();
});