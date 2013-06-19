/* jQuery Validate 1.1.1 - https://github.com/DiegoLopesLima/Validate */
define(["jquery"],function($){(function(a,b,c,d){var e=['[type="color"],[type="date"],[type="datetime"],[type="datetime-local"],[type="email"],[type="file"],[type="hidden"],[type="month"],[type="number"],[type="password"],[type="range"],[type="search"],[type="tel"],[type="text"],[type="time"],[type="url"],[type="week"],textarea',"select",'[type="checkbox"],[type="radio"]'],f=e.join(","),g={},h=function(a,c){var f={pattern:!0,conditional:!0,required:!0},h=b(this),i=h.val()||"",j=h.data("validate"),k=j!==d?g[j]:{},l=h.data("prepare")||k.prepare,m=h.data("pattern")||("regexp"==b.type(k.pattern)?k.pattern:/(?:)/),n=h.attr("data-ignore-case")||h.data("ignoreCase")||k.ignoreCase,o=h.data("mask")||k.mask,p=h.data("conditional")||k.conditional,q=h.data("required"),r=h.data("describedby")||k.describedby,s=h.data("description")||k.description,t=h.data("trim"),u=/^(true|)$/i,v=/^false$/i,s=b.isPlainObject(s)?s:c.description[s]||{};if(q=""!=q?q||!!k.required:!0,t=""!=t?t||!!k.trim:!0,u.test(t)&&(i=b.trim(i)),b.isFunction(l)?i=l.call(h,i)+"":b.isFunction(c.prepare[l])&&(i=c.prepare[l].call(h,i)+""),"regexp"!=b.type(m)&&(n=!v.test(n),m=n?RegExp(m,"i"):RegExp(m)),p!=d)if(b.isFunction(p))f.conditional=!!p.call(h,i,c);else for(var x=p.split(/[\s\t]+/),y=0,z=x.length;z>y;y++)c.conditional.hasOwnProperty(x[y])&&!c.conditional[x[y]].call(h,i,c)&&(f.conditional=!1);if(q=u.test(q),q&&(h.is(e[0]+","+e[1])?!i.length>0&&(f.required=!1):h.is(e[2])&&(h.is("[name]")?0==b('[name="'+h.prop("name")+'"]:checked').length&&(f.required=!1):f.required=h.is(":checked"))),h.is(e[0]))if(m.test(i)){if("keyup"!=a.type&&o!==d){for(var A=i.match(m),B=0,z=A.length;z>B;B++)o=o.replace(RegExp("\\$\\{"+B+"(?::`([^`]*)`)?\\}","g"),A[B]!==d?A[B]:"$1");o=o.replace(/\$\{\d+(?::`([^`]*)`)?\}/g,"$1"),m.test(o)&&h.val(o)}}else q?f.pattern=!1:i.length>0&&(f.pattern=!1);var C=b('[id="'+r+'"]'),D=s.valid;return C.length>0&&"keyup"!=a.type&&(f.required?f.pattern?f.conditional||(D=s.conditional):D=s.pattern:D=s.required,C.html(D||"")),c.eachField.call(h,a,f,c),f.required&&f.pattern&&f.conditional?(c.waiAria&&h.prop("aria-invalid",!1),c.eachValidField.call(h,a,f,c)):(c.waiAria&&h.prop("aria-invalid",!0),c.eachInvalidField.call(h,a,f,c)),f};b.extend({validateExtend:function(a){return b.extend(g,a)},validateSetup:function(c){return b.extend(a,c)}}).fn.extend({validate:function(c){return c=b.extend({},a,c),b(this).validateDestroy().each(function(){var a=b(this);if(a.is("form")){a.data(name,{options:c});var d=a.find(f);a.is("[id]")&&(d=d.add('[form="'+a.prop("id")+'"]').filter(f)),d=d.filter(c.filter),c.onKeyup&&d.filter(e[0]).on("keyup."+c.namespace,function(a){h.call(this,a,c)}),c.onBlur&&d.on("blur."+c.namespace,function(a){h.call(this,a,c)}),c.onChange&&d.on("change."+c.namespace,function(a){h.call(this,a,c)}),c.onSubmit&&a.on("submit."+c.namespace,function(e){var f=!0;d.each(function(){var a=h.call(this,e,c);a.pattern&&a.conditional&&a.required||(f=!1)}),f?(c.sendForm||e.preventDefault(),b.isFunction(c.valid)&&c.valid.call(a,e,c)):(e.preventDefault(),b.isFunction(c.invalid)&&c.invalid.call(a,e,c))})}})},validateDestroy:function(){var a=b(this),c=a.data(name);if(a.is("form")&&b.isPlainObject(c)&&"string"==typeof c.options.nameSpace){var d=c.nameSpace,e=a.removeData(name).find(f).add(a);a.is("[id]")&&(e=e.add(b('[form="'+a.prop("id")+'"]').filter(f))),e.off("."+d)}return a}})})({sendForm:!0,waiAria:!0,onSubmit:!0,onKeyup:!1,onBlur:!1,onChange:!1,nameSpace:"validate",conditional:{},prepare:{},description:{},eachField:$.noop,eachInvalidField:$.noop,eachValidField:$.noop,invalid:$.noop,valid:$.noop,filter:"*"},jQuery,window);

});