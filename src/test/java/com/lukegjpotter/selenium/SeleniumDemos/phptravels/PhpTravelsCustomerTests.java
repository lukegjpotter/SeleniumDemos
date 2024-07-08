package com.lukegjpotter.selenium.SeleniumDemos.phptravels;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class PhpTravelsCustomerTests {

    @Autowired
    private Environment env;

    @Container
    private final BrowserWebDriverContainer<?> BrowserWebDriverContainer = new BrowserWebDriverContainer<>()
            .withRecordingMode(VncRecordingMode.RECORD_FAILING, new File("./build/test-recordings/"));

    private RemoteWebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new RemoteWebDriver(BrowserWebDriverContainer.getSeleniumAddress(), new ChromeOptions());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @AfterEach
    void cleanUp() {
        driver.quit();
    }

    @Test
    void phpTravels_LoginAsAgent() {
        // Open Login Page/
        driver.get("https://phptravels.net/login");
        // Enter Credentials and Submit Form.
        driver.findElement(By.id("email")).sendKeys(env.getProperty("PhpTravels_AgentEmail"));
        driver.findElement(By.id("password")).sendKeys(env.getProperty("PhpTravels_Password"));
        driver.findElement(By.id("submitBTN")).click();
        // On Home Page, open the Profile Page via the Menu.
        String xpathToAccountsButton = "//*[@id=\"navbarSupportedContent\"]/div[2]/ul/li[3]/a";
        Wait<WebDriver> waitForPageToLoad = new WebDriverWait(driver, Duration.ofSeconds(20));
        waitForPageToLoad.until(waitWebDriver -> waitWebDriver.findElement(By.xpath(xpathToAccountsButton)).isDisplayed());
        driver.findElement(By.xpath(xpathToAccountsButton)).click();
        // FixMe: ElementNotResponding driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/div[2]/ul/li[3]/ul/li[4]/a")).click();
        // Find the User's Profile Name.
        driver.get("https://phptravels.net/profile");
        waitForPageToLoad.until(waitWebDriver -> waitWebDriver.findElement(By.className("author-content")).isDisplayed());
        String actualProfileName = driver.findElement(By.className("author-content")).findElement(By.tagName("strong")).getText();

        assertThat(actualProfileName).isEqualTo("Demo Agent");
    }
}
