package Pages;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import Base.base;
import utils.ExtentManager;

public class assesmentcriteriapage extends base {
	private final Page page;
	private static final Logger logger = Logger.getLogger(assesmentcriteriapage.class.getName());
	private ExtentTest test = ExtentManager.getinstance().createTest("Job Assessment Criteria Test");

	// Locators
	private final String searchclient = "#emailValue > div > input";
	private final String enterclientid = "#emailValue > div > input";
	private final String selectclient = "#emailValue_0";
	private final String jobsmodule = "//span[contains(text(),'Jobs')]";
	// private final String editbutton =
	// "//tr[td[contains(text(),'Automationjob')]]//button[contains(@ptooltip,'Copy
	// Job Question')]";
	private final String assesmentcriteria = "//span[contains(text(),'Assessment Criteria')]";
	private final String airankingcheckbox = "#isAddAIRank > div > div.p-checkbox-box";
	private final String aitext = "//input[@placeholder='Describe the ideal candidate or describe your absolute must-haves']";
	private final String setrule = "//span[contains(text(),'Set Rule')]";
	private final String changestatusto = "//p-dropdown[@formcontrolname='status']";
	private final String selectstatus = "//span[contains(text(),'New')]";
	private final String condition = "//p-dropdown[@formcontrolname='condition']";
	private final String selectcondition = "//span[contains(text(),'Equals')]";
	private final String rank = "//p-dropdown[@formcontrolname='rankValue']";
	private final String selectrank = "//li[@aria-label='5']";
	private final String setbutton = "//button[@label='Set']";
	private final String resumescanningcheckbox = "#isResumeScanningOn > div > div.p-checkbox-box";
	private final String resumekeywords = "//input[@placeholder='Add keywords and press Enter']";
	private final String qualifyingquestioncheckbox = "#isQualifyingQuestionsOn > div > div.p-checkbox-box";
	private final String addquestionbutton = "//span[contains(text(),'Add Questions')]";
	private final String addquestionvia = "//p-dropdown[@formcontrolname='addQuestionVia']";
	private final String selectaddquestionvia = "//span[contains(text(),'Select from Existing Questions')]";
	private final String type = "//p-dropdown[@formcontrolname='type']";
	private final String selecttype = "//li/span[contains(text(),'Yes/No')]";
	private final String questionsdropdown = "//p-dropdown[@formcontrolname='questionId']";
	private final String savequestion = "//html/body/app-root/div/app-layout/div/div[1]/div[2]/app-job/div/div[2]/div/app-job-dataview/app-job-detail/p-sidebar/div/div[2]/p-tabview/div/div[2]/p-tabpanel[4]/div/div/app-job-assessment-criteria/p-sidebar[2]/div/div[3]/div/button[2]";
	private final String saveassesmentpage = "//span[contains(text(),'Save')]";
	private final String createnewquestion = "//span[contains(text(),'Create New Question')]";
	private final String openednded = "//li/span[contains(text(),'Open Ended')]";
	private final String category = "//div[contains(text(),'Select Category')]";
	private final String selectcategory = "//li[@aria-posinset='2']";
	private final String enterquestion = "//input[@placeholder='Enter question']";

	public assesmentcriteriapage(Page page) {
		this.page = page;
	}

	@SuppressWarnings("deprecation")
	public void assesmentcriteria(String clientid, String text, String jobTitle, String keyword1, String Question) {
		logger.info("🔍 Starting assessment criteria configuration for client: " + clientid);
		test.log(Status.INFO, "🔍 Starting assessment criteria configuration for client: " + clientid);

		try {
			page.evaluate("document.body.style.zoom = '80%'");

			test.log(Status.INFO, "👉 Searching and selecting client");
			page.locator(searchclient).click(new Locator.ClickOptions().setClickCount(3));
			page.fill(enterclientid, clientid);
			page.click(selectclient);

			test.log(Status.INFO, "📂 Navigating to Jobs module");
			page.click(jobsmodule);
			page.locator("//tr[td[contains(text(),'" + jobTitle + "')]]//button[contains(@ptooltip,'Edit Job')]")
					.scrollIntoViewIfNeeded();
			page.waitForTimeout(5000);

			test.log(Status.INFO, "🖱️ Opening job for editing");
			page.click("//tr[td[contains(text(),'" + jobTitle + "')]]//button[contains(@ptooltip,'Edit Job')]");
			page.click(assesmentcriteria);

			test.log(Status.INFO, "🧠 Configuring AI Ranking");
			page.click(airankingcheckbox);
			page.fill(aitext, text);
			page.click(setrule);
			page.click(changestatusto);
			page.click(selectstatus);
			page.click(condition);
			page.click(selectcondition);
			page.click(rank);
			page.click(selectrank);
			page.click(setbutton);

			test.log(Status.INFO, "📄 Enabling Resume Scanning");
			page.click(resumescanningcheckbox);
			page.type(resumekeywords, keyword1);

			test.log(Status.INFO, "📌 Enabling Qualifying Questions");
			page.click(qualifyingquestioncheckbox);

			for (int i = 2; i < 5; i++) {
				test.log(Status.INFO, "➕ Adding qualifying question #" + (i - 1));
				page.click(addquestionbutton);
				page.click(addquestionvia);
				page.click(selectaddquestionvia);
				page.click(type);
				page.click(selecttype);
				page.click(questionsdropdown);
				page.click("//p-dropdown//li[@role='option' and @aria-posinset=" + i + "]");
				page.click(savequestion);
			}

			page.click(addquestionbutton);
			page.click(addquestionvia);
			page.click(createnewquestion);
			page.click(type);
			page.click(openednded);
			page.click(category);
			page.click(selectcategory);
			page.fill(enterquestion, Question);
			page.click(savequestion);

			for (int j = 1; j < 4; j++) {
				test.log(Status.INFO, "📋 Configuring display setting for question row " + j);
				page.click("(//table)[2]//tr[" + j + "]//p-dropdown[@formcontrolname='displayOn']");
				page.waitForTimeout(2000);
				page.click("//p-dropdownitem//li[@role='option' and @aria-posinset=" + (j + 1) + "]");
			}

			page.click(saveassesmentpage);
			test.log(Status.PASS, "✅ Assessment Criteria configuration completed successfully for client: " + clientid);

		} catch (Exception e) {
			test.log(Status.FAIL, "❌ Exception occurred: " + e.getMessage());
			logger.log(Level.SEVERE, "❌ Exception during assessment criteria configuration: " + e.getMessage(), e);
			throw e;
		}
	}
}