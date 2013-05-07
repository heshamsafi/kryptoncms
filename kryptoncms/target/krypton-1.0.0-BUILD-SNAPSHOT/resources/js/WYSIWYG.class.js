define(["jquery","libraries/mootools-base",'libraries/ckeditor/adapters/jquery'],function($,Mootools){
	   // this = [object DOMWindow]
    // CKEDITOR_BASEPATH is a global variable
	var WYSIWYG = new Mootools.Class({
		initialize : function(){
//			CKEDITOR.replace('editor1');
			$(".ckeditor").ckeditor();
		}
	});
	return WYSIWYG;
});