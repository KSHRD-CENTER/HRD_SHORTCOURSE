$(function() {
	var courseType = {};
	var checkPagination = true;
	var currentPage = 1;
	
	var _ctx = ($("meta[name='ctx']").attr("content")===undefined) ? "" : $("meta[name='ctx']").attr("content");

	// Prepend context path to all jQuery AJAX requests
	$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
	    if (!options.crossDomain) {
	        options.url = _ctx + options.url;
	    }
	});
	
	//TODO: TO FIND ALL COURSES TYPE
	courseType.findAll = function(){
		$.ajax({ 
		    url: "/v1/api/admin/courses", 
		    type: 'GET', 
		    dataType: 'JSON', 
		    data : {
		    	"limit" : 15,
		    	"page" : 1
		    },
		    beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
		    success: function(response) { 
		    	if(response.CODE=="0000"){
		    		$("#COURSE").html("");
		    		$("#COURSE_TEMPLATE").tmpl(response.DATA).appendTo("tbody#COURSE");
		    		if(checkPagination){
		    			$("#ALERT").attr("data-toastr-notification", response.MESSAGE);
		    			$("#ALERT").trigger("click");
			    		course.setPagination(response.PAGINATION.TOTAL_PAGES);
			    		checkPagination=false;
			    	}
		    	}
		    },
		    error:function(data,status,er) { 
		        console.log("error: "+data+" status: "+status+" er:"+er);
		    }
		});			
	};
});