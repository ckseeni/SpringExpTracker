package com.ExpTracker.Controller;




import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;  
import org.hibernate.SessionFactory;  

import org.hibernate.cfg.AnnotationConfiguration;
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
		@SuppressWarnings("deprecation")
		SessionFactory factory=new AnnotationConfiguration().configure()
				.buildSessionFactory();;      
		//creating session object  
		Session session=factory.openSession();  
		Query query = session.createQuery("from Expenses");
		List arr = query.list();
		for(int i=0;i<arr.size();i++)
			System.out.println(arr.get(i));
		return jsonObject.toString();
	}

}
