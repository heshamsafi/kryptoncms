<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
	<h1>Scaffold</h1>
</div>

<table class="table" class="scaffold">
	<tbody>
		<c:forEach var="entity" items="${entities}">
			<tr>
				<td><a data-ajax-enable
					href='<c:url value="/scaffold/${entity}" />'>
						<div align="center">${entity}</div>
				</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>