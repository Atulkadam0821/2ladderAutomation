package tests;

import org.junit.jupiter.api.Test;

import Base.base;
import Pages.copyjobenefitpage;
import Pages.createjobspage;

public class copyjobbeneittest extends base {
	@Test
	public void copyjob() {
		String newJobTitle = "Automation Job " + System.currentTimeMillis();
		String newBenefit = "Benefit " + System.currentTimeMillis();

		// loginpage loginPage = new loginpage(page);
		// loginPage.login("gunja23022@gmail.com", "Gunja@123");

		// Step 3: Create Job with new title using createjobspage
		createjobspage jobPage = new createjobspage(page);
		jobPage.createjob("2019", newJobTitle); // Passing the title here
		jobPage.selectdate(newBenefit);
		jobPage.savejob();

		// Step 4: Use the same title in assessment criteria logic
		copyjobenefitpage Copybenefit = new copyjobenefitpage(page);
		Copybenefit.copybenefit("2019", "abc", newJobTitle);
		Copybenefit.testcopiedbenefit(newBenefit);
	}
}
