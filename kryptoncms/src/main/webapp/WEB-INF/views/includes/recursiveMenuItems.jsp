<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

	<li 
<c:choose>
	<c:when test="${item.menuItems.size() gt 0 and item.parentId ne null}">class="dropdown-submenu"</c:when>
	<c:when test="${item.menuItems.size() gt 0 and item.parentId eq null}">class="dropdown"</c:when>
</c:choose>
	 >

		<a 
			<c:choose>
				<c:when test="${item.menuItems.size() gt 0}">href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown"</c:when>
				<c:otherwise>data-ajax-enable</c:otherwise>
			</c:choose>			
			 href='<c:url value="${item.url}"  />' data-menu-order="${item.order}" data-menu-id="${item.id}" id="${item.id}">
			${item.name}
			<c:if test="${item.menuItems.size() gt 0 and item.parentId eq null}"><b class="caret"></b></c:if>
		</a>
		<c:if test="${item.menuItems.size() gt 0}">
			<ul class="dropdown-menu">
				<c:forEach items="${item.menuItems}" var="item">
				    <c:set var="item" scope="request" value="${item}" />
					<jsp:include page="/WEB-INF/views/includes/recursiveMenuItems.jsp" />
					<c:set var="item" scope="request" value="${null}" />
				</c:forEach>
			</ul>
		</c:if>
	</li>

<%--     <jsp:include page="/WEB-INF/views/includes/file_uploader.jsp" > --%>
<%--     	<jsp:param name="upload_path" value="${controller_upload_action}"/> --%>
<%--     </jsp:include> --%>