package tests;

import org.junit.jupiter.api.Test;

import Base.base;
import Pages.createjobspage;
import Pages.jobapplypage;

public class jobapplytest extends base {

	@Test
	public void applyjob() {
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
		jobapplypage Jobapply = new jobapplypage(page);
		Jobapply.jobapply("2019", newJobTitle, "Atul", "Kadam", "atul@yopmail.com", "8888888888");
		Jobapply.fullpage(newJobTitle, "Atul", "kadam", "atul@gmail.com");
		Jobapply.getcandidates();
	}
}
