define( [
         "jquery",
         "./libraries/mootools-base",
         "./libraries/jquery.ui/widget",
         "./libraries/tmpl",
         "./libraries/load-image",
         "./libraries/canvas-to-blob",
         "./libraries/bootstrap",
         "./libraries/bootstrap-image-gallery",
         "./libraries/jquery.iframe-transport",
         "./libraries/jquery.fileupload",
         "./libraries/jquery.fileupload-fp",
         "./libraries/jquery.fileupload-ui"
         ]
,function($		 ,Mootools){
	var FileUploader = new Mootools.Class({
		
		initialize : function(){
			$('form[data-enable-ajax-uploader]').each(function(){
				var $this = $(this);
				
				$this.fileupload({
					// Uncomment the following to send cross-domain cookies:
					//xhrFields: {withCredentials: true},
					url : $this.attr("action")
				});
				$this.fileupload(
						'option',
						'redirect',
						window.location.href.replace(/\/[^\/]*$/,
								'/cors/result.html?%s'));
	
				$this.fileupload('option', {
					url : $this.attr("action"),
					maxFileSize : 5000000,
					acceptFileTypes : /(\.|\/)(gif|jpe?g|png)$/i,
					process : [ {
						action : 'load',
						fileTypes : /^image\/(gif|jpeg|png)$/,
						maxFileSize : 20000000
					}, {
						action : 'resize',
						maxWidth : 1440,
						maxHeight : 900
					}, {
						action : 'save'
					} ]
				});


			});
		}
	});
	return FileUploader;
});