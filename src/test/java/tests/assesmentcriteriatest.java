package tests;

import org.junit.jupiter.api.Test;

import Base.base;
import Pages.assesmentcriteriapage;
import Pages.createjobspage;

public class assesmentcriteriatest extends base {
	// String newJobTitle = "Automation Job " + System.currentTimeMillis();

	@Test
	public void assessmentcriteria() {
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
		assesmentcriteriapage assesmentcriteria = new assesmentcriteriapage(page);
		assesmentcriteria.assesmentcriteria("2019", "atul@yopmail.com", newJobTitle, "Atul",
				"Automation Job question @" + System.currentTimeMillis());
	}
}
