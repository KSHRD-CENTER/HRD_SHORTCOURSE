$(function() {
	var dashboards = {};
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
	dashboards.findAll = function(){
		$.ajax({ 
		    url: "/v1/api/admin/dashboards", 
		    type: 'GET', 
		    dataType: 'JSON', 
		    data:{
		    	"generationId"	: 	$("#selectGeneration").val(),
		    	"courseTypeId"	: 	$("#selectCourseType").val(),
		    	"courseId"		: 	$("#selectCourse").val(),
		    	"courseName"	:	$("#txtSearch").val(),
		    	"limit" 		: 	$("#SELECT_PER_PAGE").val(),
		    	"page" 			: 	currentPage
		    },
		    beforeSend: function(xhr) {
	            xhr.setRequestHeader("Accept", "application/json");
	            xhr.setRequestHeader("Content-Type", "application/json");
	        },
		    success: function(response) { 
		    	console.log(response);
		    	if(response.CODE=="0000"){
		    		$("#DASHBOARD").html("");
		    		if(response.DATA.length > 0){
		    			$("#TotalRecords").html(response.PAGINATION.TOTAL_COUNT);
		    			$.each(response.DATA, function(key,value){
		    				response.DATA[key]["NO"] = (key+1)+((response.PAGINATION.PAGE-1) * response.PAGINATION.LIMIT);
		    				response.DATA[key]["PAID_AMOUNT"] = formatDollar(response.DATA[key]["PAID_AMOUNT"]);
		    				response.DATA[key]["LEFT_COST"] = formatDollar(response.DATA[key]["LEFT_COST"]);
		    				response.DATA[key]["TOTAL_PAID"] = formatDollar(response.DATA[key]["TOTAL_PAID"]);
						});
		    			$("#DASHBOARD_TEMPLATE").tmpl(response.DATA).appendTo("tbody#DASHBOARD");
		    			if(checkPagination){
		    				dashboards.setPagination(response.PAGINATION.TOTAL_PAGES);
		    				checkPagination=false;
		    			}
		    		}else{
		    			$("#DASHBOARD").html("<tr style='text-align:center;'><td colspan='8'>NO CONTENT</td></tr>");
		    			$("#PAGINATION").html("");
		    		}
		    	}
		    },
		    error:function(data,status,err) { 
		        console.log("error: "+data+" status: "+status+" err:"+err);
		    }
		});			
	};
	
	dashboards.getTotalMoney = function(){
		$.ajax({ 
		    url: "/v1/api/admin/dashboards/count", 
		    type: 'GET', 
		    data:{
		    	"generationId": $("#selectGeneration").val(),
		    	"courseTypeId": $("#selectCourseType").val(),
		    	"courseId": $("#selectCourse").val(),
		    	"courseName":$("#txtSearch").val()
		    },
		    dataType: 'JSON', 
		    beforeSend: function(xhr) {
	            xhr.setRequestHeader("Accept", "application/json");
	            xhr.setRequestHeader("Content-Type", "application/json");
	        },
		    success: function(response) { 
		    	if(response.CODE=="0000"){
		    		if(response.DATA != null){
		    			console.log(response);
		    			response.DATA.ACUTAL_BALANCE = formatDollar(response.DATA.ACUTAL_BALANCE);
		    			response.DATA.REMAINING_BALANCE = formatDollar(response.DATA.REMAINING_BALANCE);
		    			$("#btnActualMoney").val("ACTUAL BALANCE: "+response.DATA.ACUTAL_BALANCE);
		    			$("#btnRemainingMoney").val("REMAINING BALANCE: "+response.DATA.REMAINING_BALANCE);
		    		}
		    	}
		    },
		    error:function(data,status,err) { 
		        console.log("error: "+data+" status: "+status+" err:"+err);
		    }
		});			
	};
	
	dashboards.getMoney = function(){
		$.ajax({ 
		    url: "/v1/api/admin/dashboards/count-money", 
		    type: 'GET', 
		    data:{
		    	"generationId": $("#selectGeneration").val(),
		    	"courseTypeId": $("#selectCourseType").val(),
		    	"courseId": $("#selectCourse").val(),
		    	"courseName":$("#txtSearch").val()
		    },
		    dataType: 'JSON', 
		    beforeSend: function(xhr) {
	            xhr.setRequestHeader("Accept", "application/json");
	            xhr.setRequestHeader("Content-Type", "application/json");
	        },
		    success: function(response) { 
		    	console.log(response);
		    	if(response.CODE=="0000"){
		    		if(response.DATA != null){
		    			console.log(response);
		    			response.DATA.ESTIMATE_BALANCE = formatDollar(response.DATA.ESTIMATE_BALANCE);
		    			$("#btnEstimateMoney").val("ESTIMATE BALANCE: "+response.DATA.ESTIMATE_BALANCE);
		    		}
		    	}
		    },
		    error:function(data,status,err) { 
		        console.log("error: "+data+" status: "+status+" err:"+err);
		    }
		});			
	};
	function formatDollar(num) {
	    var p = num.toFixed(2).split(".");
	    return "$ " + p[0].split("").reverse().reduce(function(acc, num, i, orig) {
	        return  num + (i && !(i % 3) ? "," : "") + acc;
	    }, "") + "." + p[1];
	}
	dashboards.findAll();
	dashboards.getTotalMoney();
	dashboards.getMoney();
	//TODO: TO SET THE PAGINATION FOR THE TRANSACTION
	dashboards.setPagination = function(totalPage){
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
    	dashboards.findAll();
    });
	
	$.fn.enterKey = function (fnc) {
	    return this.each(function () {
	        $(this).keypress(function (ev) {
	            var keycode = (ev.keyCode ? ev.keyCode : ev.which);
	            if (keycode == '13') {
	                fnc.call(this, ev);
	            }
	        })
	    })
	}
	
	$("#txtSearch").enterKey(function(){
		checkPagination = true;
		currentPage = 1;
		dashboards.findAll();
	});
	
	//TODO: LOADING THE COURSE TYPE TO COMBO BOX
	courseType.findAll(function(response){
		$("#selectCourseType").html("<option value=''>SELECT COURSE TYPES</option>");
		$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#selectCourseType");
		$(".selectpicker").selectpicker('refresh');
	});
	
	$("#selectCourseType").change(function(){
		courseType.findAllGenerations($(this).val(), function(response){
			$("#selectGeneration").html("<option value=''>SELECT GENERATIONS</option>");
			$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#selectGeneration");
			$(".selectpicker").selectpicker('refresh');
		});
	});
	//TODO: EVENT HANDLING ON THE PER PAGE CHANGE
	$("#SELECT_PER_PAGE").change(function(){
		$("#limitPage").html($("#SELECT_PER_PAGE").val());
		checkPagination = true;
    	dashboards.findAll();
	});
	$("#selectGeneration").change(function(){
		generation.findAllCoursesByGenerationId($(this).val(), function(response){
			$("#selectCourse").html("<option value=''>SELECT COURSES</option>");
			$("#OPTION_TEMPLATE").tmpl(response.DATA).appendTo("#selectCourse");
			$(".selectpicker").selectpicker('refresh');
		});
	});
	
	$("#btnSearch").click(function(){
		checkPagination = true;
		currentPage = 1;
		dashboards.findAll();
		dashboards.getTotalMoney();
		dashboards.getMoney();
	});
	
});