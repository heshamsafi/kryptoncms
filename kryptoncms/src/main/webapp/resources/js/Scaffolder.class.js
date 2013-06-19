define([
        "jquery",
        "libraries/mootools-base",
        "ContextMenu.class",
        "Notifier.class",
        "SocketHandler.class",
        "Ajaxifier.class",
        "libraries/jquery.ui/selectable"
        ]
,function($,Mootools,ContextMenu,Notifier,SocketHandler,Ajaxifier){
	var Scaffolder = new Mootools.Class({
		$scaffoldTable : null,
		contextMenu : null,
		socketHandler : null,
		initialize : function(ajaxifier){
			  this.ajaxifier = ajaxifier;
			  var thisInstance = this;
			  thisInstance.$scaffoldTable = $("table[data-enable-selectable] tbody");
			  thisInstance.$scaffoldTable.selectable({
				  filter :'tr',
				  cancel: 'a'
			  });
			  
			  $("#delete").click(function(event){
				  thisInstance.$scaffoldTable.find("tr.ui-selected").each(function(idx,tr){
					  	var $tr = $(tr);
			    		$form = $tr.parents("form");	
			    		var scaffoldMessage = {"className":$form.attr('className'),id:$tr.attr("data-entity-id"),action:"delete"};
			    		thisInstance.socketHandler.push(JSON.stringify(scaffoldMessage));
				  });
			  });
			  
			  $("#edit").click(function(){
				  var $tr = thisInstance.$scaffoldTable.find("tr.ui-selected");
				  var $form = $tr.parents("form");
				  var id =$tr.attr("data-entity-id");
				  $("#genericModal").load($form.attr("data-edit-action")+id,function(){
					  var $form = $("#genericModal form");
					  thisInstance.ajaxifier.formSubmitHandler($form,thisInstance.socketHandler);
					  Ajaxifier.getInstance().passiveReload();
				  });
			  });
			  
			  $("#create").click(function(){
				  $("#genericModal").load( $("#scaffoldForm").attr("data-edit-action"),function(){
					  var $form = $("#genericModal form");
					  thisInstance.ajaxifier.formSubmitHandler($form,thisInstance.socketHandler);
				  });
			  });
			  thisInstance.socket();
			  
//			  thisInstance.contextMenu = new ContextMenu();
			  //change in plans 
			  //we are going to use a dropdown button instead of a context menu.
//			  $("[data-enable-context-menu]").bind("contextmenu",function(event){
//				  var items = [
//							    {
//							    	text : "Edit",
//							    	href : "javascript:void();",
//							    	disabled: false,
//							    	dataAttributes : "data-context-menu-item-text1",
//							    	callback : function($tr){
//	
//							    	}
//								},
//							    {
//							    	text : "Delete",
//							    	href : "javascript:void();",
//							    	disabled: false,
//							    	dataAttributes : "data-context-menu-item-text2",
//							    	callback : function($tr){
//							    		$form = $tr.parents("form");
//							    		$.ajax({
//							    		    url: $form.attr("action")+$tr.attr("data-entity-id"),
//							    		    type: $form.attr("method"),
//							    		    success: function(result) {
//							    		    	$tr.find('td')
//							    		    	 .wrapInner('<div style="display: block;" />')
//							    		    	 .parent()
//							    		    	 .find('td > div')
//							    		    	 .slideUp(200, function(){
//							    		        	$tr.remove();
//							    		        });
//							    		    },
//							    		    error : function(result){
//							    		    	alert("something went wrong");
//							    		    }
//							    		});	
//							    	}
//								}
//							];
//				   if (thisInstance.contextMenu.menu.length > 0){
//					   thisInstance.contextMenu.closeMenu(thisInstance.contextMenu.openMenu,event,items);
//				   } else{
//					   thisInstance.contextMenu.openMenu(event,items);
//				   }
//			  });
		},
		createOrModifyRow : function(className,entity){
			//real time crap
			var $form = $("#scaffoldForm");
			if(className.match("Menu") != null){
				var $anchor = $("#admin_nav #"+entity.id);
				$anchor.attr({
					"href": entity.url
				}).text(entity.name);
			}
			if(className.match($form.attr('classname')+"$") == null) return;//not on the same page
			var $tbl = $form.find("table");
//			var $th = $tbl.find("th");
//			var newRow = {};
//			$th.each(function(){
//				var attrName = $(this).text().trim();
//				newRow[attrName] = entity[attrName];
//				console.log(newRow);
//			});
			var $tr = $tbl.find("tr[data-entity-id="+entity["id"]+"]");
			$tr.find("td").each(function(idx,td){
				var $td = $(td);
				var attrName = $td.attr("data-attr");
				if(typeof entity[attrName] != "undefined")
				$td.find("span").html(entity[attrName].toString());
			});
		},
		deleteRow : function(className,id){
			//real time crap
			var $form = $("#scaffoldForm");
			if(className.match("Menu") != null){
				$("#admin_nav #"+id).parent("li").slideUp(200, function(){
		        	$(this).remove();
		        });
			}
			if(className.match($form.attr('classname')+"$") == null) return;//not on the same page
			var $tbl = $form.find("table");
			var $tr = $tbl.find("tr[data-entity-id="+id+"]");
			$tr.find('td')
	    	 .wrapInner('<div style="display: block;" />')
	    	 .parent()
	    	 .find('td > div')
	    	 .slideUp(200, function(){
	        	$tr.remove();
	        });
		},
		socket : function(){
			var thisInstance = this;
			var $scaffoldForm = $("#scaffoldForm");
			if($scaffoldForm.length < 1) {//abort
				if(thisInstance.socketHandler == null) return;
				thisInstance.socketHandler.close();
				thisInstance.socketHandler=null;
				return;
			}
			thisInstance.socketHandler = new SocketHandler({ url : DOMAIN_CONFIGURATIONS.BASE_URL+"form/echo" })
				  .setOnMessageHandler(
				    	function(response) {
				    		var scaffoldMessage = JSON.parse(response.responseBody);
				    		
				    		if(scaffoldMessage.action == "delete")
				    			thisInstance.deleteRow(scaffoldMessage.className,scaffoldMessage.id);
				    		else{
				    			var entity = JSON.parse(scaffoldMessage.actualEntity);
				    			thisInstance.createOrModifyRow(scaffoldMessage.className,entity);
				    		}
				    	}
				  ).subscribe();
		}
	});
	return Scaffolder;
});