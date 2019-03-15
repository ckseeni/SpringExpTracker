package com.ExpTracker.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ExpTracker.Dao.ExpensesDao;
import com.ExpTracker.Model.ExpensesDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ImportFileTask implements Runnable{
	
	@Autowired
	private ExpensesDao expDao;
	
	private static Log logger = LogFactory.getLog(ImportFileTask.class);
	
	private JSONArray expArray;
	
	public void setExpArray(JSONArray expArray) {
		this.expArray = expArray;
	}
	
	public void run() {
		logger.debug("importing expenses!");
		for(int i=0; i<expArray.length(); i++) {
			JSONObject jsonObject = expArray.getJSONObject(i);
			String expData = jsonObject.toString();
			ExpensesDTO expensesDTO = null;
			try {
				expensesDTO = new ObjectMapper().readValue(expData, ExpensesDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			expDao.addExpenses(expensesDTO);
		}
		logger.debug("importing finished!!");
	}	
	
}
