<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<html lang="en">
<head>
	<%@ include file="/WEB-INF/views/includes/common_head.jsp"%>
</head>
<body>
	<div class="container-fluid">
		<%@ include file="/WEB-INF/views/includes/header.jsp"%>
		<!--Body-->
		<c:url var="" value="/google/person" />
		<div class="row-fluid">
			<%@ include file="/WEB-INF/views/includes/nav.jsp"%>
			<div class="span9 well well-large">
				
				<form class="form-horizontal" action="" method="POST">
					<div class="control-group">
						<label class="control-label">Profile ID:</label>
						<div class="controls">
							<span class="uneditable-input input-xxlarge">${person.id}</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Display Name:</label>
						<div class="controls">
							<span class="uneditable-input input-xxlarge">${person.displayName}</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Gender:</label>
						<div class="controls">
							<span class="uneditable-input input-xxlarge">${person.gender}</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">About Me:</label>
						<div class="controls">
							<span class="uneditable-input input-xxlarge">${person.aboutMe}</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Relationship Status:</label>
						<div class="controls">
							<span class="uneditable-input input-xxlarge">${person.relationshipStatus}</span>
						</div>
					</div>
				</form>
			
			</div>
		</div>
		<!--End Body-->
		<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
	</div>
</body>
</html>