<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Facebook Connect</h1>
</div>
<form action="<c:url value="/connect/facebook" />" method="POST">
	<input type="hidden" name="scope" value="publish_stream,user_photos,offline_access" />
	<p><button type="submit"><img src="<c:url value="/resources/social/facebook/connect_light_medium_short.gif" />"/></button></p>
</form>