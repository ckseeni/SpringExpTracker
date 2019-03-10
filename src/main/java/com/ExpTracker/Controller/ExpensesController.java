package com.ExpTracker.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ExpTracker.Email.EmailService;
import com.ExpTracker.Model.ExpensesDTO;
import com.ExpTracker.Service.ExpensesService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ExpensesController {

	@Autowired
	private ExpensesService expService;
	
	@Autowired
	private EmailService emailService;
	
	private static Log logger = LogFactory.getLog(ExpensesController.class);
	
	@RequestMapping(value = "/addExp", method = RequestMethod.POST)
	public void addExpenses(@RequestBody String expData, HttpServletResponse response, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		expData = appendUsername(expData, request);
		ExpensesDTO expensesDTO = new ObjectMapper().readValue(expData, ExpensesDTO.class);
		try {
			expService.addExpenses(expensesDTO);
			logger.info("Expenses added successfully");
			response.setStatus(201);
		}
		catch(Exception e) {
			logger.info("Exception caught while adding the expenses");
			logger.debug("Expenses object: \n"+expensesDTO.toString());
			e.printStackTrace();
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value = "/retriveExp", method = RequestMethod.GET)
	public @ResponseBody String retrieveExpenses(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		String username = getUsername(request);
		List<ExpensesDTO> result = expService.readAllExpenses(username);
		String expJson = new ObjectMapper().writeValueAsString(result);
		JSONObject expListObject = new JSONObject();
		expListObject.put("expList",expJson);
		logger.info("Expenses list retrieved successfully");
		logger.debug(expListObject.toString());
		return expListObject.toString();
	}
	
	@RequestMapping(value = "/delExp", method = RequestMethod.DELETE)
	public void deleteExpenses(HttpServletRequest request, HttpServletResponse response) {
		String username = getUsername(request);
		expService.deleteExpenses(username);
		logger.info("Expenses deleted successfully");
		response.setStatus(201);	
	}

	@RequestMapping(value = "/emailExp", method = RequestMethod.POST)
	public void emailSender(@RequestBody String expCSVData,HttpServletRequest request, HttpServletResponse response) {
		String username = getUsername(request);
		try {
			emailService.sendEmail(expCSVData,username);
			logger.info("Emailing the expenses done successfully");
			response.setStatus(200);
		} catch(Exception e) {
			logger.info("Error while emailing the expenses");
			e.printStackTrace();
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value = "/importFile", method = RequestMethod.GET)
	public void importFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		FileReader fileReader = new FileReader("C:\\Spring apps\\ExpTracker\\src\\main\\File\\importFile.csv");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		JSONArray jsonArray = new JSONArray();
		String userName = getUsername(request);
		String line = bufferedReader.readLine();
		while(line != null) {
			String arr[] = line.split(",");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name",arr[0]);
			jsonObject.put("amount", arr[1]);
			jsonObject.put("dateAndTime", "importedViaFile");
			jsonObject.put("username", userName);
			jsonArray.put(jsonObject);
			line = bufferedReader.readLine();
		}
		try {
			expService.importFileService(jsonArray);
			logger.info("File imported successfully!!");
			response.setStatus(200);
		} catch(Exception e) {
			logger.info("Error while importing file");
			response.setStatus(500);
		} finally {
			bufferedReader.close();
		}
	}

	@RequestMapping(value = "/exportFile", method = RequestMethod.GET)
	public void exportFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = getUsername(request);
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			String expList = expService.exportFileService(username);
			fileWriter = new FileWriter(new File("C:\\Spring apps\\ExpTracker\\src\\main\\File\\exportFile.csv"));
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(expList);
			logger.info("file exported succesfully");
			response.setStatus(200);
		} catch (Exception e) {
			logger.info("error while exporting file!");
			response.setStatus(500);
		} finally {
			bufferedWriter.close();
		}
	}
	
	private String getUsername(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("username");
		return username;
	}
	
	private String appendUsername(String expData, HttpServletRequest request) {
		JSONObject expDataJson = new JSONObject(expData);
		String username = getUsername(request);
		expDataJson.put("username", username);
		return expDataJson.toString();
	}
	
}
