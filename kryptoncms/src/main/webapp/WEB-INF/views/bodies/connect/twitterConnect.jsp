<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Twitter Connect</h1>
</div>

<form action="<c:url value="/connect/twitter" />" method="POST" >
		<button type="submit">
			<img src="<c:url value="/resources/social/twitter/connect-with-twitter.png" />" />
		</button>
</form>