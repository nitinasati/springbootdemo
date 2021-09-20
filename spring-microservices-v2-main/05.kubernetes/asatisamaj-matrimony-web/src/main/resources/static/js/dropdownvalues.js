$(document).ready(function() {
	$.ajax({
		type: "GET",
		url: "/matrimony/rest/api/getdropdownsamajarea",
		dataType: "json",
		success: function(data) {
			$.each(data.samajAreaDropDown, function(i, data) {
				var div_data = "<option value='" + data.value + "'>" + data.text + "</option>";
				$(div_data).appendTo('#samajArea');
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
				$(div_data).appendTo('#education');
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
				$(div_data).appendTo('#educationDetails');
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
				$(div_data).appendTo('#occupation');
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