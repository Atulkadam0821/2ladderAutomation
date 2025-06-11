package tests;

import org.junit.jupiter.api.Test;

import Base.base;
import Pages.marketinsightpage;

public class marketinsighttest extends base {
	@Test
	public void uploadsummaryandfullreport() {
		// loginpage loginPage = new loginpage(page);
		// loginPage.login("gunja23022@gmail.com", "Gunja@123");
		marketinsightpage marketinsight = new marketinsightpage(page);
		marketinsight.marketinsight("2019");
	}
}
