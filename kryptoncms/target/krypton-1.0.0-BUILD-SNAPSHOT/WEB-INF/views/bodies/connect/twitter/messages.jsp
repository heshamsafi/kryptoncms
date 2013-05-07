<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>	Your Twitter ${dmListType} Messages</h1>
</div>

<c:url var="sendMessageUrl" value="/twitter/messages" />
<sf:form action="${sendMessageUrl}" method="post"
	modelAttribute="messageForm">
	<sf:input path="to" ></sf:input>
	<input type="text" class="pull-left" name="to"
				placeholder="To" />
	<br />
	<textarea name="text" class="input-block-level" placeholder="Enter Your Message Here"></textarea>
	<br />
	<button class="btn btn-primary btn-large btn-block disabled input-block-level" >Send</button>
</sf:form>

<hr />

<c:url var="messageBaseUrl" value="/membership/social/twitter/messages" />
<ul class="choices nav nav-pills">
	<li <c:if test="${mode eq 'inbox'}">class="active"</c:if>><a data-ajax-enable href="${messageBaseUrl}">Inbox</a></li>
	<li	<c:if test="${mode eq 'sent' }">class="active"</c:if>><a data-ajax-enable href="${messageBaseUrl}/sent">Sent</a></li>
</ul>

<div class="feed">
	<ul class="imagedList">
		<c:forEach items="${directMessages}" var="dm">
			<li class="imagedItem">
				<div class="image">
					<c:if test="${not empty dm.sender.profileImageUrl}">
						<img src="<c:out value="${dm.sender.profileImageUrl}"/>"
							align="left" />
					</c:if>
				</div>
				<div class="content">
					<strong><a
						href="http://twitter.com/<c:out value="${dm.sender.screenName}" />"><c:out
								value="${dm.sender.screenName}" /></a></strong><br /> <span
						class="dmRecipient">to <c:out
							value="${dm.recipient.screenName}" /></span><br />
					<c:out value="${dm.text}" />
					<br /> <span class="postTime"><c:out
							value="${dm.createdAt}" /></span>
				</div>
			</li>
		</c:forEach>
	</ul>
</div>