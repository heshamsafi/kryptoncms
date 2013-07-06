<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<form id="article_form" action='<c:url value="/article/edit/${title}"/>' method="post" style="margin-bottom: 0px" data-validation-enable>
<div class="modal-header">
<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button> -->
      <h1>Article</h1>
</div>

	<div class="modal-body" >
	<div>
		<div class="control-group input-prepend input-append">
			<label for="title" class="add-on pull-left"> <span
				class="icon-asterisk"></span>Title
			</label> <input type="text" class="pull-left"
				id="title" name="title" value="${title}"
				placeholder="Title" data-required
<!-- 				data-describedby="username-description" data-description="username" -->
				data-pattern=".{3,}" /> 
				<label id="title-description" for="title" class="add-on"></label>
		</div>
	</div>
	<div>
		<div class="control-group input-prepend input-append">
			<label for="description" class="add-on pull-left"> <span
				class="icon-asterisk"></span>Description
			</label> <input id="description" class="pull-left"
				placeholder="Description" data-required
				type="text" name="description" value="${article.description}"
<!-- 				data-describedby="password-description" data-description="password" -->
				data-pattern=".{5,}" /> <label for="description"
				class="add-on" id="description-description"></label>
		</div>
		<div>
			
			<input type="text" name="id" value="${article.id}" disabled style="display:none"/><br />
		</div>
	</div>
	<div>
		<div class="control-group">
		    <textarea cols="80" class="ckeditor" name="content" rows="10">${article.content}</textarea>
		</div>
	</div>
	</div>
	<div class="modal-footer">
		<div class="btn-group pull-left">
			<button class="btn btn-primary" type="submit">Submit</button>
		</div>
	</div>
</form>
