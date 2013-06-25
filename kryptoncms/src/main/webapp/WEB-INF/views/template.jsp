<!DOCTYPE html>
<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<html lang="en">
<head>
<title>Home</title>
<%@include file="/WEB-INF/views/includes/common_head.jsp"%>
</head>
<body style="margin: 10px 10px 10px 10px;">
	<c:import url="/navigation/menu/user" />
	<%-- <%@ include file="/WEB-INF/views/includes/nav.jsp"%> --%>
	<!-- <div id="trig" class="well">Admin Navigation</div> -->
	<div class="row-fluid">
		<%-- 	<%@ include file="/WEB-INF/views/includes/admin_nav.jsp"%> --%>
		<c:import url="/navigation/menu/admin" />

		<div id="bod"
			class="container-fluid well well-large inner-body span12">
			<jsp:include page="${view_prefix_bodies}${view_name}${view_suffix}" />
		</div>
	</div>
	<hr>
	<footer>
		<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
	</footer>

	<div id="myModal1" class="modal hide  fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<%@ include file="/WEB-INF/views/bodies/login.jsp"%>
	</div>
	<div id="myModal2" class="modal hide  fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<%@ include file="/WEB-INF/views/bodies/register.jsp"%>
	</div>
	<div id="chat" class="modal hide  fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		style="width: 1200px; margin: 0 0 0 -600px;">
		<c:import url="/chat" />
	</div>
	<div id="genericModal" class="modal hide  fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<h1>Loading ....</h1>
	</div>
</body>
</html>