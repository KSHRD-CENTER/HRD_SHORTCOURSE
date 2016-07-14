var shift = {};
var _ctx = ($("meta[name='ctx']").attr("content")===undefined) ? "" : $("meta[name='ctx']").attr("content");

// Prepend context path to all jQuery AJAX requests
$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
    if (!options.crossDomain) {
        options.url = _ctx + options.url;
    }
});

//TODO: TO FIND ALL SHIFT
shift.findAll = function(callback){
	$.ajax({ 
	    url: "/v1/api/admin/shifts", 
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

shift.addShift = function(shift, callback){
	$.ajax({ 
	    url: "/v1/api/admin/shifts", 
	    type: 'POST', 
	    dataType: 'JSON', 
	    data : JSON.stringify(shift),
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

shift.deleteShift = function(id, callback){
	$.ajax({ 
	    url: "/v1/api/admin/shifts", 
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
};

shift.updateShift = function(shift, callback){
	$.ajax({ 
	    url: "/v1/api/admin/shifts", 
	    type: 'PUT', 
	    dataType: 'JSON', 
	    data : JSON.stringify(shift),
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
