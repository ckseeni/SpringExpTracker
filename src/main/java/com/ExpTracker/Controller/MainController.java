package com.ExpTracker.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ExpTracker.Dao.ExpensesDao;
import com.ExpTracker.Dao.UsersDao;
import com.ExpTracker.Email.EmailService;
import com.ExpTracker.Model.ExpensesDTO;
import com.ExpTracker.Model.UsersDTO;

@Controller
public class MainController {
	
	@Autowired
	public ExpensesDao expdao;
	
	@Autowired
	public UsersDao usersDao;
	
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
	
	@RequestMapping(value = "/addExp", method = RequestMethod.POST)
	public void addExpenses(@RequestBody String expData, HttpServletResponse response, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("username");
		JSONObject expDataJson = new JSONObject(expData);
		expDataJson.put("username", username);
		expData = expDataJson.toString();
		ExpensesDTO expensesDTO = new ObjectMapper().readValue(expData, ExpensesDTO.class);
		try {
			expdao.addExpenses(expensesDTO);
			logger.info("Expenses added successfully");
			logger.debug("Expenses object: \n"+expensesDTO.toString());
			response.setStatus(201);
		}
		catch(Exception e) {
			logger.info("Exception caught while adding the expenses");
			logger.debug("Expenses object: \n"+expensesDTO.toString());
			e.printStackTrace();
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value = "/retriveExp", method = RequestMethod.GET)
	public @ResponseBody String retrieveExpenses(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("username");
		List<ExpensesDTO> result = expdao.readAllExpenses(username);
		String expJson = new ObjectMapper().writeValueAsString(result);
		JSONObject expListObject = new JSONObject();
		expListObject.put("expList",expJson);
		logger.info("Expenses list retrieved successfully");
		logger.debug(expdao.toString());
		return expListObject.toString();
	}
	
	@RequestMapping(value = "/delExp", method = RequestMethod.DELETE)
	public void deleteExpenses(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("username");
		expdao.deleteExpenses(username);
		logger.info("Expenses deleted successfully");
		response.setStatus(201);	
	}

	@RequestMapping(value = "/emailExp", method = RequestMethod.POST)
	public void emailSender(@RequestBody String expCSVData,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("username");
		EmailService emailService = new EmailService();
		try {
			emailService.sendEmail(expCSVData,username);
			logger.info("Emailing the expenses done successfully");
			response.setStatus(200);
		} catch(Exception e) {
			logger.info("Error while emailing the expenses");
			e.printStackTrace();
			response.setStatus(500);
		}
	}
}
