<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<div class="page-header">
      <h1>Scaffold</h1>
</div>
<div class="btn-group">
  <a class="btn dropdown-toggle btn-primary" data-toggle="dropdown" href="javascript:void(0)" >
    Action
    <span class="caret"></span>
  </a>
  <ul class="dropdown-menu">
	  <li><a tabindex="-1" id="edit"  href="#genericModal" role="button" data-toggle="modal">Edit</a></li>
	  <li><a tabindex="-1" id="delete" href="javascript:void(0)">Delete</a></li>
	  <li><a tabindex="-1" id="create" href="#genericModal" role="button" data-toggle="modal">Add a New One</a></li>
  </ul>
</div>

<form id="scaffoldForm" className="${entityClassName}" action='<c:url value="/scaffold/${entityClassName}/" />' data-edit-action='<c:url value="/form/${entityClassName}/" />' method="DELETE">
	<table class="table" class="scaffold" data-enable-selectable data-enable-pagination
										  data-pagination-page-size="${pageSize}" data-pagination-page-no="${pageNo}"
										  data-pagination-total-size="${totalSize}">
	<c:choose>
		<c:when test="${entities.size() gt 0}">
		<thead>
		<c:forEach var="entityField" items="${entityClassFields}">
			<th>
			${entityField.name}
			</th>
		</c:forEach>
		</thead>
		<tbody>
		<c:forEach var="entity" items="${entities}">
			<tr data-enable-context-menu data-entity-id="${entity.id}" >
			<c:forEach var="entityField" items="${entityClassFields}">
			<td data-attr="${entityField.name}">
			
		<%-- 	${entity[entityField.name].getClass()} --%>
		
				<c:choose>
					
					<c:when test="${entity[entityField.name].getClass().getSimpleName() eq 'LinkedHashSet'}">

						<c:url var="url" value="/scaffold" />
						<c:set var="url" value="${url}/${entityField.getGenericType().getActualTypeArguments()[0].simpleName}?ownerType=${entity.getClass().simpleName}&ownerId=${entity['id']}" />
						<a data-ajax-enable href='${url}&pageSize=10'>
							See List
						</a>
					</c:when>
					<c:when test="${fn:contains(entity[entityField.name].getClass().name,'edu.asu.krypton.model')}">
						<c:url var="url" value="/scaffold" />
						<c:set var="url" value="${url}/${entity[entityField.name].getClass().simpleName}?id=${entity[entityField.name]['id']}" />
						<a data-placement="right" data-html="true" data-trigger="hover" data-ajax-enable href='${url}&pageSize=10' rel="popover" data-content="<h5><b>${entity[entityField.name]['id']}</b></h5>" data-title="<h4>Id</h4>">${entity[entityField.name].getClass().simpleName}</a>
					</c:when>			
					<c:otherwise>
						<c:set var="escapedVal" value="${fn:escapeXml(entity[entityField.name].toString())}"/> 
						<c:set var="cutoff" value="20"/>
						<c:choose>
							<c:when test="${escapedVal eq '[]'}">
								None
							</c:when>
							<c:otherwise>
								<span data-trigger="hover" data-html="true" data-placement="right" data-content='<h5><b>${fn:escapeXml(escapedVal)}</b></h5>' data-title='<h4>${entityField.name}</h4>' rel="popover" >
									<c:choose>
										<c:when test="${cutoff < escapedVal.length()}">
											${escapedVal.substring(0,cutoff)}...
										</c:when>
										<c:otherwise>
											${escapedVal}
										</c:otherwise>
									</c:choose>
								</span>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</td>
			</c:forEach>
	
			</tr>
		</c:forEach>
			</tbody>
		</c:when>
		<c:otherwise>
			<h3 style="text-align:center">There Is Nothing Here</h3>
		</c:otherwise>
	</c:choose>
	</table>
</form>

<script type="text/x-jquery-tmpl" id="scaffold-row-tmpl">
	<tr data-enable-context-menu data-entity-id="\${owner.id}" >
		{{each tds}}
			<td data-attr="\${$value.name}">
			{{if $value.type == "collection"}}
			<a data-ajax-enable 
				href='\${DOMAIN_CONFIGURATIONS.BASE_URL}/scaffold/\${$value.type}?ownerType=\${owner.type}&ownerId=\${owner.id}&pageSize=10'>
				See List
			</a>
			{{else $value.type == "model"}}
			<a data-placement="right" data-html="true" data-trigger="hover" data-ajax-enable href='\${DOMAIN_CONFIGURATIONS.BASE_URL}/\${$value.name}?id=\${$value.value.id}&pageSize=10' rel="popover" data-content="<h5><b>\${$value.value.id}</b></h5>" data-title="<h4>Id</h4>">\${$value.type}</a>
			{{else}}
			<span data-trigger="hover" data-html="true" data-placement="right" data-content='<h5><b>\${$value.value}</b></h5>' data-title='<h4>\${$value.name}</h4>' rel="popover" >
				\${$value.value}
			</span>
			{{/if}}
		{{/each}}
	</tr>
</script>
