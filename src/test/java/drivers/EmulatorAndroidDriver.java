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

public class EmulatorAndroidDriver implements WebDriverProvider {

    private static final String UDID = "emulator-5554";

    @Override
    public WebDriver createDriver(Capabilities ignored) {
        var config = ConfigProvider.EMULATION_CONFIG;

        File apk = new File(config.localAppPath());

        if (!apk.isFile()) {
            throw new IllegalStateException(
                    "APK not found: " + apk.getAbsolutePath()
            );
        }

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(config.localDeviceName())
                .setPlatformVersion(config.localPlatformVersion())
                .setUdid(UDID)
                .setApp(apk.getAbsolutePath())
                .setAppPackage("org.wikipedia.alpha")
                .setAppActivity("org.wikipedia.main.MainActivity");

        options.setNewCommandTimeout(Duration.ofSeconds(120));

        try {
            return new AndroidDriver(new URL(config.localUrl()), options);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Could not create Android Emulator session. Check Appium Server and emulator connection.",
                    e
            );
        }
    }
}