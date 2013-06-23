<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<div class="page-header" title="${article.description}">
      <h1>${article.title}</h1>
</div>
<div class="row-fluid">
      <c:if test="${prevId ne null}">
      	<a class="carousel-control left" href='<c:url value="/article/${prevId}"/>'  data-ajax-enable style="position:fixed" >&lsaquo;</a>
      </c:if>
      <c:if test="${nextId ne null}">
		<a class="carousel-control right" href='<c:url value="/article/${nextId}"/>' data-ajax-enable style="position:fixed" >&rsaquo;</a>
      </c:if>
	${article.content}
</div>
<c:choose>
	<c:when test="${article.commentMode eq 'custom'}">
			<div class="row-fluid" data-server-service-prefix-url="<c:url value="/comments/" />">
				<div class="span9 well well-large" data-enable-comments
					data-commentable-type="Article" data-commentable-id="${article.id}" data-comment-containter>
					<a href="javascript:void(0)" data-reply-button>Reply</a>
					<textarea class="hide" data-reply-input placeholder="Enter Your Message Here"></textarea>
				</div>
			</div>
	</c:when>
	<c:when test="${article.commentMode eq 'facebook'}">
		<c:url var="relativeCommentableUrl" value="/article/${article.id}" />
		<div class="span9 well well-large">
			<div class="fb-like pull-right" data-href="http://www.facebook.com"
				data-send="false" data-layout="box_count" data-width="450"
				data-show-faces="true"></div>
			<div class="fb-comments" data-href="${relativeCommentableUrl}"
				data-width="470" data-num-posts="10"></div>
		</div>
	</c:when>
		<c:when test="${article.commentMode eq 'facebook'}">
		<c:url var="relativeCommentableUrl" value="/article/${article.id}" />
		<div class="span9 well well-large">
			<div class="fb-like pull-right" data-href="http://www.facebook.com" data-send="false" data-layout="box_count" data-width="700" data-show-faces="true"></div>
			<div class="fb-comments" data-href="${relativeCommentableUrl}" data-width="1900" data-num-posts="10"></div>
		</div>
	</c:when>
	<c:otherwise></c:otherwise>
</c:choose>