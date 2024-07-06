package com.lukegjpotter.selenium.SeleniumDemos.testcontainers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class TestContainersTest {

    @Container
    public final BrowserWebDriverContainer<?> BrowserWebDriverContainer = new BrowserWebDriverContainer<>();

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
    void phpTravels_PageTitle() {
        driver.get("https://phptravels.com/demo/");
        String pageTitle = driver.getTitle().trim();

        assertThat(pageTitle).isEqualTo("Book Your Free Demo Now - Phptravels");
    }
}
