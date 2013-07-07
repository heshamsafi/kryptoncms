<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<form method="POST" action="<c:url value='/translate/tr' />" class="form-inline" onsubmit="return false;" data-generic-translation>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h1>Translate</h1>
	</div>
	<div class="modal-body">
		<div class="well" id="to"></div>
		<textarea name="fromText" class="input-block-level" id="from"></textarea>
		<select name="fromLang" class="from"></select> 
		<select name="toLang" class="to"></select>
	</div>
	<div class="modal-footer">
		<button id="trans" class="btn btn-primary">translate</button>
	</div>
</form>
<!-- END TRANSLATE -->

<script id="options-templ" type="text/x-jquery-tmpl">
	<option value="\${this.data}">\${this.data}</option>
</script>
<script type="text/javascript">
// 	$(document).ready(function() {
// 		$("#trans").click(function() {
// 			$.get("", {
// 				fromText : $("#from").val(),
// 				fromLang : $("select.from>option:selected").html(),
// 				toLang : $("select.to>option:selected").html()
// 			}, function(data) {
// 				$("#to").text(data);
// 			});
// 		});
// 	});
</script>