//requirejs.config({
//    //By default load any module IDs from js/lib
//    baseUrl: 'resources/js',
//    //except, if the module ID starts with "app",
//    //load it from the js/app directory. paths
//    //config is relative to the baseUrl, and
//    //never includes a ".js" extension since
//    //the paths config could be for a directory.
//    paths: {
//    	
//    }
//});

require([
         "jquery"			   
        ,"./FormSerializer.class"
        ,"./DataToggleButton.class"
        ,"./Membership.class"
        ,"./Validator.class"
        ,"./NavigationMenu.class"
        ,"./Chatter.class"       
        ,"./CommentManager.class"
        ,"./Ajaxifier.class"
        ,"./Translator.class"
        ,"./FileUploader.class"  
        ,"./PhotoAlbumManager.class"
  
        ,"WYSIWYG.class"
        ,"ArticleEditor.class"
        ,"Scaffolder.class"
        
        ,"./libraries/bootstrap"
        ,"./libraries/jquery.ui/selectable"
        ],
function(
		$	     		     ,
		FormSerializer	     ,
		DataToggleButton     ,
		Membership 		 	 ,
		Validator		 	 , 
		NavigationMenu       ,
		Chatter			     , 
		CommentManager       , 
		Ajaxifier	         ,
		Translator     	     ,
		FileUploader		 ,
		PhotoAlbumManager    ,
		WYSIWYG				 ,
		ArticleEditor        ,
		Scaffolder
		) {
	var navigationMenu = null;
	var chatter = null,
		commentManager = null;
	$(document).ready(function(){
		pageScopeMain();
		sessionScopeMain();
		Ajaxifier.getInstance(pageScopeMain,collectGarbage);
		var pin = false;
		var hoverHandler = function(){
			$("#admin_nav").toggleClass("span3 span0");
			$("#bod").toggleClass("span9 span12");
		}
		$("#admin_nav").delay(2000).on('mouseenter mouseleave',hoverHandler);
		$("a#pin").click(function(){
			pin = !pin;
			if(pin)
				$("#admin_nav").off('mouseenter mouseleave');
			else
				$("#admin_nav").on('mouseenter mouseleave',hoverHandler);
			$(this).find("span").toggle();
		});
//		$("#admin_nav").hover(hoverHandler);
	});
	function sessionScopeMain(){
		if(!FormSerializer.getInstance())
			FormSerializer.getInstance();//global configuration for singleton instance of
		
		var membership = Membership.getInstance();
		membership.bindRegisterForm("form.membership_register");
		membership.bindLoginForm("form.membership_login");
		membership.attachLogoutHandler();
		membership.updateLoginStatus();
	}
	function pageScopeMain(){
		new Scaffolder();
		new ArticleEditor();
		new WYSIWYG();
		$("a[data-popover-enable]").popover({ 
			   html : true,
			   content: function() {
				      return $('#popover_content_wrapper').html();
			   }
		});

		//form serializer
		new DataToggleButton().patch();
		new Translator();
		
		new FileUploader();
		
		
		new Validator("form[data-validation-enable]").bind();
		
		if(!navigationMenu)
			new NavigationMenu("#mainNav").highlightMenu();
		
		chatter = new Chatter();
		chatter.$elements.$chatterForm          = $("#chatter_form");
		chatter.$elements.$destination          = $("#destination");
		chatter.$elements.$messageBody          = $("#message");
		chatter.$elements.$messageBoard         = $("#message_board");
		chatter.$elements.$submitBtn            = $("#btn_submit");
		chatter.$elements.$destinationsPallette = $("#destinations_pallette");
		chatter.$elements.$destinationAdderBtn  = $("#destination_adder");
		
		chatter.attachHandlers();
		
		commentManager = new CommentManager();
		commentManager.attachHandlersToSections();
		
		$("a[data-ajax-enable]").unbind("click");
		$("a[data-ajax-enable]").click(function(event){
			event.preventDefault();
			History.pushState({}, $(this).text(), $(this).attr("href"));
			return false;
		});
		
		var photoAlbumManager = new PhotoAlbumManager();
		photoAlbumManager.$photoAlbums = $("[data-user-albums]");
		photoAlbumManager.listPhotoAlbums();​
	}
	function collectGarbage(){
		if(chatter.activated){
			chatter.closeSockets();
			delete chatter;
		}
		if(commentManager.activated){
			commentManager.closeSockets();
			delete commentManager;
		}
	}
});

