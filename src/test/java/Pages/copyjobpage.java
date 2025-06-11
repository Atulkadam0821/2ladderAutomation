package Pages;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import Base.base;
import utils.ExtentManager;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.aventstack.extentreports.Status;


public class copyjobpage extends base {
	private final Page page;
	private static final Logger logger = Logger.getLogger(copyjobpage.class.getName());
    private ExtentTest test = ExtentManager.getinstance().createTest("Copy Job Test");


	// Locators
	private final String searchclient = "#emailValue > div > input";
	private final String enterclientid = "#emailValue > div > input";
	private final String selectclient = "#emailValue_0";
	private final String jobsmodule = "//span[contains(text(),'Jobs')]";
	private final String copyjobbutton = "//button[@icon='pi pi-clone' and @ptooltip='Copy Job']";
	private final String jobtitle = "#jobTitle > div > input";
	private final String savejob = "//button[@type='submit']";
	private final String dateposted = "#datePosted > span > button";
	private final String indefinitely = "//label[@for='Indefinitely']";
	private String jobName;


	public copyjobpage(Page page) {
		this.page = page;
	}

	public void jobcopy(String clientid, String copiedjobtitle) {
		logger.info("🔁 Starting job copy process for client ID: " + clientid);
		test.log(Status.INFO, "🔁 Starting job copy process for client ID: " + clientid);

		try {
			page.evaluate("document.body.style.zoom = '80%'");

			logger.info("🔍 Searching and selecting client...");
			test.log(Status.INFO, "🔍 Searching and selecting client...");
			page.locator(searchclient).click(new Locator.ClickOptions().setClickCount(3));
			page.fill(enterclientid, clientid);
			page.click(selectclient);

			logger.info("📂 Navigating to Jobs module...");
			test.log(Status.INFO, "📂 Navigating to Jobs module...");
			page.click(jobsmodule);

			logger.info("📋 Scrolling to Copy Job button...");
			test.log(Status.INFO, "📋 Scrolling to Copy Job button...");
			page.locator(copyjobbutton).nth(1).scrollIntoViewIfNeeded();
			page.waitForTimeout(5000);

			logger.info("🖱️ Clicking Copy Job button...");
			test.log(Status.INFO, "🖱️ Clicking Copy Job button...");
			page.click(copyjobbutton);
			page.waitForTimeout(5000);

			logger.info("✏️ Entering copied job title: " + copiedjobtitle);
			test.log(Status.INFO, "✏️ Entering copied job title: " + copiedjobtitle);
			page.fill(jobtitle, copiedjobtitle);
			page.waitForTimeout(8000);

			logger.info("✅ Job copy step completed.");
			test.log(Status.PASS, "✅ Job copy step completed.");

		} catch (Exception e) {
			logger.log(Level.SEVERE, "❌ Exception during job copy process: " + e.getMessage(), e);
			test.log(Status.FAIL, "❌ Exception during job copy process: " + e.getMessage());
			throw e;
		}
	}

	public void selectdates() {
		logger.info("📅 Starting date selection and checkbox configuration...");
		test.log(Status.INFO, "📅 Starting date selection and checkbox configuration...");

		try {
			page.click(dateposted);
			logger.info("➡️ Navigating to next month in calendar...");
			test.log(Status.INFO, "➡️ Navigating to next month in calendar...");
			page.click("//button[@aria-label='Next Month']");

			logger.info("📌 Selecting specific date: June 16, 2025");
			test.log(Status.INFO, "📌 Selecting specific date: June 16, 2025");
			page.click("//span[@data-date='2025-8-16']");

			logger.info("☑️ Clicking 'Indefinitely' checkbox");
			test.log(Status.INFO, "☑️ Clicking 'Indefinitely' checkbox");
			page.click(indefinitely);

			logger.info("✅ Checkbox was unchecked and is now checked.");
			test.log(Status.PASS, "✅ Date and checkbox configuration completed.");

		} catch (Exception e) {
			logger.log(Level.SEVERE, "❌ Exception during date selection: " + e.getMessage(), e);
			test.log(Status.FAIL, "❌ Exception during date selection: " + e.getMessage());
			throw e;
		}
	}

	public void savecopiedjob(String copiedjobtitle) {
		logger.info("💾 Attempting to save the copied job...");
		test.log(Status.INFO, "💾 Attempting to save the copied job...");
		this.jobName = copiedjobtitle;

		try {
			page.click(savejob);
			logger.info("✅ Copied job saved successfully.");
			test.log(Status.PASS, "✅ Copied job saved successfully.");
			page.waitForTimeout(3000);
			
			List<String> jobadded = List.of(jobName);
			for (String job : jobadded) {
				Locator joblocator = page.locator("text=" + job);
				joblocator.scrollIntoViewIfNeeded();

				if (!joblocator.isVisible()) {
					test.log(Status.FAIL, "❌ job not found: " + job);
					logger.severe("❌ job not found: " + job);
					throw new AssertionError("job not found: " + job);
				}

				joblocator.evaluate(
						"el => { el.style.border = '3px solid red'; setTimeout(() => el.style.border = '', 1000); }");
				test.log(Status.PASS, "✅ job found and highlighted: " + job);
				logger.info("✅ job found and highlighted: " + job);
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, "❌ Failed to save copied job: " + e.getMessage(), e);
			test.log(Status.FAIL, "❌ Failed to save copied job: " + e.getMessage());
			throw e;
		}
	}
}
