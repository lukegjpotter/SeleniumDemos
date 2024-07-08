package com.lukegjpotter.selenium.SeleniumDemos.testcontroller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LocalHostServerPortControllerTest {

    private static final String recordingsOutputPath = "./build/test-recordings/";

    @Container
    private final BrowserWebDriverContainer<?> BrowserWebDriverContainer = new BrowserWebDriverContainer<>()
            .withRecordingMode(VncRecordingMode.SKIP, new File(recordingsOutputPath));

    private RemoteWebDriver driver;

    @LocalServerPort
    private int localServerPort;

    @BeforeAll
    static void beforeAll() {
        File recordingsOutput = new File(recordingsOutputPath);
        if (!recordingsOutput.exists()) recordingsOutput.mkdirs();
    }

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
    void localhostPortTest() {
        driver.get("http://host.docker.internal:" + localServerPort + "/html/simplehtml");
        String pageTitle = driver.getTitle();

        assertThat(pageTitle).isEqualTo("Local Test Works!!");
    }
}