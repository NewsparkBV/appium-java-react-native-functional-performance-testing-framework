package newspark.utils.ScreenObjectUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public abstract class BaseScreenObject {

    @Value("${explicit.wait}")
    public int explicitWaitTime;
    @Value("${implicit.wait}")
    public int implicitWaitTime;
    @Value("${default.wait}")
    public int defaultWaitTime;
    @Value("${device.name}")
    public String deviceName;
    @Value("${spring.profiles.active}")
    public String activeProfile;

    @Autowired
    public IosAndroidHelper iosAndroidHelper;

    public AppiumDriver<? extends MobileElement> driver;

    public BaseScreenObject(AppiumDriver<? extends MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, implicitWaitTime,
                TimeUnit.SECONDS), this);
    }

    public void takeScreenShot(String fileName) {
        File file = new File("target" + File.separator + "screenshots" + File.separator + deviceName + File.separator + fileName + ".png");
        File tmpFile = driver
                .getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(tmpFile, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AppiumDriver<? extends MobileElement> getDriver() {
        return driver;
    }

    public void pullToRefresh() {
        iosAndroidHelper.pullToRefresh(driver);
    }

    public MobileElement findElementByIOSClassChainorAndroidXpath(String androidXpath, String iosXpath) {
        return iosAndroidHelper.findElementByClassChainOrXPath(driver, androidXpath, iosXpath);
    }

    public MobileElement findElementByXPath(AppiumDriver driver, String androidXpath, String iosXpath) {
        return iosAndroidHelper.findElementByXPath(driver, androidXpath, iosXpath);
    }

    public List<MobileElement> findElementsByIOSClassChainOrAndroidXpath(String androidXpath, String iosClassChain) {
        return iosAndroidHelper.findElementsByClassChainOrXPath(driver, androidXpath, iosClassChain);
    }

    public WebElement findElementByElementAndIOSClassChainOrAndroidXpath(String androidXpath, String iosClasssChain, MobileElement element) {
        return iosAndroidHelper.findElementByElementAndClassChainOrXPath(driver, androidXpath, iosClasssChain, element);
    }

    public List<WebElement> findElementsByElementAndIOSClassChainOrAndroidXpath(String androidXpath, String iosClassChain, MobileElement element) {
        return iosAndroidHelper.findElementsByElementAndClassChainOrXPath(driver, androidXpath, iosClassChain, element);
    }

    public void scrollToElementByAccesibility(String accessibilityId) {
        iosAndroidHelper.scrollToElementByAccesibility(driver, accessibilityId, implicitWaitTime);
    }

    public void scrollToElementByIOSClassChainOrAndroidXpath(String androidXpath, String iosClassChain) {
        iosAndroidHelper.scrollToElementByIOSClassChainorAndroidXpath(driver, androidXpath, iosClassChain, implicitWaitTime );
    }

    public String getIosOrAndroidAttributeValue(String iosAttribute, String androidAttribute, MobileElement element) {
        return iosAndroidHelper.getIosOrAndroidAttributeValue(iosAttribute, androidAttribute, element);
    }

    public void waitForVisibilityOfElementWithIosClassChainOrAndroidXpath(String androidXpath, String iosClassChain) {
        iosAndroidHelper.waitForVisibilityOfElementWithIosClassChainOrAndroidXpath(driver, androidXpath, iosClassChain);
    }

    public void closeApp() {
        driver.closeApp();
    }

    public void openApp() {
        driver.launchApp();
    }

    public void waitForAtributeTextChange(String iosClassChain, String androidXpath, String iosAttribute, String androidAttribute, String expectedText) {
        iosAndroidHelper.waitForAtributeTextChange(driver, iosClassChain, androidXpath, iosAttribute, androidAttribute, expectedText);
    }

    public String getOrientation() {
        return driver.getOrientation().toString().toLowerCase();
    }

    public void scrollDown() {
        iosAndroidHelper.scrollDown(driver);
    }

    public void scrollUp() {
        iosAndroidHelper.scrollUp(driver);
    }

    public void scrollToElement(MobileElement element) {
        iosAndroidHelper.scrollToElement(driver, element, implicitWaitTime);
    }

    public void sendKeysAndHideKeyboardIfAndroid(MobileElement element, String charSequence) {
        element.sendKeys(charSequence);
        if(activeProfile.equalsIgnoreCase("android")) {
            try {
                driver.hideKeyboard();
            }catch (Exception e){
                System.out.println("There is no keyboard!");
            }
        }
    }

    public void hitEnterButton() {
        iosAndroidHelper.hitEnter(driver);
    }

    public void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

