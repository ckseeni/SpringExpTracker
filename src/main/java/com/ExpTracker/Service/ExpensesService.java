package com.ExpTracker.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ExpTracker.Dao.ExpensesDao;
import com.ExpTracker.Model.ExpensesDTO;

@Service
public class ExpensesService {
	
	@Autowired
	private ExpensesDao expDao;
	
	public void addExpenses(ExpensesDTO expensesDTO) {
		expDao.addExpenses(expensesDTO);
	}
	
	public List<ExpensesDTO> readAllExpenses(String username) {
		return expDao.readAllExpenses(username);
	}
	
	public void deleteExpenses(String username) {
		expDao.deleteExpenses(username);
	}

}
