buster.testCase("Notifier", function(run) {
	require([ "jquery", "Notifier.class", "libraries/jquery.tmpl" ], function($, Notifier) {
		run({
			setUp : function(done) {
				this.timeout = 500;
				$.get(buster.env.contextPath + "/Notifier/markup", function(markup) {
					$('head').append(markup);
					$('head').append($(markup).clone().attr("id","cutsom_templ"));
					done();
				});
			},
			"default template and target" : function() {
				var notifier = Notifier.getInstance();
				assert.equals(notifier.$template.length, 1,"there should be one template");
				assert(notifier.$template.is($("#notification-templ")),"template was not set correctly");
				assert.equals(notifier.$target.length, 1,"there should be one target");
				assert(notifier.$target.is($("body")),"target was not set correctly");
				Notifier.deleteInstance();
			},
			"custom template and target" : function() {
				$('body').append("<div id='custom_target'></div>")
				var notifier = Notifier.getInstance({
					"selectors":{
						"template" : "#cutsom_templ",
				     	"target"   : "#custom_target"
					}
				});
				assert.equals(notifier.$template.length, 1,"there should be one template");
				assert(notifier.$template.is($("#cutsom_templ")),"template was not set correctly");
				assert.equals(notifier.$target.length, 1,"there should be one target");
				assert(notifier.$target.is($("#custom_target")),"target was not set correctly");
				Notifier.deleteInstance();
			},
			/*
			 * This test could time out if you increase the speed or decrease the test timeout
			 */
			"notif basic test":function(done){
				var notifier = Notifier.getInstance();
				var msg = "anything";
				var speed = 1;
				var transitionSpeed = 1;
				var type= "alert-success";
				notifier.notify(msg,speed,transitionSpeed,type,function(notification){
					assert(true);
//					buster.log(notification);
					Notifier.deleteInstance();
					done();
				});
				
			},
			"notif sequence test":function(done){
				this.timeout = 3000;
				var notifier = Notifier.getInstance();
				var notifs = [ 
				               {msg:"test1",type:"",speed:1,transitionSpeed:2},
				               {msg:"test2",type:"alert-block",speed:3,transitionSpeed:4},
				               {msg:"test3",type:"alert-error",speed:5,transitionSpeed:6},
				               {msg:"test4",type:"alert-success",speed:7,transitionSpeed:8},
				               {msg:"test5",type:"alert-info",speed:9,transitionSpeed:10}
				              ];
				$.each(notifs,function(idx,item){
					notifier.notify(item.msg,item.speed,item.transitionSpeed,item.type,function(notification){
						assert.match(notification,item);
//						console.log(idx);
						if(idx >= notifs.length-1) {
							Notifier.deleteInstance();
							this.timeout = 400;
							done();
						}
					});
				});
			}
		});
	});
});
