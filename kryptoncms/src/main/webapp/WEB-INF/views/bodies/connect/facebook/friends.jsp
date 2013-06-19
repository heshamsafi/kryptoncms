<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Facebook Friends</h1>
</div>

<c:forEach items="${friends}" var="friend">
	<a
		href="javascript:window.open('${friend.link}','name','height=600,width=800')"
		target="new_window">
		<div class="span9 well well-large">
			<h3 class="pull-left">
				<c:out value="${friend.name}" />
			</h3>
			<img class="pull-right"
				src="http://graph.facebook.com/<c:out value="${friend.id}"/>/picture"
				align="middle" />
		</div>
	</a>
</c:forEach>