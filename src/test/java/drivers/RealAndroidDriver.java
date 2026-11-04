package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.ConfigProvider;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.net.URL;
import java.time.Duration;

public class RealAndroidDriver implements WebDriverProvider {

    @Override
    public WebDriver createDriver(Capabilities ignored) {
        var config = ConfigProvider.REAL_CONFIG;

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(config.realDeviceName())
                .setPlatformVersion(config.realPlatformVersion())
                .setUdid(config.realUdid())
                .setAppPackage("org.wikipedia.alpha")
                .setAppActivity("org.wikipedia.main.MainActivity");

        options.setCapability("appium:ignoreHiddenApiPolicyError", true);
        options.setNewCommandTimeout(Duration.ofSeconds(120));

        try {
            return new AndroidDriver(new URL(config.realUrl()), options);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Could not create session with real Android device. "
                            + "Check device connection and Appium Server.",
                    e
            );
        }
    }
}