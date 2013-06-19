define(["jquery","libraries/mootools-base"],function($,Mootools){
var Notifier = new Mootools.Class({
	queue	   : new Array(),
	occupied   : false,
	Implements : Mootools.Options,
	options    : {
		"selectors" : {
			"template" : "#notification-templ",
	     	"target" : "body"	
		}
	},
	initialize : function(options){
		this.setOptions(options);
		this.$template = $(this.options.selectors.template);
		this.$target = $(this.options.selectors.target);
		this.Notification =  function (msg,speed,transitionSpeed,type){
			this.msg 	 		  = msg;
			this.speed 			  = speed;
			this.transitionSpeed  = transitionSpeed;
			this.type 			  = type;
			//types are : 
			//["","alert-block","alert-error","alert-success","alert-info"];
		};
	}.protect(),
	//similar to the solution to the critical section problem using mutexes(locks).
	showNotification : function(notification,callback){
		var thisInstance = this;
		thisInstance.occupied = true;
		var speedMap = {
				"VERY_VERY_FAST":100,
				"VERY_FAST":200,
				"FAST":1000,
				"SLOW":4000,
				"VERY_SLOW":6000,
				"MEDIUM":2500
		};
		//alert("notifier is alive");
		if (typeof(notification.speed) === "string") notification.speed = speedMap[notification.speed];
		else if(notification.speed==null) notification.speed = speedMap["MEDIUM"];
		
		if (typeof(notification.transitionSpeed) === "string") notification.transitionSpeed = speedMap[notification.transitionSpeed];
		else if(notification.transitionSpeed==null) notification.transitionSpeed = speedMap["MEDIUM"];
		
		var $div_notif = thisInstance.$template.tmpl(notification);
		$div_notif.appendTo(thisInstance.$target);

		var distance = 2*$div_notif.height();
		
		$div_notif.css("top","+"+$(window).height()+"px");
		$div_notif.animate({
			"top":"-="+(distance-13)+"px"
		},notification.transitionSpeed,function(){
			setTimeout(function(){
				$div_notif.animate({
					"top":"+="+distance+"px"
				},notification.transitionSpeed,function(){
					$div_notif.remove();
					thisInstance.occupied = false;//release lock
					if(typeof callback !== 'undefined') callback(notification);
				});
			},notification.speed);
		});
	},
	notify : function(msg,speed,transitionSpeed,type,callback){
		var thisInstance = this;
		var interval = null;
		thisInstance.queue.unshift(new this.Notification(msg,speed,transitionSpeed,type));
		while(thisInstance.queue.length!=0){
			interval = setInterval(function(){
				//critical section
				if(!thisInstance.occupied) {
					if(typeof callback === "undefined")
						thisInstance.showNotification(thisInstance.queue.pop());
					else
						thisInstance.showNotification(thisInstance.queue.pop(),callback);
					//console.log("found a spot");
					clearInterval(interval);
				}//else console.log("still waiting");
			},200);
			return;
		}
	}
});
//static attributes
Notifier.self = null;
Notifier.deleteInstance = function(){
	this.self = null;
};
Notifier.getInstance = function(options){
	if(this.self === null) this.self = (typeof options === 'undefined') ? new Notifier():new Notifier(options);
	return this.self;
};
return Notifier;
});