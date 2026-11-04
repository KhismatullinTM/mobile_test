package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources("classpath:EmulationConfig.properties")
public interface EmulationConfig extends Config {

    @Key("localUrl") String localUrl();
    @Key("localDeviceName") String localDeviceName();
    @Key("localPlatformVersion") String localPlatformVersion();
    @Key("localAppPath") String localAppPath();
}