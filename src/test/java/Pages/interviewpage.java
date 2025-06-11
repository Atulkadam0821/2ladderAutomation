package Pages;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import Base.base;
import utils.ExtentManager;

public class interviewpage extends base {
	private final Page page;
	private static final Logger logger = Logger.getLogger(assesmentcriteriapage.class.getName());
	private ExtentTest test = ExtentManager.getinstance().createTest("Job Interview Tab Test");

	// Locators
	private final String searchclient = "#emailValue > div > input";
	private final String enterclientid = "#emailValue > div > input";
	private final String selectclient = "#emailValue_0";
	private final String jobsmodule = "//span[contains(text(),'Jobs')]";
	private final String editbutton = "//tr[td[contains(text(),'interview')]]//button[contains(@ptooltip,'Edit Job')]";
	private final String interviewtab = "//span[contains(text(),'Interview')]";
	private final String candidatedropdown = "//span[contains(text(),'Select a Candidate')]";
	private final String selectcandidate = "//li[@aria-posinset='2']";
	private final String enterdata = "//div/app-job-interview/div/form/div/div[2]/div[2]/div[4]/p-editor/div/div[2]/div[1]";
	private final String saveinterview = "//span[contains(text(),'Save Interview')]";

	public interviewpage(Page page) {
		this.page = page;
	}

	public void interview(int targetRating, String clientid, String data) {
		try {
			page.evaluate("document.body.style.zoom = '80%'");

			test.log(Status.INFO, "👉 Searching and selecting client");
			page.locator(searchclient).click(new Locator.ClickOptions().setClickCount(3));
			page.fill(enterclientid, clientid);
			page.click(selectclient);

			test.log(Status.INFO, "📂 Navigating to Jobs module");
			page.click(jobsmodule);
			page.locator(editbutton).scrollIntoViewIfNeeded();
			page.waitForTimeout(5000);

			test.log(Status.INFO, "🖱️ Opening job for editing");
			page.click(editbutton);
			page.click(interviewtab);
			page.click(candidatedropdown);
			page.click(selectcandidate);
			for (int i = 1; i < 3; i++) {
				page.click("//div/app-job-interview/div/form/div/div[2]/div[2]/div[" + i + "]/p-inputswitch/div/span");
			}
			page.fill(enterdata, data);

			for (int i = 1; i <= 10; i++) {
				String selector = ".btn-scale-desc-" + i;
				Locator ratingButton = page.locator(selector);

				// Hover over each rating button
				ratingButton.hover();
				//page.waitForTimeout(100); // Optional delay to see hover effect

				// Click the target rating and break
				if (i == targetRating) {
					ratingButton.click();
					break;
				}

			}
			page.click(saveinterview);

		} catch (Exception e) {
			test.log(Status.FAIL, "❌ Exception occurred: " + e.getMessage());
			logger.log(Level.SEVERE, "❌ Exception during assessment criteria configuration: " + e.getMessage(), e);
			throw e;
		}
	}
}
