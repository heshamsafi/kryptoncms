var config = module.exports;
var fs = require("fs");

var resources = [];
var files = fs.readdirSync(__dirname);
for(var i in files){
	var dir = __dirname+"/"+files[i];
	if(fs.lstatSync(dir).isDirectory()){
		var innerFiles = fs.readdirSync(dir);
		for(var j in innerFiles){
			var innerFile = dir+"/"+innerFiles[j];
			if(innerFile.match(".html$") === null) continue;
//			console.log(innerFile);
		    resources.push(
	            {
	                path : innerFile.replace(".html","").replace(__dirname+"/",""),
	                content: fs.readFileSync(innerFile)
	            }
		    );
		}
	}
}
//console.log(resources);
//resources.push({
//	"path":"/chat",
//	"backend":"http://localhost:8080/kryptoncms/chat/echo"
//});
resources.push({
	path:"/chatview",
//	file:"Main.js"
//	backend:"Notifier/markup"
	backend:"localhost:8080/kryptoncms/chat/echo"
//	backend:"http://127.0.0.1:1111/"
});
config["browser_tests"] = {
	autoRun: false,
    rootPath: "../",
    environment: "browser",
    libs: [
           "libraries/require-jquery.js"
    ],
    sources: [
        "*.js",
        "libraries/*.js"
    ],
    tests: [
        "test/*/test.js"
    ]
,
    extensions: [require('buster-amd')],
	resources: resources
//		[ 
//	             { 
//	            	 path: "FormSerializer/markup",
//	            	 content: fs.readFileSync('markup.html')
//	             },
//	             { 
//	            	 path: "markup",
//	            	 content: fs.readFileSync('test/markup.html')
//	             } 
//	]
}

// Add more configuration groups as needed
