var config=module.exports,fs=require("fs"),resources=[],files=fs.readdirSync(__dirname);for(var i in files){var dir=__dirname+"/"+files[i];if(fs.lstatSync(dir).isDirectory()){var innerFiles=fs.readdirSync(dir);for(var j in innerFiles){var innerFile=dir+"/"+innerFiles[j];if(innerFile.match(".html$")===null)continue;resources.push({path:innerFile.replace(".html","").replace(__dirname+"/",""),content:fs.readFileSync(innerFile)})}}}resources.push({path:"/chatview",backend:"localhost:8080/kryptoncms/chat/echo"}),config.browser_tests={autoRun:!1,rootPath:"../",environment:"browser",libs:["libraries/require-jquery.js"],sources:["*.js","libraries/*.js"],tests:["test/*/test.js"],extensions:[require("buster-amd")],resources:resources};