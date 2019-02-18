package ServicesTest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ExpTracker.Configuration.ApplicationConfiguration;
import com.ExpTracker.Configuration.WebInitializer;
import com.ExpTracker.Model.ExpensesDTO;
import com.ExpTracker.Service.ExpensesService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationConfiguration.class,
								 WebInitializer.class})
public class ExpensesServiceTest {
	@Autowired
	private ExpensesService expensesService;
	
	@Test
	public void addExpensesTest() {
		ExpensesDTO mockExpenses = new ExpensesDTO();
		mockExpenses.setUsername("testUsername");
		mockExpenses.setName("testName");
		mockExpenses.setAmount(100);
		mockExpenses.setDateAndTime("2018-12-6 18:51:19");
		expensesService.addExpenses(mockExpenses);
		List<ExpensesDTO> result = expensesService.readAllExpenses("testUsername");
		assertTrue(result.size() == 1);
	}
	
	@Test
	public void readAllExpensesTest() {
		List<ExpensesDTO> result = expensesService.readAllExpenses("testUsername");
		assertTrue(result.size() == 1);
	}
	
	@Test
	public void deleteExpensesTest() {
		expensesService.deleteExpenses("testUsername");
		List<ExpensesDTO> result = expensesService.readAllExpenses("testUsername");
		assertTrue(result.size() == 0);
	}

}
