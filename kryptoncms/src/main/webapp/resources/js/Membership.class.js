define(["jquery","libraries/mootools-base","Logger.class","FormSerializer.class"
       ,"Notifier.class","Chatter.class","libraries/jquery.cookie","libraries/jquery.tmpl"]
,function($		,Mootools				  ,Logger		 , FormSerializer
		,Notifier, Chatter){
	var Membership = new Mootools.Class({
	    Implements: [Mootools.Options],
	    options: {},
	    
	    rememberMe : false,
	    initialize: function(optform_loginions){
	    	this.self = this;
	    	this.logger = new Logger("Membership");
	    }.protect(),
	
	    bindRegisterForm : function(form_selector){
	    	var thisInstance = this;
	    	thisInstance.$form_register = $(form_selector);
	    	//var $btn_rememberMe 	    = $form_register.find("[name=rememberMe]");
	    	thisInstance.$form_register.submit(function(event) {
	    		event.preventDefault();
	    		var $this = $(this);
	    		if($this.find(".error").length>0) return false;
	    		$this.find("input,button").attr("disabled","disabled").addClass("disabled");
	    		var payload = FormSerializer.getInstance().serialize($this);
	    		$.ajax({
	    			"type" : $this.attr("method"),
	    			"url" : $this.attr("action"),
	    			"data" : JSON.stringify(payload),
	    			"dataType": 'json',
	    			"cache": false,
	    			"contentType" : "application/json",
	    			"success":function(data){
	    				if(data["successful"]){
		    				Notifier.getInstance().notify("success","FAST","VERY_FAST","alert-success");
		    				thisInstance.logger.log("info","registration success");
	    				}else {
		    				Notifier.getInstance().notify(data["errorMessage"],"FAST","VERY_FAST","alert-error");
		    				thisInstance.logger.log("info","registration failed bcz : "+data["errorMessage"]);
	    				}
	    			},
	    			"error":function(error){
	    				Notifier.getInstance().notify("error","FAST","VERY_FAST","alert-error");
	    				thisInstance.logger.log("error","something went wrong while processing the registation request");
	    				thisInstance.logger.log("error",error);
	    			},
	    			"complete":function(){
	    				$this.find("input,button").attr("disabled",null).removeClass("disabled");
	    			}
	    		});
	    		return false;
	    	});
	    },
	    bindLoginForm : function(form_selector){
	    	var thisInstance = this;
	    	thisInstance.$form_login = $(form_selector);
	    	//var $btn_rememberMe = thisInstance.$form_login.find("[name=rememberMe]");
	    	thisInstance.$form_login.submit(function(event) {
	    		event.preventDefault();
	    		//thisInstance.rememberMe = $btn_rememberMe.val();
	    		var $this = $(this);
	    		if($this.find(".error").length>0) return false;
	    		$this.find("input,button").attr("disabled","disabled").addClass("disabled");
	    		var payload = FormSerializer.getInstance().serialize($this);
	    		$.ajax({
	    			"type" : $this.attr("method"),
	    			"url"  : $this.attr("action"),
	    			"data" : payload,
	    			"dataType": 'json',
	    			"cache": false,
	    			"contentType" : "application/x-www-form-urlencoded",
	    			"success":function(response){
	    				if(response["successful"]){
	    					$.cookie("j_username",payload["j_username"]);
	    					$.getJSON(DOMAIN_CONFIGURATIONS.BASE_URL+"membership/userid/"+encodeURIComponent(payload["j_username"]),function(data){
	    						if(data.errorMessage == null && data.queryResult.length>0)
	    							$.cookie("userId",data.queryResult.pop());
	    					});

	    					Notifier.getInstance().notify("Welcome, "+$.cookie("j_username"),"MEDIUM","VERY_FAST","alert-success");
	    					thisInstance.$form_login.parents(".modal[aria-hidden=false]").modal("hide");
	    					thisInstance.updateLoginStatus();
	    					thisInstance.logger.log("info","user \""+$.cookie("j_username")+"\" has just logged in");
	    				} else {
	    					Notifier.getInstance().notify("Login Failed ... bcz "+response["errorMessage"] ,"MEDIUM","VERY_FAST","alert-error");
	    					thisInstance.logger.log("info","login failed ... bcz "+response["errorMessage"]);
	    				}
	    			},
	    			"error":function(error){
	    				Notifier.getInstance().notify("error","FAST","VERY_FAST","alert-error");
	    				thisInstance.logger.log("error","error while processing login request");
	    				thisInstance.logger.log("error","error");
	    			},
	    			"complete":function(){
	    				$this.find("input,button").attr("disabled",null).removeClass("disabled");
	    				Chatter.getInstance().reconnect();
	    			}
	    		});
	    		return false;
	    	});
	    },
	    attachLogoutHandler : function(){
	    	var thisInstance = this;
	    	var $logout_anchor = $("#account_controls").find("[data-logout]");
	    	$logout_anchor.unbind("click");
	    	$logout_anchor.click(function(event){
	    		event.preventDefault();
	    		var url = $(this).attr("href");
	    		$.ajax({
		    		"type" : "GET",
		    		"url" : url,
		    		//"dataType": 'jsonp',
		    		"cache": false,
		    		//"contentType" : "application/json",

		    		"error":function(){
		    			Notifier.getInstance().notify("Something Went Wrong","FAST","VERY_FAST","alert-error");
		    			thisInstance.logger.log("info","error while processing logout request");
		    		}
		    	}).always(function(){
	    			Notifier.getInstance().notify("You Have Successfully Logged out","FAST","VERY_FAST","alert-success");
	    			thisInstance.destroyCookie();
	    			thisInstance.updateLoginStatus();
	    			thisInstance.logger.log("info","logout success");
	    			Chatter.getInstance().reconnect();
	    		});
	    		return false;
	    	});
	    },
	    updateLoginStatus : function(){
	    	var thisInstance = this;
	    	var $accountControls = $("#account_controls");
	    	var $accountControls_anchor = $accountControls.find("a.displ>span:not(.caret)");
	    	var $drpDwn_lis      = $accountControls.find(".dropdown-menu li");
	    	var $drpDwn_lis_in   = $drpDwn_lis.filter(".in");
	    	var $drpDwn_lis_out  = $drpDwn_lis.filter(".out");
	    	$drpDwn_lis.hide();
	    	if($.cookie("j_username")!=""){
	    		$drpDwn_lis_in.show();
	    		$accountControls_anchor.html($.cookie("j_username"));
				$("#editAccount").click(function(){
				  $("#genericModal").load(DOMAIN_CONFIGURATIONS.BASE_URL+"form/edu.asu.krypton.model.persist.db.User/"+$.cookie("userId"),function(){
					  $("#genericModal form").submit(function(event) {
				    		event.preventDefault();
				    		var $this = $(this);
				    		var payload = FormSerializer.getInstance().serialize($this);
				    		$.ajax({
				    			"type" : $this.attr("method"),
				    			"url" : $this.attr("action"),
				    			"data" : JSON.stringify(payload),
				    			"dataType": 'json',
				    			"cache": false,
				    			"contentType" : "application/json",
				    			"success":function(data){
				    				if(data["successful"])
				    					$this.parents(".modal[aria-hidden=false]").modal("hide");
				    				else 
				    					Notifier.getInstance().notify("Something Went Wrong","FAST","VERY_FAST","alert-error");
				    			},
				    			"error":function(error){
				    				Notifier.getInstance().notify("Something Went Wrong","FAST","VERY_FAST","alert-error");
				    			},
				    			"complete":function(){
				    			}
				    		});
				    		return false;
				    	});
				  });
				});
//				  $("#edit").click(function(){
//					  var $tr = thisInstance.$scaffoldTable.find("tr.ui-selected");
//					  var $form = $tr.parents("form");
//					  var id =$tr.attr("data-entity-id");
//					  $("#genericModal").load($form.attr("data-edit-action")+id,function(){
//						  var $form = $("#genericModal form");
//						  thisInstance.ajaxifier.formSubmitHandler($form,thisInstance.socketHandler);
//					  });
//				  });
		    	//are you admin
		    	$.ajax({
		    		"type" : "GET",
		    		"url" : DOMAIN_CONFIGURATIONS.BASE_URL+"membership/status",
		    		"cache": false,
		    		"success":function(response){
		    			if(response["successful"]){
			    			if(response["loggedin"]&&response["admin"]){
			    				//alert("you are admin");
			    				if($("#admin_nav").hasClass("hide")){
			    					$("#bod").removeClass("span12").addClass("span11");
			    					$("#admin_nav").removeClass("hide");
			    				}
			    			} else{
			    				if(!response["loggedin"]){
			    					thisInstance.destroyCookie();
			    					thisInstance.updateLoginStatus();//recursive call.
			    					//the stack should NOT have more than two calls ... otherwise this is broken
			    					return;
			    				}
			    				$("#bod").toggleClass("span11 span12");
			    				$("#admin_nav").addClass("hide");
			    			}
		    			}
		    		},
		    		"error":function(){
		    			Notifier.getInstance().notify("Something Went Wrong","FAST","VERY_FAST","alert-error");
		    			thisInstance.logger.log("info","error while processing logout request");
		    		}
		    	});
		    	
	    	}else{
				$("#bod").toggleClass("span11 span12");
				$("#admin_nav").addClass("hide");
	    		$drpDwn_lis_out.show();
	    		$accountControls_anchor.html("Anonymous");
	    	}
	    },
	    destroyCookie : function(){
	    	$.cookie("j_username","");
	    	$.cookie("userId","");
	    	//$.cookie("j_password",null);
	    }
	});
	//singleton
	Membership.self = null;
	Membership.getInstance = function(){
		if(Membership.self === null) Membership.self = new Membership();
		return Membership.self;
	};
	return Membership;
});
