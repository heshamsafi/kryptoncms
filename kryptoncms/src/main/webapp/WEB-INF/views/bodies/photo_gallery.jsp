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

</div>
<!-- End Test bootstrab -->
<!-- <p id="info">Hello</p> -->
<!-- END PHOTO GALLERY -->