<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Comments Demo</h1>
</div>

<div data-server-service-prefix-url="<c:url value="/comments/" />">
	<div class="span9 well well-large" data-enable-comments
		data-commentable-type="Article" data-commentable-id="1" data-comment-containter>
		<a href="javascript:void(0)" data-reply-button>Reply</a>
		<textarea class="hide" data-reply-input placeholder="Enter Your Message Here"></textarea>
	</div>
</div>
