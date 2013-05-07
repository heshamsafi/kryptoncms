<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Social Connection Status</h1>
</div>

<c:forEach var="providerId" items="${providerIds}">
	<c:set var="connections" value="${connectionMap[providerId]}" />
	<s:message code="${providerId}.displayName" var="providerDisplayName" />

	<div class="accountConnection">
		<s:message code="${providerId}.icon" var="iconUrl" />
		<s:message code="${providerId}.button" var="buttonUrl" />
<!-- 		<h4> -->
<%-- 			<img src="<c:url value="${iconUrl}" />" width="36" height="36" />${providerDisplayName} --%>
<!-- 		</h4> -->
		<c:url value="/connect/${providerId}" var="connectUrl" />

		<c:if test="${empty connections}">
			<form action="${connectUrl}" method="POST" class="connectForm">
				<div class="formInfo">You are not yet connected to ${providerId}
					${providerDisplayName}.</div>
				<button class="connectButton" type="submit">
					Connect
				</button>
			</form>
		</c:if>

		<c:if test="${not empty connections}">
			<form id="${providerId}Disconnect" method="post"
				action="${connectUrl}">
				<p>
					You are connected to ${providerDisplayName}
					${providerId}
					as <a href="${connectionMap[providerId][0].profileUrl}">${connectionMap[providerId][0].displayName}</a>.
				</p>
				<button class="disconnectButton" type="submit">Disconnect</button>
				<input type="hidden" name="_method" value="delete" />
			</form>
		</c:if>
	</div>
</c:forEach>