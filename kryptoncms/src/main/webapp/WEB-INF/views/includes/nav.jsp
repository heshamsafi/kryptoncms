<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<c:url var="link_home" value='/' />
<c:url var="link_chat" value='/chat/' />
<c:url var="link_comments" value='/comments/' />
<c:url var="link_translate" value='/translate/' />
<c:url var="link_membership_login" value='/membership/login' />
<c:url var="link_membership_register" value='/membership/register' />
<c:url var="link_file_upload" value='/file/upload-demo' />
<c:url var="link_facebook_comments" value="/comments/facebook/Article/1" />
<c:url var="link_photo" value='/photo/' />
<c:url var="link_article" value='/article/addarticle' />
<c:url var="link_article_search" value='/article/search' />

<c:url var="link_connect_summary" value='/membership/social/' />

<c:url var="link_connect_facebook" value='/membership/social/facebook/' />
<c:url var="link_connect_facebook_profile"
	value='/membership/social/facebook/profile/' />
<c:url var="link_connect_facebook_timeline"
	value='/membership/social/facebook/feed/' />
<c:url var="link_connect_facebook_friends"
	value='/membership/social/facebook/friends/' />

<c:url var="link_connect_google" value='/membership/social/google/' />
<c:url var="link_connect_google_profile"
	value='/membership/social/google/profile/' />

<c:url var="link_connect_twitter" value='/membership/social/twitter/' />
<c:url var="link_connect_twitter_profile"
	value='/membership/social/twitter/profile/' />
<c:url var="link_connect_twitter_friends"
	value='/membership/social/twitter/friends/' />
<c:url var="link_connect_twitter_messages"
	value='/membership/social/twitter/messages/' />
<c:url var="link_connect_twitter_timeline"
	value='/membership/social/twitter/timeline/' />
<c:url var="link_connect_twitter_trends"
	value='/membership/social/twitter/trends/' />

<c:url var="link_connect_linkedin" value='/membership/social/linkedin' />
<c:url var="link_connect_linkedin_profile"
	value='/membership/social/linkedin/profile' />

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="btn btn-navbar " data-toggle="collapse"
				data-target=".nav-collapse"> <span class="icon-bar"></span> <span
				class="icon-bar"></span> <span class="icon-bar"></span>
			</a> <a class="brand" data-ajax-enable href='${link_home}'> <fmt:message
					key="message.website_name" />
			</a>

			<div class="nav-collapse collapse">
				<ul class="nav">
						<c:set var="items" scope="request" value="${items}" />
						<jsp:include page="/WEB-INF/views/includes/recursiveMenuItemsContainer.jsp" />
						<c:set var="items" scope="request" value="${null}" />
				</ul>
				<img id="loading_image"
					style="margin-left: 50px; display: none; width: 2.6em;"
					src='<c:url value="/resources/img/loading.gif" />' />

				<div class="btn-group pull-right" id="account_controls">
					<div class="pull-left" style="margin-right: 5px">
						<form id="searchArticlesForm" class="form-search"
							action='<c:url value="/article/search" />'>
							<div class="input-append">
								<input type="text" class="search-query"
									id="searchArticlesPhrase" data-required
									placeholder="enter word or phrase ..." />
								<input id="searchArticles" class="btn  btn-primary"
									type="submit" value="Search" />
							</div>
						</form>
					</div>
					<div class="pull-right" style="margin-left: 5px">
						<a class="btn dropdown-toggle btn btn-primary displ"
							data-toggle="dropdown" href="#"> <span>Anonymous</span> <span
							class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li class="out"><a href="#myModal1" role="button"
								data-toggle="modal">Sign in</a></li>
							<li class="out"><a href="#myModal2" role="button"
								data-toggle="modal">Sign up</a></li>
							<li class="in"><a href="#chat" role="button"
								data-toggle="modal">Chat</a></li>
							<li class="inout"><a href="#translateModal" role="button"
								data-toggle="modal">Translator</a></li>
							<li class="in"><a href="#genericModal" id="editAccount"
								role="button" data-toggle="modal">Edit Account</a></li>
							<li class="in"><a
								href='<c:url value="/membership/logout" />' data-logout
								role="button" data-toggle="modal">Logout</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>