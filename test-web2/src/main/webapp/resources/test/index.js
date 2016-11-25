$(document).ready(function(){
		debugger;
		$.ajax({ 
		     type: "get", 
				url: 'getUserName', 
				dataType: "text", 
				success: function (data) {
						$("#name").html(data);
				}, 
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
						   
				} 
		});
})