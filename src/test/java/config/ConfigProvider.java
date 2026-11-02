package config;

import org.aeonbits.owner.ConfigFactory;

public final class ConfigProvider {
    private ConfigProvider() { }

    // System properties (-Dkey=value) override values from BrowserstackConfig.properties.
    public static final BrowserstackConfig CONFIG =
            ConfigFactory.create(BrowserstackConfig.class, System.getProperties());
}
