<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>LinkedIn Profile</h1>
</div>

<p>
	Hello,
	<c:out value="${profile.firstName}" />
	!
</p>
<img src="<c:out value="${profile.profilePictureUrl}"/>" />
<dl>
	<dt>LinkedIn ID:</dt>
	<dd>
		<a href="<c:out value="${profile.publicProfileUrl}"/>" target="_blank"><c:out
				value="${profile.id}" /></a>
	</dd>
	<dt>Headline:</dt>
	<dd>
		<c:out value="${profile.headline}" />
	</dd>
	<dt>Industry:</dt>
	<dd>
		<c:out value="${profile.industry}" />
	</dd>
	<dt>Summary:</dt>
	<dd>
		<c:out value="${profile.summary}" />
	</dd>
</dl>

<c:url value="/connect/linkedin" var="disconnectUrl" />
<form id="disconnect" action="${disconnectUrl}" method="post">
	<button type="submit">Disconnect from LinkedIn</button>
	<input type="hidden" name="_method" value="delete" />
</form>