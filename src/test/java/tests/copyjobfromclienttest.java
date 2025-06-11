package tests;

import org.junit.jupiter.api.Test;

import Base.base;
import Pages.copyjobfromclientpage;

public class copyjobfromclienttest extends base {
	@Test
	public void editjob() {
		// loginpage loginPage = new loginpage(page);
		// loginPage.login("gunja23022@gmail.com", "Gunja@123");
		copyjobfromclientpage copyjob = new copyjobfromclientpage(page);
		copyjob.copyjobfromclient("2019", "1901", "TestingCopyJob");
		copyjob.testcopiedjob();
	}
}