define(["jquery","libraries/mootools-base"],function($,Mootools){
	var DataToggleButton = new Mootools.Class({
		Implements : [Mootools.Options],
		options : {},
		jquery : "DataToggleButton",
		initialize : function(){
			this.$elements = $("button[data-toggle=button]");
		},
		patch : function(){
			this.$elements.click(function() {
	    		$(this).val(($(this).val() === "true") ? "false" : "true");
	    	});
		}
	});
	return DataToggleButton;
});
