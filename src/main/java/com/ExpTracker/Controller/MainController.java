package com.ExpTracker.Controller;




import java.util.List;
import javax.persistence.TypedQuery;
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
		Session session=factory.openSession();  
		TypedQuery<Expenses> query = session.createQuery("from Expenses");
		List<Expenses> arr = query.getResultList();
		for(int i=0;i<arr.size();i++) {
			Expenses e = arr.get(i);
			System.out.println(e.getName());
		}	
		long endTime = System.currentTimeMillis() -startTime;
		session.close();
		return jsonObject.toString();
	}

}
