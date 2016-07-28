var users = {};
var _ctx = ($("meta[name='ctx']").attr("content")===undefined) ? "" : $("meta[name='ctx']").attr("content");

// Prepend context path to all jQuery AJAX requests
$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
    if (!options.crossDomain) {
        options.url = _ctx + options.url;
    }
});

users.login = function(user, callback){
	$.ajax({ 
	    url: "/v1/api/admin/users/login",
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
}

$("#btnLogin").click(function(){
	var data = {
			"EMAIL": $("#txtEmail").val(),
			"PASSWORD": $("#txtPassword").val()
	};
	users.login(data, function(response){
		if(response.CODE=="0000"){
			document.location = "/administrator/dashboards";
		}else{
			$("#modalMessage").modal("show");
			$("#txtEmail").val("");
			$("#txtPassword").val("");
			return;
		}
	});
});