package com.ExpTracker.Dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import com.ExpTracker.Model.UsersDTO;

@Component
public class UsersDao {
	
	private SessionFactory factory  = null;
	private Session session = null;
	
	public UsersDao() {
		factory=new Configuration().configure().buildSessionFactory();     
		session=factory.openSession(); 
	}
	
	public void addUsers(UsersDTO usersDTO) {
		Transaction transaction = session.beginTransaction();
		session.persist(usersDTO);
		transaction.commit();
	}
}
