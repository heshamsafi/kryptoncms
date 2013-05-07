<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Your Twitter Profile</h1>
</div>

<p>
	Hello,
	<c:out value="${profile.name}" />
	!
</p>
<img src="<c:out value="${profile.profileImageUrl}"/>" />
<dl>
	<dt>Twitter ID:</dt>
	<dd>
		<c:out value="${profile.id}" />
	</dd>
	<dt>Screen name:</dt>
	<dd>
		<a href="<c:out value="${profile.profileUrl}"/>" target="_blank"><c:out
				value="${profile.screenName}" /></a>
	</dd>
	<dt>Description:</dt>
	<dd>
		<c:out value="${profile.description}" />
	</dd>
	<dt>Location:</dt>
	<dd>
		<c:out value="${profile.location}" />
	</dd>
	<dt>URL:</dt>
	<dd>
		<a href="<c:out value="${profile.url}"/>"><c:out
				value="${profile.url}" /></a>
	</dd>
</dl>

<c:url value="/connect/twitter" var="disconnectUrl" />
<form id="disconnect" action="${disconnectUrl}" method="post">
	<button type="submit">Disconnect from Twitter</button>
	<input type="hidden" name="_method" value="delete" />
</form>