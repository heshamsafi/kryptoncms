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


<c:url var="link_connect_summary" value='/membership/social/' />

<c:url var="link_connect_facebook" value='/membership/social/facebook/' />
<c:url var="link_connect_facebook_profile" value='/membership/social/facebook/profile/' />
<c:url var="link_connect_facebook_timeline" value='/membership/social/facebook/feed/' />
<c:url var="link_connect_facebook_friends" value='/membership/social/facebook/friends/' />

<c:url var="link_connect_google" value='/membership/social/google/' />
<c:url var="link_connect_google_profile" value='/membership/social/google/profile/' />

<c:url var="link_connect_twitter" value='/membership/social/twitter/' />
<c:url var="link_connect_twitter_profile" value='/membership/social/twitter/profile/' />
<c:url var="link_connect_twitter_friends" value='/membership/social/twitter/friends/' />
<c:url var="link_connect_twitter_messages" value='/membership/social/twitter/messages/' />
<c:url var="link_connect_twitter_timeline" value='/membership/social/twitter/timeline/' />
<c:url var="link_connect_twitter_trends" value='/membership/social/twitter/trends/' />

<c:url var="link_connect_linkedin" value='/membership/social/linkedin' />
<c:url var="link_connect_linkedin_profile" value='/membership/social/linkedin/profile' />

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="btn btn-navbar " data-toggle="collapse"
				data-target=".nav-collapse"> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span> <span class="icon-bar"></span>
			</a> 
			<a class="brand" data-ajax-enable href='${link_home}'>
				<fmt:message key="message.website_name"/>
			</a>
			<div class="nav-collapse collapse">
				<ul class="nav">
<!-- 					<li> -->
<%-- 						<a data-ajax-enable href='${link_membership_login}'> --%>
<!-- 							<fmt:message key="message.nav.login" /> -->
<!-- 						</a> -->
<!-- 					</li> -->
<!-- 					<li> -->
<%-- 						<a data-ajax-enable href='${link_membership_register}'> --%>
<!-- 							<fmt:message key="message.nav.register" /> -->
<!-- 						</a> -->
<!-- 					</li> -->
					<li class="dropdown">
						<a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
							<fmt:message key="message.nav.social" />
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a data-ajax-enable href="${link_connect_summary}">
									<fmt:message key="message.nav.social.connection_summary" />
								</a>
							</li>
							
							<li class="divider"></li>
							<li class="nav-header">
								<fmt:message key="message.nav.social.facebook" />
							</li>
							<li>
								<a data-ajax-enable href="${link_connect_facebook}">
									<fmt:message key="message.nav.social.connect" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_connect_facebook_profile}">
									<fmt:message key="message.nav.social.profile" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_connect_facebook_timeline}">
									<fmt:message key="message.nav.social.timeline" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_connect_facebook_friends}">
									<fmt:message key="message.nav.social.friends" />
								</a>
							</li>
							
							
							<li class="divider"></li>
							<li class="nav-header">
								<fmt:message key="message.nav.social.twitter" />
							</li>
							<li>
								<a data-ajax-enable href="${link_connect_twitter}">
									<fmt:message key="message.nav.social.connect" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_connect_twitter_profile}">
									<fmt:message key="message.nav.social.profile" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_connect_twitter_friends}">
									<fmt:message key="message.nav.social.friends" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_connect_twitter_messages}">
									<fmt:message key="message.nav.social.messages" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_connect_twitter_timeline}">
									<fmt:message key="message.nav.social.timeline" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_connect_twitter_trends}">
									<fmt:message key="message.nav.social.trends" />
								</a>
							</li>
							
							<li class="divider"></li>
							<li class="nav-header">
								<fmt:message key="message.nav.social.google" />
							</li>
							<li>
								<a data-ajax-enable href="${link_connect_google}">
									<fmt:message key="message.nav.social.connect" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_connect_google_profile}">
									<fmt:message key="message.nav.social.profile" />
								</a>
							</li>
							
							<li class="divider"></li>
							<li class="nav-header">
								<fmt:message key="message.nav.social.linkedin" />
							</li>
							<li>
								<a data-ajax-enable href="${link_connect_linkedin}">
									<fmt:message key="message.nav.social.connect" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_connect_linkedin_profile}">
									<fmt:message key="message.nav.social.profile" />
								</a>
							</li>
							
						</ul>
					</li>
					<li class="dropdown">
					<a href="javascript:void(0)"
						class="dropdown-toggle" data-toggle="dropdown">
						<fmt:message key="message.nav.more" />
							<b class="caret"></b>
					</a>
						<ul class="dropdown-menu">
							<li>
								<a data-ajax-enable href="${link_chat}">
									<fmt:message key="message.nav.more.chat" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_translate}">
									<fmt:message key="message.nav.more.translate" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_comments}">
									<fmt:message key="message.nav.more.comments" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_file_upload}">
									<fmt:message key="message.nav.more.file_upload" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_facebook_comments}">
									<fmt:message key="message.nav.more.facebook.social_plugins" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_photo}">
									<fmt:message key="message.nav.more.photo" />
								</a>
							</li>
							<li>
								<a data-ajax-enable href="${link_article}">
									<fmt:message key="message.nav.more.article" />
								</a>
							</li>
							<li class="divider"></li>
							<li class="nav-header">Nav header</li>
							<li><a href="#">Separated link</a></li>
							<li><a href="#">One more separated link</a></li>
						</ul></li>
				</ul>
				<img id="loading_image" style="margin-left:50px;display:none;width: 2.6em;" src='<c:url value="/resources/img/loading.gif" />' />
				
				<div class="btn-group pull-right" id="account_controls">
				  <a class="btn dropdown-toggle btn btn-primary displ" data-toggle="dropdown" href="#">
				     <span>Anonymous</span>
				    <span class="caret"></span>
				  </a>
				  <ul class="dropdown-menu">
				    <li class="out"> <a href="#myModal1" role="button" data-toggle="modal">Sign in</a> </li>
					<li class="out"> <a href="#myModal2" role="button" data-toggle="modal">Sign up</a> </li>
					<li class="in" > <a href="#" role="button" data-toggle="modal">Edit Account</a> </li>
					<li class="in" > <a href='<c:url value="/membership/logout" />' data-logout role="button" data-toggle="modal">Logout</a> </li>
				  </ul>
				</div>
				</div>
			</div>
			</div>
	</div>
</div>