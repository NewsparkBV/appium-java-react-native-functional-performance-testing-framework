package newspark.utils.ScreenObjectUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("cucumber-glue")
public interface IosAndroidHelper {

    MobileElement findElementByClassChainOrXPath(AppiumDriver driver, String androidXpath, String iosClassChain);

    List<MobileElement> findElementsByClassChainOrXPath(AppiumDriver driver, String androidXpath, String iosClassChain);

    void scrollToElementByIOSClassChainorAndroidXpath(AppiumDriver driver, String androidXpath, String iosClassChain, int implicitWaitTime);

    WebElement findElementByElementAndClassChainOrXPath(AppiumDriver<? extends MobileElement> driver, String androidXpath, String iosClassChain, MobileElement element);

    List<WebElement> findElementsByElementAndClassChainOrXPath(AppiumDriver<? extends MobileElement> driver, String androidXpath, String iosClassChain, MobileElement element);

    void pullToRefresh(AppiumDriver<? extends MobileElement> driver);

    void scrollToElementByAccesibility(AppiumDriver<? extends MobileElement> driver, String accessibilityId, int implicitWaitTime);

    String getIosOrAndroidAttributeValue(String iosAttribute, String androidAttribute, MobileElement element);

    void waitForVisibilityOfElementWithIosClassChainOrAndroidXpath(AppiumDriver<? extends MobileElement> driver, String androidXpath, String iosClassChain);

    void waitForAtributeTextChange(AppiumDriver<? extends MobileElement> driver,  String iosClassChain, String androidXpath, String iosAttribute, String androidAttribute, String expectedText);

    void scrollDown(AppiumDriver<? extends MobileElement> driver);

    void scrollUp(AppiumDriver<? extends MobileElement> driver);

    void scrollToElement(AppiumDriver<? extends MobileElement> driver, MobileElement element, int implicitWaitTime);

    MobileElement findElementByXPath(AppiumDriver driver, String androidXpath, String iosXpath);

    void hitEnter(AppiumDriver<? extends MobileElement> driver);
}
