define([
        "jquery",
        "libraries/mootools-base"
        ]
,function(
		$,
		Mootools){
	var ThemeManager = new Mootools.Class({
		initialize : function(){
			alert("ThemeManager initialize");
			$("#sendVars").unbind("click").click(function(){
//				alert("submit vars");
				console.log("submit vars");
//				console.log("blue:" + $("#blue").val());
				vars = {};//"{";
				if($("#blue").val()) vars["\"@blue\""] = "#" + $("#blue").val();
				if($("#green").val()) vars["\"@green\""] = "#" + $("#green").val();
				if($("#red").val()) vars["\"@red\""] = "#" + $("#red").val();
				if($("#yellow").val()) vars["\"@yellow\""] = "#" + $("#yellow").val();
				if($("#orange").val()) vars["\"@orange\""] = "#" + $("#orange").val();
				if($("#pink").val()) vars["\"@pink\""] = "#" + $("#pink").val();
				if($("#purple").val()) vars["\"@purple\""] = "#" + $("#purple").val();
				
				if($("#warningText").val()) vars["\"@warningText\""] = "#" + $("#warningText").val();
				if($("#warningBackground").val()) vars["\"@warningBackground\""] = "#" + $("#warningBackground").val();
				
				if($("#errorText").val()) vars["\"@errorText\""] = "#" + $("#errorText").val();
				if($("#errorBackground").val()) vars["\"@errorBackground\""] = "#" + $("#errorBackground").val();

				if($("#successText").val()) vars["\"@successText\""] = "#" + $("#successText").val();
				if($("#successBackground").val()) vars["\"@successBackground\""] = "#" + $("#successBackground").val();
				
				if($("#infoText").val()) vars["\"@infoText\""] = "#" + $("#infoText").val();
				if($("#infoBackground").val()) vars["\"@infoBackground\""] = "#" + $("#infoBackground").val();
				
				if($("#navbarBackground").val()) vars["\"@navbarBackground\""] = "#" + $("#navbarBackground").val();
				if($("#navbarBackgroundHighlight").val()) vars["\"@navbarBackgroundHighlight\""] = "#" + $("#navbarBackgroundHighlight").val();
				
				if($("#navbarText").val()) vars["\"@navbarText\""] = "#" + $("#navbarText").val();
				if($("#navbarBrandColor").val()) vars["\"@navbarBrandColor\""] = "#" + $("#navbarBrandColor").val();
				
				if($("#navbarLinkColor").val()) vars["\"@navbarLinkColor\""] = "#" + $("#navbarLinkColor").val();
				if($("#navbarLinkColorHover").val()) vars["\"@navbarLinkColorHover\""] = "#" + $("#navbarLinkColorHover").val();
				if($("#navbarLinkColorActive").val()) vars["\"@navbarLinkColorActive\""] = "#" + $("#navbarLinkColorActive").val();
				
				if($("#navbarLinkBackgroundHover").val()) vars["\"@navbarLinkBackgroundHover\""] = "#" + $("#navbarLinkBackgroundHover").val();
				if($("#navbarLinkBackgroundActive").val()) vars["\"@navbarLinkBackgroundActive\""] = "#" + $("#navbarLinkBackgroundActive").val();
				
				if($("#navbarSearchBackground").val()) vars["\"@navbarSearchBackground\""] = "#" + $("#navbarSearchBackground").val();
				if($("#navbarSearchBackgroundFocus").val()) vars["\"@navbarSearchBackgroundFocus\""] = "#" + $("#navbarSearchBackgroundFocus").val();
				
				if($("#navbarSearchBorder").val()) vars["\"@navbarSearchBorder\""] = "#" + $("#navbarSearchBorder").val();
				if($("#navbarSearchPlaceholderColor").val()) vars["\"@navbarSearchPlaceholderColor\""] = "#" + $("#navbarSearchPlaceholderColor").val();

//				vars += "}";
				sliderValue = $("#slider-range-min").slider( "value" );
				console.log("vars length = " + Object.keys(vars).length);
				console.log(vars);
				console.log("slider:" + sliderValue);
				if(Object.keys(vars).length > 0){
					$.ajax({
						"url" : "http://localhost:8080/kryptoncms/themes",
						"type" : "POST",
						"data" : {"vars": vars},
						"success" : function(responseBody) {
							alert("submit vars success");
							console.log("submit vars success");
						},
						"error" : function(responseBody) {
							console.log(responseBody);
							alert("submit vars error");
							console.log("submit vars error");
						}
					});
				}
			});
		}
	});
	return ThemeManager;
});
