$(document).ready(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});

	$.ajax({
		type: "GET",
		url: "/matrimony/rest/api/getdropdownsamajarea",
		dataType: "json",
		success: function(data) {
			$.each(data.samajAreaDropDown, function(i, data) {
				if (jsSamajArea != null && data.text === jsSamajArea) {
					var div_data = "<option value='" + jsSamajArea + "'selected>" + jsSamajArea + "</option>";
				} else {
					var div_data = "<option value='" + data.value + "'>" + data.text + "</option>";
				}
				$(div_data).appendTo('#samajArea');
			});
		}
	});


	if (jsGender != null) {
		$("#gender option").filter(function() {
			return this.text == jsGender;
		}).attr('selected', true);
	}

	if (jsManglik != null) {
		$("#manglik option").filter(function() {
			return this.text == jsManglik;
		}).attr('selected', true);
	}

	if (jsBirthDate != null) {
		$("#birthDate").val(moment(jsBirthDate).format("MM/DD/YYYY"));
	}

	$.ajax({
		type: "GET",
		url: "/matrimony/rest/api/getdropdowneducation",
		dataType: "json",
		success: function(data) {
			$.each(data.educationDropDown, function(i, data) {
				if (jsEducation != null && data.text === jsEducation) {
					var div_data = "<option value='" + jsEducation + "'selected>" + jsEducation + "</option>";
				} else {
					var div_data = "<option value='" + data.value + "'>" + data.text + "</option>";
				}
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
				if (jsEducationDetails != null && data.text === jsEducationDetails) {
					var div_data = "<option value='" + jsEducationDetails + "'selected>" + jsEducationDetails + "</option>";
				} else {
					var div_data = "<option value='" + data.value + "'>" + data.text + "</option>";
				}
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
				if (jsOccupation != null && data.text === jsOccupation) {
					var div_data = "<option value='" + jsOccupation + "'selected>" + jsOccupation + "</option>";
				} else {
					var div_data = "<option value='" + data.value + "'>" + data.text + "</option>";
				}
				$(div_data).appendTo('#occupation');
			});
		}
	});

	/*	$.ajax({
			type: "GET",
			url: "https://countriesnow.space/api/v0.1/countries/info?returns=currency",
			// Append to data
			data: { country: $('#country').val() },
			dataType: "json",
			success: function(response) {
				$.each(response.data, function(i, data) {
					if (jsCountry != null && data.name === jsCountry) {
						var div_data = "<option value='" + jsCountry + "'selected>" + jsCountry + "</option>";
					} else if (jsCountry === null && data.name === "India") {
						var div_data = "<option value='" + data.name + "'selected>" + data.name + "</option>";
					} else {
						var div_data = "<option value='" + data.name + "'>" + data.name + "</option>";
					}
					$(div_data).appendTo('#country');
				});
			}
		});
		$.ajax({
			type: "POST",
			url: "https://countriesnow.space/api/v0.1/countries/states",
			data: { country: jsCountry != null ? jsCountry : "India" },
			dataType: "json",
			success: function(response) {
				$.each(response.data.states, function(i, data) {
					if (jsState != null && data.name === jsState) {
						var div_data = "<option value='" + jsState + "'selected>" + jsState + "</option>";
					} else if (jsState === null && data.name === "Madhya Pradesh") {
						var div_data = "<option value='" + data.name + "'selected>" + data.name + "</option>";
					}else {
						var div_data = "<option value='" + data.name + "'>" + data.name + "</option>";
					}
					$(div_data).appendTo('#state');
				});
			}
		});
		$('#country').change(function() {
			$('#state option').remove();
			var defaultDataState = "<option value=''>--Select State--</option>";
			$(defaultDataState).appendTo('#state');
			$('#cityState option').remove();
			var defaultDataCity = "<option value=''>--Select City--</option>";
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
		$.ajax({
			type: "POST",
			url: "https://countriesnow.space/api/v0.1/countries/state/cities",
			// Append to data
			data: { country: jsCountry != null ? jsCountry : "India", state: jsState != null ? jsState : "Madhya Pradesh" },
			dataType: "json",
			success: function(response) {
				$.each(response.data, function(i, data) {
					if (jsCity != null && data === jsCity) {
						var div_data = "<option value='" + jsCity + "'selected>" + jsCity + "</option>";
					} else {
						var div_data = "<option value='" + data + "'>" + data + "</option>";
					}
					$(div_data).appendTo('#cityState');
				});
			}
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
		});*/

});