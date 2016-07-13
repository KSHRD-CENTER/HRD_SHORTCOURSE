$(function() {
	
	var course = {};
	var checkPagination = true;
	var currentPage = 1;
	
	var _ctx = ($("meta[name='ctx']").attr("content")===undefined) ? "" : $("meta[name='ctx']").attr("content");

	// Prepend context path to all jQuery AJAX requests
	$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
	    if (!options.crossDomain) {
	        options.url = _ctx + options.url;
	    }
	});
	
	//TODO: TO FIND ALL COURSES BY FILTERING AND PAGINATION
	course.findAll = function(){
		$.ajax({ 
		    url: "/v1/api/admin/courses", 
		    type: 'GET', 
		    dataType: 'JSON', 
		    data : {
		    	"limit" : $("#SELECT_PER_PAGE").val(),
		    	"page" : currentPage,
		    	"course" : $("#txtSearch").val(),
		    	"courseTypeId" : $("#SELECT_COURSETYPE").val(),
		    	"generationId" : $("#SELECT_GENERATION").val(),
		    	"courseId" : $("#SELECT_COURSE").val(),
		    	"shiftId" : $("#SELECT_SHIFT").val()
		    },
		    beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
		    success: function(response) { 
		    	if(response.CODE=="0000"){
		    		$("#COURSE").html("");
		    		if(response.DATA.length > 0){
		    			$.each(response.DATA, function(key,value){
		    				response.DATA[key]["NO"] = (key+1)+((response.PAGINATION.PAGE-1) * response.PAGINATION.LIMIT);
		    				response.DATA[key]["SHIFT"] = response.DATA[key]["SHIFT"].replace(",", "<br />");;
						});
		    			$("#COURSE_TEMPLATE").tmpl(response.DATA).appendTo("tbody#COURSE");
		    			if(checkPagination){
		    				checkPagination=false;
		    				course.setPagination(response.PAGINATION.TOTAL_PAGES);
		    			}
		    		}else{
		    			$("#COURSE").html("<tr style='text-align:center;'><td colspan='8'>NO CONTENT</td></tr>");
		    			$("#PAGINATION").html("");
		    		}
		    	}
		    },
		    error:function(data,status,err) { 
		        console.log("error: "+data+" status: "+status+" err:"+err);
		    }
		});			
	};
	
	//TODO: TO FIND COURSE BY ID
	course.findCourseById = function(id, fnCallback){
		$.ajax({ 
		    url: "/v1/api/admin/courses/"+id, 
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
	
	//TODO: TO REGISTER A NEW COURSE
	course.addNewCourse = function(data, fnCallback){
		$.ajax({ 
		    url: "/v1/api/admin/courses", 
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
	}
	
	//TODO: TO UPDATE A COURSE BY ID
	course.updateCourse = function(id, data, fnCallback){
		$.ajax({ 
		    url: "/v1/api/admin/courses/"+id, 
		    type: 'PUT', 
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
	}
	
	//TODO: TO DELETE A COURSE BY ID
	course.deleteCourse = function(id, fnCallback){
		$.ajax({ 
		    url: "/v1/api/admin/courses/"+id, 
		    type: 'DELETE', 
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
	}
	
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
	
	//TODO: TO SET THE PAGINATION FOR THE USERS LIST
	course.setPagination = function(totalPage){
    	$('#PAGINATION').bootpag({
	        total: totalPage,
	        page: currentPage,
	        maxVisible: 10,
	        leaps: true,
	        firstLastUse: true,
	        first: 'First',
	        last: 'Last',
	        wrapClass: 'pagination',
	        activeClass: 'active',
	        disabledClass: 'disabled',
	        nextClass: 'next',
	        prevClass: 'prev',
	        lastClass: 'last',
	        firstClass: 'first'
	    });
	};
	
	$('#PAGINATION').bootpag().on("page", function(event, page){
    	checkPagination = false;
    	currentPage = page;
    	course.findAll();
    });
	
	//TODO: LOADING THE COURSE TYPE TO COMBO BOX
	courseType.findAll(function(response){
		$("#SELECT_COURSETYPE").html("<option value=''>All Course Types</option>");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_COURSETYPE");
		$(".selectpicker").selectpicker('refresh');
	});
	
	//TODO: LOADING THE GENERATION TO COMBO BOX
	generation.findAll(function(response){
		$("#SELECT_GENERATION").html("<option value=''>All Generations</option>");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_GENERATION");
		$(".selectpicker").selectpicker('refresh');
	});
	
	//TODO: LOADING THE SHIFT TO COMBO BOX
	shift.findAll(function(response){
		$("#SELECT_SHIFT").html("<option value=''>All Shifts</option>");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_SHIFT");
		$("#SELECT_REGISTER_SHIFT").html("<option value=''>Choose Shifts</option>")
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_REGISTER_SHIFT");
		$(".selectpicker").selectpicker('refresh');
	});
	
	$("#SELECT_COURSETYPE").change(function(){
		courseType.findAllGenerations($(this).val(), function(response){
			$("#SELECT_GENERATION").html("<option value=''>All Generations</option>");
			$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_GENERATION");
			$(".selectpicker").selectpicker('refresh');
		});
	});
	
	$("#SELECT_GENERATION").change(function(){
		generation.findAllCoursesByGenerationId($(this).val(), function(response){
			$("#SELECT_COURSE").html("<option value=''>All Courses</option>");
			$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_COURSE");
			$(".selectpicker").selectpicker('refresh');
		});
	});
	
	$("#SELECT_COURSE").change(function(){
		course.findAllShiftsByCourseId($(this).val(), function(response){
			$("#SELECT_SHIFT").html("<option value=''>All Shifts</option>");
			$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_SHIFT");
			$(".selectpicker").selectpicker('refresh');
		});
	});
	
	//TODO: LOADING THE DATA
	course.findAll();
	
	//TODO: EVENT HANDLING ON THE PER PAGE CHANGE
	$("#SELECT_PER_PAGE").change(function(){
		currentPage = 1;
		checkPagination = true;
		course.findAll();
	});
	
	$("#btnSearch").click(function(){
		checkPagination = true;
		currentPage = 1;
		course.findAll();
	});
	
	var index = 0;
	$("#btnAddMoreShift").click(function(){
		if($("#SELECT_REGISTER_SHIFT").val()!=""){
			var shiftRow = "<tr data-id='"+$("#SELECT_REGISTER_SHIFT").val()+"' data-start_date='"+ $("#txtStartDate").val() +"'>" +
							"	<td>" + ++index + "</td>" +
							"	<td>" + $("#SELECT_REGISTER_SHIFT option:selected").text()+"</td>" +
							"	<td>" + $("#txtStartDate").val()+"</td>" +
							"	<td><input type='button' class='btn btn-red btn-ripple' value='REMOVE' id='btnRemoveShift'/></td>" +
							"</tr>";
			$("#TABLE_SHIFT tbody").append(shiftRow);
			$('#SELECT_REGISTER_SHIFT option:selected').remove();
			$(".selectpicker").selectpicker('refresh');
		}
	});
	
	$("#btnRegisterNewCourse").click(function(){
		$("#TABLE_SHIFT tbody").html("");
		
		//TODO: LOADING THE SHIFT TO COMBO BOX
		shift.findAll(function(response){
			$("#SELECT_REGISTER_SHIFT").html("<option value=''>Choose Shifts</option>")
			$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_REGISTER_SHIFT");
			$(".selectpicker").selectpicker('refresh');
		});
		
		//TODO: LOADING THE COURSE TYPE TO COMBO BOX
		courseType.findAll(function(response){
			$("#SELECT_REGISTER_COURSETYPE").html("<option value=''>All Course Types</option>");
			$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_REGISTER_COURSETYPE");
			$(".selectpicker").selectpicker('refresh');
		});
		
		generation.findAll(function(response){
			$("#SELECT_REGISTER_GENERATION").html("<option value=''>All Generations</option>");
			$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_REGISTER_GENERATION");
			$(".selectpicker").selectpicker('refresh');
		});
		
	});
	
	$("#SELECT_REGISTER_COURSETYPE").change(function(){
		courseType.findAllGenerations($(this).val(), function(response){
			$("#SELECT_REGISTER_GENERATION").html("<option value=''>All Generations</option>");
			$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_REGISTER_GENERATION");
			$(".selectpicker").selectpicker('refresh');
		});
	});
	
	//TODO: WHEN CLICK ON THE SAVE CHANGE BUTTON
	$("#btnSaveChange").click(function(){
		var courseDetails = [];
		$("#TABLE_SHIFT tbody tr").each(function(key, value){
			courseDetails.push({
				"SHIFT":$(this).data("id"),
				"START_DATE" : $(this).data("start_date")
			});
		});
		var input = {
			"COURSE_NAME" : $("#txtCourseName").val(),
			"COST_PRICE" : $("#txtCostPrice").val(),
			"DESCRIPTION" : $("#txtDescription").val(),
			"DISCOUNT" : $("#txtDiscount").val(),
			"COURSE_TYPE" : $("#SELECT_REGISTER_COURSETYPE").val(),
			"GENERATION" : $("#SELECT_REGISTER_GENERATION").val(),
			"STATUS" : $('input[name=radioStatus]:checked').val(),
			"TOTAL_HOUR" : $("#txtTotalHour").val(),
			"COURSE_DETAILS" : courseDetails
		}
		
		//TODO: TO UPDATE THE COURSE
		if($(this).data("id")!=null || $(this).data("id")!=""){
			course.updateCourse($(this).data("id"), input, function(response){
				if(response.CODE=="0000"){
					$("#ALERT").attr("data-toastr-notification", response.MESSAGE);
					$("#ALERT").trigger("click");
				}else{
					$("#ALERT").attr("data-toastr-notification", response.MESSAGE);
					$("#ALERT").trigger("click");
				}
				console.log("RESPONSE DATA==>",response);
			});
			
		//TODO: TO DELETE THE COURSE
		}else{
			course.addNewCourse(input, function(response){
				console.log(response);
				if(response.CODE=="0000"){
					$("#ALERT").attr("data-toastr-notification", response.MESSAGE);
					$("#ALERT").trigger("click");
					checkPagination = true;
					course.findAll();
					$("#btnClose").trigger('click');
				}
			});
		}
	});
	
	//TODO: EVENT WHEN EDIT
	$(document).on('click', '#btnEdit', function(){
		$("#TITLE").html("UPDATE EXISTING COURSE");
		var id = $(this).parents("tr").data("id");
		course.findCourseById(id, function(response){
			console.log(response);
			if(response.CODE=="0000"){
				$("#txtCourseName").val(response.DATA.NAME);
				$("#txtDescription").val(response.DATA.DESCRIPTION);
				$("#txtCostPrice").val(response.DATA.COST);
				$("#txtDiscount").val(response.DATA.DISCOUNT);
				$("#SELECT_REGISTER_COURSETYPE").val(response.DATA.GENERATION.COURSE_TYPE.ID);
				$("#txtTotalHour").val(response.DATA.TOTAL_HOUR);
				$("#SELECT_REGISTER_GENERATION").val(response.DATA.GENERATION.ID);
				$(".selectpicker").selectpicker('refresh');
				
				var index = 0;
				$("#TABLE_SHIFT tbody").html("");
				$.each(response.DATA.COURSE_DETAILS, function(key,value){
					var shiftRow = "<tr data-id='"+value.SHIFT.ID+"' data-start_date='"+ $("#txtStartDate").val() +"'>" +
								   "	<td>" + ++index + "</td>" +
								   "	<td>" + value.SHIFT.NAME +
								   "	<td>" + value.START_DATE +"</td>" +
								   "	<td><input type='button' class='btn btn-red btn-ripple' value='REMOVE' id='btnRemoveShift'/></td>" +
								   "</tr>";
					$("#TABLE_SHIFT tbody").append(shiftRow);
    				console.log(value);
				});
				
				//TODO: TO SET THE ID TO THE BUTTON
				$("#btnSaveChange").data("id", id);
				
				//TODO: TO SHOW THE UPDATE MODAL
				$("#modalAddNewCourse").modal("show");
			}
		});
	});
	
	$("#btnRegisterNewCourse").click(function(){
		$("#TITLE").html("REGISTER NEW COURSE");
		$("#modalAddNewCourse").modal("show");
	});
	
	$(document).on('click', "#btnRemoveShift", function(){
		$(this).parents("tr").remove();
	});
	
	$(document).on('click', '#btnDelete', function(){
		$("#modalMessage").modal("show");
		$("#btnOk").data("id", $(this).parents("tr").data("id"));
	});
	
	$("#btnOk").click(function(){
		course.deleteCourse($(this).data("id"), function(response){
			if(response.CODE=="0000"){
				$("#ALERT").attr("data-toastr-notification", response.MESSAGE);
				$("#ALERT").trigger("click");
				course.findAll();
				$("#modalMessage").modal("hide");
			}else{
				$("#ALERT").attr("data-toastr-notification", response.MESSAGE);
				$("#ALERT").trigger("click");
			}
		});
	});
	
});