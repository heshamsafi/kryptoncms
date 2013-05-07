<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
<head>

<title>Welcome to Spring Web MVC - Atmosphere Sample</title>

<%@ include file="/WEB-INF/views/includes/common_head.jsp"%>
<style type="text/css">
#name {
	width: 80px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#trans").click(function() {
			$.get("<c:url value='/translate/tr' />", {
				fromText : $("#from").val(),
				fromLang : $("select.from>option:selected").html(),
				toLang : $("select.to>option:selected").html()
			}, function(data) {
				$("#to").text(data);
			});
		});
	});
</script>
</head>
<body style="margin: 10px 10px 10px 10px;">
	<div class="container-fluid">
		<%@ include file="/WEB-INF/views/includes/header.jsp"%>
		<!--Body-->
		<div class="row-fluid">
			<%@ include file="/WEB-INF/views/includes/nav.jsp"%>
			<div class="span9 well well-large">
				<!-- BEGIN TRANSLATE -->
				<textarea rows="3" cols="100" id="from" style="width: 516px;"></textarea>
				<br>
				<div class="well" id="to"></div>
				<form class="form-inline" onsubmit="return false;">
					<select class="from">
						<option>ARABIC</option>
						<option>BULGARIAN</option>
						<option>CATALAN</option>
						<option>CHINESE_SIMPLIFIED</option>
						<option>CHINESE_TRADITIONAL</option>
						<option>CZECH</option>
						<option>DANISH</option>
						<option>DUTCH</option>
						<option>ENGLISH</option>
						<option>ESTONIAN</option>
						<option>FINNISH</option>
						<option>FRENCH</option>
						<option>GERMAN</option>
						<option>GREEK</option>
						<option>HAITIAN_CREOLE</option>
						<option>HEBREW</option>
						<option>HINDI</option>
						<option>HMONG_DAW</option>
						<option>HUNGARIAN</option>
						<option>INDONESIAN</option>
						<option>ITALIAN</option>
						<option>JAPANESE</option>
						<option>KOREAN</option>
						<option>LATVIAN</option>
						<option>LITHUANIAN</option>
						<option>NORWEGIAN</option>
						<option>POLISH</option>
						<option>PORTUGUESE</option>
						<option>ROMANIAN</option>
						<option>RUSSIAN</option>
						<option>SLOVAK</option>
						<option>SLOVENIAN</option>
						<option>SPANISH</option>
						<option>SWEDISH</option>
						<option>THAI</option>
						<option>TURKISH</option>
						<option>UKRAINIAN</option>
						<option>VIETNAMESE</option>
					</select> <select class="to">
						<option>ARABIC</option>
						<option>BULGARIAN</option>
						<option>CATALAN</option>
						<option>CHINESE_SIMPLIFIED</option>
						<option>CHINESE_TRADITIONAL</option>
						<option>CZECH</option>
						<option>DANISH</option>
						<option>DUTCH</option>
						<option>ENGLISH</option>
						<option>ESTONIAN</option>
						<option>FINNISH</option>
						<option>FRENCH</option>
						<option>GERMAN</option>
						<option>GREEK</option>
						<option>HAITIAN_CREOLE</option>
						<option>HEBREW</option>
						<option>HINDI</option>
						<option>HMONG_DAW</option>
						<option>HUNGARIAN</option>
						<option>INDONESIAN</option>
						<option>ITALIAN</option>
						<option>JAPANESE</option>
						<option>KOREAN</option>
						<option>LATVIAN</option>
						<option>LITHUANIAN</option>
						<option>NORWEGIAN</option>
						<option>POLISH</option>
						<option>PORTUGUESE</option>
						<option>ROMANIAN</option>
						<option>RUSSIAN</option>
						<option>SLOVAK</option>
						<option>SLOVENIAN</option>
						<option>SPANISH</option>
						<option>SWEDISH</option>
						<option>THAI</option>
						<option>TURKISH</option>
						<option>UKRAINIAN</option>
						<option>VIETNAMESE</option>
					</select>
					<button id="trans" class="btn btn-primary">translate</button>
				</form>
				<!-- END TRANSLATE -->
			</div>
		</div>
		<!--End Body-->
		<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
	</div>
</body>
</html>