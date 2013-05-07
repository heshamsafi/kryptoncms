<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Google Connected</h1>
</div>
<form id="disconnect" method="post">
	<div class="formInfo">
		<p>
			You are connected
		</p>		
	</div>
	<button type="submit">Disconnect</button>	
	<input type="hidden" name="_method" value="delete" />
</form>