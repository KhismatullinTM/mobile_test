package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({"classpath:BrowserstackConfig.properties"})
public interface BrowserstackConfig extends Config {
    @Key("executionMode") @DefaultValue("local") String executionMode();
    @Key("localUrl") @DefaultValue("http://127.0.0.1:4723") String localUrl();
    @Key("localDeviceName") @DefaultValue("Android Emulator") String localDeviceName();
    @Key("localPlatformVersion") @DefaultValue("14") String localPlatformVersion();
    @Key("localAppPath") @DefaultValue("src/test/resources/apps/wikipedia.apk") String localAppPath();
    @Key("browserstackUrl") String browserstackUrl();
    @Key("userName") String userName();
    @Key("accessKey") String accessKey();
    @Key("projectName") String projectName();
    @Key("buildName") String buildName();
    @Key("sessionName") String sessionName();
    @Key("browserstackDevice") String browserstackDevice();
    @Key("browserstackOsVersion") String browserstackOsVersion();
    @Key("appiumVersion") String appiumVersion();
    @Key("app") String app();
}
