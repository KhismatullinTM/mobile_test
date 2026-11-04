package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.ConfigProvider;
import drivers.BrowserStackAndroidDriver;
import drivers.DriverFactory;
import drivers.EmulatorAndroidDriver;
import helpers.Attach;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public abstract class TestBase {

    @BeforeAll
    static void configure() {
        Configuration.browserSize = null;
        Configuration.timeout = 20_000;
        Configuration.pageLoadTimeout = 30_000;
    }

    @BeforeEach
    void setUp() {
        if (!SelenideLogger.hasListener("AllureSelenide")) {
            SelenideLogger.addListener("AllureSelenide",
                    new AllureSelenide().screenshots(true).savePageSource(true));
        }

        WebDriver driver = DriverFactory.getDriver().createDriver(null);

        WebDriverRunner.setWebDriver(driver);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(
                            AppiumBy.androidUIAutomator(
                                    "new UiSelector().text(\"OK\")"
                            )
                    ))
                    .click();
        } catch (TimeoutException e) {
        }
    }

    @AfterEach
    void tearDown() {
        String sessionId = Selenide.sessionId().toString();
        System.out.println(sessionId);
        // Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        if ("browserstack".equalsIgnoreCase(ConfigProvider.deviceHost())) {
            Attach.addVideo(sessionId);
        }
        closeWebDriver();
        System.clearProperty("platform");
    }
}