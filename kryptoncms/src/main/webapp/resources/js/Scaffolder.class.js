define([
        "jquery",
        "libraries/mootools-base",
        "ContextMenu.class",
        "Notifier.class",
        "libraries/jquery.ui/selectable"
        ]
,function($,Mootools,ContextMenu,Notifier){
	var Scaffolder = new Mootools.Class({
		$scaffoldTable : null,
		contextMenu : null,
		initialize : function(){
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
			    		$.ajax({
			    		    url: $form.attr("action")+$tr.attr("data-entity-id"),
			    		    type: $form.attr("method"),
			    		    success: function(result) {
			    		    	$tr.find('td')
			    		    	 .wrapInner('<div style="display: block;" />')
			    		    	 .parent()
			    		    	 .find('td > div')
			    		    	 .slideUp(200, function(){
			    		        	$tr.remove();
			    		        });
			    		    },
			    		    error : function(result){
			    		    	Notifier.getInstance().notify("Something Went Wrong,try again later",1000,500,"error");
			    		    }
			    		});	
				  });
			  });
			  
			  $("#edit").click(function(){
				  var $tr = thisInstance.$scaffoldTable.find("tr.ui-selected");
				  var $form = $tr.parents("form");
				  var id =$tr.attr("data-entity-id");
				  $("#genericModal").load($form.attr("data-edit-action")+id);
			  });
			  
			  $("#create").click(function(){
				  $("#genericModal").load( $("#scaffoldForm").attr("data-edit-action"));
			  });
			  
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
		}
	});
	return Scaffolder;
});