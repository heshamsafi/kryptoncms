(function(e){var t=function(e,n){var r=/[^\w\-\.:]/.test(e)?new Function(t.arg+",tmpl","var _e=tmpl.encode"+t.helper+",_s='"+e.replace(t.regexp,t.func)+"';return _s;"):t.cache[e]=t.cache[e]||t(t.load(e));return n?r(n,t):function(e){return r(e,t)}};t.cache={},t.load=function(e){return document.getElementById(e).innerHTML},t.regexp=/([\s'\\])(?![^%]*%\})|(?:\{%(=|#)([\s\S]+?)%\})|(\{%)|(%\})/g,t.func=function(e,t,n,r,i,s){if(t)return{"\n":"\\n","\r":"\\r","	":"\\t"," ":" "}[e]||"\\"+e;if(n)return n==="="?"'+_e("+r+")+'":"'+("+r+"||'')+'";if(i)return"';";if(s)return"_s+='"},t.encReg=/[<>&"'\x00]/g,t.encMap={"<":"&lt;",">":"&gt;","&":"&amp;",'"':"&quot;","'":"&#39;"},t.encode=function(e){return String(e||"").replace(t.encReg,function(e){return t.encMap[e]||""})},t.arg="o",t.helper=",print=function(s,e){_s+=e&&(s||'')||_e(s);},include=function(s,d){_s+=tmpl(s,d);}",typeof define=="function"&&define.amd?define([],function(){return t}):e.tmpl=t})(this);