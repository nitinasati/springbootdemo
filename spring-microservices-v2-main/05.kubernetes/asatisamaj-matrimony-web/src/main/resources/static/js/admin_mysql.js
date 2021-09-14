$(document).ready(function() {
	var datatable = $('#paginatedTable').DataTable({
		"processing": true,
		"serverSide": true,
		"order": [[0, "desc"]],
		"dom": '<"top"pB>rt<"clear"><"bottom"ipB>',
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
			{ "data": "fatherName", "name": "fatherName", "title": "Father's Name'" },
			{ "data": "motherName", "name": "motherName", "title": "Mother's Name'" },
			{ "data": "samajArea", "name": "samajArea", "title": "Samaj Area'" },
			{ "data": "grandFather", "name": "grandFather", "title": "Grandfather's Name'" },
			{ "data": "gender", "name": "gender", "title": "Gender" },
			{ "data": "age", "name": "age", "title": "Age" },
			{ "data": "birthDate", "name": "birthDate", "title": "Birth Date" },
			{ "data": "height", "name": "height", "title": "Height" },
			{ "data": "weight", "name": "weight", "title": "Weight" },
			{ "data": "complexion", "name": "complexion", "title": "Complexion" },
			{ "data": "manglik", "name": "manglik", "title": "Manglik" },
			{ "data": "education", "name": "education", "title": "Education" },
			{ "data": "educationDetails", "name": "educationDetails", "title": "Education Details" },
			{ "data": "boardUniversity", "name": "boardUniversity", "title": "Board/University" },
			{ "data": "occupation", "name": "occupation", "title": "Occupation" },
			{ "data": "occupationDetails", "name": "occupationDetails", "title": "Occupation Details" },
			{ "data": "fullAddress", "name": "fullAddress", "title": "Full Address" },
			{ "data": "cityState", "name": "cityState", "title": "CityState" },
			{ "data": "mobile1", "name": "mobile1", "title": "Mobile 1" },
			{ "data": "mobile2", "name": "mobile2", "title": "Mobile 2" },
			{ "data": "email", "name": "email", "title": "Email" },
			{ "data": "fatherOccupation", "name": "fatherOccupation", "title": "Father's Occupation" },
			{ "data": "brothers", "name": "brothers", "title": "Brothers" },
			{ "data": "marriedBrothers", "name": "marriedBrothers", "title": "Married Brothers" },
			{ "data": "sisters", "name": "sisters", "title": "Sisters" },
			{ "data": "marriedSisters", "name": "marriedSisters", "title": "Married Sisters" },
			{ "data": "vansh", "name": "vansh", "title": "Vansh" },
			{ "data": "gotra", "name": "gotra", "title": "Gotra" },
			{ "data": "requirement", "name": "requirement", "title": "Requirement" }
		]
	});
	$('#searchBtn').click(function() {
		datatable.draw();
	});

 
    $('#paginatedTable tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
    } );
 
    $('#button').click( function () {
        alert( table.rows('.selected').data().length +' row(s) selected' );
    } );

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
			$.each(data.occupationDropDown, function(i,data) {
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