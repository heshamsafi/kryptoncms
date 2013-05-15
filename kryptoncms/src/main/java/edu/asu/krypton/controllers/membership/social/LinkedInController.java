/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.asu.krypton.controllers.membership.social;

import java.security.Principal;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


//@Controller
@RequestMapping(value="/membership/social/linkedin")
public class LinkedInController extends edu.asu.krypton.controllers.Controller {

//	@Inject
	private ConnectionRepository connectionRepository;
	
	private final String DEFAULT_DIR  = "connect/linkedin/";
	private final String PROFILE_VIEW = DEFAULT_DIR+"profile";
	private final String BODIES_PREFIX = "bodies/";
	
	@RequestMapping(method=RequestMethod.GET,value="profile")
	public String home(Principal currentUser, ModelMap model,HttpServletRequest request) {
		Connection<LinkedIn> connection = connectionRepository.findPrimaryConnection(LinkedIn.class);
		if (connection == null) {
			return appropriateView(request, BODIES_PREFIX+DEFAULT_DIR, defaultView(model,DEFAULT_DIR));
		}
		model.addAttribute("profile", connection.getApi().profileOperations().getUserProfile());
		return appropriateView(request, BODIES_PREFIX+PROFILE_VIEW, defaultView(model, PROFILE_VIEW));
	}
	
}
