package Pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import Base.base;
import utils.ExtentManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class teamsandrolepage extends base {
	private final Page page;
	private static final Logger logger = Logger.getLogger(teamsandrolepage.class.getName());
	private ExtentTest test = ExtentManager.getinstance().createTest("Teams & Roles Test");


	// Locators
	private final String searchclient = "#emailValue > div > input";
	private final String enterclientid = "#emailValue > div > input";
	private final String selectclient = "#emailValue_0";
	private final String jobsmodule = "//span[contains(text(),'Jobs')]";
	private final String editbutton = "//button[@icon='pi pi-pencil']";
	private final String teamsandroles = "//span[contains(text(),'Team & Roles')]";
	private final String adduser = "//span[contains(text(),'Add User')]";
	private final String email = "#email";
	private final String firstName = "#firstName";
	private final String lastName = "#lastName";
	private final String reqapprovalneeded = "#isReqApprovalNeeded > div > div.p-checkbox-box";
	private final String role = "//p-dropdown[@placeholder='Select a Role']";
	private final String saveperson = "//button[@label='Save']";
	private final String selectrole = "//span[contains(text(),'HR Staff')]";

	public teamsandrolepage(Page page) {
		this.page = page;
	}

	public void addperson(String clientid, String emailid, String firstname, String lastname) {
		logger.info("👤 Starting process to add user to Team & Roles for client: " + clientid);
		test.log(Status.INFO, "👤 Starting process to add user to Team & Roles for client: " + clientid);

		try {
			page.evaluate("document.body.style.zoom = '80%'");

			logger.info("🔍 Searching for client ID...");
			test.log(Status.INFO, "🔍 Searching for client ID...");
			page.locator(searchclient).click(new Locator.ClickOptions().setClickCount(3));
			page.fill(enterclientid, clientid);
			page.click(selectclient);

			logger.info("📂 Navigating to Jobs module...");
			test.log(Status.INFO, "📂 Navigating to Jobs module...");
			page.click(jobsmodule);

			logger.info("✏️ Scrolling to Edit button...");
			test.log(Status.INFO, "✏️ Scrolling to Edit button...");
			page.locator(editbutton).nth(1).scrollIntoViewIfNeeded();
			page.waitForTimeout(5000);
			page.click(editbutton);

			logger.info("👥 Opening 'Team & Roles' section...");
			test.log(Status.INFO, "👥 Opening 'Team & Roles' section...");
			page.click(teamsandroles);

			logger.info("➕ Clicking 'Add User'...");
			test.log(Status.INFO, "➕ Clicking 'Add User'...");
			page.click(adduser);

			logger.info("📧 Entering user email: " + emailid);
			test.log(Status.INFO, "📧 Entering user email: " + emailid);
			page.fill(email, emailid);

			logger.info("🧍 Entering user first name: " + firstname);
			test.log(Status.INFO, "🧍 Entering user first name: " + firstname);
			page.fill(firstName, firstname);

			logger.info("🧍 Entering user last name: " + lastname);
			test.log(Status.INFO, "🧍 Entering user last name: " + lastname);
			page.fill(lastName, lastname);

			logger.info("☑️ Clicking 'Request Approval Needed' checkbox...");
			test.log(Status.INFO, "☑️ Clicking 'Request Approval Needed' checkbox...");
			page.click(reqapprovalneeded);

			logger.info("📜 Selecting role...");
			test.log(Status.INFO, "📜 Selecting role...");
			page.click(role);
			page.click(selectrole);

			logger.info("💾 Saving the new team member...");
			test.log(Status.INFO, "💾 Saving the new team member...");
			page.click(saveperson);

			logger.info("✅ User successfully added to Team & Roles.");
			test.log(Status.PASS, "✅ User successfully added to Team & Roles.");

		} catch (Exception e) {
			logger.log(Level.SEVERE, "❌ Error while adding user to Team & Roles: " + e.getMessage(), e);
			test.log(Status.FAIL, "❌ Error while adding user to Team & Roles: " + e.getMessage());
			throw e;
		}
	}
}
