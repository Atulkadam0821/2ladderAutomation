package tests;

import org.junit.jupiter.api.Test;

import Base.base;
import Pages.copyjobquestionspage;

public class copyjobquestiontest extends base {
	@Test
	public void copyjob() {
		// loginpage loginPage = new loginpage(page);
		// loginPage.login("gunja23022@gmail.com", "Gunja@123");
		copyjobquestionspage copyquestions = new copyjobquestionspage(page);
		copyquestions.copyquestions("2019", "abc");
		copyquestions.testcopiedquestion();

	}
}
