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

public class jobapplypage extends base {
	private final Page page;
	private static final Logger logger = Logger.getLogger(copyjobquestionspage.class.getName());
	private ExtentTest test = ExtentManager.getinstance().createTest("Copy Job Test");
	private String candidateName;
	private String jobName;

	private final String searchclient = "#emailValue > div > input";
	private final String enterclientid = "#emailValue > div > input";
	private final String selectclient = "#emailValue_0";
	private final String jobsmodule = "//span[contains(text(),'Jobs')]";
	private final String firstname = "#name";
	private final String lastname = "#lastName";
	private final String phonenumber = "#phone";
	private final String emailid = "#email";
	private final String quickapplybutton = "//button[@label='Quick Apply']";
	private final String Submitbutton = "//button[@label='SUBMIT']";
	private final String candidatestab = "//span[contains(text(),'Candidates')]";
	private final String applyvia = "//span[contains(text(),'Quick Apply')]";
	private final String fullpage = "//span[contains(text(),'Full Page Application')]";
	private final String savejob = "//button[@type='submit']";
	private final String fullfirstname = "//input[@formcontrolname='firstName']";
	private final String fulllastname = "//input[@formcontrolname='lastName']";
	private final String email = "//input[@formcontrolname='email']";
	private final String positiondropdown = "//span[contains(text(),'Select a job.')]";
	private final String selectposition = "//li[@aria-posinset='1']";
	private final String shiftdropdown = "//div[contains(text(),'Shift Availability')]";
	private final String Selectshift = "//li[@aria-posinset='1']";
	private final String calltexttoggle = "//span[@class='p-inputswitch-slider']";
	private final String submit = "//button[@label='Submit']";

	public jobapplypage(Page page) {
		this.page = page;
	}

	public void jobapply(String clientid, String jobTitle, String name, String Lastname, String email, String number) {
		logger.info("🔍 Starting 'job apply' for client ID: " + clientid);
		test.log(Status.INFO, "Starting 'job apply' from another client ID: " + clientid);
		this.candidateName = name;
		this.jobName = jobTitle;

		try {
			page.evaluate("document.body.style.zoom = '80%'");
			test.log(Status.INFO, "Zoom adjusted to 80%");

			page.locator(searchclient).click(new Locator.ClickOptions().setClickCount(3));
			test.log(Status.INFO, "Clicked on client search box");

			page.fill(enterclientid, clientid);
			test.log(Status.INFO, "Entered client ID");

			page.click(selectclient);
			test.log(Status.INFO, "Selected client from dropdown");

			page.click(jobsmodule);
			test.log(Status.INFO, "Navigated to Jobs module");
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
						"el => { el.style.border = '3px solid red'; setTimeout(() => el.style.border = '', 800); }");
				test.log(Status.PASS, "✅ job found and highlighted: " + job);
				logger.info("✅ job found and highlighted: " + job);
			}

			page.waitForTimeout(5000);
			test.log(Status.PASS, "✅ job name verified successfully.");
			logger.info("✅ job name verified and highlighted.");

			String dynamicXPath = "//tr[td[contains(text(),'" + jobTitle + "')]]//a[contains(@href,'https:')]";

			Page popup = page.waitForPopup(() -> {
				page.click(dynamicXPath); // Replace selector with the one for your URL
			});
			popup.waitForLoadState();

			popup.fill(firstname, name);
			popup.fill(lastname, Lastname);
			popup.fill(phonenumber, number);
			popup.fill(emailid, email);
			popup.click(quickapplybutton);
			popup.click(Submitbutton);
			page.bringToFront();

			page.locator("//tr[td[contains(text(),'" + jobTitle + "')]]//button[contains(@ptooltip,'Edit Job')]")
					.scrollIntoViewIfNeeded();
			page.click("//tr[td[contains(text(),'" + jobTitle + "')]]//button[contains(@ptooltip,'Edit Job')]");

		} catch (Exception e) {
			test.log(Status.FAIL, "Exception in 'copy job': " + e.getMessage());
			logger.log(Level.SEVERE, "❌ Exception in 'copy job': " + e.getMessage(), e);
			throw e;
		}
	}

	public void fullpage(String jobTitle, String fullpagename, String fullpagelastname, String emails) {
		try {
			page.click(applyvia);
			page.click(fullpage);
			page.click(savejob);
			String dynamicXPath = "//tr[td[contains(text(),'" + jobTitle + "')]]//a[contains(@href,'https:')]";

			Page popup = page.waitForPopup(() -> {
				page.click(dynamicXPath); // Replace selector with the one for your URL
			});
			popup.waitForLoadState();
			popup.fill(fullfirstname, fullpagename);
			popup.fill(fulllastname, fullpagelastname);
			popup.fill(email, emails);
			popup.click(positiondropdown);
			popup.click(selectposition);
			popup.click(shiftdropdown);
			popup.click(Selectshift);
			popup.click(calltexttoggle);
			popup.click(submit);
			
			page.bringToFront();
			page.locator("//tr[td[contains(text(),'" + jobTitle + "')]]//button[contains(@ptooltip,'Edit Job')]")
					.scrollIntoViewIfNeeded();
			page.click("//tr[td[contains(text(),'" + jobTitle + "')]]//button[contains(@ptooltip,'Edit Job')]");

		} catch (Exception e) {
			test.log(Status.FAIL, "Exception in 'copy job': " + e.getMessage());
			logger.log(Level.SEVERE, "❌ Exception in 'copy job': " + e.getMessage(), e);
			throw e;
		}
	}

	public void getcandidates() {
		try {
			page.click(candidatestab);
			// page.wait(3000);
			List<String> expectedcandidates = List.of(candidateName);

			for (String candidate : expectedcandidates) {
				Locator candidatelocator = page
						.locator("//span[contains(@class, 'truncate-text') and contains(text(), '" + candidate + "')]");
				candidatelocator.scrollIntoViewIfNeeded();

				if (!candidatelocator.isVisible()) {
					test.log(Status.FAIL, "❌ candidate not found: " + candidate);
					logger.severe("❌ candidate not found: " + candidate);
					throw new AssertionError("candidate not found: " + candidate);
				}

				candidatelocator.evaluate(
						"el => { el.style.border = '3px solid red'; setTimeout(() => el.style.border = '', 800); }");
				test.log(Status.PASS, "✅ cadidate for job found and highlighted: " + candidate);
				logger.info("✅ cadidate for job found and highlighted: " + candidate);
			}

			page.waitForTimeout(5000);
			test.log(Status.PASS, "✅ All expected candidates applied for job verified successfully.");
			logger.info("✅ All expected candidates applied for job verified and highlighted.");

		} catch (Exception e) {
			test.log(Status.FAIL, "Exception in 'apply job': " + e.getMessage());
			logger.log(Level.SEVERE, "❌ Exception in 'apply job': " + e.getMessage(), e);
			throw e;
		}
	}
}
