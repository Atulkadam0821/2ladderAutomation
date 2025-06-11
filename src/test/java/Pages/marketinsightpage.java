package Pages;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import Base.base;
import utils.ExtentManager;

public class marketinsightpage extends base {
	private final Page page;
	private static final Logger logger = Logger.getLogger(marketinsightpage.class.getName());
	private ExtentTest test = ExtentManager.getinstance().createTest("Market Insights Test");

	// Locators
	private final String searchclient = "#emailValue > div > input";
	private final String enterclientid = "#emailValue > div > input";
	private final String selectclient = "#emailValue_0";
	private final String jobsmodule = "//span[contains(text(),'Jobs')]";
	private final String editbutton = "//button[@icon='pi pi-pencil']";
	private final String marketinsight = "//span[contains(text(),'Market Insights')]";
	
	public marketinsightpage(Page page) {
		this.page = page;
	}

	public void marketinsight(String clientid) {
		logger.info("📊 Starting Market Insight upload for client ID: " + clientid);
		test.log(Status.INFO, "📊 Starting Market Insight upload for client ID: " + clientid);

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

			logger.info("✏️ Scrolling to Edit Job button...");
			test.log(Status.INFO, "✏️ Scrolling to Edit Job button...");
			page.locator(editbutton).nth(1).scrollIntoViewIfNeeded();
			page.waitForTimeout(5000);

			logger.info("🖱️ Clicking Edit Job...");
			test.log(Status.INFO, "🖱️ Clicking Edit Job...");
			page.click(editbutton);

			logger.info("📈 Opening Market Insights tab...");
			test.log(Status.INFO, "📈 Opening Market Insights tab...");
			page.click(marketinsight);

			logger.info("📤 Uploading summary report...");
			test.log(Status.INFO, "📤 Uploading summary report...");
			//page.click(uploadsummary);
			Path summaryFile = Paths.get("marketinsight/summaryupload.pdf");
			page.locator("//input[@type='file' and @name='uploadSummary']").setInputFiles(summaryFile);
			page.waitForTimeout(1000);

			logger.info("📤 Uploading full report...");
			test.log(Status.INFO, "📤 Uploading full report...");
			//page.waitForSelector(uploadfullreport);
			//page.click(uploadfullreport);
			Path fullReportFile = Paths.get("marketinsight/wipo_ip_bkk_17_15.pdf");
			page.locator("//input[@type='file' and @name='uploadReport']").setInputFiles(fullReportFile);
			page.waitForTimeout(5000);
			logger.info("✅ Market Insight upload completed successfully.");
			test.log(Status.PASS, "✅ Market Insight upload completed successfully.");
			

		} catch (Exception e) {
			logger.log(Level.SEVERE, "❌ Market Insight upload failed: " + e.getMessage(), e);
			test.log(Status.FAIL, "❌ Market Insight upload failed: " + e.getMessage());
			throw e;
		}
	}
}