package newspark.utils.ScreenObjectUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@Component
@Scope("cucumber-glue")
@Profile("ios")
public class IosHelper implements IosAndroidHelper {

    @Override
    public MobileElement findElementByClassChainOrXPath(AppiumDriver driver, String androidXpath, String iosClassChain) {
        return (MobileElement) driver.findElement(MobileBy.iOSClassChain(iosClassChain));
    }

    @Override
    public MobileElement findElementByXPath(AppiumDriver driver, String androidXpath, String iosXpath) {
        return (MobileElement) driver.findElement(By.xpath(iosXpath));
    }

    @Override
    public void hitEnter(AppiumDriver<? extends MobileElement> driver) {
        driver.getKeyboard().pressKey(Keys.ENTER);
    }

    @Override
    public List<MobileElement> findElementsByClassChainOrXPath(AppiumDriver driver, String androidXpath, String iosClassChain) {
        return driver.findElements(MobileBy.iOSClassChain(iosClassChain));
    }

    @Override
    public void scrollToElementByIOSClassChainorAndroidXpath(AppiumDriver driver, String androidXpath, String iosClassChain, int implicitWaitTime) {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        Dimension size = driver.manage().window().getSize();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.ignoring(NoSuchElementException.class);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.iOSClassChain(iosClassChain)));
        } catch (Exception e){
            boolean isFoundTheElement = !driver.findElements(MobileBy.iOSClassChain(iosClassChain)).isEmpty();
            String screenSource = driver.getPageSource();
            while (!isFoundTheElement) {
                TouchAction action = new TouchAction(driver);
                action.press(size.width / 2, size.height / 2).waitAction(Duration.ofMillis(100)).moveTo(0, -300).release().perform();
                isFoundTheElement = !driver.findElements(MobileBy.iOSClassChain(iosClassChain)).isEmpty();
                if (driver.getPageSource().equals(screenSource)) {
                    throw new ElementNotVisibleException("Element niet gevonden op het scherm");
                }
                screenSource = driver.getPageSource();
            }
            driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        }
    }

    @Override
    public WebElement findElementByElementAndClassChainOrXPath(AppiumDriver<? extends MobileElement> driver, String androidXpath, String iosClassChain, MobileElement element) {
        return element.findElement(MobileBy.iOSClassChain(iosClassChain));
    }

    @Override
    public List findElementsByElementAndClassChainOrXPath(AppiumDriver<? extends MobileElement> driver, String androidXpath, String iosClassChain, MobileElement element) {
        return element.findElements(MobileBy.iOSClassChain(iosClassChain));
    }

    @Override
    public void pullToRefresh(AppiumDriver<? extends MobileElement> driver) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        action.press((int) (size.width * 0.5), (int) (size.height * 0.2)).waitAction(Duration.ofMillis(1000)).moveTo(0, (int) (size.height * 0.6)).release().perform();
    }

    @Override
    public void scrollToElementByAccesibility(AppiumDriver<? extends MobileElement> driver, String accessibilityId, int implicitWaitTime) {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        Dimension size = driver.manage().window().getSize();
        FluentWait wait = new WebDriverWait(driver, 20).ignoring(NoSuchElementException.class);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId(accessibilityId)));
        } catch (Exception e) {
            boolean isFoundTheElement = !driver.findElementsByAccessibilityId(accessibilityId).isEmpty();
            String screenSource = driver.getPageSource();
            while (!isFoundTheElement) {
                TouchAction action = new TouchAction(driver);
                action.press(size.width / 2, size.height / 2).waitAction(Duration.ofMillis(500)).moveTo(0, -300).release().perform();
                isFoundTheElement = !driver.findElementsByAccessibilityId(accessibilityId).isEmpty();
                if (driver.getPageSource().equals(screenSource)) {
                    throw new ElementNotVisibleException("Element niet gevonden op het scherm");
                }
                screenSource = driver.getPageSource();
            }
            driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        }
    }

    @Override
    public String getIosOrAndroidAttributeValue(String iosAttribute, String androidAttribute, MobileElement element) {
        return element.getAttribute(iosAttribute);
    }

    @Override
    public void waitForVisibilityOfElementWithIosClassChainOrAndroidXpath(AppiumDriver<? extends MobileElement> driver, String androidXpath, String iosClassChain) {
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.iOSClassChain(iosClassChain)));
    }

    @Override
    public void waitForAtributeTextChange(AppiumDriver<? extends MobileElement> driver,  String iosClassChain, String androidXpath, String iosAttribute, String androidAttribute, String expectedText) {
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.attributeContains(MobileBy.iOSClassChain(iosClassChain), iosAttribute, expectedText));
    }

    @Override
    public void scrollDown(AppiumDriver<? extends MobileElement> driver) {
        Dimension size = driver.manage().window().getSize();
        TouchAction action = new TouchAction(driver);
        action.press(size.width / 2, (int) (size.height / 1.5)).waitAction(Duration.ofMillis(500)).moveTo(0, -400).release().perform();
    }

    @Override
    public void scrollUp(AppiumDriver<? extends MobileElement> driver) {
        Dimension size = driver.manage().window().getSize();
        TouchAction action = new TouchAction(driver);
        action.press(size.width / 2, (int) (size.height / 2)).waitAction(Duration.ofMillis(500)).moveTo(0, 400).release().perform();
    }

    @Override
    public void scrollToElement(AppiumDriver<? extends MobileElement> driver, MobileElement element, int implicitWaitTime) {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        Dimension size = driver.manage().window().getSize();
        FluentWait wait = new WebDriverWait(driver, 20).ignoring(NoSuchElementException.class);
        List<WebElement> listOfElementsToFind = new ArrayList<>();
        listOfElementsToFind.add(element);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            boolean isFoundTheElement = element.isEnabled();
            String screenSource = driver.getPageSource();
            while (!isFoundTheElement) {
                TouchAction action = new TouchAction(driver);
                action.press(size.width / 2, size.height / 2).waitAction(Duration.ofMillis(500)).moveTo(0, -300).release().perform();
                isFoundTheElement = element.isEnabled();
                if (driver.getPageSource().equals(screenSource)) {
                    throw new ElementNotVisibleException("Element niet gevonden op het scherm");
                }
                screenSource = driver.getPageSource();
            }
            driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        }
    }
}
