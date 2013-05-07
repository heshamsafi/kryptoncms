<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Facebook Feed</h1>
</div>

<form method="POST" action="<c:url value="/facebook/feed" />">
	<p>Post to your Facebook wall:
	<p>
		<textarea class="input-block-level" id="message" name="message"
			rows="2" cols="60"></textarea>
		<br />
		<button type="submit"
			class="btn btn-primary btn-large btn-block input-block-level">Post</button>
</form>

<div class="feed">
	<ul class="feedList" style="list-style: none;">
		<c:forEach items="${feed}" var="post">
			<li class="post">
				<div class="span9 well well-large">
					<c:if test="${not empty post.picture}">
						<dl>
							<dt>Picture</dt>
							<dd>
								<img src="<c:out value="${post.picture}"/>" align="top" />
							</dd>
						</dl>
					</c:if>
					<dl>
						<dt>Message</dt>
						<dd>
							<c:out value="${post.message}" />
						</dd>
					</dl>
					<dl>
						<dt>Name</dt>
						<dd>
							<c:out value="${post.name}" />
						</dd>
					</dl>
				</div>
			</li>
		</c:forEach>
	</ul>
</div>