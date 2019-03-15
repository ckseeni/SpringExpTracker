package com.ExpTracker.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ExpTracker.Dao.ExpensesDao;
import com.ExpTracker.Model.ExpensesDTO;
@Service
public class ExpensesService {
	
	@Autowired
	private ExpensesDao expDao;
	
	@Autowired 
	private ImportFileTask importFileTask;
	
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

	public void importFileService(String username) throws IOException {
		JSONArray jsonArray = getFileContent(username);
		importFileTask.setExpArray(jsonArray);
		Thread thread = new Thread(importFileTask);
		thread.start();
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
	
	private JSONArray getFileContent(String username) throws IOException {
		FileReader fileReader = new FileReader("C:\\Spring apps\\ExpTracker\\src\\main\\File\\importFile.csv");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		JSONArray jsonArray = new JSONArray();
		String line = bufferedReader.readLine();
		while(line != null) {
			String arr[] = line.split(",");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name",arr[0]);
			jsonObject.put("amount", arr[1]);
			jsonObject.put("dateAndTime", "importedViaFile");
			jsonObject.put("username", username);
			jsonArray.put(jsonObject);
			line = bufferedReader.readLine();
		}
		bufferedReader.close();
		return jsonArray;
	}
	
}
