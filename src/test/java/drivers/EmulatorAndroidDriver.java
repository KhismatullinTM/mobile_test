package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.ConfigProvider;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public class EmulatorAndroidDriver implements WebDriverProvider {

    private static final String UDID = "emulator-5554";

    private static final String APP_URL =
            "https://github.com/wikimedia/apps-android-wikipedia/releases/download/latest/app-alpha-universal-release.apk";

    @Override
    public WebDriver createDriver(Capabilities ignored) {
        var config = ConfigProvider.EMULATION_CONFIG;

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(config.localDeviceName())
                .setPlatformVersion(config.localPlatformVersion())
                .setUdid(UDID)
                .setApp(getAppPath(config.localAppPath()))
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

    private String getAppPath(String appPath) {
        Path apk = Path.of(appPath);

        try {
            if (Files.exists(apk) && Files.size(apk) > 0) {
                return apk.toAbsolutePath().toString();
            }

            Files.createDirectories(apk.getParent());

            try (InputStream in = new URL(APP_URL).openStream()) {
                Path tempApk = apk.resolveSibling(apk.getFileName() + ".tmp");

                Files.copy(in, tempApk, StandardCopyOption.REPLACE_EXISTING);

                Files.move(tempApk, apk, StandardCopyOption.REPLACE_EXISTING);
            }

            return apk.toAbsolutePath().toString();

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to download Wikipedia application",
                    e
            );
        }
    }
}