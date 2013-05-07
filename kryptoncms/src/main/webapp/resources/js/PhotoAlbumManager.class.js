define([
        "jquery",
        "libraries/mootools-base",
        "libraries/jquery.tmpl"
        ]
,function(
		$,
		Mootools){
	var PhotoAlbumManager = new Mootools.Class({
		$photoAlbums : $(),
		
		initialize : function(){

		},
		listPhotoAlbums : function(){
			this.$photoAlbums.each(function(index,item){
				$.ajax({
					"url" : "http://localhost:8080/kryptoncms/photo/albums/",
					"type" : "GET",
					"dataType" : 'json',
					"cache" : false,
					"contentType" : "application/json",
					"success" : function(responseBody) {
						if (responseBody["successful"]) {
							//alert("Success");
							var albums = $("#album-tmpl").tmpl(
									responseBody["queryResult"]);
							albums.appendTo(item);
						} else
							alert("Success Else");
					},
					"error" : function(responseBody) {
						alert("Error");
					}
				});
			});
		}
	});
	return PhotoAlbumManager;
});