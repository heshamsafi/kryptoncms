<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Error</h1>
</div>

<h3 class="text-error" >
	<fmt:message key='message.error.${errorCode}' var="errorMessage" />
	<c:set var="invalidString" value="???message.error.${errorCode}???" />
    ${fn:replace(errorMessage,invalidString, "Are You Trying To Hack Us, Well Quit it ! :)")}
</h3>