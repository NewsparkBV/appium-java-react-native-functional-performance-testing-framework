package newspark.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class WebDriverUtils {
    @Value("${explicit.wait}")
    public int explicitWaitTime;
    @Value("${implicit.wait}")
    public int implicitWaitTime;
    @Value("${default.wait}")
    public int defaultWaitTime;
    @Value("${application.path: x}")
    private String appPath;
    @Value("${automation.instrumentation}")
    private String instrumentation;
    @Value("${platform.name}")
    private String platformName;
    @Value("${device.name}")
    private String deviceName;
    @Value("${platform.version}")
    private String platformVersion;
    @Value("${new.command.timeout}")
    private String newCommandTimeout;
    @Value("${device.ready.timeout}")
    private String deviceReadyTimeout;
    @Value("${appium.version}")
    private String appiumVersion;
    @Value("${project}")
    private String project;
    @Value("${browserstack.debug: true}")
    private boolean browserstackDebug;
    @Value("${udid: udid}")
    private String udid;
    @Value("${app.activity:com.newspark.MainActivity}")
    private String appActivity;
    @Value("${app.package:com.newspark}")
    private String appPackage;
    @Value("${real.device}")
    private boolean realDevice;
    @Value("${skip.unlock:false}")
    private boolean skipUnlock;
    @Value("${noreset:true}")
    private boolean noReset;
    @Value("${autoAcceptAlerts:true}")
    private boolean autoAcceptAlerts;
    @Value("${cloud:true}")
    private boolean cloud;
    @Value("${spring.profiles.active}")
    public String activeProfile;
    @Value("${browserstack.username}")
    private String browserstackUsername;
    @Value("${browserstack.key}")
    private String browserstackKey;

    private AppiumDriver<? extends MobileElement> driver;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverUtils.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        String deviceIdName = System.getProperty("deviceName");
        String devicePropertiesFilename = "/properties/deviceprops/" + deviceIdName + ".properties";
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocations(
                new ClassPathResource(devicePropertiesFilename),
                new ClassPathResource("properties/testprops.properties")
        );
        return configurer;
    }

    @Bean(name = "androidDriver", destroyMethod = "quit")
    @Scope("cucumber-glue")
    @Profile("android")
    public AppiumDriver<? extends MobileElement> getAndroidDriver() {
        DesiredCapabilities capabilities = getDesiredCapabilities();
        capabilities.setCapability("autoGrantPermissions", true);
        if (realDevice) {
            capabilities.setCapability("appActivity", appActivity);
            capabilities.setCapability("appPackage", appPackage);
        }

        driver = new AndroidDriver(getServerUrl(), capabilities);
        driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        return driver;
    }

    @Bean(destroyMethod = "quit")
    @Scope("cucumber-glue")
    @Profile("ios")
    public AppiumDriver getIosDriver() {
        DesiredCapabilities capabilities = getDesiredCapabilities();
        driver = new IOSDriver(getServerUrl(), capabilities);
        driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        return driver;
    }

    private DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, newCommandTimeout);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, instrumentation);
        capabilities.setCapability("skipUnlock", skipUnlock);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, noReset);
        capabilities.setCapability("appiumVersion", appiumVersion);
        capabilities.setCapability("autoAcceptAlerts", autoAcceptAlerts);
        capabilities.setCapability("appWaitActivity", "com.newspark.MainActivity");
        if (cloud) {
            String buildNr = System.getProperty("buildNr");
            capabilities.setCapability(MobileCapabilityType.APP, buildNr + "-" + activeProfile);
            capabilities.setCapability("project", project);
            capabilities.setCapability("build", buildNr + activeProfile);
            capabilities.setCapability("browserstack.debug", browserstackDebug);
        } else {
            if (realDevice) {
                capabilities.setCapability(MobileCapabilityType.UDID, udid);
            }
            capabilities.setCapability(MobileCapabilityType.APP, new File(appPath).getAbsolutePath());
        }
        return capabilities;
    }

    private URL getServerUrl() {
        String urlText;
        if (cloud) {
            urlText = "https://" + browserstackUsername + ":" + browserstackKey + "@hub-cloud.browserstack.com/wd/hub";
        } else {
            urlText = "http://localhost:" + RunnerHooks.getAppiumServicePort() + "/wd/hub";
        }
        try {
            return new URL(urlText);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
