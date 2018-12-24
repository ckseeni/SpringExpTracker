package com.ExpTracker.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ExpTracker.Dao.UsersDao;
import com.ExpTracker.Model.UsersDTO;

@Controller
public class UserController {
	
	@Autowired
	public UsersDao usersDao;
	
	private static Log logger = LogFactory.getLog(UserController.class);
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public void addUser(@RequestBody String userObj, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		UsersDTO usersDTO = new ObjectMapper().readValue(userObj, UsersDTO.class);
		try {
			usersDao.addUsers(usersDTO);
			logger.info("User "+usersDTO.getUsername()+ " registered successfully");
			logger.debug("User object: "+usersDTO.toString());
			response.setStatus(201);
		}
		catch(Exception e) {
			logger.info("Error while registering user: "+usersDTO.getUsername());
			logger.debug("User object: \n"+usersDTO.toString());
			e.printStackTrace();
			response.setStatus(403);
		}
	}
	
	@RequestMapping(value = "/authenticateUser", method = RequestMethod.POST)
	public void authenticateUser(@RequestBody String userObj, HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		UsersDTO usersDTO = new ObjectMapper().readValue(userObj, UsersDTO.class);
		try {
			UsersDTO fetchedUser = usersDao.authenticateUser(usersDTO);
			if(fetchedUser.getPassword().equals(usersDTO.getPassword())) {
				HttpSession session = request.getSession(true);
				session.setAttribute("username", usersDTO.getUsername());
				session.setMaxInactiveInterval(5*60);
				logger.info("Created a new session "+session.getId()+" for the login user: "+fetchedUser.getUsername());
				logger.debug("User object: "+usersDTO.toString());
				response.setStatus(201);
			}		
			else {
				logger.info("Entered password is wrong for the login user: "+fetchedUser.getUsername());
				logger.debug("User object: "+usersDTO.toString());
				response.setStatus(401);
			}	
		}
		catch(Exception e) {
			logger.info("Error while authentication of user: "+usersDTO.getUsername());
			logger.debug("User object: "+usersDTO.toString());
			e.printStackTrace();
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logoutUser(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		try {
			logger.info("Logging out the user: "+(String)session.getAttribute("username")+". Invalidating the session:"+session.getId());
			session.invalidate();
			response.setStatus(200);
		}
		catch(Exception e) {
			logger.info("Error while logging out the user: "+(String)session.getAttribute("username")+"and the session:"+session.getId());
			e.printStackTrace();
			response.setStatus(500);
		}
	}

}
