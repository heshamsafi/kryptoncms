<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Comment Module</h1>
</div>

<c:set var="baseURL"
	value="${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
<c:url var="relativeCommentableUrl"
	value="/${commentableType}/${commentableId}" />
<div class="span9 well well-large">
	<div class="fb-like pull-right" data-href="http://www.facebook.com"
		data-send="false" data-layout="box_count" data-width="450"
		data-show-faces="true"></div>
	<div class="fb-comments" data-href="${relativeCommentableUrl}"
		data-width="470" data-num-posts="10"></div>
</div>
