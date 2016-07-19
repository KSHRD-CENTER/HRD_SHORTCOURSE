$(function(){
	var registration = {};
	var course = {};
	var _ctx = ($("meta[name='ctx']").attr("content")===undefined) ? "" : $("meta[name='ctx']").attr("content");

	// Prepend context path to all jQuery AJAX requests
	$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
	    if (!options.crossDomain) {
	        options.url = _ctx + options.url;
	    }
	});

	//TODO: TO REGISTER NEW STUDENT 
	registration.addNewRegistration = function(data, fnCallback){
		$.ajax({ 
		    url: "/v1/api/admin/students", 
		    type: 'POST', 
		    dataType: 'JSON', 
		    data : JSON.stringify(data),
		    beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
		    success: function(response) { 
		    	if(fnCallback){
		    		fnCallback(response);
		    	}
		    },
		    error:function(data,status,err) { 
		        console.log("error: "+data+" status: "+status+" err:"+err);
		    }
		});			
	};

	//TODO: TO FIND ALL COURSES BY FILTERING AND PAGINATION
	course.findAllShiftsByCourseId = function(id, fnCallback){
		$.ajax({ 
		    url: "/v1/api/admin/courses/"+id+"/shifts", 
		    type: 'GET', 
		    dataType: 'JSON', 
		    beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
		    success: function(response) { 
		    	if(fnCallback){
		    		fnCallback(response);
		    	}
		    },
		    error:function(data,status,err) { 
		        console.log("error: "+data+" status: "+status+" err:"+err);
		    }
		});			
	};
	
	generation.findAllCoursesByGenerationId(2, function(response){
		$("#SELECT_COURSE").html("<option value=''>All Courses</option>");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_COURSE");
		$(".selectpicker").selectpicker('refresh');
	});

	$("#SELECT_COURSE").change(function(){
		course.findAllShiftsByCourseId($(this).val(), function(response){
			$("#SELECT_SHIFT").html("<option value=''>All Shifts</option>");
			$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_SHIFT");
			$(".selectpicker").selectpicker('refresh');
		});
	});
	
	$("#btnRegister").click(function(){
		var courses = [];
		courses.push({
			"COURSE" 		: $("#SELECT_COURSE").val(),
			"SHIFT" 		: $("#SELECT_SHIFT").val(),
			"COST_PRICE"	: $("#SELECT_COURSE option:selected").data("cost"),
			"DISCOUNT" 		: 0,
			"PAID" 			: 0
		});
		var data = {
			"NAME" 		: $("#txtStudentName").val(),
			"GENDER" 	: $("#SELECT_GENDER").val(),
			"YEAR" 		: $("#SELECT_YEAR").val(),
			"TELEPHONE" : $("#txtTelephone").val(),
			"EMAIL" 	: $("#txtEmail").val(),
			"ADDRESS" 	: $("#textAreaAddress").val(),
			"UNIVERSITY": $("#SELECT_UNIVERSITY").val(),
			"STATUS" 	: "3",
			"COURSES" 	: courses
		};
		
		registration.addNewRegistration(data, function(response){
			if(response.CODE=="0000"){
				$("#ALERT").attr("data-toastr-notification", response.MESSAGE);
				$("#ALERT").trigger("click");
			}else{
				$("#ALERT").attr("data-toastr-notification", response.MESSAGE);
				$("#ALERT").trigger("click");
			}
		});
	});
});
