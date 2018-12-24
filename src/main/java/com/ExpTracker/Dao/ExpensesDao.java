package com.ExpTracker.Dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	private static Log logger = LogFactory.getLog(ExpensesDao.class);
	
	public ExpensesDao() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(com.ExpTracker.Model.ExpensesDTO.class);
		factory=configuration.configure().buildSessionFactory();     
		session=factory.openSession(); 
		logger.debug("Hibernate session created for ExpensesDao");
	}
	
	public List<ExpensesDTO> readAllExpenses(String username) {
		TypedQuery<ExpensesDTO> query = session.createQuery("from ExpensesDTO where username = :userName");
		query.setParameter("userName", username);
		List<ExpensesDTO> arr = query.getResultList();
		logger.debug("Querying done for readAllExpenses");
		return arr;
	}
	
	public void addExpenses(ExpensesDTO e) {
		Transaction transaction = session.beginTransaction();
		session.persist(e);
		transaction.commit();
		logger.debug("Transaction commited for adding Expenses");
	}
	
	public void deleteExpenses(String username) {
		Transaction transaction = session.beginTransaction();
		TypedQuery<ExpensesDTO> query = session.createQuery("delete from ExpensesDTO where username = :userName");
		query.setParameter("userName", username);
		query.executeUpdate();
		transaction.commit();
		logger.debug("Transaction commited for Deleting expenses");
	}
}
