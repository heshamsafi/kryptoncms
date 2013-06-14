define(["jquery","libraries/mootools-base","SocketHandler.class","./libraries/jquery.ui/sortable","./libraries/jquery.ui/draggable","./libraries/jquery.ui/droppable"],function($,Mootools,SocketHandler){
	var MenuManager = new Mootools.Class({
		initialize : function(){
			var thisMenuManager = this;
			thisMenuManager.subscribeSocket();
		    
	        $( ".nav,.dropdown-menu" ).sortable({
				placeholder: "ui-state-highlight",
				connectWith: "#bod",
				items: "li:not(.exclude)",
		        stop: function() {
		        	var newOrder = [];
		        	$.when($(this).find('li a').each(function(){ 
		        		newOrder.push($(this).attr('data-menu-id'));
		        	})).then(function(){
		        		thisMenuManager.socketHandler.push(JSON.stringify({"newOrder":newOrder,"admin":true,"parentId":null,"action":"rearrange"}));
		        	});
		        }
			}).droppable();
		    $( ".nav,.dropdown-menu" ).disableSelection();

	        $( "#bod" ).sortable({
	          connectWith: "#admin_nav",
	          activeClass: "ui-state-default",
	          hoverClass: "ui-state-hover",
	          accept: ":not(.ui-sortable-helper)",
	          receive: function(event, ui ) {
		        	thisMenuManager.socketHandler.push(JSON.stringify({"admin":true,"action":"delete","operandId":$(ui.item).find("a").attr("data-menu-id")}));
		      }
	        });
			$("#addMenu").click(function(){
				$("#genericModal").load(DOMAIN_CONFIGURATIONS.BASE_URL+"form/Menu/");
			});
		},
		subscribeSocket : function(){
			var thisMenuManager = this;
			thisMenuManager.socketHandler 
				= new SocketHandler({ url : "http://localhost:8080/kryptoncms/navigation/menu/echo" })
				  .setOnMessageHandler(
				    	function(response) {
				    		console.log(response);
				    		var response = JSON.parse(response.responseBody);
				    		if(response.action == "rearrange"){
					    		$.each(response.newOrder.reverse(),function(idx,item){
					    			$("#"+item).parent("li").prependTo("#admin_nav ul");
					    		});
				    		}else if (response.action == "delete"){
				    			$("#"+response.operandId).parent("li").remove();
				    		}
				    	}
				  ).subscribe();
		}.protect()
	});
	return MenuManager;
});