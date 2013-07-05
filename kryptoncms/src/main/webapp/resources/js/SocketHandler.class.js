//api encapsulation 3alashan el decoupling we kda :)
define(["jquery","libraries/mootools-base","Logger.class","libraries/jquery.atmosphere","libraries/jquery.cookie"],function($,Mootools,Logger){
var SocketHandler = new Mootools.Class({
	Implements : [Mootools.Options],
	options    : {
		prependUrlPrefix : false
	},
	subsocket  : {},
	jquery     : "SocketHandler",
	initialize : function(options){
		var thisInstance = this;
		
		thisInstance.logger = new Logger("SocketHandler");
		this.options = new $.atmosphere.AtmosphereRequest();
		this.setOptions({
			url 			  : "/",
			contentType 	  : "text/plain",
			logLevel		  : Logger.logLevel,
			//html5 server sent events (sse)
			transport:'websocket',
//			transport:'long-polling',
			fallbackTransport : 'long-polling',
			contentType : "application/json",
			enableXDR : true,
            rewriteURL : true,
			withCredentials : true
		});//defaults
		this.setOptions(options);//overrides
		
		//prepend url prefix
		if(thisInstance.options.prependUrlPrefix)
			this.options.url = DOMAIN_CONFIGURATIONS.BASE_URL + this.options.url;
		// + ";" + document.cookie; // this attempt failed bcz the HTTP session id is protected by the 
		// browser for security reasons :\
		
		//default handlers
		this.setOnOpenHandler(
			function(response) {
				thisInstance.logger.log("info",'Atmosphere connected using ');
				thisInstance.logger.log("info",response.transport);
			}
		).setOnMessageHandler(
		    function(response) {
		    	thisInstance.logger.log("debug","message received "+response.responseBody);
			}
		).setOnCloseHandler(
			function(){
				thisInstance.push("closing");
			}
		).setOnErrorHandler(
		    function(response) {
		    	thisInstance.logger.log("error",response);
		    }	
		);
	},
	subscribe : function(){
		this.subsocket = $.atmosphere.subscribe(this.options,[{"key":"j_username","value":$.cookie("j_username")}]);
		return this;
	},
	push : function(data){
		this.subsocket.push(data);
		return this;
	},
	close : function(){
		this.push("closing");
		this.subsocket.close();
		return this;
	},
	setOnOpenHandler : function(handler){
		this.options.onOpen = handler;
		return this;
	},
	setOnCloseHandler : function(handler){
		this.options.onClose = handler;
		return this;
	},
	setOnErrorHandler : function(handler){
		this.options.onError = handler;
		return this;
	},
	setOnMessageHandler : function(handler){
		this.options.onMessage = handler;
		return this;
	}
});
return SocketHandler;
});