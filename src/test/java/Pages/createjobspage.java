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

public class createjobspage extends base {
	private final Page page;
	// private final String createdJobTitle;;
	private static final Logger logger = Logger.getLogger(createjobspage.class.getName());
	private ExtentTest test = ExtentManager.getinstance().createTest("Create Job Test");
	private String jobName;

	// Locators
	private final String searchclient = "#emailValue > div > input";
	private final String enterclientid = "#emailValue > div > input";
	private final String selectclient = "#emailValue_0";
	private final String jobsmodule = "//span[contains(text(),'Jobs')]";
	private final String addjob = "//button[@label='Add Job'] ";
	private final String jobtitle = "#jobTitle > div > input";
	private final String savejob = "//button[@type='submit']";
	private final String dateposted = "#datePosted > span > button";
	private final String indefinitely = "//label[@for='Indefinitely']";
	private final String addbenefiticon = "//div/app-job-info/div/div/form/div/div[26]/button";
	private final String benefittitle = "//input[@id='benefitTitle']";
	private final String savebenefit = "//div/app-job-info/p-sidebar[1]/div/div[3]/div/button[2]";

	public createjobspage(Page page) {
		this.page = page;

	}

	public void createjob(String clientid, String jobname) {

		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		logger.info("🚀 Starting job creation for client ID: " + clientid + " with job name: " + jobname);
		test.log(Status.INFO, "🚀 Starting job creation for client ID: " + clientid + " with job name: " + jobname);

		this.jobName = jobname;

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

			logger.info("➕ Clicking Add Job button...");
			test.log(Status.INFO, "➕ Clicking Add Job button...");
			page.click(addjob);

			logger.info("✏️ Entering job title: " + jobname);
			test.log(Status.INFO, "✏️ Entering job title: " + jobname);
			page.fill(jobtitle, jobname);

			page.waitForTimeout(8000);
			logger.info("✅ Job creation step completed.");
			test.log(Status.PASS, "✅ Job creation step completed.");

		} catch (Exception e) {
			logger.log(Level.SEVERE, "❌ Error during job creation: " + e.getMessage(), e);
			test.log(Status.FAIL, "❌ Error during job creation: " + e.getMessage());
			throw e;
		}
	}

	public void selectdate(String benefit) {
		logger.info("📅 Starting date selection and checkbox interaction...");
		test.log(Status.INFO, "📅 Starting date selection and checkbox interaction...");

		try {
			page.click(dateposted);
			logger.info("➡️ Clicking to navigate to next month...");
			test.log(Status.INFO, "➡️ Clicking to navigate to next month...");
			page.click("//button[@aria-label='Next Month']");

			logger.info("📌 Selecting specific date: July 16, 2025");
			test.log(Status.INFO, "📌 Selecting specific date: July 16, 2025");
			page.click("//span[@data-date='2025-7-16']");

			logger.info("☑️ Checking 'Indefinitely' checkbox...");
			test.log(Status.INFO, "☑️ Checking 'Indefinitely' checkbox...");
			page.click(indefinitely);

			logger.info("☑️ Clicking add benefit button");
			test.log(Status.INFO, "☑️ Clicking add benefit button");
			page.click(addbenefiticon);

			logger.info("☑️ Entering benefit name");
			test.log(Status.INFO, "☑️ Entering benefit name");
			page.fill(benefittitle, benefit);

			logger.info("☑️ Saving benefit name");
			test.log(Status.INFO, "☑️ Saving benefit name");
			page.click(savebenefit);

			logger.info("☑️ Selecting benefit name");
			test.log(Status.INFO, "☑️ Selecting benefit name");
			page.click("//div[label[contains(text(), '" + benefit + "')]]//div[contains(@class, 'p-checkbox-box')]");

			logger.info("✅ Date selection and checkbox complete.");
			test.log(Status.PASS, "✅ Date selection and checkbox complete.");

		} catch (Exception e) {
			logger.log(Level.SEVERE, "❌ Error during date selection: " + e.getMessage(), e);
			test.log(Status.FAIL, "❌ Error during date selection: " + e.getMessage());
			throw e;
		}
	}

	public void savejob() {
		logger.info("💾 Attempting to save job...");
		test.log(Status.INFO, "💾 Attempting to save job...");

		try {
			page.click(savejob);
			logger.info("✅ Job saved successfully.");
			test.log(Status.PASS, "✅ Job saved successfully.");
			page.click("//tr[td[contains(text(),'" + jobName + "')]]//div[contains(@aria-haspopup,'listbox')]");
			page.click("//span[contains(text(),'Internal')]");
			page.click("//tr[td[contains(text(),'" + jobName
					+ "')]]//p-multiselect[contains(@placeholder,'Select Integrations')]");
			page.click("//li[@aria-posinset='3']");
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
			logger.log(Level.SEVERE, "❌ Error while saving job: " + e.getMessage(), e);
			test.log(Status.FAIL, "❌ Error while saving job: " + e.getMessage());
			throw e;
		}
	}
}
