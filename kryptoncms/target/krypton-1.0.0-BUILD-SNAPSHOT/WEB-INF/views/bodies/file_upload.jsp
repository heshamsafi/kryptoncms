<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

    <div class="page-header">
        <h1>File Upload Demo</h1>
    </div>
    <c:url var="controller_upload_action" value="/file/upload.action" />
    
    <jsp:include page="/WEB-INF/views/includes/file_uploader.jsp" >
    	<jsp:param name="upload_path" value="${controller_upload_action}"/>
    </jsp:include>