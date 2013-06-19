define(["jquery","libraries/mootools-base","Notifier.class","libraries/jquery-validate"]
,function($,Mootools,Notifier){
var Validator = new Mootools.Class({
	Implements : [Mootools.Options],
	options : {},
	jquery : "Validator",
	initialize : function(form_selector){
		this.$element = $(form_selector);
	},
	bind : function(){
    	var thisInstance = this;
    	thisInstance.$element.validate({
    		"onKeyup" : true,
    		"sendForm" : false,
    		"eachValidField" : function () {
    			$(this).closest('div').removeClass('error').addClass('success');
    		},
    		"valid":function(){
    			//Notifier.getInstance().notify("Submitting Form","FAST","VERY_FAST","alert-success");
    		},
    		"invalid":function(){
    			Notifier.getInstance().notify("Please Correct Form Entries","MEDIUM","VERY_FAST","alert-error");
    		},
    		"eachInvalidField" : function() {
    			$(this).closest('div').removeClass('success').addClass('error');
    		},
    		"conditional" : {
    			"confirm" : function() {
    				var targetId = $(this).attr("data-conditional-confirm-with");
    				return $(this).val() === $(this).closest("form").find("#"+targetId).val();
    			}
    		},
    		"description" : {
    			"password" : {
    				"required" 	  : '<div class="text-error">Password is Required</div>',
    				"pattern" 	  : '<div class="text-error">Password Must Consist Of Atleast 5 Characters</div>',
    				//"conditional" : '',//mafeesh conditional lel password/
    				"valid" 	  : '<div class="text-success">Valid Password</div>'
    			},
    			"username" : {
    				"required"    : '<div class="text-error">Username is Required</div>',
    				"pattern"     : '<div class="text-error">Username Must Consist Of Atleast 3 Characters</div>',
    				//"conditional" : '',
    				"valid"       : '<div class="text-success">Valid Username</div>'
    			},
    			"password-confirm" : {
    				"required"    : '<div class="text-error">Password Must Be Confirmed</div>',
    				//"pattern"     : '',
    				"conditional" : '<div class="text-error">Password Confirmation Failed</div>',
    				"valid" 	  : '<div class="text-success">Password Confirmed</div>'
    			}
    		}
    	});
	}
});
return Validator;
});