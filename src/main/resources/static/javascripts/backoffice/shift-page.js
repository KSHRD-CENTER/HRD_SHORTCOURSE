$(function() {
	
	var shiftPage = {};
	var checkPagination = true;
	var currentPage = 1;
	var id = 0;
	
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
	
	$("#btnSave").click(function(){
		var data = {
				"NAME": $("#txtName").val(),
				"STATUS" 	: $('input[name=radioStatus]:checked').val()
		};
		shift.addShift(data, function(response){
			if(response.CODE=="0000"){
				$("#txtName").val("");
				$("#modalAddNewShift").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewShift").trigger("click");
				checkPagination = true;
				shift.findAll(function(response){
					console.log(response);
					$.each(response.DATA, function(key,value){
						response.DATA[key]["NO"] = (key+1);
					});
					$("#SHIFT").html("");
					$("#SHIFT_TEMPLATE").tmpl(response.DATA).appendTo("tbody#SHIFT");
				});
			}else{
				$("#modalAddNewShift").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewShift").trigger("click");
			}
		});
	});
	
	$("#btnRegister").click(function(){
		$("#btnSaveUpdate").hide();
		$("#btnSave").show();
	});
	
	$(document).on('click', '#btnDelete', function(){
		$("#modalMessage").modal("show");
		id = $(this).parents("tr").data("id");
	});
	
	$("#btnOk").click(function(){
		console.log(id);
		shift.deleteShift(id, function(response){
			if(response.CODE=="0000"){
				$("#modalAddNewGeneration").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewGeneration").trigger("click");
				checkPagination = true;
				shift.findAll(function(response){
					console.log(response);
					$.each(response.DATA, function(key,value){
						response.DATA[key]["NO"] = (key+1);
					});
					$("#SHIFT").html("");
					$("#SHIFT_TEMPLATE").tmpl(response.DATA).appendTo("tbody#SHIFT");
				});
			}else{
				$("#modalAddNewGeneration").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewGeneration").trigger("click");
			}
		});
		$("#modalMessage").modal("hide");
	});
	
	$(document).on('click', '#btnEdit', function(){
		id = $(this).parents("tr").data("id");
		$("#btnSave").hide();
		$("#btnSaveUpdate").show();
		shift.getShift(id, function(response){
			$("#txtName").val(response.DATA.NAME);
			$(".selectpicker").selectpicker('refresh');
			$("#radioer").val(response.DATA.STATUS);
			$("#modalAddNewShift").modal("show");
		});
	});
});