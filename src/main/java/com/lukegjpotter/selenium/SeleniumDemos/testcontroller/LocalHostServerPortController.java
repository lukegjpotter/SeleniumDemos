package com.lukegjpotter.selenium.SeleniumDemos.testcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/html")
public class LocalHostServerPortController {

    @GetMapping("/simplehtml")
    public ResponseEntity<String> getSimpleHtml() {
        return ResponseEntity.ok("<html><head><title>Local Test Works!!</title></head><body>Testing on Local</body></html>");
    }
}
