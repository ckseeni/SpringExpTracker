package com.ExpTracker.Dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.ExpTracker.Model.ExpensesDTO;

public class ExpensesDao {
	
	private SessionFactory factory  = null;
	private Session session = null;

	public ExpensesDao() {
		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
		factory = metaData.getSessionFactoryBuilder().build();
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
}
