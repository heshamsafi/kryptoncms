define(	  ["jquery","libraries/mootools-base","libraries/jquery.history","FormSerializer.class","SocketHandler.class"]
 ,function( $	   , Mootools				 , History,FormSerializer){
	var Ajaxifier = new Mootools.Class({
		initialize:function(pageScopeMain,collectGarbage){
			this.pageScopeMain = pageScopeMain;
			this.collectGarbage= collectGarbage;
			this.formSerializer = new FormSerializer();
		    if ( !History.enabled ) {
		         // History.js is disabled for this browser.
		         // This is because we can optionally choose to support HTML4 browsers or not.
		        return false;
		    }
		    // Bind to StateChange Event
		    
		    History.Adapter.bind(window,'statechange',$.proxy(function(){ // Note: We are using statechange instead of popstate
		        var state = History.getState(); // Note: We are using History.getState() instead of event.state
		        History.log(state.data, state.title, state.url);
		        $(".dropdown.open").removeClass("open");
		        this.loadDynamicContent(state.url,this.pageScopeMain,this.collectGarbage);
		    },this));
		},
		loadDynamicContent :function(href,pageScopeMain,collectGarbage){
			$("#loading_image").slideDown(50);
			var speed = 400;
			$(".inner-body").slideUp(speed);
			
			$.get(href).always(function(data){
				if(typeof data !== "string" /*error*/) data = data.responseText; 
				$(".inner-body").html(data).slideDown(speed);
				collectGarbage();
				pageScopeMain();
				$("#loading_image").slideUp(50);
			});
		},pushState : function(data, title, url){
			History.pushState(data, title, url);
		},reload : function(){
			this.loadDynamicContent(window.location.href,this.pageScopeMain,this.collectGarbage);
		},
		passiveReload : function(){
			this.pageScopeMain();
		},
		formSubmitHandler : function($form,socket){
			var thisAjaxifierInstance = this;
			$form.submit(function(event){
				event.preventDefault();
				var serializedForm = thisAjaxifierInstance.formSerializer.serialize($form);
				console.log(serializedForm);
				var serializedForm = JSON.stringify(serializedForm);
				var scaffoldMessage = {"className":$form.attr('className'),actualEntity:serializedForm,action:"modify"};
				var stringifiedScaffoldMessage = JSON.stringify(scaffoldMessage);
				socket.push(stringifiedScaffoldMessage);
				$form.parents(".modal[aria-hidden=false]").modal("hide");
				return false;
			});
		}
	});
	//static fields
	Ajaxifier.self = null;//initially
	Ajaxifier.getInstance = function(pageScopeMain,collectGarbage){
		if(Ajaxifier.self == null){
			if(typeof pageScopeMain === 'undefined' || typeof collectGarbage === 'undefined') throw new Error("Ajaxifier Singleton was not previously configured");
			Ajaxifier.self = new Ajaxifier(pageScopeMain,collectGarbage);
		}
		return Ajaxifier.self;
	}
	return Ajaxifier;
});