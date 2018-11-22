package newspark.utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnerHooks {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunnerHooks.class);
    private static AppiumDriverLocalService service;

    @BeforeClass
    public static void setup() {
        if ("local".equals(System.getProperty("testLocation"))) {
            service = new AppiumServiceBuilder().usingAnyFreePort().build();
            service.start();
        }
    }

    public static int getAppiumServicePort() {
        return service == null ? 4723 : service.getUrl().getPort();
    }

    @AfterClass
    public static void tearDown() {
        if ("local".equals(System.getProperty("testLocation"))) {
            service.stop();
        }
    }
}
