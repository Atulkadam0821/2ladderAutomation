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

public class copyjobfromclientpage extends base {
	private final Page page;
	private static final Logger logger = Logger.getLogger(copyjobquestionspage.class.getName());
	private ExtentTest test = ExtentManager.getinstance().createTest("Copy Job Test");

	private final String searchclient = "#emailValue > div > input";
	private final String enterclientid = "#emailValue > div > input";
	private final String selectclient = "#emailValue_0";
	private final String jobsmodule = "//span[contains(text(),'Jobs')]";
	private final String copybutton = "//span[contains(text(),'Copy')]";
	private final String searchclientid = "//input[@role='combobox' and @placeholder='Search by account ID or company']";
	private final String selectclientid = "//span[contains(text(),'1901 - atulcompaniesUpdate')]";
	private final String jobsdropdown = "//span[contains(text(),'Select a Job')]";
	private final String searchjob = "//input[@type='text' and @role='searchbox']";
	private final String selectjob = "//span[contains(text(),'TestingCopyJob')]";
	private final String copyjobbutton = "//html/body/div/div/div[2]/div[3]/button";
	private final String savejobbutton = "//button[@type='submit']";

	public copyjobfromclientpage(Page page) {
		this.page = page;
	}

	public void copyjobfromclient(String clientid, String id, String jobname) {
		logger.info("🔍 Starting 'copyquestions' for client ID: " + clientid);
		test.log(Status.INFO, "Starting 'copyjob' from another client ID: " + clientid);

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

			page.click(copybutton);
			test.log(Status.INFO, "Clicked on copy button");

			page.fill(searchclientid, id);
			test.log(Status.INFO, "Entered client ID");

			page.click(selectclientid);
			test.log(Status.INFO, "Selected client id");

			page.click(jobsdropdown);
			test.log(Status.INFO, "Clicked on job dropdown");

			/*
			 * for (int i = 2; i < 3; i++) {
			 * page.click("//p-multiselectitem//li[@role='option' and @aria-posinset=" + i +
			 * "]"); test.log(Status.INFO, "Selected job option: " + i); }
			 */

			page.fill(searchjob, jobname);
			test.log(Status.INFO, "Entered job name");

			page.click(selectjob);
			test.log(Status.INFO, "Selected job name");

			page.click(copyjobbutton);
			test.log(Status.INFO, "Clicked copy job button");

			page.click(savejobbutton);
			test.log(Status.INFO, "Clicked save job button");

			page.waitForTimeout(5000);
			test.log(Status.PASS, "'copy job from client' completed successfully");

		} catch (Exception e) {
			test.log(Status.FAIL, "Exception in 'copy job': " + e.getMessage());
			logger.log(Level.SEVERE, "❌ Exception in 'copy job': " + e.getMessage(), e);
			throw e;
		}
	}

	public void testcopiedjob() {
		logger.info("🔍 Starting 'testcopiedquestion'...");
		test.log(Status.INFO, "Starting 'testcopiedquestion'...");

		try {
			List<String> jobcopied = List.of("TestingCopyJob");

			for (String jobname : jobcopied) {
				Locator jobLocator = page.locator("text=" + jobname);
				jobLocator.scrollIntoViewIfNeeded();

				if (!jobLocator.isVisible()) {
					test.log(Status.FAIL, "Job not copied: " + jobname);
					throw new AssertionError("Expected job not found: " + jobname);
				}

				test.log(Status.PASS, "Verified question: " + jobname);
				jobLocator.evaluate(
						"el => { el.style.border = '3px solid red'; setTimeout(() => el.style.border = '', 2000); }");
			}

			page.waitForTimeout(5000);
			test.log(Status.PASS, "copied job is present and verified.");
		} catch (AssertionError ae) {
			test.log(Status.FAIL, "Assertion failed: " + ae.getMessage());
			throw ae;
		} catch (Exception e) {
			test.log(Status.FAIL, "Exception in 'testcopiedjob': " + e.getMessage());
			throw e;
		}
	}
}
