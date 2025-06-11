package Pages;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import Base.base;
import utils.ExtentManager;


public class copyjobquestionspage extends base {
	private final Page page;
	private static final Logger logger = Logger.getLogger(copyjobquestionspage.class.getName());
    private ExtentTest test = ExtentManager.getinstance().createTest("Copy Job Questions Test");



	private final String searchclient = "#emailValue > div > input";
	private final String enterclientid = "#emailValue > div > input";
	private final String selectclient = "#emailValue_0";
	private final String jobsmodule = "//span[contains(text(),'Jobs')]";
	private final String copyjobquestionbutton = "//tr[td[contains(text(),'Automationjob')]]//button[contains(@ptooltip,'Copy Job Question')]";
	private final String selectjobsdropdown = "//p-multiselect[@placeholder='Select jobs']";
	private final String copybutton = "//html/body/div/div/form/div/div[2]/button";
	private final String editbutton = "//tr[td[contains(text(),'abc')]]//button[contains(@ptooltip,'Edit Job')]";
	private final String confirmation = "//span[contains(text(),'Yes')]";
	private final String assesmentcriteria = "//span[contains(text(),'Assessment Criteria')]";
	private final String searchjob = "//input[@type='text' and @role='searchbox']";
	private final String selectjob = "//span[contains(text(),'abc')]";
	public copyjobquestionspage(Page page) {
		this.page = page;
	}

	public void copyquestions(String clientid, String jobname) {

		logger.info("🔍 Starting 'copyquestions' for client ID: " + clientid);
		test.log(Status.INFO, "Starting 'copyquestions' for client ID: " + clientid);

		try {
			page.evaluate("document.body.style.zoom = '80%'");
			test.log(Status.INFO, "Zoom adjusted to 80%");

			page.locator(searchclient).click(new Locator.ClickOptions().setClickCount(3));
			test.log(Status.INFO, "Clicked on client search box");

			page.fill(enterclientid, clientid);
			test.log(Status.INFO, "Entered client ID");

			page.click(selectclient);
			test.log(Status.INFO, "Selected client from dropdown");

			page.click(jobsmodule);
			test.log(Status.INFO, "Navigated to Jobs module");

			page.locator(copyjobquestionbutton).scrollIntoViewIfNeeded();
			page.waitForTimeout(5000);
			test.log(Status.INFO, "Scrolled to and waited before clicking copy button");

			page.locator(copyjobquestionbutton).click();
			test.log(Status.INFO, "Clicked 'Copy Job Question' button");

			page.click(selectjobsdropdown);
			test.log(Status.INFO, "Opened jobs dropdown");

			page.fill(searchjob, jobname);
			test.log(Status.INFO, "Searched job name");

			page.click(selectjob);
			test.log(Status.INFO, "Selected job from dropdown");
			
			/*for (int i = 2; i < 3; i++) {
				page.click("//p-multiselectitem//li[@role='option' and @aria-posinset=" + i + "]");
				test.log(Status.INFO, "Selected job option: " + i);
			}*/

			page.click(copybutton);
			test.log(Status.INFO, "Clicked 'Copy' button");

			page.click(confirmation);
			test.log(Status.INFO, "Confirmed copy action");

			page.waitForTimeout(5000);
			test.log(Status.PASS, "'copyquestions' completed successfully");

		} catch (Exception e) {
			test.log(Status.FAIL, "Exception in 'copyquestions': " + e.getMessage());
			logger.log(Level.SEVERE, "❌ Exception in 'copyquestions': " + e.getMessage(), e);
			throw e;
		}
	}
	public void testcopiedquestion() {
		logger.info("🔍 Starting 'testcopiedquestion'...");
		test.log(Status.INFO, "Starting 'testcopiedquestion'...");

		try {
			page.locator(editbutton).scrollIntoViewIfNeeded();
			page.waitForTimeout(5000);
			page.click(editbutton);
			test.log(Status.INFO, "Clicked Edit button");

			page.click(assesmentcriteria);
			test.log(Status.INFO, "Clicked Assessment Criteria tab");

			List<String> fewexpectedquestions = List.of(
				"Are you able to commute reliably?",
				"Can you work legally in the US?",
				"Do you require sponsorship for visa status?"
			);

			for (String question : fewexpectedquestions) {
				Locator questionLocator = page.locator("text=" + question);
				questionLocator.scrollIntoViewIfNeeded();

				if (!questionLocator.isVisible()) {
					test.log(Status.FAIL, "Question not found: " + question);
					throw new AssertionError("Expected question not found: " + question);
				}

				test.log(Status.PASS, "Verified question: " + question);
				questionLocator.evaluate("el => { el.style.border = '3px solid red'; setTimeout(() => el.style.border = '', 800); }");
			}

			page.waitForTimeout(5000);
			test.log(Status.PASS, "All expected questions are present and verified.");
		} 
		catch (AssertionError ae) {
			test.log(Status.FAIL, "Assertion failed: " + ae.getMessage());
			throw ae;
		} 
		catch (Exception e) {
			test.log(Status.FAIL, "Exception in 'testcopiedquestion': " + e.getMessage());
			throw e;
		}
	}
	}
