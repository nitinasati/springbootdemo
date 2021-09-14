$(document).ready(function() {
	var datatable = $('#paginatedTable').DataTable({
		"processing": true,
		"serverSide": true,
		"responsive": true,
		"order": [[0, "desc"]],
		"dom": '<"top"B>rt<"clear"><"bottom"ipB>',
		"pagingType": "simple_numbers",
		"ajax": {
			"url": "/users/paginated/getmemberlist",
			"type": "POST",
			"data": function(data) {
				// Append to data
				data.searchByMemberId = $('#searchByMemberId').val();
				data.searchByGender = $('#searchByGender').val();
				data.searchByEducation = $('#searchByEducation').val();
				data.searchByEducationDetails = $('#searchByEducationDetails').val();
				data.searchByAgeRange = $('#searchByAgeRange').val();
				data.searchByOccupation = $('#searchByOccupation').val();
				data.searchBySamajArea = $('#searchBySamajArea').val();
				//process data before sent to server.
			}
		},
		"columns": [
			{ "data": "memberId", "name": "memberId", "title": "Member ID" },
			{ "data": "fullName", "name": "fullName", "title": "Full Name" },
			{ "data": "age", "name": "age", "title": "Age" },
			{ "data": "fatherName", "name": "fatherName", "title": "Father's Name'" },
			{ "data": "birthDate", "name": "birthDate", "title": "Birth Date" },
			{ "data": "mobile1", "name": "mobile1", "title": "Mobile 1" },
			{ "data": "educationDetails", "name": "educationDetails", "title": "Education Details" },
			{ "data": "occupation", "name": "occupation", "title": "Occupation" },
			{ "data": "cityState", "name": "cityState", "title": "CityState" },
			{ "data": "samajArea", "name": "samajArea", "title": "Samaj Area'" },
			{ "data": "motherName", "name": "motherName", "title": "Mother's Name'" },
			{ "data": "grandFather", "name": "grandFather", "title": "Grandfather's Name'" },
			{ "data": "gender", "name": "gender", "title": "Gender" },
			{ "data": "height", "name": "height", "title": "Height" },
			{ "data": "gotra", "name": "gotra", "title": "Gotra" },
			{ "data": "manglik", "name": "manglik", "title": "Manglik" },
			{ "data": "mobile2", "name": "mobile2", "title": "Mobile 2" },
			{ "data": "education", "name": "education", "title": "Education" },
			{ "data": "boardUniversity", "name": "boardUniversity", "title": "Board/University" },
			{ "data": "occupationDetails", "name": "occupationDetails", "title": "Occupation Details" },
			{ "data": "fatherOccupation", "name": "fatherOccupation", "title": "Father's Occupation" },
			{ "data": "fullAddress", "name": "fullAddress", "title": "Full Address" },
			{ "data": "email", "name": "email", "title": "Email" },
			{ "data": "comments", "name": "comments", "title": "Other Details" }
		]
	});
	$('#searchBtn').click(function() {
		datatable.draw();
	});

	$('#btn-show-all-doc').on('click', expandCollapseAll);

	function expandCollapseAll() {
		datatable.rows('.parent').nodes().to$().find('td:first-child').trigger('click').length || datatable.rows(':not(.parent)').nodes().to$().find('td:first-child').trigger('click')
	}

	$.ajax({
		type: "GET",
		url: "/matrimony/rest/api/getdropdownsamajarea",
		dataType: "json",
		success: function(data) {
			$.each(data.samajAreaDropDown, function(i, data) {
				var div_data = "<option value='" + data.value + "'>" + data.text + "</option>";
				$(div_data).appendTo('#searchBySamajArea');
			});
		}
	});

	$.ajax({
		type: "GET",
		url: "/matrimony/rest/api/getdropdowneducation",
		dataType: "json",
		success: function(data) {
			$.each(data.educationDropDown, function(i, data) {
				var div_data = "<option value='" + data.value + "'>" + data.text + "</option>";
				$(div_data).appendTo('#searchByEducation');
			});
		}
	});

	$.ajax({
		type: "GET",
		url: "/matrimony/rest/api/getdropdowneducationdetails",
		dataType: "json",
		success: function(data) {
			$.each(data.educationDetailsDropDown, function(i, data) {
				var div_data = "<option value='" + data.value + "'>" + data.text + "</option>";
				$(div_data).appendTo('#searchByEducationDetails');
			});
		}
	});

	$.ajax({
		type: "GET",
		url: "/matrimony/rest/api/getdropdownoccupation",
		dataType: "json",
		success: function(data) {
			$.each(data.occupationDropDown, function(i, data) {
				var div_data = "<option value='" + data.value + "'>" + data.text + "</option>";
				$(div_data).appendTo('#searchByOccupation');
			});
		}
	});

	$.ajax({
		type: "GET",
		url: "/matrimony/rest/api/getdropdownagerange",
		dataType: "json",
		success: function(data) {
			$.each(data.ageRangeDropDown, function(i, data) {
				var div_data = "<option value='" + data.value + "'>" + data.text + "</option>";
				$(div_data).appendTo('#searchByAgeRange');
			});
		}
	});


});