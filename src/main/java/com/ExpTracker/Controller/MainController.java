package com.ExpTracker.Controller;




import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;  
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ExpTracker.Model.Expenses;



@Controller
public class MainController {
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexPage() {
		return "index";
	}
	
	@RequestMapping(value = "/updateExp", method = RequestMethod.POST)
	public @ResponseBody String updateExpenses(@RequestBody String expData) {
		long startTime = System.currentTimeMillis();
		JSONObject jsonObject = new JSONObject(expData);
		SessionFactory factory  = null;
		try {
			StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
			Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
			factory = metaData.getSessionFactoryBuilder().build();
		}
		catch (Throwable th) {
			System.err.println("Enitial SessionFactory creation failed" + th);
			throw new ExceptionInInitializerError(th);
		}
		//creating session object  
		Session session=factory.openSession();  
		Query query = session.createQuery("from Expenses");
		List arr = query.list();
		for(int i=0;i<arr.size();i++) {
			Expenses e = (Expenses)arr.get(i);
			System.out.println(e.getName());
		}	
		long endTime = System.currentTimeMillis() -startTime;
		System.out.println(endTime);
		return jsonObject.toString();
	}

}
