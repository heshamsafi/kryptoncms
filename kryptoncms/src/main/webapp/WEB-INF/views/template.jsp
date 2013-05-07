<!DOCTYPE html>
<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<html lang="en">
<head>
	<title>Home</title>
	<%@include file="/WEB-INF/views/includes/common_head.jsp"%>
</head>
<body style="margin: 10px 10px 10px 10px;">
<%@ include file="/WEB-INF/views/includes/nav.jsp"%>
<!-- <div id="trig" class="well">Admin Navigation</div> -->
	<div class="row-fluid">
		<div id="admin_nav" class="well span0 hide" style="max-width: 340px; padding: 8px 0;">
			<a href="javascript:void(0)" id="pin">
				<span >Pin</span>
				<span style="display:none">Unpin</span>
			</a>
              <ul class="nav nav-pills nav-stacked">
<!--                 <li class="nav-header">List header</li> -->
                <li class="active"><a data-ajax-enable href='<c:url value="/scaffold" />'>Scaffold</a></li>
                <li><a href="components.html#">Library</a></li>
                <li><a href="components.html#">Applications</a></li>
                <li class="nav-header">Another list header</li>
                <li><a href="components.html#">Profile</a></li>
                <li><a href="components.html#">Settings</a></li>
<!--                 <li class="divider"></li> -->
                <li><a href="components.html#">Help</a></li>
              </ul>
        </div>

		<div id="bod" class="container-fluid well well-large inner-body span12" >
			<jsp:include page="${view_prefix_bodies}${view_name}${view_suffix}"  />
		</div>
	</div>
	<hr>
	<footer>
		<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
	</footer>
	
	<div id="myModal1" class="modal hide  fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <%@ include file="/WEB-INF/views/bodies/login.jsp"%>
	</div>
	<div id="myModal2" class="modal hide  fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <%@ include file="/WEB-INF/views/bodies/register.jsp"%>
	</div>

</body>
</html>