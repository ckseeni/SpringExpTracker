package com.ExpTracker.Dao;

import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	private static Log logger = LogFactory.getLog(UsersDao.class);
	
	public UsersDao() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(com.ExpTracker.Model.UsersDTO.class);
		factory=configuration.configure().buildSessionFactory();     
		session=factory.openSession();
		logger.debug("Hibernate session created for UsersDao");
	}
	
	public void addUsers(UsersDTO usersDTO) {
		Transaction transaction = session.beginTransaction();
		session.persist(usersDTO);
		transaction.commit();
		logger.debug("Transaction commited for adding users");
	}
	
	public UsersDTO authenticateUser(UsersDTO usersDTO) {
		TypedQuery<UsersDTO> query = session.createQuery("from UsersDTO where username = :userName");
		query.setParameter("userName", usersDTO.getUsername());
		UsersDTO fetchedUser = query.getSingleResult();
		logger.debug("Queryubg done for authenticate Users");
		return fetchedUser;
	}
}
