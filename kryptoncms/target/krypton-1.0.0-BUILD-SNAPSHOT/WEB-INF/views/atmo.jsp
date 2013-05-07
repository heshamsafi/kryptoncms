<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<html>
<head>
	<title>Welcome to Spring Web MVC - Atmosphere Sample</title>
	<%@ include file="/WEB-INF/views/includes/common_head.jsp"%>
	<style type="text/css">
		#name {
			width: 80px;
		}
	</style>
</head>
<body style="margin: 10px 10px 10px 10px;">
	<div class="container-fluid">
		<%@ include file="/WEB-INF/views/includes/header.jsp"%>
		<!--Body-->
		<div class="row-fluid">
			<%@ include file="/WEB-INF/views/includes/nav.jsp"%>
			<div class="span9 well well-large">
				<!-- BEGIN CHAT -->
				<section
					style="height: 300px; overflow-y: scroll; margin-bottom: 10px"
					id="data" class="well"></section>
				<form class="form-inline">
					<input type="text" id="name" placeholder="name" /><input
						type="text" id="message" placeholder="message" />
					<button id="btn" style="width: 163px;" class="btn btn-primary"
						onclick="return false">send</button>
				</form>
				<!-- END CHAT -->
			</div>
		</div>
		<!--End Body-->
		<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
	</div>
</body>
</html>
