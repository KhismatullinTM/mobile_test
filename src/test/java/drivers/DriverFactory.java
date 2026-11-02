package drivers;

import config.ConfigProvider;
import java.util.Locale;

public final class DriverFactory {
    private DriverFactory() { }

    public static String driverClassName() {
        return switch (ConfigProvider.CONFIG.executionMode().toLowerCase(Locale.ROOT)) {
            case "local" -> LocalAndroidDriver.class.getName();
            case "browserstack" -> BrowserStackAndroidDriver.class.getName();
            default -> throw new IllegalArgumentException(
                    "Unknown executionMode. Use 'local' or 'browserstack'.");
        };
    }
}
