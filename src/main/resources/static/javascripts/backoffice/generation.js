var generation = {};
var _ctx = ($("meta[name='ctx']").attr("content")===undefined) ? "" : $("meta[name='ctx']").attr("content");

// Prepend context path to all jQuery AJAX requests
$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
    if (!options.crossDomain) {
        options.url = _ctx + options.url;
    }
});

//TODO: TO FIND ALL GENERATION
generation.findAll = function(fnCallback){
	$.ajax({ 
	    url: "/v1/api/admin/generations", 
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

//TODO: TO FIND ALL GENERATION
generation.findAllCoursesByGenerationId = function(id, callback){
	$.ajax({ 
	    url: "/v1/api/admin/generations/"+ id + "/courses", 
	    type: 'GET', 
	    dataType: 'JSON', 
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success: function(response) { 
    		if(callback){
    			callback(response);
    		}
	    },
	    error:function(data,status,err) { 
	        console.log("error: "+data+" status: "+status+" err:"+err);
	    }
	});			
};

generation.addGeneration = function(generation, callback){
	$.ajax({ 
	    url: "/v1/api/admin/generations", 
	    type: 'POST', 
	    dataType: 'JSON', 
	    data : JSON.stringify(generation),
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success: function(response) { 
    		if(callback){
    			callback(response);
    		}
	    },
	    error:function(data,status,err) { 
	        console.log("error: "+data+" status: "+status+" err:"+err);
	    }
	});
};

generation.deleteGeneration = function(id, callback){
	$.ajax({ 
	    url: "/v1/api/admin/generations/"+id, 
	    type: 'DELETE', 
	    dataType: 'JSON', 
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success: function(response) { 
    		if(callback){
    			callback(response);
    		}
	    },
	    error:function(data,status,err) { 
	        console.log("error: "+data+" status: "+status+" err:"+err);
	    }
	});
}

generation.getGeneration = function(id, callback){
	$.ajax({ 
	    url: "/v1/api/admin/generations/"+id,
	    type: 'GET',
	    dataType: 'JSON', 
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success: function(response) { 
    		if(callback){
    			callback(response);
    		}
	    },
	    error:function(data,status,err) { 
	        console.log("error: "+data+" status: "+status+" err:"+err);
	    }
	});
}

generation.updateGeneration = function(generation, callback){
	$.ajax({ 
	    url: "/v1/api/admin/generations",
	    type: 'PUT',
	    dataType: 'JSON', 
	    data : JSON.stringify(generation),
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success: function(response) { 
    		if(callback){
    			callback(response);
    		}
	    },
	    error:function(data,status,err) { 
	        console.log("error: "+data+" status: "+status+" err:"+err);
	    }
	});
}