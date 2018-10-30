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
		factory=new Configuration().configure().buildSessionFactory();     
		session=factory.openSession(); 
	}
	
	public List<ExpensesDTO> readAllExpenses() {
		TypedQuery<ExpensesDTO> query = session.createQuery("from ExpensesDTO");
		List<ExpensesDTO> arr = query.getResultList();
		return arr;
	}
	
	public void addExpenses(ExpensesDTO e) {
		Transaction transaction = session.beginTransaction();
		session.persist(e);
		transaction.commit();
	}
	
	public void deleteExpenses() {
		Transaction transaction = session.beginTransaction();
		TypedQuery<ExpensesDTO> query = session.createQuery("delete from ExpensesDTO");
		query.executeUpdate();
		transaction.commit();
	}
}
