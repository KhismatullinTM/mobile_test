package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.ConfigProvider;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BrowserStackAndroidDriver implements WebDriverProvider {
    @Override
    public WebDriver createDriver(Capabilities ignored) {
        var c = ConfigProvider.CONFIG;
        requireConfigured("userName", c.userName());
        requireConfigured("accessKey", c.accessKey());
        requireConfigured("app", c.app());

        Map<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("userName", c.userName());
        bstackOptions.put("accessKey", c.accessKey());
        bstackOptions.put("projectName", c.projectName());
        bstackOptions.put("buildName", c.buildName());
        bstackOptions.put("sessionName", c.sessionName());
        bstackOptions.put("deviceName", c.browserstackDevice());
        bstackOptions.put("osVersion", c.browserstackOsVersion());
        bstackOptions.put("appiumVersion", c.appiumVersion());

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setApp(c.app());
        options.setCapability("bstack:options", bstackOptions);
        options.setNewCommandTimeout(Duration.ofSeconds(120));

        try {
            return new AndroidDriver(new URL(c.browserstackUrl()), options);
        } catch (Exception e) {
            throw new IllegalStateException("Could not create BrowserStack session. Check URL, credentials and app id.", e);
        }
    }

    private static void requireConfigured(String key, String value) {
        if (value == null || value.isBlank() || value.startsWith("YOUR_")) {
            throw new IllegalStateException("Set '" + key + "' in BrowserstackConfig.properties "
                    + "or pass it as a -D" + key + "=... system property.");
        }
    }
}
