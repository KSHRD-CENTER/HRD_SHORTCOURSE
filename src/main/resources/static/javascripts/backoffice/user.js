var users = {};
var _ctx = ($("meta[name='ctx']").attr("content")===undefined) ? "" : $("meta[name='ctx']").attr("content");

// Prepend context path to all jQuery AJAX requests
$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
    if (!options.crossDomain) {
        options.url = _ctx + options.url;
    }
});

users.addUser = function(user, callback){
	$.ajax({ 
	    url: "/v1/api/admin/users", 
	    type: 'POST', 
	    dataType: 'JSON', 
	    data : JSON.stringify(user),
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

users.deleteUser = function(id, callback){
	$.ajax({ 
	    url: "/v1/api/admin/users/"+id, 
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

users.getUser = function(id, callback){
	$.ajax({ 
	    url: "/v1/api/admin/users/"+id,
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

users.updateUser = function(user, callback){
	$.ajax({ 
	    url: "/v1/api/admin/users",
	    type: 'PUT',
	    dataType: 'JSON', 
	    data : JSON.stringify(user),
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

users.changePassword = function(user, callback){
	$.ajax({ 
	    url: "/v1/api/admin/users/change-password",
	    type: 'PUT',
	    dataType: 'JSON', 
	    data : JSON.stringify(user),
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