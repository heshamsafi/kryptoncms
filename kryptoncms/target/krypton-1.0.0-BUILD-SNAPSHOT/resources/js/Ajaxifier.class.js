define(	  ["jquery","libraries/mootools-base","libraries/jquery.history"]
 ,function( $	   , Mootools				 , History){
	var Ajaxifier = new Mootools.Class({
		initialize:function(pageScopeMain,collectGarbage){
			this.pageScopeMain = pageScopeMain;
			this.collectGarbage= collectGarbage;
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