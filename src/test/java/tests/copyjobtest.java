package tests;

import org.junit.jupiter.api.Test;

import Base.base;
import Pages.copyjobpage;

public class copyjobtest extends base {
	@Test
	public void copyjob()
	{
		//loginpage loginPage = new loginpage(page);
		//loginPage.login("gunja23022@gmail.com", "Gunja@123");
		copyjobpage copyjob = new copyjobpage(page);
		copyjob.jobcopy("2019","copiedjobtitle");
		copyjob.selectdates();
		copyjob.savecopiedjob("copiedjobtitle");
	}
}
