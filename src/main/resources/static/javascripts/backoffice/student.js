$(function() {
	
	var student = {};
	var checkPagination = true;
	var currentPage = 1;
	
	var _ctx = ($("meta[name='ctx']").attr("content")===undefined) ? "" : $("meta[name='ctx']").attr("content");

	// Prepend context path to all jQuery AJAX requests
	$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
	    if (!options.crossDomain) {
	        options.url = _ctx + options.url;
	    }
	});
	
	//TODO: TO FIND ALL STUDENT BY FILTERING AND PAGINATION
	student.findAll = function(){
		$.ajax({ 
		    url: "/v1/api/admin/students", 
		    type: 'GET', 
		    dataType: 'JSON', 
		    data : {
		    	"limit" : $("#SELECT_PER_PAGE").val(),
		    	"page" : currentPage,
		    },
		    beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
		    success: function(response) { 
		    	if(response.CODE=="0000"){
		    		if(response.DATA.length > 0){
		    			$("#STUDENT").html("");
		    			$("#STUDENT_TEMPLATE").tmpl(response.DATA).appendTo("tbody#STUDENT");
		    			if(checkPagination){
		    				student.setPagination(response.PAGINATION.TOTAL_PAGES);
		    				checkPagination=false;
		    			}
		    		}else{
		    			$("#STUDENT").html("<tr style='text-align:center;'><td colspan='7'>NO CONTENT</td></tr>");
		    			$("#PAGINATION").html("");
		    		}
		    	}
		    },
		    error:function(data,status,err) { 
		        console.log("error: "+data+" status: "+status+" err:"+err);
		    }
		});			
	};
	
	//TODO: TO SET THE PAGINATION FOR THE USERS LIST
	student.setPagination = function(totalPage){
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
	    }).on("page", function(event, page){
	    	checkPagination = false;
	    	currentPage = page;
	    	student.findAll();
	    }); 
	};
	
	//TODO: LOADING THE COURSE TYPE TO COMBO BOX
	courseType.findAll(function(response){
		$("#SELECT_COURSETYPE").html("<option value=''>All Course Types</option>");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_COURSETYPE");
		$("#SELECT_REGISTER_COURSETYPE").html("<option value=''>Choose Course Types</option>");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_REGISTER_COURSETYPE");
		$(".selectpicker").selectpicker('refresh');
	});
	
	//TODO: LOADING THE GENERATION TO COMBO BOX
	generation.findAll(function(response){
		$("#SELECT_GENERATION").html("<option value=''>All Generations</option>");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_GENERATION");
		$("#SELECT_REGISTER_GENERATION").html("<option value=''>Choose Generations</option>");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_REGISTER_GENERATION");
		$(".selectpicker").selectpicker('refresh');
	});
	
	//TODO: LOADING THE SHIFT TO COMBO BOX
	shift.findAll(function(response){
		$("#SELECT_SHIFT").html("<option value=''>All Shifts</option>");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_SHIFT");
		$("#SELECT_REGISTER_SHIFT").html("<option value=''>Choose Shifts</option>");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_REGISTER_SHIFT");
		$(".selectpicker").selectpicker('refresh');
	});
	
	student.findAll();
	
	$("#SELECT_GENERATION").change(function(){
		generation.findAllCoursesByGenerationId($(this).val(), function(response){
			$("#SELECT_COURSE").html("<option value=''>All Courses</option>");
			$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_COURSE");
			$(".selectpicker").selectpicker('refresh');
		});
	});
	
	//TODO: EVENT HANDLING ON THE PER PAGE CHANGE
	$("#SELECT_PER_PAGE").change(function(){
		checkPagination = true;
		student.findAll();
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
	
	$("#SELECT_REGISTER_COURSETYPE").change(function(){
		courseType.findAllGenerations($(this).val(), function(response){
			$("#SELECT_REGISTER_GENERATION").html("<option value=''>Choose Generations</option>");
			$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_REGISTER_GENERATION");
			$(".selectpicker").selectpicker('refresh');
		});
	});
	
	$("#SELECT_REGISTER_GENERATION").change(function(){
		generation.findAllCoursesByGenerationId($(this).val(), function(response){
			$("#SELECT_REGISTER_COURSE").html("<option value=''>Choose Courses</option>");
			$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_REGISTER_COURSE");
			$(".selectpicker").selectpicker('refresh');
		});
	});
	
	$("#SELECT_REGISTER_COURSE").change(function(){
		course.findAllShiftsByCourseId($(this).val(), function(response){
			$("#SELECT_REGISTER_SHIFT").html("<option value=''>Choose Shifts</option>");
			$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_REGISTER_SHIFT");
			$(".selectpicker").selectpicker('refresh');
		});
	});
	
	var index = 0;
	$("#btnAddMoreCourse").click(function(){
		var courseRow = "<tr>" +
					   "	<td>"+ ++index +"</td>" +
					   " 	<td id='COURSE' data-id='"+$('#SELECT_REGISTER_COURSE').val()+"'>"+ $('#SELECT_REGISTER_COURSE option:selected').text()  +"</td>" +
					   "	<td id='COURSETYPE' data-id='"+$("#SELECT_REGISTER_COURSETYPE").val()+"'>"+ $("#SELECT_REGISTER_COURSETYPE option:selected").text() +"</td>" +
					   "	<td id='GENERATION' data-id='"+$("#SELECT_REGISTER_GENERATION").val()+"'>"+ $("#SELECT_REGISTER_GENERATION option:selected").text() +"</td>" +
					   "	<td id='SHIFT' data-id='"+ $("#SELECT_REGISTER_SHIFT").val()+"'>"+ $("#SELECT_REGISTER_SHIFT option:selected").text() +"</td>" +
					   " 	<td id='COSTPRICE'><span class='badge badge-success'>$ "+ $("#txtCostPrice").val() +"</span></td>" +
					   "	<td id='DISCOUNT'><span class='badge badge-success'>"+ $("#txtDiscount").val() +" %</span></td>" +
					   "	<td id='PAID'><span class='badge badge-success'>$ "+ $("#txtPaid").val() +"</span></td>" +
					   " 	<td id='REGISTERED_DATE'>28-06-2016</td>" +
					   "	<td>" +
					   "		<input type='button' class='btn btn-red btn-ripple' value='REMOVE'/>" +
					   "	</td>" +
					   "</tr>";
		$("#TABLE_COURSE tbody").append(courseRow);
		$(".selectpicker").selectpicker('refresh');
	});
});