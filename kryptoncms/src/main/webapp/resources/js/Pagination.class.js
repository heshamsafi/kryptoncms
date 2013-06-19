define(["jquery","libraries/mootools-base","libraries/jquery.tmpl"]
,function($		,Mootools){
	var Pagination = new Mootools.Class({
		initialize : function(){
			this.$elements = $("[data-enable-pagination]");
			this.$tmpl = $("script#pagination-templ");
		},
		generatePaginationElement : function(){
			var thisPaginationInstance = this;
			this.$elements.each(function(idx,$el){
				$el = $($el);
//				  data-pagination-page-size="10" data-pagination-page-no="1"
//				  data-pagination-total-size="${totalSize}"
				var model = {
					pages : []
				}; 
				var currentIdx = parseInt($el.attr('data-pagination-page-no'));
				for(var i=1;i<=Math.ceil(parseInt($el.attr('data-pagination-total-size'))/parseInt($el.attr('data-pagination-page-size')));i++){
					if( window.location.search.match(/^\?.*[^\?]$/) == null ){
						//no query string
						link = "?pageNo="+i;
					}else if(window.location.search.match(/pageNo=\d+/) == null){
						link = window.location.search+"&"+"pageNo="+i;
					}else {
						link = window.location.search.replace(/pageNo=\d+/,"pageNo="+i)
					}
					model.pages.push({ caption:i , link : link ,active:currentIdx===i});
				}
				thisPaginationInstance.$tmpl.tmpl(model).insertAfter($el);
			});
		}
	});
	return Pagination;
});