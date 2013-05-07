define(["jquery","libraries/mootools-base","FormSerializer.class","Ajaxifier.class","Notifier.class"],function($,Mootools,FormSerializer,Ajaxifier,Notifier){
	var ArticleEditor = new Mootools.Class({
		initialize : function(){
			$('#article_form').submit(function() {
				var $thisArticleForm = $(this);
				var url = $thisArticleForm.attr("action");
				for (instance in CKEDITOR.instances)
					CKEDITOR.instances[instance].updateElement();
				$('textarea').trigger('keyup');//what is this for ?
				$.ajax({
					"dataType": 'json',
	    			"cache": false,
	    			"contentType" : "application/json",
					data : JSON.stringify(FormSerializer.getInstance().serialize($thisArticleForm)),
					type :  $thisArticleForm.attr("method"),
					url : url
				})
				.success(function(response){
					if(response["successful"]){
						var id = response["id"];
						if(url.match(new RegExp("\\d$")) == null){
							if( url.match(new RegExp("\\w$") ) )  url += "/"; 
							if( url.match(new RegExp("/$")   ) )  url += id; 
						}
						if(url === window.location.pathname)
							Ajaxifier.getInstance().reload();
						else 
							Ajaxifier.getInstance().pushState({},"Article",url);
					}else Notifier.getInstance().notify("Something Went Wrong","FAST","VERY_FAST","alert-error");
				})
				.fail(function(){})
				.always(function(){
					
				});
			});
		}
	});
	return ArticleEditor;
});