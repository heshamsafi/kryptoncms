<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<c:forEach items="${items}" var="item">
	<c:set var="item" scope="request" value="${item}" />
	<jsp:include page="/WEB-INF/views/includes/recursiveMenuItems.jsp" />
	<c:set var="item" scope="request" value="${null}" />
</c:forEach>