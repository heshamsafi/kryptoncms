buster.testCase("Notifier",function(e){require(["jquery","SocketHandler.class","libraries/jquery.tmpl"],function(t,n){e({setUp:function(){this.timeout=2e3,this.socketHandler=new n({url:buster.env.contextPath+"/chat",transport:"long-polling"}),this.socketHandler.subscribe()},"push test":function(){this.socketHandler.push("data"),this.socketHandler.close(),assert(!0)}})})});