package Pages;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import Base.base;
import utils.ExtentManager;

public class fromdocumentpage extends base {
	private final Page page;
	private static final Logger logger = Logger.getLogger(assesmentcriteriapage.class.getName());
	private ExtentTest test = ExtentManager.getinstance().createTest("From Document Test");
	private final String searchclient = "#emailValue > div > input";
	private final String enterclientid = "#emailValue > div > input";
	private final String selectclient = "#emailValue_0";
	private final String jobsmodule = "//span[contains(text(),'Jobs')]";
	private final String fromdocbutton = "//span[contains(text(),'From Document')]";
	private final String uploaddocument = "//span[contains(text(),' Upload Document')]";
	private final String Createbutton = "//button[@label='Create']";
	private final String savejob = "//button[@type='submit']";

	public fromdocumentpage(Page page) {
		this.page = page;
	}

	public void fromdocument(String clientid) {
		try {
			page.evaluate("document.body.style.zoom = '80%'");

			test.log(Status.INFO, "👉 Searching and selecting client");
			page.locator(searchclient).click(new Locator.ClickOptions().setClickCount(3));
			page.fill(enterclientid, clientid);
			page.click(selectclient);

			test.log(Status.INFO, "📂 Navigating to Jobs module");
			page.click(jobsmodule);
			page.waitForTimeout(5000);
			page.click(fromdocbutton);
			// page.click(uploaddocument);

			logger.info("📤 Uploading document...");
			test.log(Status.INFO, "📤 Uploading Document...");
			Path documentFile = Paths.get("fromdocument/Job Description HTE Service Technicians.docx");
			page.locator(uploaddocument).setInputFiles(documentFile);
			page.waitForTimeout(1000);
			page.click(Createbutton);
			page.waitForTimeout(10000);
			String jobTitle = page.inputValue("#jobTitle > div > input");
			page.click(savejob);
			page.waitForTimeout(3000);
			List<String> jobadded = List.of(jobTitle);
			for (String job : jobadded) {
				Locator joblocator = page.locator("text=" + job);
				joblocator.scrollIntoViewIfNeeded();

				if (!joblocator.isVisible()) {
					test.log(Status.FAIL, "❌ job not found: " + job);
					logger.severe("❌ job not found: " + job);
					throw new AssertionError("job not found: " + job);
				}
				joblocator.evaluate(
						"el => { el.style.backgroundColor = 'yellow';setTimeout(() => el.style.backgroundColor = '', 1000); }");

				test.log(Status.PASS, "✅ job created through document found and highlighted: " + job);
				logger.info("✅ job created through document found and highlighted: " + job);
			}

		} catch (Exception e) {
			test.log(Status.FAIL, "❌ Exception occurred: " + e.getMessage());
			logger.log(Level.SEVERE, "❌ Exception during from document: " + e.getMessage(), e);
			throw e;
		}
	}
}
