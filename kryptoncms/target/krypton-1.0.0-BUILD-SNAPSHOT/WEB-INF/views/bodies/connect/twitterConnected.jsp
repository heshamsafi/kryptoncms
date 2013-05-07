<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Twitter Connected</h1>
</div>
<form id="disconnect" method="post">
	<button type="submit">Disconnect</button>
	<input type="hidden" name="_method" value="delete" />
</form>
