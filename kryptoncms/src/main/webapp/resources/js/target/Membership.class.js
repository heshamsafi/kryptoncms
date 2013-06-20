define(["jquery","libraries/mootools-base","Logger.class","FormSerializer.class","Notifier.class","libraries/jquery.cookie","libraries/jquery.tmpl"],function(e,t,n,r,i){var s=new t.Class({Implements:[t.Options],options:{},rememberMe:!1,initialize:function(e){this.self=this,this.logger=new n("Membership")}.protect(),bindRegisterForm:function(t){var n=this;n.$form_register=e(t),n.$form_register.submit(function(t){t.preventDefault();var s=e(this);if(s.find(".error").length>0)return!1;s.find("input,button").attr("disabled","disabled").addClass("disabled");var o=r.getInstance().serialize(s);return e.ajax({type:s.attr("method"),url:s.attr("action"),data:JSON.stringify(o),dataType:"json",cache:!1,contentType:"application/json",success:function(e){e.successful?(i.getInstance().notify("success","FAST","VERY_FAST","alert-success"),n.logger.log("info","registration success")):(i.getInstance().notify(e.errorMessage,"FAST","VERY_FAST","alert-error"),n.logger.log("info","registration failed bcz : "+e.errorMessage))},error:function(e){i.getInstance().notify("error","FAST","VERY_FAST","alert-error"),n.logger.log("error","something went wrong while processing the registation request"),n.logger.log("error",e)},complete:function(){s.find("input,button").attr("disabled",null).removeClass("disabled")}}),!1})},bindLoginForm:function(t){var n=this;n.$form_login=e(t),n.$form_login.submit(function(t){t.preventDefault();var s=e(this);if(s.find(".error").length>0)return!1;s.find("input,button").attr("disabled","disabled").addClass("disabled");var o=r.getInstance().serialize(s);return e.ajax({type:s.attr("method"),url:s.attr("action"),data:o,dataType:"json",cache:!1,contentType:"application/x-www-form-urlencoded",success:function(t){t.successful?(e.cookie("j_username",o.j_username),e.getJSON(DOMAIN_CONFIGURATIONS.BASE_URL+"membership/userid/"+encodeURIComponent(o.j_username),function(t){t.errorMessage==null&&t.queryResult.length>0&&e.cookie("userId",t.queryResult.pop())}),i.getInstance().notify("Welcome, "+e.cookie("j_username"),"MEDIUM","VERY_FAST","alert-success"),n.$form_login.parents(".modal[aria-hidden=false]").modal("hide"),n.updateLoginStatus(),n.logger.log("info",'user "'+e.cookie("j_username")+'" has just logged in')):(i.getInstance().notify("Login Failed ... bcz "+t.errorMessage,"MEDIUM","VERY_FAST","alert-error"),n.logger.log("info","login failed ... bcz "+t.errorMessage))},error:function(e){i.getInstance().notify("error","FAST","VERY_FAST","alert-error"),n.logger.log("error","error while processing login request"),n.logger.log("error","error")},complete:function(){s.find("input,button").attr("disabled",null).removeClass("disabled")}}),!1})},attachLogoutHandler:function(){var t=this,n=e("#account_controls").find("[data-logout]");n.unbind("click"),n.click(function(n){n.preventDefault();var r=e(this).attr("href");return e.ajax({type:"GET",url:r,cache:!1,error:function(){i.getInstance().notify("Something Went Wrong","FAST","VERY_FAST","alert-error"),t.logger.log("info","error while processing logout request")}}).always(function(){i.getInstance().notify("You Have Successfully Logged out","FAST","VERY_FAST","alert-success"),t.destroyCookie(),t.updateLoginStatus(),t.logger.log("info","logout success")}),!1})},updateLoginStatus:function(){var t=this,n=e("#account_controls"),s=n.find("a.displ>span:not(.caret)"),o=n.find(".dropdown-menu li"),u=o.filter(".in"),a=o.filter(".out");o.hide(),e.cookie("j_username")!=""?(u.show(),s.html(e.cookie("j_username")),e("#editAccount").click(function(){e("#genericModal").load(DOMAIN_CONFIGURATIONS.BASE_URL+"form/edu.asu.krypton.model.persist.db.User/"+e.cookie("userId"),function(){e("#genericModal form").submit(function(t){t.preventDefault();var n=e(this),s=r.getInstance().serialize(n);return e.ajax({type:n.attr("method"),url:n.attr("action"),data:JSON.stringify(s),dataType:"json",cache:!1,contentType:"application/json",success:function(e){e.successful?n.parents(".modal[aria-hidden=false]").modal("hide"):i.getInstance().notify("Something Went Wrong","FAST","VERY_FAST","alert-error")},error:function(e){i.getInstance().notify("Something Went Wrong","FAST","VERY_FAST","alert-error")},complete:function(){}}),!1})})}),e.ajax({type:"GET",url:DOMAIN_CONFIGURATIONS.BASE_URL+"membership/status",cache:!1,success:function(n){if(n.successful)if(n.loggedin&&n.admin)e("#admin_nav").hasClass("hide")&&(e("#bod").removeClass("span12").addClass("span11"),e("#admin_nav").removeClass("hide"));else{if(!n.loggedin){t.destroyCookie(),t.updateLoginStatus();return}e("#bod").toggleClass("span11 span12"),e("#admin_nav").addClass("hide")}},error:function(){i.getInstance().notify("Something Went Wrong","FAST","VERY_FAST","alert-error"),t.logger.log("info","error while processing logout request")}})):(e("#bod").toggleClass("span11 span12"),e("#admin_nav").addClass("hide"),a.show(),s.html("Anonymous"))},destroyCookie:function(){e.cookie("j_username",""),e.cookie("userId","")}});return s.self=null,s.getInstance=function(){return s.self===null&&(s.self=new s),s.self},s});