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
		}).done(function(response){
			/*$("#ALERT").attr("data-toastr-notification", response);
			$("#ALERT").trigger("click");*/
		});			
	};
	
	course.findAll();
	
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
	    	course.findAll(currentPage);
	    }); 
	};
	
});