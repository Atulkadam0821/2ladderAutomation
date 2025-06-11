package tests;

import org.junit.jupiter.api.Test;

import Base.base;
import Pages.interviewpage;

public class interviewtest extends base {
	@Test
	public void interview() {

		// loginpage loginPage = new loginpage(page);
		// loginPage.login("gunja23022@gmail.com", "Gunja@123");

		interviewpage Interviewpage = new interviewpage(page);
		Interviewpage.interview(9, "2019", "dummy data");

	}
}
