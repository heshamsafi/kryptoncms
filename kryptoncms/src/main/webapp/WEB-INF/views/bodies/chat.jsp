<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">x</button>
	<h1>Chat</h1>
</div>

<div id="chat-modal" class="modal-body" style="max-height: 800px;">
	<div class="tabbable tabs-left">
		<ul id="chatterConv" class="nav nav-tabs nav-pills">
			<li><a href="javascript:void(0)" class="add"
				data-toggle="tab">+ Add Conversation</a></li>

		</ul>

		<div class="tab-content">

		</div>

	</div>
</div>

<script type="text/x-jquery-tmpl" id="chat-tab-content">
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
    		<section
    			style="height: 300px; overflow-y: scroll; margin-bottom: 10px"
    			id="message_board" class="well">
				{{each messages}}
						<span style="font-size:20px"><b>\${$value.source.username}</b> : \${$value.message}<br /></span>
				{{/each}}
			</section>
    
    		<form id="chatter_form" action="<c:url value='/chat/echo' />">
    			<textarea class="input-block-level" id="message"
    				placeholder="Enter Your Message Here"></textarea>
    			<button id="btn_submit"
    				class="btn btn-primary btn-large btn-block disabled input-block-level"
    				onclick="return false">send</button>
    			<!-- END CHAT -->
    		</form>
    	</div>
    
    	<div class="span2">
    		<div class="pull-right" id="destinations_pallette">
<%-- 
    			<div class="alert">
    				<span>You</span>
    			</div>
--%>
				{{each parties}}
                 <div class="alert alert-info" data-username="\${$value.username}">
					{{if  $value.username != $.cookie("j_username")}}
					<a onclick="javascript:void(0)" class="close" data-dismiss="alert">&times;</a>
					{{/if}}
					<span>\${$value.username}</span>
					
                 </div>
				{{/each}}
    		</div>
    	</div>
    </div>
</script>

<!-- scripts -->
<script type="text/x-jquery-tmpl" id="destination-pallette-item-tmpl">
    <div class="alert alert-info" data-username="\${destination}">
    	<a onclick="javascript:void(0)" class="close" data-dismiss="alert">&times;</a>
    	<span>\${destination}</span>
    </div>
</script>
<script type="text/x-jquery-tmpl" id="chat-message-tmpl">
	<span style="font-size:20px"><b>\${sourceUsername}</b> : \${messageBody}<br /></span>
</script>
<script type="text/x-jquery-tmpl" id="convTabTmpl">
	<li id="\${conversationId}"><button type="button" class="close">&times;</button><a href="javascript:void(0)" data-toggle="tab">\${convName}</a></li>
</script>