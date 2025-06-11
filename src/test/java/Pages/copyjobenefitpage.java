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

public class copyjobenefitpage extends base {
	private final Page page;
	private static final Logger logger = Logger.getLogger(copyjobenefitpage.class.getName());
	private ExtentTest test = ExtentManager.getinstance().createTest("Copy Job Benefit Test");

	private final String searchclient = "#emailValue > div > input";
	private final String enterclientid = "#emailValue > div > input";
	private final String selectclient = "#emailValue_0";
	private final String jobsmodule = "//span[contains(text(),'Jobs')]";
	private final String selectjobsdropdown = "//p-multiselect[@placeholder='Select jobs']";
	private final String copybutton = "//html/body/div/div/form/div/div[2]/button";
	private final String editbutton = "//tr[td[contains(text(),'abc')]]//button[contains(@ptooltip,'Edit Job')]";
	private final String confirmation = "//span[contains(text(),'Yes')]";
	private final String searchjob = "//input[@type='text' and @role='searchbox']";
	private final String selectjob = "//span[contains(text(),'abc')]";

	public copyjobenefitpage(Page page) {
		this.page = page;
	}

	public void copybenefit(String clientid, String jobname, String newJobTitle) {
		logger.info("🔄 Starting benefit copy process for client: " + clientid);
		test.log(Status.INFO, "🔄 Starting benefit copy process for client: " + clientid);
		try {
			page.evaluate("document.body.style.zoom = '80%'");

			test.log(Status.INFO, "📌 Searching and selecting client...");
			page.locator(searchclient).click(new Locator.ClickOptions().setClickCount(3));
			page.fill(enterclientid, clientid);
			page.click(selectclient);

			test.log(Status.INFO, "📂 Navigating to Jobs module...");
			page.click(jobsmodule);

			test.log(Status.INFO, "📥 Scrolling to Copy Job Benefit button...");
			page.locator("//tr[td[contains(text(),'testjob')]]//button[contains(@ptooltip,'Copy Job Benefit')]")
					.scrollIntoViewIfNeeded();
			page.waitForTimeout(5000);

			test.log(Status.INFO, "🖱️ Clicking Copy Job Benefit button...");
			page.locator("//tr[td[contains(text(),'"+newJobTitle+"')]]//button[contains(@ptooltip,'Copy Job Benefit')]").scrollIntoViewIfNeeded();
			page.waitForTimeout(5000);
			page.click("//tr[td[contains(text(),'"+newJobTitle+"')]]//button[contains(@ptooltip,'Copy Job Benefit')]");

			test.log(Status.INFO, "📌 Selecting jobs from dropdown...");
			page.click(selectjobsdropdown);

			page.fill(searchjob, jobname);
			test.log(Status.INFO, "Searched job name");

			page.click(selectjob);
			test.log(Status.INFO, "Selected job from dropdown");

			/*
			 * for (int i = 2; i < 4; i++) { String optionLocator =
			 * "//p-multiselectitem//li[@role='option' and @aria-posinset=" + i + "]";
			 * page.click(optionLocator); test.log(Status.INFO,
			 * "✅ Selected job option at position " + i); }
			 */

			test.log(Status.INFO, "💾 Clicking Copy button...");
			page.click(copybutton);

			test.log(Status.INFO, "☑️ Confirming the copy action...");
			page.click(confirmation);

			page.waitForTimeout(5000);
			test.log(Status.PASS, "✅ Copy benefit process completed successfully.");

		} catch (Exception e) {
			test.log(Status.FAIL, "❌ Exception occurred: " + e.getMessage());
			logger.log(Level.SEVERE, "❌ Exception during copy benefit process: " + e.getMessage(), e);
			throw e;
		}
	}

	public void testcopiedbenefit(String newBenefit ) {
		test.log(Status.INFO, "🔍 Starting verification of copied job benefits...");
		logger.info("🔍 Starting verification of copied job benefits...");
		try {
			
			page.locator(editbutton).scrollIntoViewIfNeeded();
			page.click(editbutton);
			List<String> expectedBenefits = List.of(newBenefit);

			for (String benefit : expectedBenefits) {
				Locator benefitLocator = page.locator("text=" + benefit);
				benefitLocator.scrollIntoViewIfNeeded();

				if (!benefitLocator.isVisible()) {
					test.log(Status.FAIL, "❌ Benefit not found: " + benefit);
					logger.severe("❌ Benefit not found: " + benefit);
					throw new AssertionError("Benefit not found: " + benefit);
				}

				benefitLocator.evaluate(
						"el => { el.style.border = '3px solid red'; setTimeout(() => el.style.border = '', 800); }");
				test.log(Status.PASS, "✅ Benefit found and highlighted: " + benefit);
				logger.info("✅ Benefit found and highlighted: " + benefit);
			}

			page.waitForTimeout(5000);
			test.log(Status.PASS, "✅ All expected benefits verified successfully.");
			logger.info("✅ All expected benefits are verified and highlighted.");

		} catch (Exception e) {
			test.log(Status.FAIL, "❌ Error during benefit verification: " + e.getMessage());
			logger.log(Level.SEVERE, "❌ Error during benefit verification: " + e.getMessage(), e);
			throw e;
		}
	}
}