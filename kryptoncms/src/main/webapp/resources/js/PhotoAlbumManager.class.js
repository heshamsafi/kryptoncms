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
			$("#showComments").click(function() {
				var srcIn = $("img.in").attr("src");
				var id;
				$("img").each(function() {
					if ($(this).attr("src") == srcIn) {
						id = $(this).attr("id");
					}
				});
//				alert(id);
				var url = "http://localhost:8080/kryptoncms/comments/Photo/" + id;
				$.ajax({
					"url" : url,
					"type" : "GET",
					"dataType" : 'json',
					"cache" : false,
					"contentType" : "application/json",
					"success" : function(responseBody) {
							if (responseBody["successful"]) {
								var comments = $("#comments-tmpl").tmpl(responseBody["queryResult"]);
//								$("#commentBody").html(comments);
								comments.appendTo($("#commentBody"));
								//alert(comments);
//								alert("successful ok");
							} else {
//								alert("Comment successful else");
							}
					},
					"error" : function(responseBody) {
						alert("Comment error");
					}
				});
				//$("#modal-gallery").modal();
			});
			$(".modal-prev").click(function(){
				$("#commentBody").html(null);
			});
			$(".modal-next").click(function(){
				$("#commentBody").html(null);
			});
		},
		listPhotoAlbums : function(){
			if($("#gallery").length != 0){
	//			alert("LIST PHOTO ALBUMS");
				var albumNumber = decodeURIComponent( (RegExp('album_number=' + '(.+?)(&|$)', 'i').exec(location.search) || [, ""])[1]);
	//			this.$photoAlbums.each(function(index,item){
					$.ajax({
						"url" : "http://localhost:8080/kryptoncms/photo/Album/" + albumNumber,
						"type" : "GET",
						"dataType" : 'json',
						"cache" : false,
						"contentType" : "application/json",
						"success" : function(responseBody) {
	//						alert("Test - LIST - PHOTOS");
							console.log("Test - LIST - PHOTOS");
							if (responseBody["successful"]) {
								console.log("Test - LIST - PHOTOS - SUCCESSFUL");
	//							alert("Test - LIST - PHOTOS - SUCCESSFUL");
								var photos = $("#photo-tmpl").tmpl(responseBody["queryResult"]);
								console.log("photos is");
								console.log(responseBody["queryResult"]);
								//photos.appendTo($("#modal-gallery > .modal-body > .modal-image"));
								photos.appendTo($("#gallery"));
	//							$("#gallery").html(photos);
	//							$("#gallery").html("<p>TEST2!!!</p>");
								console.log(photos);
								//$("#modal-gallery > .modal-body > .modal-image:first-child").addClass("in");
							} else
								alert("Success Else");
						},
						"error" : function(responseBody) {
							console.log(responseBody);
	//						alert("Error - LIST - PHOTOS");
						}
					});
	//			});
			}
		},
		listAlbums : function(){
			if($("#albums").length != 0){
	//			test = "<p>TEST2</p>";
				console.log("Test - LIST - ALBUMS");
	//			alert("ALBUMS");
	//			test.appendTo($("#albums"));
				$.ajax({
					"url" : "http://localhost:8080/kryptoncms/photo/albums",
					"type" : "GET",
					"dataType" : 'json',
					"cache" : false,
					"contentType" : "application/json",
					"success" : function(responseBody) {
						if (responseBody["successful"]) {
	//						alert("Success - ALBUMS");
							var albums = $("#album-tmpl").tmpl(	responseBody["queryResult"]);
							console.log(albums);
							albums.appendTo($("#albums"));
						} else
							alert("Success Else");
					},
					"error" : function(responseBody) {
						console.log(responseBody);
	//					alert("Error - LIST - ALBUMS");
					}
				});
			}
		}
	});
	return PhotoAlbumManager;
});
