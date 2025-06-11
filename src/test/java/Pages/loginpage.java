package Pages;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Page;

import Base.base;
import utils.ExtentManager;

public class loginpage extends base {
	private final Page page;
	private static final Logger logger = Logger.getLogger(loginpage.class.getName());
	private ExtentTest test = ExtentManager.getinstance().createTest("Login Test");

	// Locators
	private final String usernameInput = "#\\31 -email";
	private final String passwordInput = "#\\31 -password";
	private final String loginButton = "#\\31 -submit";
	private final String clientdropdown = "//div[@aria-haspopup='listbox']";
	private final String clientoption = "//li[@aria-posinset='1']";
	private final String submitbutton = "#Submit";
	//li[@aria-posinset='1']
	public loginpage(Page page) {
		this.page = page;
	}

	public void login(String username, String password) {
		logger.info("🔐 Starting login process for user: " + username);
		test.log(Status.INFO, "🔐 Starting login process for user: " + username);

		try {
			logger.info("🧾 Filling in username...");
			test.log(Status.INFO, "🧾 Filling in username...");
			page.fill(usernameInput, username);

			logger.info("🔑 Filling in password...");
			test.log(Status.INFO, "🔑 Filling in password...");
			page.fill(passwordInput, password);

			logger.info("▶️ Clicking Login button...");
			test.log(Status.INFO, "▶️ Clicking Login button...");
			page.click(loginButton);

			logger.info("📂 Selecting client from dropdown...");
			test.log(Status.INFO, "📂 Selecting client from dropdown...");
			page.click(clientdropdown);
			page.click(clientoption);

			logger.info("✅ Clicking final Submit...");
			test.log(Status.INFO, "✅ Clicking final Submit...");
			page.getByText(submitbutton).click();

			logger.info("🎉 Login successful for user: " + username);
			test.log(Status.PASS, "🎉 Login successful for user: " + username);
			


		} catch (Exception e) {
			logger.log(Level.SEVERE, "❌ Login failed: " + e.getMessage(), e);
			test.log(Status.FAIL, "❌ Login failed: " + e.getMessage());
			throw e;
		}
	}
}
