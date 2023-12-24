function clearFilter() {
	window.location = moduleURL;	
}

function showDeleteConfirmModal(link, entityName) {
	entityId = link.attr("entityId");
	
	$("#yesButton").attr("href", link.attr("href"));	
	$("#confirmText").text("Are you sure you want to delete this "
							 + entityName + " ID " + entityId + "?");
	$("#confirmModal").modal();	
}

function showDeleteConfirmModal2(link, entityName){
	entityId = link.attr("entityId");

	Swal.fire({
		title: "Delete Confirmation",
		text: "Are you sure you want to delete this " + entityName + " ID " + entityId + "?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonColor: "#3085d6",
		cancelButtonColor: "#d33",
		confirmButtonText: "Yes",
	}).then((result) => {
		if (result.isConfirmed) {
			window.location.href = link.attr("href");
		}
	});
}


function deleteConf(link,entityName){
	entityId = link.attr("entityId");

	const swalWithBootstrapButtons = Swal.mixin({
		customClass: {
			confirmButton: "btn btn-success",
			cancelButton: "btn btn-danger"
		},
		buttonsStyling: true

	});
	swalWithBootstrapButtons.fire({
		title: "Are you sure you want to delete this " + entityName + " ID " + entityId + "?",
		text: "You won't be able to revert this!",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: "Yes, delete it!",
		cancelButtonText: "No, cancel!",
		reverseButtons: true
	}).then((result) => {
		if (result.isConfirmed) {
			window.location.href = link.attr("href");
			swalWithBootstrapButtons.fire({
				title: "Deleted!",
				text: "Your file has been deleted.",
				icon: "success"
			});
		} else if (
			/* Read more about handling dismissals below */
			result.dismiss === Swal.DismissReason.cancel
		) {
			swalWithBootstrapButtons.fire({
				title: "Cancelled",
				text: "Your imaginary file is safe :)",
				icon: "error"
			});
		}
	});
}