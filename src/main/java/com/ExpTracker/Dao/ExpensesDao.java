package com.ExpTracker.Dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import com.ExpTracker.Model.ExpensesDTO;

@Component
public class ExpensesDao {
	
	private SessionFactory factory  = null;
	private Session session = null;
	
	public ExpensesDao() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(com.ExpTracker.Model.ExpensesDTO.class);
		factory=configuration.configure().buildSessionFactory();     
		session=factory.openSession(); 
	}
	
	public List<ExpensesDTO> readAllExpenses(String username) {
		TypedQuery<ExpensesDTO> query = session.createQuery("from ExpensesDTO where username = :userName");
		query.setParameter("userName", username);
		List<ExpensesDTO> arr = query.getResultList();
		return arr;
	}
	
	public void addExpenses(ExpensesDTO e) {
		Transaction transaction = session.beginTransaction();
		session.persist(e);
		transaction.commit();
	}
	
	public void deleteExpenses(String username) {
		Transaction transaction = session.beginTransaction();
		TypedQuery<ExpensesDTO> query = session.createQuery("delete from ExpensesDTO where username = :userName");
		query.setParameter("userName", username);
		query.executeUpdate();
		transaction.commit();
	}
}
