define(["libraries/mootools-base"],function(Mootools){
var Logger = new Mootools.Class({
	initialize : function(className){
		this.className = className;
		this.logLevels = [
		      "off","warn","error","info","debug"
		];
	},
	log : function(logLevel,logObject){
		var logLevelWeight = this.logLevels.indexOf(logLevel);
		var configLogLevelWeight = this.logLevels.indexOf(Logger.logLevel);
		if(console && typeof console.log == "function" && configLogLevelWeight >= logLevelWeight);else return;
		
		if(logLevelWeight <= -1){
			console.log("error : " + this.className + " - \""+logLevel+"\" is not a valid log level");
			return;
		}
		
		console.log(logLevel + " : " + this.className + " - ");
		console.log(logObject);
	}
});

//static
Logger.logLevel = "debug";
return Logger;
});