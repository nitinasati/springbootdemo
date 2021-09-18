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

	$.ajax({
		type: "GET",
		url: "https://countriesnow.space/api/v0.1/countries/info?returns=currency",
		// Append to data
		dataType: "json",
		success: function(response) {
			$.each(response.data, function(i, data) {
				var div_data = "<option value='" + data.name + "'>" + data.name + "</option>";
				$(div_data).appendTo('#country');
			});
		}
	});

	$('#country').change(function() {
		$('#state option').remove();
		var defaultDataState = "<option value=''>Select State</option>";
		$(defaultDataState).appendTo('#state');
		$('#cityState option').remove();
		var defaultDataCity = "<option value=''>Select City</option>";
		$(defaultDataCity).appendTo('#cityState');
		$.ajax({
			type: "POST",
			url: "https://countriesnow.space/api/v0.1/countries/states",
			// Append to data
			data: { country: $('#country').val() },
			dataType: "json",
			success: function(response) {
				$.each(response.data.states, function(i, data) {
					var div_data = "<option value='" + data.name + "'>" + data.name + "</option>";
					$(div_data).appendTo('#state');
				});
			}
		});
	});

	$('#state').change(function() {
		$('#cityState option').remove();
		var defaultDataCity = "<option value=''>Select City</option>";
		$(defaultDataCity).appendTo('#cityState');
		$.ajax({
			type: "POST",
			url: "https://countriesnow.space/api/v0.1/countries/state/cities",
			// Append to data
			data: { country: $('#country').val(), state: $('#state').val() },
			dataType: "json",
			success: function(response) {
				$.each(response.data, function(i, data) {
					var div_data = "<option value='" + data + "'>" + data + "</option>";
					$(div_data).appendTo('#cityState');
				});
			}
		});
	});
});