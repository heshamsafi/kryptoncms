<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
	<h1>Album Gallery Demo</h1>
</div>
<br>
<!-- BEGIN ALBUM GALLERY -->
<script id="album-tmpl" type="text/x-jquery-tmpl">
	<a href="/kryptoncms/photo/photo?album_number=\${id}"><button class="btn">\${title}</button></a>
</script>
<div class="container">
	<div class="row-fluid">
		<div id="albums" data-user-albums></div>
	</div>
</div>
<!-- END ALBUM GALLERY -->