var courseType = {};
var _ctx = ($("meta[name='ctx']").attr("content")===undefined) ? "" : $("meta[name='ctx']").attr("content");

// Prepend context path to all jQuery AJAX requests
$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
    if (!options.crossDomain) {
        options.url = _ctx + options.url;
    }
});

//TODO: TO FIND ALL COURSES TYPE
courseType.findAll = function(callback){
	$.ajax({ 
	    url: "/v1/api/admin/course-types", 
	    type: 'GET', 
	    dataType: 'JSON', 
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success: function(response) { 
	    	if(response.CODE=="0000"){
	    		if(callback){
	    			callback(response);
	    		}
	    	}
	    },
	    error:function(data,status,err) { 
	        console.log("error: "+data+" status: "+status+" err:"+err);
	    }
	});			
};

//TODO: TO FIND ALL GENERATIONS BY COURSE TYPE ID
courseType.findAllGenerations = function(id, callback){
	$.ajax({ 
	    url: "/v1/api/admin/course-types/"+ id +"/generations", 
	    type: 'GET', 
	    dataType: 'JSON', 
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success: function(response) { 
	    	console.log(response);
	    	if(callback){
	    		callback(response);
	    	}
	    },
	    error:function(data,status,err) { 
	        console.log("error: "+data+" status: "+status+" err:"+err);
	    }
	});			
};