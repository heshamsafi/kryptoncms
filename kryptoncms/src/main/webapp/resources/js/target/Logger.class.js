define(["libraries/mootools-base"],function(e){var t=new e.Class({initialize:function(e){this.className=e,this.logLevels=["off","warn","error","info","debug"]},log:function(e,n){var r=this.logLevels.indexOf(e),i=this.logLevels.indexOf(t.logLevel);if(!(console&&typeof console.log=="function"&&i>=r))return;if(r<=-1){console.log("error : "+this.className+' - "'+e+'" is not a valid log level');return}console.log(e+" : "+this.className+" - "),console.log(n)}});return t.logLevel="debug",t});