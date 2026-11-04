package config;

import org.aeonbits.owner.ConfigFactory;

public final class ConfigProvider {

    private ConfigProvider() {
    }

    public static String deviceHost() {
        return System.getProperty("deviceHost", "emulation");
    }

    public static final BrowserstackConfig BROWSERSTACK_CONFIG =
            ConfigFactory.create(BrowserstackConfig.class, System.getProperties());

    public static final EmulationConfig EMULATION_CONFIG =
            ConfigFactory.create(EmulationConfig.class, System.getProperties());

    public static final RealConfig REAL_CONFIG =
            ConfigFactory.create(RealConfig.class, System.getProperties());
}