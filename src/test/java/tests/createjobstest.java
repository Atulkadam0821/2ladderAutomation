package tests;

import org.junit.jupiter.api.Test;

import Base.base;
import Pages.createjobspage;

public class createjobstest extends base {
	@Test
	public void createjobtest() {
		String newJobTitle = "Automation Job " + System.currentTimeMillis();
		String newBenefit = "Benefit " + System.currentTimeMillis();

		// loginpage loginPage = new loginpage(page);
		createjobspage Jobspage = new createjobspage(page);
		// loginPage.login("gunja23022@gmail.com", "Gunja@123");
		Jobspage.createjob("2019", newJobTitle);
		Jobspage.selectdate(newBenefit);
		Jobspage.savejob();
	}
}
