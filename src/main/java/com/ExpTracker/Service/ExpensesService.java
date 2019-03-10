package com.ExpTracker.Service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ExpTracker.Dao.ExpensesDao;
import com.ExpTracker.Model.ExpensesDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExpensesService {
	
	@Autowired
	private ExpensesDao expDao;
	
	private static Log logger = LogFactory.getLog(ExpensesService.class);
	
	public void addExpenses(ExpensesDTO expensesDTO) {
		expDao.addExpenses(expensesDTO);
	}
	
	public List<ExpensesDTO> readAllExpenses(String username) {
		return expDao.readAllExpenses(username);
	}
	
	public void deleteExpenses(String username) {
		expDao.deleteExpenses(username);
	}

	public void importFileService(JSONArray jsonArray) throws JSONException, JsonParseException, JsonMappingException, IOException {
		logger.debug("importing expenses!");
		for(int i=0; i<jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String expData = jsonObject.toString();
			ExpensesDTO expensesDTO = new ObjectMapper().readValue(expData, ExpensesDTO.class);
			expDao.addExpenses(expensesDTO);
		}
		logger.debug("importing finished!!");
	}
	
	public String exportFileService(String username) {
		logger.debug("Exporting expenses!!");
		List<ExpensesDTO> expList = expDao.readAllExpenses(username);
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<expList.size(); i++) {
			builder.append(expList.get(i).getName());
			builder.append(",");
			builder.append(expList.get(i).getAmount());
			builder.append("\n");
		}
		logger.debug("Exporting expenses finished!!");
		return builder.toString();
	}
	
}
