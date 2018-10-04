package com.ExpTracker.Controller;


import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexPage() {
		return "index";
	}
	
	@RequestMapping(value = "/updateExp", method = RequestMethod.GET, produces = {"application/json"})
	public @ResponseBody Object updateExpenses() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put ("msg", "dontknow");
		return jsonObject.toString();
	}

}
