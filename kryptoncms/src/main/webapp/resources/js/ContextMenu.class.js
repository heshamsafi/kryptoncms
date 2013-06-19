define(["jquery","libraries/mootools-base"],function($,Mootools){
	var ContextMenu = new Mootools.Class({
		menu : $(),
		Implements    : [Mootools.Options],
		initialize : function(){
			document.oncontextmenu = function() {return false;};
		},
		openMenu : function(event,items){
			var thisInstance = this;
			var props = {
				offset : {top:event.pageY,left:event.pageX},
				items  : items
			};
			$.when(thisInstance.closeMenu()).promise().then(function(){
				thisInstance.menu.remove();
				thisInstance.menu = $("#context-menu-templ").tmpl(props)
				   .appendTo($(event.target))
				   .slideDown(200);
				var target = thisInstance.menu.parent().parent();
				for(i in props.items){
					thisInstance.menu.find("li["+props.items[i].dataAttributes+"]")
					.attr("data-context-menu-item-index",i)
					.click(function(){
						props.items[parseInt($(this).attr("data-context-menu-item-index"))]
						.callback(target);
					});
				}
				//thisInstance.menu.find("a").mousedown($.proxy(thisInstance.closeMenu,this));
				$(document).click($.proxy(thisInstance.closeMenu,thisInstance));
			});
		},
		closeMenu : function(){
			var thisInstance = this;
			return thisInstance.menu.slideUp(100);
		}
		
	});
	return ContextMenu;
});