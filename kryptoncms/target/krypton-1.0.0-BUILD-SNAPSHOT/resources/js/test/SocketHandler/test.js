buster.testCase("Notifier", function(run) {
	require([ "jquery", "SocketHandler.class", "libraries/jquery.tmpl" ], function($, SocketHandler) {
		run({
			setUp : function() {
				this.timeout = 2000;
				this.socketHandler = new SocketHandler({
					url:buster.env.contextPath+"/chat",
					transport:"long-polling"
				});
				this.socketHandler.subscribe();
//				$.get(buster.env.contextPath+"/chatview").always(function(response){
//					console.log(response)
//					done();
//				});
//				.error(function(err){
//					console.log(err);
//				})
				;
			},
			"willy nilly" : function(){
				this.socketHandler.push("crap");
				this.socketHandler.close();
				assert(true);
			}
		});
	});
});
