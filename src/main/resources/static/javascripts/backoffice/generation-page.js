$(function() {
	
	var generationPage = {};
	var checkPagination = true;
	var currentPage = 1;
	
	var _ctx = ($("meta[name='ctx']").attr("content")===undefined) ? "" : $("meta[name='ctx']").attr("content");

	// Prepend context path to all jQuery AJAX requests
	$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
	    if (!options.crossDomain) {
	        options.url = _ctx + options.url;
	    }
	});
	
	
	//TODO: TO FIND ALL THE GENERATIONS THE LOAD INTO THE TABLE
	generation.findAll(function(response){
		console.log(response);
		$.each(response.DATA, function(key,value){
			response.DATA[key]["NO"] = (key+1);
		});
		$("#GENERATION").html("");
		$("#GENERATION_TEMPLATE").tmpl(response.DATA).appendTo("tbody#GENERATION");
	});
	
	//TODO: LOADING THE COURSE TYPE TO COMBO BOX
	courseType.findAll(function(response){
		$("#SELECT_COURSETYPE").html("<option value=''>All Course Types</option>");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_COURSETYPE");
		$(".selectpicker").selectpicker('refresh');
	});
});