<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<div id="admin_nav" class="well span0 hide" style="max-width: 340px; padding: 8px 0;">
		<a href="javascript:void(0)" id="pin">
			<span >Pin</span>
			<span style="display:none">Unpin</span>
		</a>
      <ul class="nav nav-pills nav-stacked" data-menu-submit-url='<c:url value="/menu"  />'>
      
<%--       ${items} --%>
      <c:forEach items="${items}" var="item">
      	<li><a data-ajax-enable href='<c:url value="${item.url}"  />' data-menu-order="${item.order}" data-menu-id="${item.id}" id="${item.id}">${item.name}</a></li>
      </c:forEach>
<!--       <li class="exclude" ><a tabindex="-1" id="addMenu" href="#genericModal" role="button" data-toggle="modal">+ add Menu Item</a></li> -->

      </ul>
</div>