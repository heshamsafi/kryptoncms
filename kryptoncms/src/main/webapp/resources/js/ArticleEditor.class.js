define(["jquery","libraries/mootools-base","FormSerializer.class","Ajaxifier.class","Notifier.class"],function($,Mootools,FormSerializer,Ajaxifier,Notifier){
	var ArticleEditor = new Mootools.Class({
		initialize : function(){
			$('#article_form').submit(function() {
				var $thisArticleForm = $(this);
				var url = $thisArticleForm.attr("action");
				for (instance in CKEDITOR.instances)
					CKEDITOR.instances[instance].updateElement();
				var newTitle= $('#title').val();
				{
					
					var Url = url;
					var toRemove = '/kryptoncms/article/edit/';
					var title = Url.replace(toRemove,'')
					if(title!=newTitle){
						url=toRemove
					}
				}	
					
					
					
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
						var title = response["title"];
						var version = response["version"];
						if(url.match(new RegExp("(edit(/)?)$")) != null){
							if( url.match(new RegExp("\\w$") ) )  {
								url += "/"; 
								url+=version;
							}
							if( url.match(new RegExp("/$")   ) ){ 
								url += title;
								url+="/";
								url+=version;
							}
							
						}
						else{
							url+="/";
							url+=version;
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