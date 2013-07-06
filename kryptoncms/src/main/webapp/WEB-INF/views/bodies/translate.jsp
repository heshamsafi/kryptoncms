<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Translate</h1>
</div>

<form method="GET" action="<c:url value='/translate/tr' />" class="form-inline" onsubmit="return false;" data-generic-translation>
	<div class="well" id="to"></div>
	<textarea name="fromText" class="input-block-level" id="from"></textarea>
	<select name="fromLang" class="from"></select> 
	<select name="toLang" class="to"></select>
	<button id="trans" class="btn btn-primary">translate</button>
</form>
<!-- END TRANSLATE -->

<script id="options-templ" type="text/x-jquery-tmpl">
	<li><a tabindex="-1" href="#">\${this.data}</a></li>
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