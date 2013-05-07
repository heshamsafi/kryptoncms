<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Facebook Profile</h1>
</div>
<p>
	Hello,
	<c:out value="${profile.firstName}" />
	!
</p>
<dl>
	<dt>Facebook ID:</dt>
	<dd>
		<a
			href="javascript:window.open('http://www.facebook.com/${profile.id}','name','height=600,width=800')">
			<c:out value="${profile.id}" />
		</a>
	</dd>
	<dt>Name:</dt>
	<dd>
		<c:out value="${profile.name}" />
	</dd>
	<dt>Email:</dt>
	<dd>
		<c:out value="${email}" />
	</dd>
</dl>

<c:url value="/connect/facebook" var="disconnectUrl" />
<form id="disconnect" action="${disconnectUrl}" method="post">
	<button type="submit">Disconnect from Facebook</button>
	<input type="hidden" name="_method" value="delete" />
</form>
