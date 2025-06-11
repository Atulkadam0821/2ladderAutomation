package tests;

import org.junit.jupiter.api.Test;

import Base.base;
import Pages.fromdocumentpage;

public class fromdocumenttest extends base {
	@Test
	public void uploadsummaryandfullreport()
	{
		//loginpage loginPage = new loginpage(page);
		//loginPage.login("gunja23022@gmail.com", "Gunja@123");
		fromdocumentpage fromdocumentpage = new fromdocumentpage(page);
		fromdocumentpage.fromdocument("2019");
	}
}
