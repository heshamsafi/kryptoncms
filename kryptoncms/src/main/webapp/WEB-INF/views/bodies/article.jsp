<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<div class="page-header" title="${article.description}">
      <h1>${article.title}</h1>
</div>
<div class="row-fluid">
      <c:if test="${prevId ne 0}">
      	<a class="carousel-control left" href='<c:url value="/article/${prevId}"/>'  data-ajax-enable style="position:fixed" >&lsaquo;</a>
      </c:if>
      <c:if test="${nextId ne 0}">
		<a class="carousel-control right" href='<c:url value="/article/${nextId}"/>' data-ajax-enable style="position:fixed" >&rsaquo;</a>
      </c:if>
	${article.content}
</div>
<div class="row-fluid" data-server-service-prefix-url="<c:url value="/comments/" />">
	<div class="span9 well well-large" data-enable-comments
		data-commentable-type="Article" data-commentable-id="${article.id}" data-comment-containter>
		<a href="javascript:void(0)" data-reply-button>Reply</a>
		<textarea class="hide" data-reply-input placeholder="Enter Your Message Here"></textarea>
	</div>
</div>