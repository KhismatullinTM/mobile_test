package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources("classpath:RealConfig.properties")
public interface RealConfig extends Config {

    @Key("realUrl") String realUrl();
    @Key("realDeviceName") String realDeviceName();
    @Key("realPlatformVersion") String realPlatformVersion();
    @Key("realUdid") String realUdid();
}