<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="text/javascript">
	var DOMAIN_CONFIGURATIONS = {
			BASE_URL : "<c:url value='/'  />"
	};
</script>
<script id="comments-tmpl" type="text/x-jquery-tmpl">
	<div class="row-fluid" >
        <div class="span12 well" data-commentable-id="\${id}" data-commentable-type="Comment" style="background-color:rgb(195, 202, 221)">
        	<dl>
        		<dt data-username>\${username}</dt>
        		<dd class="muted" data-content><%--\${id}---%>\${content}</dd>
        	</dl>
			<a href="javascript:void(0)" data-reply-button>Reply</a>
			<textarea class="hide" data-reply-input placeholder="Enter Your Message Here"></textarea>

			
			<button data-show-replies-btn class="btn dropdown-toggle pull-right <%--dropup--%>" {{if noOfReplies <= 0}}disabled="disabled"{{/if}} >
				<span class="annotate">See Replies(</span>
				<span class="annotate" data-show-replies-text>\${noOfReplies}</span>
				<span class="annotate">)</span>
				<span class="annotate hide">Hide Replies</span>
				<span class="caret"></span>
			</button>
			<div data-server-service-prefix-url="<c:url value="/comments/" />">
				<div class="span9 well well-large hide"
	    		    data-enable-comments                              
	    		    data-commentable-type="Comment"            
	    		    data-commentable-id="\${id}"
					data-comment-containter
					>
		    	</div>
		    </div>
			
        </div>
	</div>
</script>

<link rel="stylesheet" href="${pageScope.bootstrapCssUrl}" />
<link rel="stylesheet" href="${pageScope.colorpickerCss}" />
<link rel="stylesheet" href="${pageScope.bootstrapResponsiveCssUrl}">
<link rel="stylesheet" href="${pageScope.main_style}" />

<!-- JS facebook SDK -->
<script>
// (function(d, s, id) { 
//   var js, fjs = d.getElementsByTagName(s)[0];
//   if (d.getElementById(id)) ret<script>urn;
//   js = d.createElement(s); js.id = id;
//   js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=318851281514290";
//   fjs.parentNode.insertBefore(js, fjs);
// }(document, 'script', 'facebook-jssdk'));
</script>


<!-- <script -->
<!-- 	src="http://platform.twitter.com/anywhere.js?id=7yWLgCOuQhIpPyffm0o2Vg&v=1" -->
<!-- 	type="text/javascript"></script> -->
<script type="text/javascript">
//TODO: this code snippet was accidentally capitalized ... look in the source control versions for the original
// 	TWTTR.ANYWHERE(FUNCTION(T) {
// 		T(".FEED").LINKIFYUSERS();
// 	});
</script>

<script id="notification-templ" type="text/x-jquery-tmpl">
	<div class="alert \${type}" style="position:fixed;width:80%;margin-left:10%;margin-right:auto">
		<button type="button" data-dismiss="alert" class="close">&times;</button>
		<p style="text-align:center" ><strong>\${msg}</strong></p>
	</div>
</script>

<script id="pagination-templ" type="text/x-jquery-tmpl">
{{if pages.length>1}}
	<div class="pagination pagination-large" align="center">
		<ul>
		  <li class="disabled"><a data-ajax-enable href="#">&laquo;</a></li>
		  {{each pages}}
		  	<li {{if active}} class="active" {{/if}}><a data-ajax-enable href="\${link}">\${caption}</a></li>
		  {{/each}}
		  <li class="disabled"><a data-ajax-enable href="#">&raquo;</a></li>
		</ul>
	</div>
{{/if}}
</script>

<link rel="stylesheet" href="${pageScope.bootstrapCssUrl}" />
<link rel="stylesheet" href="${pageScope.bootstrapResponsiveCssUrl}">
<link rel="stylesheet" href="${pageScope.bootstrap_image_gallery}">
<link rel="stylesheet" href='<c:url value="/resources/css/jquery.fileupload-ui.css" />'>
<link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.smoothness.css" />" />
<noscript><link rel="stylesheet" href='<c:url value="/resources/css/jquery.fileupload-ui-noscript.css"/>'></noscript>
<script data-main="${pageScope.Main}" src="${pageScope.requireJqueryJs}" ></script>