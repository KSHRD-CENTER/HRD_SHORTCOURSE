var registration = {};
var _ctx = ($("meta[name='ctx']").attr("content")===undefined) ? "" : $("meta[name='ctx']").attr("content");

// Prepend context path to all jQuery AJAX requests
$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
    if (!options.crossDomain) {
        options.url = _ctx + options.url;
    }
});

registration.addNewRegistration = function(){
	$("#MESSAGE").html("YOU HAVE BEEN REGISTERED SUCCESSFULLY...");
	$("#modalMessage").modal("show");
};

registration.addNewRegistration();