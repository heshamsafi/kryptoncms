<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<div class="page-header">
      <h1>Chat Demo</h1>
</div>
<div class="row-fluid">
	<div class="span10">
		<div class="input-prepend input-block-level">
			<button class="btn pull-left disabled span2" type="button"
				id="destination_adder">Add</button>
			<input type="text" id="destination"
				placeholder="Someone To The Conversation"
				data-provide="typeahead" autocomplete="off" class="span10"
				data-source='[<c:forEach var="username" varStatus="status" items="${usernames}">"<c:out value="${username}" />"<c:if test="${!status.last}">,</c:if></c:forEach>]'>
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
	
	<!-- scripts -->
	<script type="text/x-jquery-tmpl" id="destination-pallette-item-tmpl">
	    <div class="alert alert-info" data-username="\${destination}">
	    	<a onclick="javascript:void(0)" class="close" data-dismiss="alert">&times;</a>
	    	<span>\${destination}</span>
	    </div>
	</script>
	<script type="text/x-jquery-tmpl" id="chat-message-tmpl">
		<span style="font-size:20px"><b>\${source}</b> : \${body}<br /></span>
	</script>
	
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