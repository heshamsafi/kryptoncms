<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="text/javascript">
	var DOMAIN_CONFIGURATIONS = {
			BASE_URL : "<c:url value='/' />"
	};
	var MESSAGES = { };
    <c:forEach var="message" items="${messages}">
    	MESSAGES["${message.key}"] = "${message.value}";
  	</c:forEach>
  	//var length = 0;
  	//for(key in LOCALE) length++;
  	//alert(length);
</script>

<script type="text/javascript" src="${pageScope.jqueryJavascriptUrl}"></script>
<script type="text/javascript" src="${pageScope.jqueryTmplJavascriptUrl}"></script>
<script type="text/javascript" src="${pageScope.MootoolsBase}"></script>
<script type="text/javascript" src="${pageScope.JQueryClassMutators}"></script>
<script type="text/javascript" src="${pageScope.jqueryAtmosphereUrl}"></script>
<script type="text/javascript" src="${pageScope.bootstrapUrl}"></script>
<script type="text/javascript" src="${pageScope.jqueryCookie}"></script>
<script type="text/javascript" src="${pageScope.jqueryValidation}"></script>

<!-- Message proxies -->
<script type="text/javascript" src="${pageScope.ChatMessage_class}" ></script>

<script type="text/javascript" src="${pageScope.Membership_class}"></script>
<script type="text/javascript" src="${pageScope.Notifier_class}"></script>
<script type="text/javascript" src="${pageScope.NavigationMenu_class}"></script>
<script type="text/javascript" src="${pageScope.DataToggleButton_class}"></script>
<script type="text/javascript" src="${pageScope.SocketHandler_class}"></script>
<script type="text/javascript" src="${pageScope.Chatter_class}"></script>
<script type="text/javascript" src="${pageScope.Main}"></script>

<link rel="stylesheet" href="${pageScope.bootstrapCssUrl}" />
<link rel="stylesheet" href="${pageScope.bootstrapResponsiveCssUrl}" />
<link rel="stylesheet" href="${pageScope.main_style}" />
