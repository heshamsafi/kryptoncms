(function(){CKEDITOR.on("instanceReady",function(e){var t=e.editor,n=CKEDITOR.document.$.getElementsByName("ckeditor-sample-required-plugins"),r=n.length?CKEDITOR.dom.element.get(n[0]).getAttribute("content").split(","):[],i=[];if(r.length){for(var s=0;s<r.length;s++)t.plugins[r[s]]||i.push("<code>"+r[s]+"</code>");if(i.length){var o=CKEDITOR.dom.element.createFromHtml('<div class="warning"><span>To fully experience this demo, the '+i.join(", ")+" plugin"+(i.length>1?"s are":" is")+" required.</span>"+"</div>");o.insertBefore(t.container)}}})})();