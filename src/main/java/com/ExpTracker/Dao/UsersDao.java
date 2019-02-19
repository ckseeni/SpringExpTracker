package com.ExpTracker.Dao;

import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ExpTracker.Model.UsersDTO;

@Repository
@Transactional
public class UsersDao {
	
	@Autowired
	private SessionFactory factory;
	
	private Session session = null;
	
	private static Log logger = LogFactory.getLog(UsersDao.class);
	
	private Session getCurrentSession() {
		return factory.getCurrentSession();
	}
	
	public void addUsers(UsersDTO usersDTO) {
		session = getCurrentSession();
		session.persist(usersDTO);
		logger.debug("New User : "+usersDTO.getUsername()+" registered!");
	}
	
	public UsersDTO authenticateUser(UsersDTO usersDTO) {
		session = getCurrentSession();
		TypedQuery<UsersDTO> query = session.createQuery("from UsersDTO where username = :userName");
		query.setParameter("userName", usersDTO.getUsername());
		UsersDTO fetchedUser = query.getSingleResult();
		logger.debug("Queryubg done for authenticate Users");
		return fetchedUser;
	}
}
