Class.Mutators.jQuery=function(e){var t=this;jQuery.fn[e]=function(n){var r=this.data(e);if($type(n)=="string"){var i=r[n];if($type(i)=="function"){var s=i.apply(r,Array.slice(arguments,1));return s==r?this:s}if(arguments.length==1)return i;r[n]=arguments[1]}else{if(r)return r;this.data(e,new t(this.selector,n))}return this}};