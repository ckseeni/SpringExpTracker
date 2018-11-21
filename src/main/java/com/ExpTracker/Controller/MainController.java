package com.ExpTracker.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexPage() {
		return "index";
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public void addUser(@RequestBody String userObj, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		UsersDTO usersDTO = new ObjectMapper().readValue(userObj, UsersDTO.class);
		try {
			usersDao.addUsers(usersDTO);
			response.setStatus(201);
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(403);
		}
	}
	
	@RequestMapping(value = "/authenticateUser", method = RequestMethod.POST)
	public void authenticateUser(@RequestBody String userObj, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		UsersDTO usersDTO = new ObjectMapper().readValue(userObj, UsersDTO.class);
		try {
			UsersDTO fetchedUser = usersDao.authenticateUser(usersDTO);
			if(fetchedUser.getPassword().equals(usersDTO.getPassword()))
				response.setStatus(201);
			else
				response.setStatus(401);
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value = "/addExp", method = RequestMethod.POST)
	public void addExpenses(@RequestBody String expData, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		ExpensesDTO expensesDTO = new ObjectMapper().readValue(expData, ExpensesDTO.class);
		try {
			expdao.addExpenses(expensesDTO);
			response.setStatus(201);
		}
		catch(Exception e) {
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value = "/retriveExp", method = RequestMethod.GET)
	public @ResponseBody String retrieveExpenses() throws JsonGenerationException, JsonMappingException, IOException {
		List<ExpensesDTO> result = expdao.readAllExpenses();
		String expJson = new ObjectMapper().writeValueAsString(result);
		JSONObject expListObject = new JSONObject();
		expListObject.put("expList",expJson);
		return expListObject.toString();
	}
	
	@RequestMapping(value = "/delExp", method = RequestMethod.DELETE)
	public void deleteExpenses(HttpServletResponse response) {
		expdao.deleteExpenses();
		response.setStatus(201);	
	}

	@RequestMapping(value = "/emailExp", method = RequestMethod.POST)
	public void emailSender(@RequestBody String expCSVData, HttpServletResponse response) {
		EmailService emailService = new EmailService();
		try {
			emailService.sendEmail(expCSVData);
			response.setStatus(200);
		} catch(Exception e) {
			response.setStatus(500);
		}
	}
}
