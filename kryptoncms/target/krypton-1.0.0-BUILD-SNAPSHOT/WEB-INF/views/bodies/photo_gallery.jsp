<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
	<h1>Photo Gallery Demo</h1>
</div>
<br>
<!-- BEGIN PHOTO GALLERY -->

<!-- Test bootstrab -->
<style>
<!--
body{
 overflow: scroll; 
 max-height: 400px;
}
-->
</style>

<script id="photo-tmpl" type="text/x-jquery-tmpl">
	<a href="http://localhost:8080/kryptoncms/resources/img/\${album}/\${path}" title="\${path}" data-gallery="gallery" ><img id="\${id}" src="http://localhost:8080/kryptoncms/resources/img/\${album}/\${path}" width="100px" height="100px" data-gallery="gallery" class="img-polaroid" /></a>
</script>

<script id="comments-tmpl" type="text/x-jquery-tmpl">
    	<div class="row-fluid" >
	        <div class="span12 well" data-commentable-id="\${id}" data-commentable-type="Comment" style="background-color:rgb(195, 202, 221)">
	        	<dl>
	        		<dt data-username>\${username}</dt>
	        		<dd class="muted" data-content><%--\${id}---%>\${content}</dd>
	        	</dl>
				<a href="javascript:void(0)" data-reply-button>Reply</a>
				<textarea class="hide" data-reply-input placeholder="Enter Your Message Here"></textarea>

				
				<button data-show-replies-btn class="btn dropdown-toggle pull-right <%--dropup--%>" {{if noOfReplies <= 0}}disabled="disabled"{{/if}} >
					<span class="annotate">See Replies(</span>
					<span class="annotate" data-show-replies-text>\${noOfReplies}</span>
					<span class="annotate">)</span>
					<span class="annotate hide">Hide Replies</span>
					<span class="caret"></span>
				</button>
				<div data-server-service-prefix-url="<c:url value="/comments/" />">
					<div class="span9 well well-large hide"
		    		    data-enable-comments                              
		    		    data-commentable-type="Comment"            
		    		    data-commentable-id="\${id}"
						data-comment-containter
						>
			    	</div>
			    </div>
				
	        </div>
		</div>
	</script>

<div clas="container" style="overflow:hidden">
	<div class="row-fluid">
		<!-- Add this directly below div class="container"> -->
		<!-- Modal -->
		<div class="modal-container draggable">
			<div id="modal-gallery" class="modal modal-gallery hide fade in draggable"
				tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="background-attachment: scroll; overflow: scroll; max-height: 600px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="myModalLabel">Photo Header</h3>
				</div>
				<div class="modal-body">
					<div class="modal-image"></div>
				</div>
				<div class="modal-footer">

					<a id="showComments" class="btn">Show comments</a> <a
						class="btn btn-info modal-prev"><i
						class="icon-arrow-left icon-white"></i> Previous</a> <a
						class="btn btn-success modal-play modal-slideshow"
						data-slideshow="5000"><i class="icon-play icon-white"></i>
						Slideshow</a> <a class="btn btn-primary modal-next">Next <i
						class="icon-arrow-right icon-white"></i></a>
					<div id="commentBody" style="overflow: auto; max-height: 400px"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- 	<a href="#modal-galleryl" role="button" class="btn" data-toggle="modal">Show Photos</a> -->
	<div id="gallery" data-toggle="modal-gallery"
		data-target="#modal-gallery"></div>

	<script type="text/javascript">
		$(document).ready(function() {
							//							$("body.modal-open").css("overflow", "hidden");
							/* $('#modal-gallery').on('shown', function () {
							    $('body').on('wheel.modal mousewheel.modal', function () {
							      return false;
							    });
							  }).on('hidden', function () {
							    $('body').off('wheel.modal mousewheel.modal');
							  }); */
							$.ajax({
										"url" : "http://localhost:8080/kryptoncms/photo/Album/${album_number}",
										"type" : "GET",
										"dataType" : 'json',
										"cache" : false,
										"contentType" : "application/json",
										"success" : function(responseBody) {
											if (responseBody["successful"]) {
												var photos = $("#photo-tmpl").tmpl(responseBody["queryResult"]);
												//photos.appendTo($("#modal-gallery > .modal-body > .modal-image"));
												photos.appendTo($("#gallery"));
												//$("#modal-gallery > .modal-body > .modal-image:first-child").addClass("in");
											} else
												alert("Success Else");
										},
										"error" : function(responseBody) {
											alert("Error");
										}
									});

							$("#showComments").click(function() {
												var srcIn = $("img.in").attr("src");
												var id;
												$("img").each(function() {
																	if ($(this).attr("src") == srcIn) {
																		id = $(this).attr("id");
																	}
																});
												//alert(id);
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
																	$("#commentBody").html(comments);
																	//comments.appendTo($("#commentBody"));
																	//alert(comments);
																	alert("successful ok");
																} else {
																	alert("Comment successful else");
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
							$("body").keypress(function(event) {
								//alert(event.which);
								//event.preventDefault();
								});
							$(".modal-gallery").scroll(function(event) {
								//alert(event.which);
								//event.preventDefault();
								});
							Init();
						});
	</script>
</div>
<!-- End Test bootstrab -->
    <script type="text/javascript">
        function MouseScroll (event) {
            var rolled = 0;
            if ('wheelDelta' in event) {
                rolled = event.wheelDelta;
            }
            else {  // Firefox
                    // The measurement units of the detail and wheelDelta properties are different.
                rolled = -40 * event.detail;
            }
            //alert(rolled);
            var info = document.getElementById ("info");
            info.innerHTML = rolled;
          	event.preventDefault();
        }

        function Init () {
                // for mouse scrolling in Firefox
                $("#modal-gallery").unbind("mousewheel");
                $("#modal-gallery").unbind("DOMMouseScroll");
                $("#modal-gallery").unbind("onmousewheel");
                $("#modal-gallery").unbind("onscroll");
            var elem = document.getElementById ("modal-gallery");
            if (elem.addEventListener) {    // all browsers except IE before version 9
                    // Internet Explorer, Opera, Google Chrome and Safari
                elem.addEventListener ("mousewheel", MouseScroll, false);
                    // Firefox
                elem.addEventListener ("DOMMouseScroll", MouseScroll, false);
            }
            else {
                if (elem.attachEvent) { // IE before version 9
                    elem.attachEvent ("onmousewheel", MouseScroll);
                }
            }
        }
    </script>
<!-- <p id="info">Hello</p> -->
<!-- END PHOTO GALLERY -->