$(document).ready(function() {
	var datatable = $('#paginatedTable').DataTable({
		"processing": true,
		"serverSide": true,
		"responsive": true,
		"pageLength": 25,
		"scrollY": 600,
		"order": [[0, "desc"]],
		"dom": '<"top"lB>rt<"clear"><"bottom"ipB>',
		"pagingType": "simple_numbers",
		"ajax": {
			"url": "/matrimony/getmemberlist",
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
			{
				"data": "memberId", "name": "memberId", "title": "मेंबर ID", "className": "dt-body-left"
			},
/*			{
				"data": "fullName", "name": "fullName", "title": "पूरा नाम", "className": "dt-body-left", "render": function(data, type, row, meta) {
					var idp = row['memberId'];
					return '<a href="/addupdatemember?memberId=' + idp + '">' + data + '</a>';
				}
			},*/
			{
				"data": "fullName", "name": "fullName", "title": "पूरा नाम", "className": "dt-body-left",
			},
			{ "data": "fatherName", "name": "fatherName", "title": "पिता का नाम", "className": "dt-body-left" },
			{ "data": "education", "name": "education", "title": "शिक्षा", "className": "dt-body-left" },
			{ "data": "occupation", "name": "occupation", "title": "व्यवसाय", "className": "dt-body-left" },
		//	{ "data": "mobile1", "name": "mobile1", "title": "Contact No.", "className": "dt-body-left" },
			{ "data": "age", "name": "age", "title": "उम्र", "className": "dt-body-center" },
			{ "data": "samajArea", "name": "samajArea", "title": "समाज क्षेत्र", "className": "dt-body-left" },
			{ "data": "educationDetails", "name": "educationDetails", "title": "शिक्षा विवरण", "className": "dt-body-left" },
			{ "data": "cityState", "name": "cityState", "title": "सिटी/स्टेट", "className": "dt-body-left" },
//			{ "data": "state", "name": "state", "title": "State", "className": "dt-body-left" },
//			{ "data": "country", "name": "country", "title": "Country", "className": "dt-body-left" },
			{ "data": "motherName", "name": "motherName", "title": "माता का नाम", "className": "dt-body-left" },
			{ "data": "grandFather", "name": "grandFather", "title": "दादा का नाम", "className": "dt-body-left" },
			{ "data": "gender", "name": "gender", "title": "जेंडर", "className": "dt-body-center" },
			{ "data": "height", "name": "height", "title": "हाइट", "className": "dt-body-center" },
			{ "data": "gotra", "name": "gotra", "title": "गोत्र", "className": "dt-body-center" },
			{ "data": "manglik", "name": "manglik", "title": "मांगलिक?", "className": "dt-body-center" },
		//	{ "data": "mobile2", "name": "mobile2", "title": "सम्पक्र (whatsapp) :", "className": "dt-body-left" },
			{ "data": "boardUniversity", "name": "boardUniversity", "title": "बोर्ड/यूनिवर्सिटी", "className": "dt-body-left" },
			{ "data": "occupationDetails", "name": "occupationDetails", "title": "व्यवसाय विवरण", "className": "dt-body-left" },
			{ "data": "fatherOccupation", "name": "fatherOccupation", "title": "पिता विवरण", "className": "dt-body-left" },
			{ "data": "fullAddress", "name": "fullAddress", "title": "पूरा पता", "className": "dt-body-left" },
			{
				"data": "birthDate", "name": "birthDate", "title": "जन्म दिनांक", "className": "dt-body-center",
				render: function(d) {
					return moment(d).format("DD-MMM-YYYY");
				}
			},
			{ "data": "email", "name": "email", "title": "ईमेल", "className": "dt-body-left" },
			{ "data": "comments", "name": "comments", "title": "अन्य विवरण", "className": "dt-body-left" }
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