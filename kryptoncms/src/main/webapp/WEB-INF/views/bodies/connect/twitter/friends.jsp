<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Twitter Friends</h1>
</div>

<c:forEach items="${profiles}" var="profile">
	<a
		href="javascript:window.open('http://twitter.com/${profile.screenName}','name','height=600,width=800')"
		target="new_window">
		<div class="span9 well well-large">
			<h3 class="pull-left">
				${profile.screenName}
			</h3>
			<c:if test="${not empty profile.profileImageUrl}">
				<img class="pull-right"
					src="${profile.profileImageUrl}"
					align="middle" />
			</c:if>
		</div>
	</a>
</c:forEach>