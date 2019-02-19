package com.ExpTracker.Dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ExpTracker.Model.ExpensesDTO;

@Repository
@Transactional
public class ExpensesDao {
	
	@Autowired
	private SessionFactory factory;
	
	private Session session = null;
	
	private static Log logger = LogFactory.getLog(ExpensesDao.class);
	
	private Session getCurrentSession() {
		return factory.getCurrentSession();
	}
	
	public List<ExpensesDTO> readAllExpenses(String username) {
		session = getCurrentSession();
		TypedQuery<ExpensesDTO> query = session.createQuery("from ExpensesDTO where username = :userName");
		query.setParameter("userName", username);
		List<ExpensesDTO> arr = query.getResultList();
		logger.debug("Querying done for readAllExpenses");
		return arr;
	}
	
	public void addExpenses(ExpensesDTO expensesDTO) {
		session = getCurrentSession();
		session.persist(expensesDTO);
		logger.debug("adding Expenses: "+expensesDTO.getName()+"!");
	}
	
	public void deleteExpenses(String username) {
		session = getCurrentSession();
		TypedQuery<ExpensesDTO> query = session.createQuery("delete from ExpensesDTO where username = :userName");
		query.setParameter("userName", username);
		query.executeUpdate();
		logger.debug("Deleting expenses for user: "+username);
	}
}
