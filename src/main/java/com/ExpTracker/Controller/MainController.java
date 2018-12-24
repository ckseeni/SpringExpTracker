package com.ExpTracker.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	private static Log logger = LogFactory.getLog(MainController.class);
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexPage(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null) {
			logger.info("Existing session: "+session.getId()+" and logged username is "+(String)session.getAttribute("username")+".Redirecting to expMain page.");
			return "expMain";
		}
		else {
			logger.info("No Existing session. Redirecting to login page.");
			return "index";
		}
	}
	
}
