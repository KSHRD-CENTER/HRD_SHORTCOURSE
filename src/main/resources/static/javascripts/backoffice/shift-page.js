$(function() {
	
	var shiftPage = {};
	var checkPagination = true;
	var currentPage = 1;
	
	var _ctx = ($("meta[name='ctx']").attr("content")===undefined) ? "" : $("meta[name='ctx']").attr("content");

	// Prepend context path to all jQuery AJAX requests
	$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
	    if (!options.crossDomain) {
	        options.url = _ctx + options.url;
	    }
	});
	
	//TODO: TO FIND ALL SHIFT AND LOADING TO TABLE
	shift.findAll(function(response){
		console.log(response);
		$.each(response.DATA, function(key,value){
			response.DATA[key]["NO"] = (key+1);
		});
		$("#SHIFT").html("");
		$("#SHIFT_TEMPLATE").tmpl(response.DATA).appendTo("tbody#SHIFT");
	});
	//TODO: LOADING THE COURSE TYPE TO COMBO BOX
	courseType.findAll(function(response){
		$("#SELECT_COURSETYPE").html("<option value=''>All Course Types</option>");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_COURSETYPE");
		$(".selectpicker").selectpicker('refresh');
	});
	
	//TODO: LOADING THE GENERATION TO COMBO BOX
	generation.findAll(function(response){
		$("#SELECT_GENERATION").html("<option value=''>All Generations</option>");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_GENERATION");
		$(".selectpicker").selectpicker('refresh');
	});
	
	$("#SELECT_COURSETYPE").change(function(){
		courseType.findAllGenerations($(this).val(), function(response){
			$("#SELECT_GENERATION").html("<option value=''>All Generations</option>");
			$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_GENERATION");
			$(".selectpicker").selectpicker('refresh');
		});
	});
});