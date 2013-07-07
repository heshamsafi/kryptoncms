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
        ,"./ThemeManager.class"
  
        ,"WYSIWYG.class"
        ,"ArticleEditor.class"
        ,"Scaffolder.class"
        ,"Pagination.class"
        ,"MenuManager.class"
        
        ,"./libraries/bootstrap"
        ,"./libraries/jquery.ui/selectable"
        ,"./libraries/jquery.ui/colorpicker"
        ,"./libraries/jquery.ui/slider"
        ,"./libraries/jquery.ui/draggable"
        
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
		ThemeManager    ,
		WYSIWYG				 ,
		ArticleEditor        ,
		Scaffolder			 ,
		Pagination			 ,
		MenuManager
		) {
	var navigationMenu = null;
	var commentManager = null;
	$(document).ready(function(){
		sessionScopeMain();
		Ajaxifier.getInstance(pageScopeMain,collectGarbage);
		pageScopeMain();
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
	var membership = null;
	function sessionScopeMain(){
		if(!FormSerializer.getInstance())
			FormSerializer.getInstance();//global configuration for singleton instance of
		
		Chatter.getInstance().reconnect();
		
		new Translator();
		
		membership = Membership.getInstance();
		membership.bindRegisterForm("form.membership_register");
		membership.bindLoginForm("form.membership_login");
		membership.attachLogoutHandler();
		membership.updateLoginStatus();
		membership.attachLogoutHandler();
		
		$(".modal").draggable({ handle: ".modal-header" });
	}
	function pageScopeMain(){
		$('.cp-basic').colorpicker();
		$( "#slider-range-min" ).slider({
	      range: "min",
	      value: 37,
	      min: 1,
	      max: 700,
	      slide: function( event, ui ) {
	        $( "#amount" ).val( ui.value + "px" );
	      }
	    });
	    $( "#amount" ).val( $( "#slider-range-min" ).slider( "value" ) + "px" );
		
		$("#createAppFormSubmit").click(function() {
			$.get($("#createAppForm").attr('action'), {
				publicKey_x : $("#createAppForm_X").val(),
				publicKey_y : $("#createAppForm_Y").val(),
				e : $("#createAppForm_E").val(),
				n : $("#createAppForm_N").val(),
				appName : $("#createAppForm_appName").val()
			}, function() {
			});
		});
		
		$("#searchArticles").attr("disabled",true);
		$("#searchArticlesPhrase").keyup(function(){
			$("#searchArticles").attr("disabled",$("#searchArticlesPhrase").val().length>0?false:true);
		});
		$("#searchArticlesForm").submit(function(event) {
			event.preventDefault();
			$.get($("#searchArticlesForm").attr('action'), {
				phrase : $("#searchArticlesPhrase").val()
			}, function(articles) {
				$("#bod").html(articles);
			});
			return false;
		});
		
//		$('select').each(function(i, e){
//	        if (!($(e).data('convert') == 'no')) {
//	                $(e).hide().wrap('<div class="btn-group" id="select-group-' + i + '" />');
//	                var select = $('#select-group-' + i);
//	                var current = ($(e).val()) ? $(e).val(): '&nbsp;';
//	                select.html('<input type="hidden" value="' + $(e).val() + '" name="' + $(e).attr('name') + '" id="' + $(e).attr('id') + '" class="' + $(e).attr('class') + '" /><a class="btn" href="javascript:;">' + current + '</a><a class="btn dropdown-toggle" data-toggle="dropdown" href="javascript:;"><span class="caret"></span></a><ul class="dropdown-menu"></ul>');
//	                $(e).find('option').each(function(o,q) {
//	                        select.find('.dropdown-menu').append('<li><a href="javascript:;" data-value="' + $(q).attr('value') + '">' + $(q).text() + '</a></li>');
//	                        if ($(q).attr('selected')) select.find('.dropdown-menu li:eq(' + o + ')').click();
//	                });
//	                select.find('.dropdown-menu a').click(function() {
//	                        select.find('input[type=hidden]').val($(this).data('value')).change();
//	                        select.find('.btn:eq(0)').text($(this).text());
//	                });
//	        }
//		});
		
		if(membership != null)
			membership.updateLoginStatus();
		
		$('[rel=tooltip]').tooltip();
		$('[rel=popover]').popover();
		
		Scaffolder.getInstance().activate();
		
		new MenuManager();
		
		var pagination = new Pagination();
		pagination.generatePaginationElement();
		
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
		
		new FileUploader();
		
		
		new Validator("form[data-validation-enable]").bind();
		
		if(!navigationMenu)
			new NavigationMenu("#mainNav").highlightMenu();
		
		commentManager = new CommentManager();
		commentManager.attachHandlersToSections();
		
		$("a[data-ajax-enable]").unbind("click");
		$("a[data-ajax-enable]").click(function(event){
			event.preventDefault();
			History.pushState({}, $(this).text(), $(this).attr("href"));
			return false;
		});
		
		var photoAlbumManager = new PhotoAlbumManager();
//		console.log("Test - MAIN");
		photoAlbumManager.listAlbums();
//		photoAlbumManager.$photoAlbums = $("[data-user-albums]");
		photoAlbumManager.listPhotoAlbums();

		$( "#slider-navbarHeight" ).slider({
	      range: "min",
	      value: 37,
	      min: 1,
	      max: 700,
	      slide: function( event, ui ) {
	        $( "#amount" ).val( ui.value + "px" );
	      }
	    });
	    $( "#amount" ).val( $( "#slider-navbarHeight" ).slider( "value" ) + "px" );
	
		$( "#slider-gridColumnWidth" ).slider({
		      range: "min",
		      value: 37,
		      min: 1,
		      max: 700,
		      slide: function( event, ui ) {
		        $( "#amount-gridColumnWidth" ).val( ui.value + "px" );
		      }
		});
		$( "#amount-gridColumnWidth" ).val( $( "#slider-gridColumnWidth" ).slider( "value" ) + "px" );

		$( "#slider-baseFontSize" ).slider({
		      range: "min",
		      value: 37,
		      min: 1,
		      max: 700,
		      slide: function( event, ui ) {
		        $( "#amount-baseFontSize" ).val( ui.value + "px" );
		      }
		});
		$( "#amount-baseFontSize" ).val( $( "#slider-baseFontSize" ).slider( "value" ) + "px" );

		$( "#slider-baseLineHeight" ).slider({
		      range: "min",
		      value: 37,
		      min: 1,
		      max: 700,
		      slide: function( event, ui ) {
		        $( "#amount-baseLineHeight" ).val( ui.value + "px" );
		      }
		});
		$( "#amount-baseLineHeight" ).val( $( "#slider-baseLineHeight" ).slider( "value" ) + "px" );

		$( "#slider-baseBorderRadius" ).slider({
		      range: "min",
		      value: 37,
		      min: 1,
		      max: 700,
		      slide: function( event, ui ) {
		        $( "#amount-baseBorderRadius" ).val( ui.value + "px" );
		      }
		});
		$( "#amount-baseBorderRadius" ).val( $( "#slider-baseBorderRadius" ).slider( "value" ) + "px" );
		
		$("#sendVars").unbind("click").click(function(){
//			alert("submit vars");
			console.log("submit vars");
//			console.log("blue:" + $("#blue").val());

			vars = {};
			$("form#themes_changer input").each(function(index, element){
				if($(this).val){
					if($(this).val())
						vars["@" + $(this).attr("name")] = ($(this).hasClass("cp-basic")?"#":"") + ($(this).hasClass("cp-basic")?$(this).val():$(("#slider-" + $(this).attr("name"))).slider( "value" )) + ($(this).hasClass("cp-basic")?"":"px");
//					console.log($(this).attr("name") + " = " + "#" + $(this).val());
				}
			});
//			vars["@navbarHeight"] = $("#slider-range-min").slider( "value" ) + "px";
			
//			vars += "}";
//			sliderValue = $("#slider-range-min").slider( "value" );
//			console.log("vars length = " + Object.keys(vars).length);
			console.log(vars);
//			console.log("slider:" + sliderValue);
			if(Object.keys(vars).length > 0){
				$("#loading_image").slideDown(50);
//				var speed = 400;
//				$(".inner-body").slideUp(speed);

				$.ajax({
					"url" : DOMAIN_CONFIGURATIONS.BASE_URL+"themes",
					"type" : "POST",
					"data" : JSON.stringify(vars),
					"contentType" : "application/json",
					"success" : function(responseBody) {
//						$(".inner-body").slideDown(speed);
//						collectGarbage();
//						pageScopeMain();
						$("#loading_image").slideUp(50);

						alert("submit vars success");
						console.log("submit vars success");
					},
					"error" : function(responseBody) {
						console.log(responseBody);
						alert("submit vars error");
						console.log("submit vars error");
					}
				});
			}
		});
		

	}
	function collectGarbage(){
		if(commentManager.activated){
			commentManager.closeSockets();
			delete commentManager;
		}
	}
});

