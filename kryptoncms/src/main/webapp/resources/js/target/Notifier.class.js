define(["jquery","libraries/mootools-base"],function(e,t){var n=new t.Class({queue:new Array,occupied:!1,Implements:t.Options,options:{selectors:{template:"#notification-templ",target:"body"}},initialize:function(t){this.setOptions(t),this.$template=e(this.options.selectors.template),this.$target=e(this.options.selectors.target),this.Notification=function(e,t,n,r){this.msg=e,this.speed=t,this.transitionSpeed=n,this.type=r}}.protect(),showNotification:function(t,n){var r=this;r.occupied=!0;var i={VERY_VERY_FAST:100,VERY_FAST:200,FAST:1e3,SLOW:4e3,VERY_SLOW:6e3,MEDIUM:2500};typeof t.speed=="string"?t.speed=i[t.speed]:t.speed==null&&(t.speed=i.MEDIUM),typeof t.transitionSpeed=="string"?t.transitionSpeed=i[t.transitionSpeed]:t.transitionSpeed==null&&(t.transitionSpeed=i.MEDIUM);var s=r.$template.tmpl(t);s.appendTo(r.$target);var o=2*s.height();s.css("top","+"+e(window).height()+"px"),s.animate({top:"-="+(o-13)+"px"},t.transitionSpeed,function(){setTimeout(function(){s.animate({top:"+="+o+"px"},t.transitionSpeed,function(){s.remove(),r.occupied=!1,typeof n!="undefined"&&n(t)})},t.speed)})},notify:function(e,t,n,r,i){var s=this,o=null;s.queue.unshift(new this.Notification(e,t,n,r));while(s.queue.length!=0){o=setInterval(function(){s.occupied||(typeof i=="undefined"?s.showNotification(s.queue.pop()):s.showNotification(s.queue.pop(),i),clearInterval(o))},200);return}}});return n.self=null,n.deleteInstance=function(){this.self=null},n.getInstance=function(e){return this.self===null&&(this.self=typeof e=="undefined"?new n:new n(e)),this.self},n});