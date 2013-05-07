<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Google Connect</h1>
</div>
<form action="<c:url value="/connect/google" />" method="POST">
	<input type="hidden" name="scope" value="https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo#email https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/tasks https://www-opensocial.googleusercontent.com/api/people https://www.googleapis.com/auth/drive" />		    
	<div class="formInfo">
		<p>You aren't connected to Google yet.</p>
	</div>
	<p>
		<button type="submit">
			Google Connect
		</button>
	</p>
</form>