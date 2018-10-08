package com.ExpTracker.Controller;



import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexPage() {
		return "index";
	}
	
	@RequestMapping(value = "/updateExp", method = RequestMethod.POST)
	public @ResponseBody String updateExpenses(@RequestBody String expData) {
		JSONObject jsonObject = new JSONObject(expData);
		return jsonObject.toString();
	}

}
