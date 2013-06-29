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
			  this.activate();
			  
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
		activate : function(){
			  var thisInstance = this;
			  thisInstance.socket();
			  thisInstance.$scaffoldTable = $("table[data-enable-selectable] tbody");
			  thisInstance.$scaffoldTable.selectable({
				  filter :'tr',
				  cancel: 'a'
			  });
			  
			  $("#delete").click(function(event){
				  thisInstance.$scaffoldTable.find("tr.ui-selected").each(function(idx,tr){
					  	var $tr = $(tr);
			    		$form = $tr.parents("form");	
			    		var scaffoldMessage = {"className":$form.attr('className'),id:$tr.attr("data-entity-id"),action:"DELETE"};
			    		thisInstance.socketHandler.push(JSON.stringify(scaffoldMessage));
				  });
			  });
			  
			  $("#edit").click(function(){
				  var $tr = thisInstance.$scaffoldTable.find("tr.ui-selected");
				  var $form = $tr.parents("form");
				  var id =$tr.attr("data-entity-id");
				  $("#genericModal").load($form.attr("data-edit-action")+id,function(){
					  var $form = $("#genericModal form");
					  thisInstance.ajaxifier.formSubmitHandler($form,thisInstance.socketHandler,"MODIFY");
					  Ajaxifier.getInstance().passiveReload();
				  });
			  });
			  
			  $("#create").click(function(){
				  $("#genericModal").load( $("#scaffoldForm").attr("data-edit-action"),function(){
					  var $form = $("#genericModal form");
					  thisInstance.ajaxifier.formSubmitHandler($form,thisInstance.socketHandler,"CREATE");
				  });
			  });
		},
		modifyRow : function(className,entity){
			var $form = $("#scaffoldForm");
			if(className.match("Menu") != null){
				var $anchor = $("#admin_nav #"+entity.id);
				$anchor.attr({
					"href": DOMAIN_CONFIGURATIONS.BASE_URL+entity.url
				}).text(entity.name);
			}
			if(className.match($form.attr('classname')+"$") == null) return;//not on the same page
			var $tbl = $form.find("table");
			var $tr = $tbl.find("tr[data-entity-id="+entity["id"]+"]");
			console.log(entity);
//			$tr.html($("script#chat-message-tmpl").tmpl({
//				"action":"edit"
//			}));
			$tr.find("td").each(function(idx,td){
				var $td = $(td);
				var attrName = $td.attr("data-attr");
				if(typeof entity[attrName] != "undefined"){
					var buffer = entity[attrName];
					if(typeof buffer != "boolean"){
						var buffer = buffer.replace(new RegExp("<(/?)([^>]*)>","gi")," ");
						if(buffer.length > 20) buffer = buffer.substring(0,20)+"...";
					}
					$td.find("span").html(buffer);
				}
			});
		},
		insertRow : function(className,entity){
			var $form = $("#scaffoldForm");
			if(className.match("Menu") != null){
				var $ul = $("#admin_nav ul");
				$("script#admin-menu-item-tmpl").tmpl(entity).appendTo($ul);
			}
			if(className.match($form.attr('classname')+"$") == null) return;//not on the same page
			var adaptedObject = {
					"owner":{
						"id":entity["id"],
						"type": $form.attr('classname').replace(new RegExp("([^\\.]*\\.)","g"),"")
					},
					"tds":[]
			};
			$form.find("th[data-field-type]").each(function(idx,item){
				var $item = $(item); 
				var key  = $item.text().trim();
				var type = $.isArray(entity[key])?"collection":
						   $.isPlainObject(entity[key])?"model":typeof entity[key];
				var value = entity[key];
				if(type == "string"){
					value = entity[key].replace(new RegExp("<(/?)([^>]*)>","gi")," ");
					if (value.length > 20) value = value.substring(0,20)+"...";
				}
				adaptedObject.tds.push({
					"realValue" : entity[key],
					"value":value,
					"name":key,
					"type":type,
					"javaType":$form.find("th[data-field-type]:contains("+key+")").attr("data-field-type")
				});
			});
			console.log(adaptedObject);
			$("script#scaffold-row-tmpl").tmpl(adaptedObject).appendTo($form.find("table tbody"));
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
			if(thisInstance.socketHandler == null){
				thisInstance.socketHandler = new SocketHandler({ url : DOMAIN_CONFIGURATIONS.BASE_URL+"form/echo" })
					  .setOnMessageHandler(
					    	function(response) {
					    		var scaffoldMessage = JSON.parse(response.responseBody);
					    		
					    		if(scaffoldMessage.action == "DELETE")
					    			thisInstance.deleteRow(scaffoldMessage.className,scaffoldMessage.id);
					    		else if(scaffoldMessage.action == "MODIFY"){
					    			var entity = JSON.parse(scaffoldMessage.actualEntity);
					    			thisInstance.modifyRow(scaffoldMessage.className,entity);
					    		}else if(scaffoldMessage.action == "CREATE"){
					    			var entity = JSON.parse(scaffoldMessage.actualEntity);
					    			thisInstance.insertRow(scaffoldMessage.className,entity);
					    		}
					    	}
					  ).subscribe();
			}
		}
	});
	Scaffolder.self = null;
	Scaffolder.getInstance = function(){
		if(Scaffolder.self == null){
			Scaffolder.self = new Scaffolder(Ajaxifier.getInstance());//no need to call activate
		}
		return Scaffolder.self;
	};
	return Scaffolder;
});