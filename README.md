# Selenium Demos

Learning Selenium via a combination of open websites.

### Deploy Buttons

[![Deploy to Render](https://render.com/images/deploy-to-render-button.svg)](https://render.com/deploy?repo=https://github.com/lukegjpotter/SeleniumDemos)

### How to Build and Run

CLI Gradle

    ./gradlew build bootRun

Docker CLI Instructions

    docker build --pull -t selenium-demos:latest .
    
    docker run --name selenium_demos \
      -p 8080:8080 \
      -d --rm selenium-demos:latest
