package com.ExpTracker.Controller;

import java.util.List;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ExpTracker.Dao.ExpensesDao;
import com.ExpTracker.Model.ExpensesDTO;

@Controller
public class MainController {
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexPage() {
		return "index";
	}
	
	@RequestMapping(value = "/addExp", method = RequestMethod.POST)
	public @ResponseBody String addExpenses(@RequestBody String expData) {
		JSONObject jsonObject = new JSONObject(expData);
		ExpensesDao expdao = new ExpensesDao();
		List<ExpensesDTO> arr = expdao.readAllExpenses();
		for(int i=0;i<arr.size();i++) {
			ExpensesDTO e = arr.get(i);
			System.out.println(e.getName());
		}	
		return jsonObject.toString();
	}

}
