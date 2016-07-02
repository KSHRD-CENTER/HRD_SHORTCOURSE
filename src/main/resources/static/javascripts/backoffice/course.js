$(function() {
	
	//$('.selectpicker').selectpicker();
	//Pleasure.initSelectPicker();
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
		    	"course" : $("#txtSearch").val()
		    },
		    beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
		    success: function(response) { 
		    	if(response.CODE=="0000"){
		    		$("#COURSE").html("");
		    		if(response.DATA.length > 0){
		    			$("#COURSE_TEMPLATE").tmpl(response.DATA).appendTo("tbody#COURSE");
		    			if(checkPagination){
		    				/*$("#ALERT").attr("data-toastr-notification", response.MESSAGE);
		    				$("#ALERT").trigger("click");*/
		    				course.setPagination(response.PAGINATION.TOTAL_PAGES);
		    				checkPagination=false;
		    			}
		    		}else{
		    			$("#COURSE").html("<tr style='text-align:center;'><td colspan='7'>NO CONTENT</td></tr>");
		    			$("#PAGINATION").html("");
		    		}
		    	}
		    },
		    error:function(data,status,err) { 
		        console.log("error: "+data+" status: "+status+" err:"+err);
		    }
		}).done(function(response){
			/*$("#ALERT").attr("data-toastr-notification", response);
			$("#ALERT").trigger("click");*/
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
	    }).on("page", function(event, page){
	    	checkPagination = false;
	    	currentPage = page;
	    	course.findAll();
	    }); 
	};
	
	//TODO: LOADING THE COURSE TYPE TO COMBO BOX
	courseType.findAll(function(response){
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_COURSETYPE");
		$(".selectpicker").selectpicker('refresh');
	});
	
	//TODO: LOADING THE GENERATION TO COMBO BOX
	generation.findAll(function(response){
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_GENERATION");
		$(".selectpicker").selectpicker('refresh');
	});
	
	//TODO: LOADING THE SHIFT TO COMBO BOX
	shift.findAll(function(response){
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_SHIFT");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_REGISTER_SHIFT");
		
		$(".selectpicker").selectpicker('refresh');
	});
	
	//TODO: LOADING THE DATA
	course.findAll();
	
	//TODO: EVENT HANDLING ON THE PER PAGE CHANGE
	$("#SELECT_PER_PAGE").change(function(){
		checkPagination = true;
		course.findAll();
	});
	
	$("#btnSearch").click(function(){
		checkPagination = true;
		currentPage = 1;
		course.findAll();
	});
});