package com.lukegjpotter.selenium.SeleniumDemos.testcontainers;

import com.lukegjpotter.selenium.SeleniumDemos.SeleniumDemosApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@org.testcontainers.junit.jupiter.Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SeleniumDemosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = TestContainersTest.Initializer.class)
public class TestContainersTest {

    @Container
    public static BrowserWebDriverContainer chrome = new BrowserWebDriverContainer().withCapabilities(new ChromeOptions());
    private static RemoteWebDriver driver;

    @BeforeAll
    static void beforeAll() {
        driver = new RemoteWebDriver(chrome.getSeleniumAddress(), new ChromeOptions());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    void phpTravels_PageTitle() {
        driver.get("https://phptravels.com/demo/");
        String pageTitle = driver.getTitle();

        assertThat(pageTitle).isEqualTo("Book Your Free Demo Now - Phptravels");
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            applicationContext.addApplicationListener(
                    (ApplicationListener<WebServerInitializedEvent>) event -> {
                        Testcontainers.exposeHostPorts(event.getWebServer().getPort());
                    }
            );
        }
    }
}
