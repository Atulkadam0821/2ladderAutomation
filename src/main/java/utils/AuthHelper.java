package utils;
import com.microsoft.playwright.*;
import java.nio.file.Paths;

public class AuthHelper {
	public static void saveAuthState() {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			BrowserContext context = browser.newContext();
			Page page = context.newPage();

			page.navigate("https://qa.my2l.com");

			// Replace with actual selectors and credentials
			page.fill("#\\31 -email", "gunja23022@gmail.com");
			page.fill("#\\31 -password", "Gunja@123");
			page.click("#\\31 -submit");
			page.click("//div[@aria-haspopup='listbox']");
			page.click("//li[@aria-posinset='1']");
			page.click("//html/body/app-root/app-multiple-account-dialog/p-dialog/div/div/div[4]/button");

			// Wait for successful login indicator
			//page.waitForSelector("text=Logout"); // or a unique dashboard element

			// Save session state
			context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("auth.json")));

			browser.close();
		}
	}

	// ✅ Main method to run manually
	public static void main(String[] args) {
		saveAuthState();
		System.out.println("✅ Auth state saved to auth.json");
	}
}
