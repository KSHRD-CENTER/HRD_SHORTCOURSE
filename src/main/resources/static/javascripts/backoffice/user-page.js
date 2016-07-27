$(function() {
	var userPages = {};
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
	userPages.findAll = function(){
		$.ajax({ 
		    url: "/v1/api/admin/users", 
		    type: 'GET', 
		    dataType: 'JSON', 
		    data:{
		    	"limit" : $("#SELECT_PER_PAGE").val(),
		    	"page" : currentPage,
		    	"name" : $("#txtSearch").val(),
		    },
		    beforeSend: function(xhr) {
	            xhr.setRequestHeader("Accept", "application/json");
	            xhr.setRequestHeader("Content-Type", "application/json");
	        },
		    success: function(response) { 
		    	if(response.CODE=="0000"){
		    		$("#USER").html("");
		    		if(response.DATA.length > 0){
		    			$("#TotalRecords").html(response.PAGINATION.TOTAL_COUNT);
		    			$.each(response.DATA, function(key,value){
		    				response.DATA[key]["NO"] = (key+1)+((response.PAGINATION.PAGE-1) * response.PAGINATION.LIMIT);
						});
		    			$("#USER_TEMPLATE").tmpl(response.DATA).appendTo("tbody#USER");
		    			if(checkPagination){
		    				userPages.setPagination(response.PAGINATION.TOTAL_PAGES);
		    				checkPagination=false;
		    			}
		    		}else{
		    			$("#USER").html("<tr style='text-align:center;'><td colspan='6'>NO CONTENT</td></tr>");
		    			$("#PAGINATION").html("");
		    		}
		    	}
		    	console.log(response);
		    },
		    error:function(data,status,err) { 
		        console.log("error: "+data+" status: "+status+" err:"+err);
		    }
		});			
	};
	userPages.findAll();
	//TODO: TO SET THE PAGINATION FOR THE GENERATION
	userPages.setPagination = function(totalPage){
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
	    });
	};
	
	$("#PAGINATION").on("page", function(event, page){
    	checkPagination = false;
    	currentPage = page;
    	userPages.findAll();
    }); 
	
	//TODO: EVENT HANDLING ON THE PER PAGE CHANGE
	$("#SELECT_PER_PAGE").change(function(){
		$("#limitPage").html($("#SELECT_PER_PAGE").val());
		checkPagination = true;
		userPages.findAll();
	});
	
	$("#btnSave").click(function(){
		var data = {
				"EMAIL": $("#txtEmail").val(),
				"PASSWORD": $("#txtPassword").val(),
				"ROLE": $("#selectRole").val(),
		};
		users.addUser(data, function(response){
			if(response.CODE=="0000"){
				$("#txtEmail").val("");
				$("#txtPassword").val("");
				$("#txtConfirmPassword").val("");
				$("#modalAddNewUser").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewUser").trigger("click");
				checkPagination = true;
				userPages.findAll();
			}else{
				$("#modalAddNewUser").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewUser").trigger("click");
			}
		});
	});
	
	$("#btnSearch").click(function(){
		checkPagination = true;
		currentPage = 1;
		userPages.findAll();
	});
	
	var id = 0;
	$(document).on('click', '#btnEdit', function(){
		id = $(this).parents("tr").data("id");
		$("#btnSave").hide();
		$("#btnSaveUpdate").show();
		$("#status").show();
		$("#password").hide();
		$("#cPassword").hide();
		users.getUser(id, function(response){
			$("#txtEmail").val(response.DATA.EMAIL);
			$("#selectRole").val(response.DATA.ROLE);
			$(".selectpicker").selectpicker('refresh');
			$("#radioer").val(response.DATA.STATUS);
			$("#modalAddNewUser").modal("show");
		});
	});
	
	
	$(document).on('click', '#btnChangePassword', function(){
		$("#modalChangePassword").modal("show");
		id = $(this).parents("tr").data("id");
	});
	
	$("#btnChange").click(function(){
		var data = {
				"OLD_PASSWORD": $("#txtOldPassword").val(),
				"NEW_PASSWORD": $("#txtNewPassword").val(),
				"ID": id
		};
		users.changePassword(data, function(response){
			if(response.CODE=="0000"){
				$("#modalAddNewUser").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewUser").trigger("click");
				checkPagination = true;
				userPages.findAll();
				$("#modalChangePassword").modal("hide");
			}else if(response.CODE=="9999"){
				$("#modalAlert").modal("show");
				return;
			}else{
				$("#modalAddNewUser").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewUser").trigger("click");
			}
		});
		$("#txtOldPassword").val("");
		$("#txtNewPassword").val("");
		$("#txtConfirm-Password").val("");
	});
	
	$(document).on('click', '#btnDelete', function(){
		$("#modalMessage").modal("show");
		id = $(this).parents("tr").data("id");
	});
	
	$("#btnOk").click(function(){
		users.deleteUser(id, function(response){
			if(response.CODE=="0000"){
				$("#modalAddNewUser").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewUser").trigger("click");
				checkPagination = true;
				userPages.findAll();
			}else{
				$("#modalAddNewUser").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewUser").trigger("click");
			}
		});
		$("#modalMessage").modal("hide");
	});
	
	$("#btnRegister").click(function(){
		$("#btnSaveUpdate").hide();
		$("#btnSave").show();
		$("#status").hide();
		$("#password").show();
		$("#cPassword").show();
	});
	
	$("#btnSaveUpdate").click(function(){
		var data = {
				"EMAIL": $("#txtEmail").val(),
				"ROLE": $("#selectRole").val(),
				"STATUS" 	: $('input[name=radioStatus]:checked').val(),
				"ID"		: id
		};
		users.updateUser(data, function(response){
			if(response.CODE=="0000"){
				$("#txtEmail").val("");
				$("#selectRole").val("DEFAULT");
				$("#modalAddNewUser").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewUser").trigger("click");
				checkPagination = true;
				userPages.findAll();
			}else{
				$("#modalAddNewUser").attr("data-toastr-notification", response.MESSAGE);
				$("#modalAddNewUser").trigger("click");
			}
		});
	});
});