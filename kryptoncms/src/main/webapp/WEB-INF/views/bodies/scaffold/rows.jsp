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
	  <li><a tabindex="-1" href="#edit-add-modal" role="button" data-toggle="modal">Edit</a></li>
	  <li><a tabindex="-1" id="delete" href="javascript:void(0)">Delete</a></li>
  </ul>
</div>

<form action='<c:url value="/scaffold/${entityClassName}/" />' method="DELETE">
	<table class="table" class="scaffold" data-enable-selectable>
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
						<a data-ajax-enable href='${url}'>
							See List
						</a>
					</c:when>
					<c:when test="${fn:contains(entity[entityField.name].getClass().name,'edu.asu.krypton.model')}">
						<c:url var="url" value="/scaffold" />
						<c:set var="url" value="${url}/${entity[entityField.name].getClass().simpleName}?id=${entity[entityField.name]['id']}" />
						<a data-ajax-enable href='${url}'>${entity[entityField.name].getClass().simpleName}#${entity[entityField.name]['id']}</a>
					</c:when>			
					<c:otherwise>
						${entity[entityField.name]}
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
<div id="edit-add-modal" class="modal hide  fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<h1>
	Coming Soon ...
	As soon as Toba finishes form generation i will be useful :)
	</h1>
</div>
<%-- <script id="context-menu-templ" type="text/x-jquery-tmpl">
    <ul id="dropdown-menu" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu" style="position:absolute;top:\${offset.top}px;left:\${offset.left}px;">
	{{each items}}
      <li {{if disabled}}class="disabled"{{/if}} \${dataAttributes} ><a tabindex="-1" href="\${href}">\${text}</a></li>
	{{/each}}
    </ul>	
</script> --%>