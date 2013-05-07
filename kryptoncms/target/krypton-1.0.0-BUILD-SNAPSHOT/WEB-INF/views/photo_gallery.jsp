<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
<head>

<title>Welcome to Spring Web MVC - Atmosphere Sample</title>

<%@ include file="/WEB-INF/views/includes/common_head.jsp"%>

</head>

<body style="margin: 10px 10px 10px 10px;">
	<div class="container-fluid">
		<%@ include file="/WEB-INF/views/includes/header.jsp"%>
		<!--Body-->
		<div class="row-fluid">
			<%@ include file="/WEB-INF/views/includes/nav.jsp"%>
			<div class="span9 well well-large">
				<!-- BEGIN PHOTO GALLERY -->

				<!-- Test bootstrab -->

				<script id="photo-tmpl" type="text/x-jquery-tmpl">
						<a href="/kryptoncms/resources/img/\${album}/\${path}" title="\${path}" data-gallery="gallery"><img src="/kryptoncms/resources/img/\${album}/\${path}" width="100px" height="100px" data-gallery="gallery" /></a>
	</script>

				<div clas="container">
					<div class="row-fluid">
						<!-- Add this directly below div class="container"> -->
						<!-- Modal -->
						<div id="modal-gallery" class="modal modal-gallery hide fade"
							tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">×</button>
								<h3 id="myModalLabel">Photo Header</h3>
							</div>
							<div class="modal-body">
								<div class="modal-image"></div>
							</div>
							<div class="modal-footer">

								<a class="btn btn-info modal-prev"><i
									class="icon-arrow-left icon-white"></i> Previous</a> <a
									class="btn btn-success modal-play modal-slideshow"
									data-slideshow="5000"><i class="icon-play icon-white"></i>
									Slideshow</a> <a class="btn btn-primary modal-next">Next <i
									class="icon-arrow-right icon-white"></i></a>
							</div>
						</div>
					</div>
					<!-- 	<a href="#modal-galleryl" role="button" class="btn" data-toggle="modal">Show Photos</a> -->
					<div id="gallery" data-toggle="modal-gallery"
						data-target="#modal-gallery"></div>


					<script type="text/javascript">
						$(document)
								.ready(
										function() {
											$
													.ajax({
														"url" : "http://localhost:8080/kryptoncms/photo/Album/${album_number}",
														"type" : "GET",
														"dataType" : 'json',
														"cache" : false,
														"contentType" : "application/json",
														"success" : function(
																responseBody) {
															if (responseBody["successful"]) {
																var photos = $(
																		"#photo-tmpl")
																		.tmpl(
																				responseBody["queryResult"]);
																//photos.appendTo($("#modal-gallery > .modal-body > .modal-image"));
																photos
																		.appendTo($("#gallery"));
																//$("#modal-gallery > .modal-body > .modal-image:first-child").addClass("in");
															} else
																alert("Success Else");
														},
														"error" : function(
																responseBody) {
															alert("Error");
														}
													});
										});
					</script>
				</div>
				<!-- End Test bootstrab -->

				<!-- END PHOTO GALLERY -->
			</div>
		</div>
		<!--End Body-->
		<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
	</div>
</body>
</html>