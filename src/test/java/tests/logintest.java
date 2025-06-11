package tests;

import org.junit.jupiter.api.Test;

import Base.base;
import Pages.loginpage;

public class logintest extends base{

    @Test
    public void validLoginTest() {
        loginpage loginPage = new loginpage(page);
        //HomePage homePage = new HomePage(page);

       // loginPage.navigateToLogin();
        loginPage.login("gunja23022@gmail.com", "Gunja@123");

        //String welcomeText = homePage.getWelcomeText();
        //assertTrue(welcomeText.contains("Welcome"));
    }
}

