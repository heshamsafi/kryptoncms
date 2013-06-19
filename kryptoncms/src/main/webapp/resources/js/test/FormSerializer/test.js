buster.testCase("FormSerializer",function(run) {
  require(["jquery","FormSerializer.class","libraries/jquery.tmpl"],function($,FormSerializer) {
    run({
      setUp : function(done){
    	  this.data = {"key1":"val1","key2":"val2"};
    	  $.get(buster.env.contextPath+"/FormSerializer/markup",function(markup){
    		 $('head').append(markup);
    		 done();
    	  });
      },
      "default is JSON": function() { 
    	  var serialized = new FormSerializer().serialize($("<form>"));
    	  assert($.isPlainObject(serialized));
      },
      "Type Of Url Encoded": function() { 
    	  var serialized = new FormSerializer({"format":"urlencoded"}).serialize($("<form>"));
    	  assert(typeof serialized === "string");
      },
      "JSON Serialization" :function(){
    	  var form = $("script#form").tmpl({data:this.data});
    	  
    	  var serialized = new FormSerializer().serialize($(form));
    	  
    	  //this function is NOT commutative !
    	  //if you interchange the params the test will pass for empty objects !
    	  assert.match(serialized,this.data);
      },
      "UrlEncoding" :function(){
    	  var data = {"key1":"val1","key2":"val2"};
    	  var form = $("script#form").tmpl({data:this.data});
    	  var serialized = new FormSerializer({"format":"urlencoded"}).serialize($(form));
    	  assert.match("key1=val1&key2=val2",serialized);
      },
    });
  });
});
