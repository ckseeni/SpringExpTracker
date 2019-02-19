package com.ExpTracker.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ExpTracker.Dao.UsersDao;
import com.ExpTracker.Model.UsersDTO;

@Service
public class UserService {

	@Autowired
	private UsersDao usersDao;
	
	public void addUsers(UsersDTO usersDTO) {
		usersDao.addUsers(usersDTO);
	}
	
	public boolean authenticateUser(UsersDTO usersDTO, HttpServletRequest request) {
		UsersDTO fetchedUser = usersDao.authenticateUser(usersDTO);
		if(fetchedUser.getPassword().equals(usersDTO.getPassword())) {
			HttpSession session = request.getSession(true);
			session.setAttribute("username", usersDTO.getUsername());
			session.setMaxInactiveInterval(5*60);
			return true;
		}
		else
			return false;
	}
	
	public void logOut(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.invalidate();
	}
}
