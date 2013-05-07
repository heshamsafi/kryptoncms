<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<html lang="en">
<head>
	<%@ include file="/WEB-INF/views/includes/common_head.jsp"%>
	
	<script id="comments-tmpl" type="text/x-jquery-tmpl">
    	<div class="row-fluid" >
	        <div class="span12 well" data-commentable-id="\${id}" style="background-color:black">
	        	<dl>
	        		<dt data-username>\${username}</dt>
	        		<dd class="muted" data-content><%--\${id}---%>\${content}</dd>
	        	</dl>
				<a href="javascript:void(0)" data-reply-button>Reply</a>
				<textarea class="hide" data-reply-input placeholder="Enter Your Message Here"></textarea>

				{{if noOfReplies > 0}}
				<button class="btn dropdown-toggle pull-right <%--dropup--%>" >
					<span class="annotate" >See Replies(\${noOfReplies})</span>
					<span class="annotate hide">Hide Replies</span>
					<span class="caret"></span>
				</button>
				<div data-server-service-prefix-url="<c:url value="/comments/" />">
					<div class="span9 well well-large hide"
		    		    data-enable-comments                              
		    		    data-commentable-type="CommentComment"            
		    		    data-commentable-id="\${id}"
						>
			    	</div>
			    </div>
				{{/if}}
	        </div>
		</div>
	</script>
</head>
<body>
	<div class="container-fluid">
		<%@ include file="/WEB-INF/views/includes/header.jsp" %>
		<!--Body-->
		<div class="row-fluid">
			<%@ include file="/WEB-INF/views/includes/nav.jsp"%>
<%-- 			<form action="<c:url value="/comments/socket" />"> --%>
				<div class="span9 well well-large">
					<div data-server-service-prefix-url="<c:url value="/comments/" />">
						<div class="span9 well well-large"
							 data-enable-comments
							 data-commentable-type="ArticleComment"
							 data-commentable-id="1"				>
							 </div>
					</div>
				</div>
<!-- 			</form> -->
		</div>
	</div>
		<!--End Body-->
		<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
</html>