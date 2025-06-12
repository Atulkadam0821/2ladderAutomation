package Base;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.*;

import com.microsoft.playwright.options.Geolocation;
import utils.AuthHelper;
import utils.ExtentManager;

public class base {
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    static void setupAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions()
                .setHeadless(true)
                .setSlowMo(2000)
        );

        // ✅ Save auth state only once
        Path authPath = Paths.get("auth.json");
        if (!Files.exists(authPath)) {
            System.out.println("🔐 Auth state not found, logging in...");
            AuthHelper.saveAuthState();  // <- This handles login and saves auth.json
        } else {
            System.out.println("✅ Auth state already exists, using saved session.");
        }
    }

    @BeforeEach
    void setup() {
        // ✅ Load saved auth session with your custom context options
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setPermissions(Arrays.asList("geolocation"))
                .setGeolocation(new Geolocation(37.7749, -122.4194))
                .setLocale("en-In")
                .setStorageStatePath(Paths.get("auth.json")); // Load session

        context = browser.newContext(contextOptions);
        page = context.newPage();
        page.evaluate("document.body.style.zoom = '80%'");
        page.navigate("https://qa.my2l.com");
    }

    @AfterAll
    static void teardownAll() {
        ExtentManager.getinstance().flush();
        browser.close();
        playwright.close();
    }
}
