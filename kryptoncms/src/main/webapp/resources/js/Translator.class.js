define(["jquery","libraries/mootools-base","FormSerializer.class","libraries/jquery.tmpl"]
,function($,Mootools,FormSerializer){
var Translator = new Mootools.Class({
	Implements : [Mootools.Options],
	options    : {},
	langs      : ["ARABIC","BULGARIAN","CATALAN","CHINESE_SIMPLIFIED","CHINESE_TRADITIONAL","CZECH","DANISH","DUTCH","ENGLISH",
	              "ESTONIAN","FINNISH","FRENCH","GERMAN","GREEK","HAITIAN_CREOLE","HEBREW","HINDI","HMONG_DAW","HUNGARIAN","INDONESIAN",
	              "ITALIAN","JAPANESE","KOREAN","LATVIAN","LITHUANIAN","NORWEGIAN","POLISH","PORTUGUESE","ROMANIAN","RUSSIAN",
	              "SLOVAK","SLOVENIAN","SPANISH","SWEDISH","THAI","TURKISH","UKRAINIAN","VIETNAMESE"],
	$genericForms : null,
	initialize : function(){
		this.$genericForms = $("form[data-generic-translation]");
		this.populateLangs();
		this.attachSubmitEvent();
	},
	populateLangs : function(){
		this.$genericForms.each($.proxy(function(index,thisElement){
			var $fromLangs = $("#options-templ").tmpl(this.langs);
			var $toLangs = $fromLangs.clone();
			$(thisElement).find("select[name=toLang]").html($fromLangs);
			$(thisElement).find("select[name=fromLang]").html($toLangs);
		},this));
	},
	attachSubmitEvent : function(){
		var thisTranslatorInstance = this;
		this.$genericForms.each(function(index,thisElement){
			$(thisElement).submit(function(event){
				event.preventDefault();
				var $thisForm = $(this);
		 		$.get($thisForm.attr("action"), new FormSerializer({"format":"urlencoded"}).serialize($thisForm), function(data) {
		 			$thisForm.find("#to").text(data);
	 			});
				return false;
			});
		});
	}
});
return Translator;
});