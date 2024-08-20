package StepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class LoginPage {
    WebDriver driver;
    @Given("User is on LoginPage")
    public void userIsOnLoginPage() {
        System.out.println("First Test feature file KB");
        System.setProperty("webdriver.chrome.driver","C:/Users/prash/Downloads/chromedriver-win32/chromedriver.exe");
        driver=new ChromeDriver();
        driver.get("https://google.com");

    }

    @When("User enter Username and password")
    public void userEnterUsernameAndPassword() {
    }

    @And("click on submit")
    public void clickOnSubmit() {
    }

    @Then("Dashboard is displayed")
    public void dashboardIsDisplayed() {
    }
}
