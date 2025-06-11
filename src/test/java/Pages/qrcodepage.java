package Pages;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import Base.base;
import utils.ExtentManager;

public class qrcodepage extends base {
	private final Page page;
	private static final Logger logger = Logger.getLogger(assesmentcriteriapage.class.getName());
	private ExtentTest test = ExtentManager.getinstance().createTest("Job QR Code Test");

	private final String searchclient = "#emailValue > div > input";
	private final String enterclientid = "#emailValue > div > input";
	private final String selectclient = "#emailValue_0";
	private final String jobsmodule = "//span[contains(text(),'Jobs')]";
	private final String selectqrtype = "//span[contains(text(),'Twitter')]";
	
	public qrcodepage(Page page) {
		this.page = page;
	}
	
	public void qrcode(String clientid) throws Exception
	{
		try
		{
			page.evaluate("document.body.style.zoom = '80%'");

			test.log(Status.INFO, "👉 Searching and selecting client");
			page.locator(searchclient).click(new Locator.ClickOptions().setClickCount(3));
			page.fill(enterclientid, clientid);
			page.click(selectclient);

			test.log(Status.INFO, "📂 Navigating to Jobs module");
			page.click(jobsmodule);
			page.locator("//tr[td[contains(text(),'testjob')]]//i[contains(@ptooltip,'Download QR')]")
					.scrollIntoViewIfNeeded();
			//page.waitForTimeout(5000);
			page.click("//tr[td[contains(text(),'testjob')]]//i[contains(@ptooltip,'Download QR')]");
			Download download = page.waitForDownload(() -> {
                page.click(selectqrtype);
            });
			Path qrPath = download.path();
            File qrFile = qrPath.toFile();

            // Step 3: Decode QR Code using ZXing
            BufferedImage bufferedImage = ImageIO.read(qrFile);
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);

            String qrUrl = result.getText();
            System.out.println("🔍 QR Code URL: " + qrUrl);

            // Step 4: Open the QR URL in new Playwright page and validate
            Page jobApplyPage = page.context().newPage();
            jobApplyPage.navigate(qrUrl);

            if (jobApplyPage.url().contains("job/apply") || jobApplyPage.locator("text=Quick Apply").isVisible()) {
                System.out.println("✅ Job apply page loaded successfully!");
            } else {
                System.out.println("❌ Job apply page not found or invalid QR code link.");
            }

		}
		catch (Exception e) {
			test.log(Status.FAIL, "❌ Exception occurred: " + e.getMessage());
			logger.log(Level.SEVERE, "❌ Exception during assessment criteria configuration: " + e.getMessage(), e);
			throw e;
		}
	}
}
