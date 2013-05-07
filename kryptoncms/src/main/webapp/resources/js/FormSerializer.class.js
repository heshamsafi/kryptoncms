define(["jquery","libraries/mootools-base"],function($,Mootools){
	var FormSerializer = new Mootools.Class({
		Implements:[Mootools.Options],
		options:{
			"format":"json"
		},
		initialize : function(options){
			this.setOptions(options);
		}.protect(),
		serialize : function($form,optionsOverride){
			if(optionsOverride){
				var originalOptions = this.options;
				this.setOptions(optionsOverride);
			}
	
			var payload = this["serializeTo_"+this.options.format]($form);
			
			if(optionsOverride) this.setOptions(originalOptions);
			
			return payload;
		},
		
		serializeTo_json : function($form){
			var payload = {};
			$form.find("[name]").each(function() {
				payload[$(this).attr("name")] = $(this).val();
			});
			return payload;
		}.protect(),
		serializeTo_urlencoded:function($form){
			return $form.serialize();//using the jquery utility function
		}.protect(),
		setForm : function($form){
			this.$form = $form;
		}
	});
	
	//singleton
	FormSerializer.self = null;
	FormSerializer.getInstance = function(options){
		if(FormSerializer.self == null) 
			FormSerializer.self = new FormSerializer(options);
		return FormSerializer.self;
	};
	return FormSerializer;
});