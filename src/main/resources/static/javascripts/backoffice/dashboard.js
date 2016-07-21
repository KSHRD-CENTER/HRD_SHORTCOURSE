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
		    	"generationId": $("#selectGeneration").val(),
		    	"courseTypeId": $("#selectCourseType").val(),
		    	"courseId": $("#selectCourse").val(),
		    	"limit" : $("#SELECT_PER_PAGE").val(),
		    	"page" : currentPage
		    },
		    beforeSend: function(xhr) {
	            xhr.setRequestHeader("Accept", "application/json");
	            xhr.setRequestHeader("Content-Type", "application/json");
	        },
		    success: function(response) { 
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
		    	console.log(response);
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
	
	//TODO: EVENT HANDLING ON THE PER PAGE CHANGE
	$("#SELECT_PER_PAGE").change(function(){
		$("#limitPage").html($("#SELECT_PER_PAGE").val());
		checkPagination = true;
		dashboards.findAll();
	});
});