<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<h3>Your Google+ Profile</h3>
<p>
	Hello,
	<c:out value="${profile.firstName}" />
	!
</p>

<dl>
	<dt>Profile ID</dt>
	<dd>
		<a href="${profile.link}" target="_blank">${profile.id}</a>
	</dd>

	<dt>E-Mail</dt>
	<dd>${profile.email}</dd>

	<dt>Display Name</dt>
	<dd>${profile.name}</dd>

	<dt>First Name</dt>
	<dd>${profile.firstName}</dd>

	<dt>Last Name</dt>
	<dd>${profile.lastName}</dd>

	<dt>Gender</dt>
	<dd>${profile.gender}</dd>

	<dt>Locale</dt>
	<dd>${profile.locale}</dd>

</dl>
<img src="${profile.profilePictureUrl}" />
<c:url value="/connect/facebook" var="disconnectUrl" />
<form id="disconnect" action="${disconnectUrl}" method="post">
	<button type="submit">Disconnect from Facebook</button>
	<input type="hidden" name="_method" value="delete" />
</form>
