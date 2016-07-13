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
	
	//TODO: TO FIND ALL GENERATION
	generationPage.findAll = function(){
		$.ajax({ 
		    url: "/v1/api/admin/generations", 
		    type: 'GET', 
		    dataType: 'JSON', 
		    data:{
		    	"limit" : $("#SELECT_PER_PAGE").val(),
		    	"page" : currentPage,
		    	"name" : $("#txtSearch").val(),
		    	"courseId" : $("#SELECT_COURSETYPE").val()
		    },
		    beforeSend: function(xhr) {
	            xhr.setRequestHeader("Accept", "application/json");
	            xhr.setRequestHeader("Content-Type", "application/json");
	        },
		    success: function(response) { 
		    	if(response.CODE=="0000"){
		    		$("#GENERATION").html("");
		    		if(response.DATA.length > 0){
		    			$("#TotalRecords").html(response.PAGINATION.TOTAL_COUNT);
		    			$.each(response.DATA, function(key,value){
		    				response.DATA[key]["NO"] = (key+1)+((response.PAGINATION.PAGE-1) * response.PAGINATION.LIMIT);
						});
		    			$("#GENERATION_TEMPLATE").tmpl(response.DATA).appendTo("tbody#GENERATION");
		    			if(checkPagination){
		    				generationPage.setPagination(response.PAGINATION.TOTAL_PAGES);
		    				checkPagination=false;
		    			}
		    		}else{
		    			$("#GENERATION").html("<tr style='text-align:center;'><td colspan='7'>NO CONTENT</td></tr>");
		    			$("#PAGINATION").html("");
		    		}
		    	}
		    },
		    error:function(data,status,err) { 
		        console.log("error: "+data+" status: "+status+" err:"+err);
		    }
		});			
	};
	generationPage.findAll();
	//TODO: TO SET THE PAGINATION FOR THE GENERATION
	generationPage.setPagination = function(totalPage){
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
	    	generationPage.findAll();
	    }); 
	};
	
	//TODO: EVENT HANDLING ON THE PER PAGE CHANGE
	$("#SELECT_PER_PAGE").change(function(){
		$("#limitPage").html($("#SELECT_PER_PAGE").val());
		checkPagination = true;
		generationPage.findAll();
	});
	
	$("#SELECT_COURSETYPE").change(function(){
		checkPagination = true;
		generationPage.findAll();
	});
	//TODO: LOADING THE COURSE TYPE TO COMBO BOX
	courseType.findAll(function(response){
		$("#selectCourseType").html("<option value=''>Choose Course Types</option>");
		$("#SELECT_COURSETYPE").html("<option value=''>All Course Types</option>");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#SELECT_COURSETYPE");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#selectCourseType");
		$(".selectpicker").selectpicker('refresh');
	});
	
	$("#btnSave").click(function(){
		var data = {
				"NAME": $("#txtName").val(),
				"COURSE_TYPE": $("#selectCourseType").val(),
				"IS_DEFAULT": $("#txtDefault").val(),
				"STATUS" 	: $('input[name=radioStatus]:checked').val()
		};
		generation.addGeneration(data, function(response){
			if(response.CODE=="0000"){
				$("#txtName").val("");
				$("#txtDefault").val("");
				$("#modalAddNewGeneration").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewGeneration").trigger("click");
				checkPagination = true;
				generationPage.findAll();
			}else{
				$("#modalAddNewGeneration").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewGeneration").trigger("click");
			}
		});
	});
	
	$("#btnSearch").click(function(){
		checkPagination = true;
		currentPage = 1;
		generationPage.findAll();
		console.log('s');
	});
	
	var id = 0;
	$(document).on('click', '#btnEdit', function(){
		id = $(this).parents("tr").data("id");
		$("#btnSave").hide();
		$("#btnSaveUpdate").show();
		generation.getGeneration(id, function(response){
			$("#txtName").val(response.DATA.NAME);
			$("#txtDefault").val(response.DATA.IS_DEFAULT);
			$("#selectCourseType").val(response.DATA.COURSE_TYPE.ID);
			$(".selectpicker").selectpicker('refresh');
			$("#radioer").val(response.DATA.STATUS);
			$("#modalAddNewGeneration").modal("show");
		});
	});
	
	$(document).on('click', '#btnDelete', function(){
		$("#modalMessage").modal("show");
		id = $(this).parents("tr").data("id");
	});
	
	$("#btnOk").click(function(){
		generation.deleteGeneration(id, function(response){
			if(response.CODE=="0000"){
				$("#txtName").val("");
				$("#txtDefault").val("");
				$("#modalAddNewGeneration").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewGeneration").trigger("click");
				checkPagination = true;
				generationPage.findAll();
			}else{
				$("#modalAddNewGeneration").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewGeneration").trigger("click");
			}
		});
		$("#modalMessage").modal("hide");
	});
	
	$("#btnRegister").click(function(){
		$("#btnSaveUpdate").hide();
		$("#btnSave").show();
	});
	
	$("#btnSaveUpdate").click(function(){
		var data = {
				"NAME": $("#txtName").val(),
				"COURSE_TYPE": $("#selectCourseType").val(),
				"IS_DEFAULT": $("#txtDefault").val(),
				"STATUS" 	: $('input[name=radioStatus]:checked').val(),
				"ID"		: id
		};
		generation.updateGeneration(data, function(response){
			if(response.CODE=="0000"){
				$("#txtName").val("");
				$("#txtDefault").val("");
				$("#modalAddNewGeneration").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewGeneration").trigger("click");
				checkPagination = true;
				generationPage.findAll();
			}else{
				$("#modalAddNewGeneration").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewGeneration").trigger("click");
			}
		});
	});
});