package com.ExpTracker.Controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ExpTracker.Dao.ExpensesDao;
import com.ExpTracker.Model.ExpensesDTO;

@Controller
public class MainController {
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexPage() {
		return "index";
	}
	
	@RequestMapping(value = "/addExp", method = RequestMethod.POST)
	public void addExpenses(@RequestBody String expData, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		ExpensesDTO expensesDTO = new ObjectMapper().readValue(expData, ExpensesDTO.class);
		ExpensesDao expdao = new ExpensesDao();
		try {
			expdao.addExpenses(expensesDTO);
			response.setStatus(201);
		}
		catch(Exception e) {
			response.setStatus(500);
		}
	}

}
