package com.lukegjpotter.selenium.SeleniumDemos;

import org.springframework.boot.SpringApplication;

public class TestSeleniumDemosApplication {

    public static void main(String[] args) {
        SpringApplication.from(SeleniumDemosApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
