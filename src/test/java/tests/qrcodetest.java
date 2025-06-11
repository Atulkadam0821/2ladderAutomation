package tests;

import org.junit.jupiter.api.Test;

import Base.base;
import Pages.qrcodepage;

public class qrcodetest extends base {
	@Test
	public void editjob() throws Exception {
		// loginpage loginPage = new loginpage(page);
		// loginPage.login("gunja23022@gmail.com", "Gunja@123");
		qrcodepage Qrcodepage = new qrcodepage(page);
		Qrcodepage.qrcode("2019");
	}
}
