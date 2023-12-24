
function showModalDialog(title, message) {
	Swal.fire({
		title: title,
		text: message,
		icon: 'success',
		confirmButtonText: 'OK'
	});
}
function showModalDialog2(title, message) {
	Swal.fire({
		title: title,
		text: message,
		icon: 'error',
		confirmButtonText: 'OK'
	});
}
function showModalDialog3(title, message) {
	Swal.fire({
		title: title,
		text: message,
		icon: 'warning',
		confirmButtonText: 'OK'
	});
}

function showErrorModal(message) {
	showModalDialog2("Error", message);
}

function showWarningModal(message) {
	showModalDialog3("Warning", message);
}
