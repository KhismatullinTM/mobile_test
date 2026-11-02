package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.ConfigProvider;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class LocalAndroidDriver implements WebDriverProvider {
    @Override
    public WebDriver createDriver(Capabilities ignored) {
        var config = ConfigProvider.CONFIG;
        File apk = new File(config.localAppPath());
        if (!apk.isFile()) {
            throw new IllegalStateException("APK not found: " + apk.getAbsolutePath()
                    + ". Download Wikipedia APK and set -DlocalAppPath=<path>.");
        }

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(config.localDeviceName())
                .setPlatformVersion(config.localPlatformVersion())
                .setApp(apk.getAbsolutePath())
                .setAppPackage("org.wikipedia.alpha")
                .setAppActivity("org.wikipedia.main.MainActivity");
        options.setNewCommandTimeout(Duration.ofSeconds(120));

        try {
            return new AndroidDriver(new URL(config.localUrl()), options);
        } catch (Exception e) {
            throw new IllegalStateException("Could not start local Appium session at " + config.localUrl(), e);
        }
    }
}
