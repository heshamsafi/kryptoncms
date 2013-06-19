define(["jquery","libraries/mootools-base"],function($,Mootools){
var NavigationMenu = new Mootools.Class({
	Implements : [Mootools.Options],
	options : {},
	jquery : "NavigationMenu",
	initialize : function(selector,options){
		this.$element = $(selector);
	},
	highlightMenu : function(){
		//thanks to jquery chaining i was able to minimize this function into one line of code :)
		this.$element.find("li").removeClass("active")
					 .find("a").filter(function(){
					       return $(this).attr("href") === window.location.pathname;
					 }).parent("li").addClass("active");
	}
});
return NavigationMenu;
});