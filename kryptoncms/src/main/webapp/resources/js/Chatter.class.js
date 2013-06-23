define([
        "jquery",
        "SocketHandler.class",
        "libraries/mootools-base",
        "message_proxies/ChatMessage.class",
        "Notifier.class",
        "Logger.class",
        "libraries/jquery.cookie",
        "libraries/jquery.ui/draggable",
        "libraries/jquery.ui/resizable"
        ]
,function(
		$,
		SocketHandler,
		Mootools,
		ChatMessage,
		Notifier,
		Logger){
var Chatter = new Mootools.Class({
	Implements    : [Mootools.Options],
	options       : {},
	$elements     : null,
	initialize : function(options){
		var thisChatterInstance = this;
		
		thisChatterInstance.logger = new Logger("Chatter");
		
		//private
		//thisChatterInstance.subsocket	  = {};
		thisChatterInstance.socketHandler = null;
		
		thisChatterInstance.setOptions(options);
		thisChatterInstance.$elements = {
			$destination          : $(),
			$messageBody          : $(),
			$messageBoard         : $(),
			$submitBtn 	          : $(),
			$chatterForm          : $(),
			$destinationsPallette : $(),
			$destinationAdderBtn  : $()
		};
	},
	reconnect : function(){
		var thisChatterInstance = this;
		thisChatterInstance.collectGarbage();
		thisChatterInstance.activated = true;
		thisChatterInstance.subscribeSocket();
		thisChatterInstance.attachSubmitHandler();
		thisChatterInstance.attachDestinationAdderHandler(
			eval(thisChatterInstance.$elements.$destination.attr("data-source"))
		);
		thisChatterInstance.i = 1;
		thisChatterInstance.attachConversationsPalletteHandler();
	},
	attachConversationsPalletteHandler : function(){
		var thisChatterInstance = this;
		thisChatterInstance.closeHandler = function(event){
			thisChatterInstance.socketHandler.push(JSON.stringify({"action":"DELETE_CHANNEL","conversationId":$(event.target).parent('li').attr('id')}));
		};
		$("#chatterConv .close").unbind("click").click(function(event){
			thisChatterInstance.closeHandler(event);
		});
		
		$("#chatterConv .add").unbind("click").click(function(event){
			thisChatterInstance.socketHandler.push(JSON.stringify({"action":"ADD_CHANNEL","sourceUsername":$.cookie("j_username")}));
		});
		
		$("#chatterConv a:not(.add)").unbind("click").click(function(event){
			var $thisAnchor = $(this);
			$("#chat-modal .tab-content").slideUp(function(){
				$(this).html("");
				$.getJSON(DOMAIN_CONFIGURATIONS.BASE_URL+"chat/conversation/"+$thisAnchor.parent("li").attr("id"),function(conversation){
					console.log(conversation);
					$("#chat-modal .tab-content").html($("#chat-tab-content").tmpl(conversation)).slideDown();
					thisChatterInstance.attachConversationsPalletteHandler();
					thisChatterInstance.attachSubmitHandler();
					thisChatterInstance.attachDestinationAdderHandler(
						eval(thisChatterInstance.$elements.$destination.attr("data-source"))
					);
				});
			});
		});
	},
	subscribeSocket : function(){
		var thisChatterInstance = this;
		thisChatterInstance.socketHandler 
			= new SocketHandler({ url : DOMAIN_CONFIGURATIONS.BASE_URL+"chat/echo" })
			  .setOnMessageHandler(
			    	function(response) {
			    		try{
			    			var response = JSON.parse(response.responseBody);
			    			console.log(response);
			    			if(response.action === "ADD_CHANNEL"){
			    				response.convName = "Conversation #"+thisChatterInstance.i++;
			    				var $ul = $("ul#chatterConv");
			    				var $convLi = $("script#convTabTmpl").tmpl(response);
			    				$convLi.find(".close").unbind("click").click(thisChatterInstance.closeHandler);
			    				$convLi.prependTo($ul);
			    				thisChatterInstance.attachConversationsPalletteHandler();
			    			} else if (response.action === "DELETE_CHANNEL"){
			    				console.log(response);
			    				var $convLi = $("li#"+response.conversationId);
			    				if($convLi.hasClass("active")){
			    					$("#chat-modal .tab-content").slideUp(function(){
			    						$(this).html("");
			    					})
			    				}
			    				$convLi.remove();
			    			} else if (response.action == "ADD_PARTY"){
			    				var $conversationLi = $("#"+response.conversationId);
			    				if($conversationLi.length == 1){
			    					if($conversationLi.hasClass("active")){
			    						for(var i=0;i<response.parties.length;i++)
			    						$("#destination-pallette-item-tmpl").tmpl({"destination":response.parties[i]})
									    .appendTo(thisChatterInstance.$elements.$destinationsPallette);
			    						thisChatterInstance.attachDestinationAdderHandler(
			    								eval(thisChatterInstance.$elements.$destination.attr("data-source"))
			    						);
			    					}else{
			    						//nothing to do 
			    					}
			    				}else // zero
			    				{
				    				response.convName = "Conversation #"+thisChatterInstance.i++;
				    				var $ul = $("ul#chatterConv");
				    				var $convLi = $("script#convTabTmpl").tmpl(response);
				    				$convLi.find(".close").unbind("click").click(thisChatterInstance.closeHandler);
				    				$convLi.prependTo($ul);
				    				thisChatterInstance.attachConversationsPalletteHandler();
			    				}
			    			} else if (response.action == "REMOVE_PARTY"){
			    				var party = response.parties.pop();
			    				var $convLi = $("li#"+response.conversationId);
			    				if(party == $.cookie("j_username")){
				    				if($convLi.hasClass("active")){
				    					$("#chat-modal .tab-content").slideUp(function(){
				    						$(this).html("");
				    					});
				    				}
				    				$convLi.remove();
			    				} else {
			    					if($convLi.hasClass("active")){
			    						thisChatterInstance.$elements.$destinationsPallette.find("[data-username="+party+"]").remove();
			    					}
			    				}
			    			} else if(response.action == "BROADCAST_MESSAGE"){
			    				if($("#"+response.conversationId).hasClass("active"))
			    					thisChatterInstance.appendToMessageBoard(response);
			    				else //notify because he/she is not looking !
			    				{
			    					
			    				}
			    			}
			    			
//			    			thisChatterInstance.appendToMessageBoard(JSON.parse(response.responseBody));
			    		}catch(ex){}
			    	}
			  ).subscribe();
		if($.cookie("j_username")){
			$.getJSON(DOMAIN_CONFIGURATIONS.BASE_URL+"chat/conversations",function(queryMessage){
				console.log(queryMessage);
				if(queryMessage.successful){
					for(var i =0;i<queryMessage.queryResult.length;i++){
						queryMessage.queryResult[i].convName = "Conversation #"+thisChatterInstance.i++;
						var $ul = $("ul#chatterConv");
						var $convLi = $("script#convTabTmpl").tmpl({"conversationId":queryMessage.queryResult[i].id,"convName":queryMessage.queryResult[i].convName});
						$convLi.find(".close").unbind("click").click(thisChatterInstance.closeHandler);
						$convLi.prependTo($ul);
						thisChatterInstance.attachConversationsPalletteHandler();
					}
				}
//				$("#chat-modal .tab-content").html($("#chat-tab-content").tmpl(conversation)).slideDown();
//				thisChatterInstance.attachConversationsPalletteHandler();
//				thisChatterInstance.attachSubmitHandler();
//				thisChatterInstance.attachDestinationAdderHandler(
//					eval(thisChatterInstance.$elements.$destination.attr("data-source"))
//				);
			});
		}
	}.protect(),
	attachSubmitHandler : function(){
		var thisChatterInstance = this;
		thisChatterInstance.$elements.$chatterForm          = $("#chatter_form");
		thisChatterInstance.$elements.$destination          = $("#destination");
		thisChatterInstance.$elements.$messageBody          = $("#message");
		thisChatterInstance.$elements.$messageBoard         = $("#message_board");
		thisChatterInstance.$elements.$submitBtn            = $("#btn_submit");
		thisChatterInstance.$elements.$destinationsPallette = $("#destinations_pallette");
		thisChatterInstance.$elements.$destinationAdderBtn  = $("#destination_adder");
		for(var key in thisChatterInstance.$elements){
			if(thisChatterInstance.$elements[key].length != 1 || $.cookie("j_username") == ""){
				thisChatterInstance.logger.log("error","aborting chat bcz these element(s) are not right");
				thisChatterInstance.logger.log("error",key);
				thisChatterInstance.logger.log("error",thisChatterInstance.$elements[key]);
				return;
			}
		}
		
		var changeHandlerForMessage = function(event){
			var valid = thisChatterInstance.$elements.$messageBody.val().trim().length > 0;
			if(!valid)
			   	 thisChatterInstance.$elements.$submitBtn.addClass("disabled");
			else thisChatterInstance.$elements.$submitBtn.removeClass("disabled");
		};
		
		thisChatterInstance.$elements.$messageBody.bind({
			"change":changeHandlerForMessage,
			"keyup" :changeHandlerForMessage
		});
		
		thisChatterInstance.$elements.$submitBtn.unbind("click");
		thisChatterInstance.$elements.$submitBtn.click(function(){
			var validityReport = {
					"nonEmptyMessage":{
						"isValid":false,
						"errorMessage" : "You can't send an empty message"
					},
					"takingToOtherPpl":{
						"isValid":false,
						"errorMessage" : "We Can't Let You Talk to Yourself :)"
					}
			};
			validityReport.nonEmptyMessage.isValid   = thisChatterInstance.$elements.$messageBody.val().trim().length > 0;
			validityReport.takingToOtherPpl.isValid  = thisChatterInstance.$elements
														       .$destinationsPallette
														       .find("[data-username]")
														       .length > 0;
			var isValid = true;
			for(var key in validityReport){
				isValid = isValid && validityReport[key].isValid;
			}
			if(isValid){
				var chatMessage 		 = {};
				if($.cookie("j_username"))
					chatMessage.sourceUsername 		 = $.cookie("j_username");
				else 
					chatMessage.source 		 = "You";
				chatMessage.parties = [];
				thisChatterInstance.$elements.$destinationsPallette.find("[data-username]").each(function(){
					chatMessage.parties.push($(this).attr("data-username"));//building the destinations array to send to server
				});
				chatMessage.messageBody  = thisChatterInstance.$elements.$messageBody.val();
				chatMessage.action = "BROADCAST_MESSAGE";
				chatMessage.conversationId = $("#chatterConv li.active").attr("id");
				thisChatterInstance.socketHandler.push(JSON.stringify(chatMessage));
				thisChatterInstance.$elements.$messageBody.val("");
				
				thisChatterInstance.logger.log("info","outgoing message");
				thisChatterInstance.logger.log("debug","destinations are ");
				thisChatterInstance.logger.log("debug",chatMessage.destinations);
				thisChatterInstance.logger.log("debug","message is "+chatMessage.body);
			} else{
				for(var key in validityReport){
					if(validityReport[key].isValid == false){
						Notifier.getInstance().notify(validityReport[key].errorMessage,"MEDIUM","VERY_FAST","alert-error");
						break;
					}
				}
			}
		});
	},
	attachDestinationAdderHandler : function(dataSource){
		var thisChatterInstance = this;
		
		var changeHandlerForDestination = function(event){
			var destination = thisChatterInstance.$elements.$destination.val();
			if(!dataSource) return;
			if(dataSource.indexOf(destination) < 0)
			   	 thisChatterInstance.$elements.$destinationAdderBtn.addClass("disabled");
			else thisChatterInstance.$elements.$destinationAdderBtn.removeClass("disabled");
		};
		
		thisChatterInstance.$elements.$destination.bind({
			"change":changeHandlerForDestination,
			"keyup" :changeHandlerForDestination
		});
		thisChatterInstance.$elements.$destinationAdderBtn.unbind("click").click(function(){
			var destination = thisChatterInstance.$elements.$destination.val();
			
			var validityReport = {
					"nonempty":{
						"isValid":false,
						"errorMessage": "You forgot to enter a username"
					},
					"registeredUser":{
						"isValid":false,
						"errorMessage" : "\""+destination+"\" is not a registered user"
					},
					"notDuplicate":{
						"isValid":false,
						"errorMessage" : "\""+destination+"\" is already added to the conversation"
					}
			};
			validityReport.registeredUser.isValid = dataSource.indexOf(destination)>-1;
			validityReport.nonempty.isValid       = destination.trim() !== "";
			validityReport.notDuplicate.isValid   = thisChatterInstance.$elements
														       .$destinationsPallette
														       .find("[data-username=\""+destination+"\"]")
														       .length == 0;
			var isValid = true;
			for(var key in validityReport){
				isValid = isValid && validityReport[key].isValid;
			}
			if(isValid){				
				//changed the code generation to jquery templates
				thisChatterInstance.socketHandler.push(JSON.stringify({"action":"ADD_PARTY","parties":[destination],"conversationId":$("ul#chatterConv li.active").attr("id")}));
//				$("#destination-pallette-item-tmpl").tmpl({"destination":destination})
//												    .appendTo(thisChatterInstance.$elements.$destinationsPallette);
				thisChatterInstance.$elements.$destination.val("");
			}else {
				for(var key in validityReport){
					if(validityReport[key].isValid == false){
						Notifier.getInstance().notify(validityReport[key].errorMessage,"MEDIUM","VERY_FAST","alert-error");
						break;
					}
				}
			}
		});
		thisChatterInstance.$elements.$destinationsPallette.find("a.close").unbind("click").click(function(event){
			thisChatterInstance.socketHandler.push(JSON.stringify({"action":"REMOVE_PARTY","parties":[$(event.target).next("span").html()],"conversationId":$("ul#chatterConv li.active").attr("id")}));
		});
	},
	appendToMessageBoard : function(chatMessage){
		$("#chat-message-tmpl").tmpl(chatMessage).appendTo(this.$elements.$messageBoard);
		this.$elements.$messageBoard.get(0).scrollTop = this.$elements.$messageBoard.get(0).scrollHeight;
	}//.protect()
	//if i protect this method i can't call it from the context of callbacks
	,
	closeSockets : function(){
		this.socketHandler.close();
	},
	collectGarbage : function(){
		if(this.activated){
			this.closeSockets();
		}
	}
});
Chatter.self = null;
Chatter.deleteInstance = function(){
	this.self = null;
};
Chatter.getInstance = function(){
	if(this.self === null) this.self = new Chatter();
	return this.self;
};
return Chatter;
});