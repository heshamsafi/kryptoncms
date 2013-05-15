define([
        "jquery",
        "SocketHandler.class",
        "libraries/mootools-base",
        "message_proxies/ChatMessage.class",
        "Notifier.class",
        "Logger.class",
        "libraries/jquery.cookie"]
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
	attachHandlers : function(){
		var thisChatterInstance = this;
		//validation
		for(var key in thisChatterInstance.$elements){
			if(thisChatterInstance.$elements[key].length != 1){
				thisChatterInstance.logger.log("error","aborting chat bcz these element(s) are not right");
				thisChatterInstance.logger.log("error",key);
				thisChatterInstance.logger.log("error",thisChatterInstance.$elements[key]);
				return;
			}
		}
		thisChatterInstance.activated = true;
		thisChatterInstance.subscribeSocket();
		thisChatterInstance.attachSubmitHandler();
		thisChatterInstance.attachDestinationAdderHandler(
			eval(thisChatterInstance.$elements.$destination.attr("data-source"))
		);
	},
	subscribeSocket : function(){
		var thisChatterInstance = this;
		thisChatterInstance.socketHandler 
			= new SocketHandler({ url : thisChatterInstance.$elements.$chatterForm.attr("action") })
			  .setOnMessageHandler(
			    	function(response) {
			    		thisChatterInstance.appendToMessageBoard(JSON.parse(response.responseBody));
			    	}
			  ).subscribe();
	}.protect(),
	attachSubmitHandler : function(){
		var thisChatterInstance = this;
		
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
				var chatMessage 		 = new ChatMessage();
				if($.cookie("j_username"))
					chatMessage.source 		 = $.cookie("j_username");
				else 
					chatMessage.source 		 = "You";
				chatMessage.destinations = [];
				thisChatterInstance.$elements.$destinationsPallette.find("[data-username]").each(function(){
					chatMessage.destinations.push($(this).attr("data-username"));//building the destinations array to send to server
				});
				chatMessage.body 		 = thisChatterInstance.$elements.$messageBody.val();
			
				thisChatterInstance.appendToMessageBoard(chatMessage);
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
	}.protect(),
	attachDestinationAdderHandler : function(dataSource){
		var thisChatterInstance = this;
		
		var changeHandlerForDestination = function(event){
			var destination = thisChatterInstance.$elements.$destination.val();
			if(dataSource.indexOf(destination) < 0)
			   	 thisChatterInstance.$elements.$destinationAdderBtn.addClass("disabled");
			else thisChatterInstance.$elements.$destinationAdderBtn.removeClass("disabled");
		};
		
		thisChatterInstance.$elements.$destination.bind({
			"change":changeHandlerForDestination,
			"keyup" :changeHandlerForDestination
		});
		
		thisChatterInstance.$elements.$destinationAdderBtn.click(function(){
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
//				thisChatterInstance.$elements.$destinationsPallette.append(
//						$("<div>").attr({
//							"class":"alert alert-info",
//							"data-username":destination
//						}).append($("<a>").attr({
//							"onclick":"javascript:void(0)",
//							"class":"close",
//							"data-dismiss":"alert"
//						}).html("&times;").after(
//								$("<span>").text(destination)
//						))
//				);
				
				//changed the code generation to jquery templates
				$("#destination-pallette-item-tmpl").tmpl({"destination":destination})
												    .appendTo(thisChatterInstance.$elements.$destinationsPallette);
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
	}.protect(),
	appendToMessageBoard : function(chatMessage){
		$("#chat-message-tmpl").tmpl(chatMessage).appendTo(this.$elements.$messageBoard);
		this.$elements.$messageBoard.get(0).scrollTop = this.$elements.$messageBoard.get(0).scrollHeight;
	}//.protect()
	//if i protect this method i can't call it from the context of callbacks
	,
	closeSockets : function(){
		this.socketHandler.close();
	}
});
return Chatter;
});