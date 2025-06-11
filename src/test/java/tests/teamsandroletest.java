package tests;

import org.junit.jupiter.api.Test;

import Base.base;
import Pages.teamsandrolepage;

public class teamsandroletest extends base {
	@Test
	public void editjob() {
		// loginpage loginPage = new loginpage(page);
		// loginPage.login("gunja23022@gmail.com", "Gunja@123");
		teamsandrolepage Editjobpage = new teamsandrolepage(page);
		Editjobpage.addperson("2019", "atul@yopmail.com", "Atul", "Kadam");
	}
}
