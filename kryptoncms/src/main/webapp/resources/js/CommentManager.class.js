/**
 * there is ALOT of recursion in this class ... read carefully :)
 */
define([
        "jquery",
        "SocketHandler.class",
        "libraries/mootools-base",
        "Logger.class",
        "./libraries/jquery.tmpl"
        ],
function($,SocketHandler,Mootools,Logger){
var CommentManager = new Mootools.Class({
	logger     : null,
	children   : [],
	initialize : function($context,root){
		this.isRoot = false;
		this.logger = new Logger("CommentManager");
		if(typeof $context === "undefined"){
			$context = $("body").keydown($.proxy(function(event){
							var isEscapeKey = (event.keyCode === 27);
							if(isEscapeKey){
								this.hideCommentInput($("[data-reply-input]"));
							}
						},this));
			this.isRoot = true;
			this.root = this;
		} else this.root = root;
		this.logger.log("debug","tree");
		this.logger.log("debug",this.root.children);
		this.refreshContext($context);
	}, 
	subscribeSocket : function(){
		if(!this.isRoot) return;
		var thisCommentManagerInstance = this;
		thisCommentManagerInstance.socketHandler
			= new SocketHandler({ url : thisCommentManagerInstance.channelPath })
		  	  .setOnMessageHandler(
			    	function(response) {
			    		try{
				    		var parsedResponse = JSON.parse(response.responseBody);
				    		if(parsedResponse.successful == true){
				    			$.each(parsedResponse.queryResult,function(index,item){
					    			var parentSelector = "[data-commentable-type][data-comment-containter][data-commentable-type="+item.parentType+"][data-commentable-id="+item.parentId+"]";
						    		var parent = $(parentSelector+":not(.hide):first");
						    		if(parent.length === 1){
						    			var comments = $("#comments-tmpl").tmpl(item);
						    			comments.appendTo(parent);
						    		}
						    		parent = $(parentSelector);
						    		var data_reply_btn = parent.parent().prev("[data-show-replies-btn]").attr("disabled",null);
						    		var data_reply_text = data_reply_btn.find("[data-show-replies-text]");
						    		var text = data_reply_text.text();
						    		data_reply_text.text(parseInt(text)+1);
						    		
						    		thisCommentManagerInstance.attachReplyHandler($(parentSelector).find("[data-reply-button]"));
						    		thisCommentManagerInstance.attachHandlerToRepliesToggler($(parentSelector).find("button"));
				    			});
				    		}
			    		}catch(ex){//sometimes the servers responds with a page saying you are not authorized to view this resource if the user
			    			//does not have enough permissions to see comments ... it should not return a 200 status code but right now i don't have
			    			//the time to change that and fix all the parts of the app that will break as a consequence thats why i wrote this temp.
			    			//try,catch blocks as a quick fix.
			    		}
			    	}
			  ).subscribe();
	},
	refreshContext: function($context){
		var thisCommentManagerInstance = this;
		thisCommentManagerInstance.$commentSections = [];
		
		$context.find("[data-enable-comments]").each(function(){
				thisCommentManagerInstance.$commentSections.push({
					"$element" : $(this),
					"data_server_service_prefix_url" : $(this).closest("[data-server-service-prefix-url]").attr("data-server-service-prefix-url")
				});
				
				if(thisCommentManagerInstance.isRoot){
					var closestCore = $(this).closest("[data-server-service-prefix-url]");
					var $enabledSection = closestCore.find("[data-enable-comments]");
					thisCommentManagerInstance.channelPath = closestCore.attr("data-server-service-prefix-url")
															+"socket/"
															+$enabledSection.attr("data-commentable-type") + "/"
															+$enabledSection.attr("data-commentable-id")
															;
					//only the root subscribes socket to save resources
					thisCommentManagerInstance.subscribeSocket();
				}
		});
	},
	attachHandlersToSections : function(){
		var thisCommentManagerInstance = this;
		if( thisCommentManagerInstance.$commentSections.length <= 0){
			 thisCommentManagerInstance.logger.log("debug","this page is clean ... aborting");
			 return;
		}
		thisCommentManagerInstance.activated = true;
		var rpcs = [];//for the use of jquery's deferred call-backs feature.
		thisCommentManagerInstance.$commentSections.each(function(iterableSection){
			rpcs.push(
				$.ajax({
					"url":iterableSection["data_server_service_prefix_url"]+iterableSection["$element"].attr("data-commentable-type")+"/"+iterableSection["$element"].attr("data-commentable-id"),
	    			"type" : "GET",
	    			"dataType": 'json',
	    			"cache": false,
	    			"contentType" : "application/json",
	    			"success" : function(responseBody){
	    				if(responseBody["successful"]){
	    					if(responseBody["queryResult"] == null) responseBody["queryResult"] = [];
	    					thisCommentManagerInstance.logger.log("debug","all went well");
	    					thisCommentManagerInstance.logger.log("debug",responseBody["queryResult"]);
	    					var comments = $("#comments-tmpl").tmpl(responseBody["queryResult"]);
	    					comments.appendTo(iterableSection["$element"]);
	    					var $replyBtn = iterableSection["$element"].find("[data-reply-button]");
	    					thisCommentManagerInstance.attachReplyHandler($replyBtn);
	    					thisCommentManagerInstance.attachHandlerToRepliesToggler(iterableSection["$element"].find("button"));
	    					//i fear for thread safety ... let's see how it works out
	    				} else thisCommentManagerInstance.logger.log("error",responseBody["errorMessage"]);
	    			},
	    			"error":function(responseBody){
	    				thisCommentManagerInstance.logger.log("error","error in the HTTP request check the XHR Stacktrace");
	    			}
				})
			);
		});
		return rpcs;
	},
	attachHandlerToRepliesToggler : function($togglers){
		var child;
		var thisCommentManagerInstance = this;
		$togglers.unbind("click");
		var toggle = true;
		$togglers.click(function(event){
			if(toggle){
				$(this).attr("disabled","")
					   .toggleClass("dropup")
					   .find(".annotate").each(function(){
					       $(this).toggleClass("hide");
					   });
				child =  new CommentManager($(this).parent(), thisCommentManagerInstance.root);
				thisCommentManagerInstance.children.push(child);
				$.when(child.attachHandlersToSections()).then($.proxy(function(){
					$(this).attr("disabled",null)
						   .parent()
						   .find("[data-enable-comments]")
						   .removeClass("hide")
						   .hide(0)
						   .slideDown();
				},this));
			}else{
				var thisButton = $(this);
				thisButton.attr("disabled","")
						  .toggleClass("dropup")
						  .find(".annotate").each(function(){
						      $(this).toggleClass("hide");
					      });
				$(this).parent()
					   .find("[data-enable-comments]")
					   .slideUp(function(){
					      $(this).html("").addClass("hide");
						  thisButton.attr("disabled",null);
					   });
				
				thisCommentManagerInstance.root.killNode(child);
			}toggle = !toggle;
		});
	},
	attachReplyHandler : function($replyBtn){
		var thisCommentManagerInstance = this;
		$replyBtn.unbind("click");
		$replyBtn.click(function(){
			var $thisReplyLink = $(this);
			$thisReplyLink.hide(0);
			$thisReplyLink.next("[data-reply-input]")
						  .removeClass("hide")
						  .hide(0)
						  //.focus()
						  .show(function(){
    							var $thisReplyInputField = $(this);
                                $("[data-reply-input]").each(function(){
                                	if(!$(this).is($thisReplyInputField))
                                		thisCommentManagerInstance.hideCommentInput($(this));
                                });
                                $thisReplyInputField.addClass("input-block-level");
                                if(typeof $thisReplyInputField.attr("data-event-keydown") === "undefined")
	    							$thisReplyInputField.keydown(function(event){
						    								//thisCommentManagerInstance.logger.log("debug",event);
						    								var isEnterKeyWithNoShiftMask = (!event.shiftKey&&event.keyCode === 13);
						    								if(isEnterKeyWithNoShiftMask){
						    									event.preventDefault();
						    									thisCommentManagerInstance.submitComment({
						    										 "parentId" : $thisReplyInputField.parent().attr("data-commentable-id"),
						    										 "commentableType":$thisReplyInputField.parent().attr("data-commentable-type"),
						    										 "content": $(this).val()
						    									});
						    									thisCommentManagerInstance.hideCommentInput($(this));
						    								}
	    							}).attr("data-event-keydown","");
    					   });
			
	})
	//.attr("data-event-click")
	;
	},
	hideCommentInput : function($input){
		$input.val("").hide(200,function(){
			$input.prev("[data-reply-button]").show(0);
		});
	}//.protect()
	,submitComment : function(comment){
		this.logger.log("debug","sending this");
		this.logger.log("debug",comment);
		this.root.socketHandler.push(JSON.stringify(comment));
	}
	//is this method brilliant or what ! :D
	,killNode : function(node){
		for(var i = 0;i<this.children.length;++i){
			this.children[i].killNode(node);
			if(this.children[i]===node){
				//before we kill a node we have to kill its children if it had any
				for(var j=0;j<this.children[i].children.length;j++)
					this.children[i].killNode(this.children[i].children[j]);
				this.children.splice(i,1);
				return;
			}
		}
	},
	closeSockets : function(){
		this.socketHandler.close();
	}
});
return CommentManager;
});