<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<html>
<head>
<title>Welcome to Spring Web MVC - Atmosphere Sample</title>
	<%@ include file="/WEB-INF/views/includes/common_head.jsp"%>
	<style type="text/css">
	#name {
		width: 80px;
	}
	</style>
	<script type="text/x-jquery-tmpl" id="destination-pallette-item-tmpl">
	    <div class="alert alert-info" data-username="\${destination}">
	    	<a onclick="javascript:void(0)" class="close" data-dismiss="alert">&times;</a>
	    	<span>\${destination}</span>
	    </div>
	</script>
	<script type="text/x-jquery-tmpl" id="chat-message-tmpl">
		<span style="font-size:20px"><b>\${source}</b> : \${body}<br /></span>
	</script>
</head>
<body style="margin: 10px 10px 10px 10px;">
	<div class="container-fluid">
		<%@ include file="/WEB-INF/views/includes/header.jsp"%>
		<!--Body-->
		<div class="row-fluid">
			<%@ include file="/WEB-INF/views/includes/nav.jsp"%>
			<div class="span9 well well-large">
				<!-- BEGIN CHAT -->
				<div class="row-fluid">
					<div class="span12">
						<div class="row-fluid">
							<div class="span10">
								<div class="input-prepend input-block-level">
									<button class="btn pull-left disabled span2" type="button"
										id="destination_adder">Add</button>
									<input type="text" id="destination"
										placeholder="Someone To The Conversation"
										data-provide="typeahead" autocomplete="off" class="span10"
										data-source='[<c:forEach var="username" varStatus="status" items="${usernames}">"<c:out value="${username}" />"<c:if test="${!status.isLast()}">,</c:if></c:forEach>]'>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span10">
								<section style="height: 300px; overflow-y: scroll; margin-bottom: 10px"
									id="message_board" class="well"></section>
		
								<form id="chatter_form" action="<c:url value='/chat/echo' />">
									<textarea class="input-block-level" id="message" placeholder="Enter Your Message Here"></textarea>
									<button id="btn_submit"
										class="btn btn-primary btn-large btn-block disabled input-block-level" onclick="return false">send</button>
									<!-- END CHAT -->
								</form>
							</div>
							<div class="span2">
								<div class="pull-right" id="destinations_pallette" 
								<%-- style="height:300px;overflow-y:scroll" --%>
								>
										<div class="alert">
											<span>You</span>
										</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--End Body-->
		<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
	</div>
</body>
</html>
