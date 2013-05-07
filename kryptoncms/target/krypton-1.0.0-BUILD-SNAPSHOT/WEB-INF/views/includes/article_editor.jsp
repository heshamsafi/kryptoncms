<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%-- <form action='${param.article_edit_path}${id}' method="post"> --%>
<form action='' method="post" id="articleForm">
	<table>
	<tr>
		<td>
			title: <input type="text" id="title" name="title" value="${title}">
		</td>
		<td>
			<input type="hidden" name="id" value="${id}" id="Id">
		</td>
	</tr>
	<tr>
		<td>
			description: <input type="text"  name="description" value="${description}">
		</td>
	</tr>
	</table>
		<textarea cols="80" id="editor1" name="content" rows="10">
			<c:out value="${content}"/>
		</textarea>
		<script type="text/javascript">
            CKEDITOR.replace( 'editor1',{
            	 filebrowserBrowseUrl : '/browser/browse.php?type=Images',
           	  	 filebrowserUploadUrl : 'http://localhost:8080/kryptoncms/file/upload.Action'
            });
        </script>
        <input type="button" id="click" value="submit"/>
        <script type="text/javascript">
		$(document).ready(function() {
			$('#click').click(function(){
				function CKupdate(){
				    for ( instance in CKEDITOR.instances )
				        CKEDITOR.instances[instance].updateElement();
			    		$('textarea').trigger('keyup');
				}
				CKupdate();
				article = $("#articleForm").serialize();
				$.ajax({
				 	data: article,
					type: "POST",  
					url:'${param.article_edit_path}'+$('#Id').val(),
				 	success: function(id){
				 		$("#Id").attr('value',id);
				 		alert("success");	 
					}
				})	
			});
		});
	</script>
	</form>