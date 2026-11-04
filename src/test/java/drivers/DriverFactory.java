package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.ConfigProvider;

import java.util.Locale;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriverProvider getDriver() {
        return switch (ConfigProvider.deviceHost().toLowerCase(Locale.ROOT)) {
            case "emulation" -> new EmulatorAndroidDriver();
            case "browserstack" -> new BrowserStackAndroidDriver();
            case "real" -> new RealAndroidDriver();
            default -> throw new IllegalArgumentException(
                    "Unknown deviceHost. Use 'browserstack', 'emulation' or 'real'."
            );
        };
    }
}