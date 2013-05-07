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
				<!-- BEGIN ALBUM GALLERY -->
				<script id="album-tmpl" type="text/x-jquery-tmpl">
	<a href="/kryptoncms/photo/photo?album_number=\${id}"><button class="btn">\${title}</button></a>
</script>
				<div class="container">
					<div class="row-fluid">
						<div id="albums"></div>
					</div>
				</div>

				<script type="text/javascript">
					$(document)
							.ready(
									function() {
										$
												.ajax({
													"url" : "http://localhost:8080/kryptoncms/photo/albums/",
													"type" : "GET",
													"dataType" : 'json',
													"cache" : false,
													"contentType" : "application/json",
													"success" : function(
															responseBody) {
														if (responseBody["successful"]) {
															//alert("Success");
															var albums = $(
																	"#album-tmpl")
																	.tmpl(
																			responseBody["queryResult"]);
															albums
																	.appendTo($("#albums"));
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


				<!-- END ALBUM GALLERY -->
			</div>
		</div>
		<!--End Body-->
		<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
	</div>
</body>
</html>