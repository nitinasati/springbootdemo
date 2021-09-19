$(document).ready(function() {
	var datatable = $('#paginatedTable').DataTable({
		"processing": true,
		"serverSide": true,
		"responsive": true,
		"pageLength": 20,
		"scrollY": 600, 
		"order": [[0, "desc"]],
		"dom": '<"top"lB>rt<"clear"><"bottom"ipB>',
		"pagingType": "simple_numbers",
		"ajax": {
			"url": "/users/paginated/getmemberlist",
			"type": "POST",
			"data": function(data) {
				// Append to data
				data.searchByMemberId = $('#searchByMemberId').val();
				data.searchByGender = $('#gender').val();
				data.searchByEducation = $('#education').val();
				data.searchByEducationDetails = $('#educationDetails').val();
				data.searchByAgeRange = $('#searchByAgeRange').val();
				data.searchByOccupation = $('#occupation').val();
				data.searchBySamajArea = $('#samajArea').val();
				//process data before sent to server.
			}
		},
		"columns": [
			{ "data": "memberId", "name": "memberId", "title": "Member ID", "className": "dt-body-left" },
			{ "data": "fullName", "name": "fullName", "title": "Full Name", "className": "dt-body-left" },
			{ "data": "age", "name": "age", "title": "Age", "className": "dt-body-center" },
			{ "data": "fatherName", "name": "fatherName", "title": "Father's Name", "className": "dt-body-left" },
			{
				"data": "birthDate", "name": "birthDate", "title": "Birth Date", "className": "dt-body-center",
				render: function(d) {
					return moment(d).format("DD-MMM-YYYY");
				}
			},
			{ "data": "mobile1", "name": "mobile1", "title": "Mobile 1", "className": "dt-body-left" },
			{ "data": "education", "name": "education", "title": "Education", "className": "dt-body-left" },
			{ "data": "educationDetails", "name": "educationDetails", "title": "Education Details", "className": "dt-body-left" },
			{ "data": "occupation", "name": "occupation", "title": "Occupation", "className": "dt-body-left" },
			{ "data": "cityState", "name": "cityState", "title": "CityState", "className": "dt-body-left" },
			{ "data": "state", "name": "state", "title": "State", "className": "dt-body-left" },
			{ "data": "country", "name": "country", "title": "Country", "className": "dt-body-left" },
			{ "data": "samajArea", "name": "samajArea", "title": "Samaj Area", "className": "dt-body-left" },
			{ "data": "motherName", "name": "motherName", "title": "Mother's Name", "className": "dt-body-left" },
			{ "data": "grandFather", "name": "grandFather", "title": "Grandfather's Name", "className": "dt-body-left" },
			{ "data": "gender", "name": "gender", "title": "Gender", "className": "dt-body-center" },
			{ "data": "height", "name": "height", "title": "Height", "className": "dt-body-center" },
			{ "data": "gotra", "name": "gotra", "title": "Gotra", "className": "dt-body-center" },
			{ "data": "manglik", "name": "manglik", "title": "Manglik", "className": "dt-body-center" },
			{ "data": "mobile2", "name": "mobile2", "title": "Mobile 2", "className": "dt-body-left" },
			{ "data": "boardUniversity", "name": "boardUniversity", "title": "Board/University", "className": "dt-body-left" },
			{ "data": "occupationDetails", "name": "occupationDetails", "title": "Occupation Details", "className": "dt-body-left" },
			{ "data": "fatherOccupation", "name": "fatherOccupation", "title": "Father's Occupation", "className": "dt-body-left" },
			{ "data": "fullAddress", "name": "fullAddress", "title": "Full Address", "className": "dt-body-left" },
			{ "data": "email", "name": "email", "title": "Email", "className": "dt-body-left" },
			{ "data": "comments", "name": "comments", "title": "Other Details", "className": "dt-body-left" }
		]
	});
	$('#searchBtn').click(function() {
		datatable.draw();
	});

	$('#btn-show-all-doc').on('click', expandCollapseAll);

	function expandCollapseAll() {
		datatable.rows('.parent').nodes().to$().find('td:first-child').trigger('click').length || datatable.rows(':not(.parent)').nodes().to$().find('td:first-child').trigger('click')
	}
});