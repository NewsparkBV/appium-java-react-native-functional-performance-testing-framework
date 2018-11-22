package newspark.utils.ScreenObjectUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
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
@Profile("android")
public class AndroidHelper implements IosAndroidHelper {

    @Value("${platform.version}")
    private String platformVersion;

    @Override
    public MobileElement findElementByClassChainOrXPath(AppiumDriver driver, String androidXpath, String iosXpath) {
        return (MobileElement) driver.findElementByXPath(androidXpath);
    }

    @Override
    public List findElementsByClassChainOrXPath(AppiumDriver driver, String androidXpath, String iosClassChain) {
        return driver.findElementsByXPath(androidXpath);
    }

    @Override
    public MobileElement findElementByXPath(AppiumDriver driver, String androidXpath, String iosXpath) {
        return (MobileElement) driver.findElement(By.xpath(androidXpath));
    }

    @Override
    public void hitEnter(AppiumDriver<? extends MobileElement> driver) {
        ((AndroidDriver)driver).pressKeyCode(AndroidKeyCode.ENTER);
    }

    @Override
    public void scrollToElementByIOSClassChainorAndroidXpath(AppiumDriver driver, String androidXpath, String iosClassChain, int implicitWaitTime) {
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
        Dimension size = driver.manage().window().getSize();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.ignoring(NoSuchElementException.class);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(androidXpath)));
        } catch (Exception e) {
            boolean isFoundTheElement = !driver.findElementsByXPath(androidXpath).isEmpty();
            String screenSource = driver.getPageSource();
            while (!isFoundTheElement) {
                TouchAction action = new TouchAction(driver);
                action.press((int) (size.width * 0.5), (int) (size.height * 0.6)).waitAction(Duration.ofMillis(1500)).moveTo((int) (size.width * 0.5), (int) (size.height * 0.3)).release().perform();
                isFoundTheElement = !driver.findElementsByXPath(androidXpath).isEmpty();
                if (driver.getPageSource().equals(screenSource)) {
                    throw new ElementNotVisibleException("Element not found");
                }
                screenSource = driver.getPageSource();
            }
        }
        driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
    }

    @Override
    public WebElement findElementByElementAndClassChainOrXPath(AppiumDriver<? extends MobileElement> driver, String androidXpath, String iosClassChain, MobileElement element) {
        return element.findElementByXPath(androidXpath);
    }

    @Override
    public List findElementsByElementAndClassChainOrXPath(AppiumDriver<? extends MobileElement> driver, String androidXpath, String iosClassChain, MobileElement element) {
        return element.findElementsByXPath(androidXpath);
    }

    @Override
    public void pullToRefresh(AppiumDriver<? extends MobileElement> driver) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        action.press((int) (size.width * 0.5), (int) (size.height * 0.2)).waitAction(Duration.ofMillis(1000)).moveTo((int) (size.width * 0.5), (int) (size.height * 0.8)).release().perform();
    }

    @Override
    public void scrollToElementByAccesibility(AppiumDriver<? extends MobileElement> driver, String accessibilityId, int implicitWaitTime) {
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
        Dimension size = driver.manage().window().getSize();
        FluentWait wait = new WebDriverWait(driver, 10).ignoring(NoSuchElementException.class);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId(accessibilityId)));
        } catch (Exception e) {
            boolean isFoundTheElement = !driver.findElementsByAccessibilityId(accessibilityId).isEmpty();
            String screenSource = driver.getPageSource();
            while (!isFoundTheElement) {
                TouchAction action = new TouchAction(driver);
                action.press((int) (size.width * 0.5), (int) (size.height * 0.6)).waitAction(Duration.ofMillis(1500)).moveTo((int) (size.width * 0.5), (int) (size.height * 0.3)).release().perform();
                isFoundTheElement = !driver.findElementsByAccessibilityId(accessibilityId).isEmpty();
                if (driver.getPageSource().equals(screenSource)) {
                    throw new ElementNotVisibleException("Element niet gevonden op het scherm");
                }
                screenSource = driver.getPageSource();
            }
        }
        driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
    }

    @Override
    public String getIosOrAndroidAttributeValue(String iosAttribute, String androidAttribute, MobileElement element) {
        return element.getAttribute(androidAttribute);
    }

    @Override
    public void waitForVisibilityOfElementWithIosClassChainOrAndroidXpath(AppiumDriver<? extends MobileElement> driver, String androidXpath, String iosClassChain) {
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(androidXpath)));
    }

    @Override
    public void waitForAtributeTextChange(AppiumDriver<? extends MobileElement> driver,  String iosClassChain, String androidXpath, String iosAttribute, String androidAttribute, String expectedText) {
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.attributeContains(By.xpath(androidXpath), androidAttribute, expectedText));
    }

    @Override
    public void scrollDown(AppiumDriver<? extends MobileElement> driver) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        action.press((int) (size.width * 0.5), (int) (size.height * 0.7)).waitAction(Duration.ofMillis(500)).moveTo((int) (size.width * 0.5), (int) (size.height * 0.2)).release().perform();
    }

    @Override
    public void scrollUp(AppiumDriver<? extends MobileElement> driver) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        action.press((int) (size.width * 0.5), (int) (size.height * 0.2)).waitAction(Duration.ofMillis(500)).moveTo((int) (size.width * 0.5), (int) (size.height * 0.7)).release().perform();
    }

    @Override
    public void scrollToElement(AppiumDriver<? extends MobileElement> driver, MobileElement element, int implicitWaitTime) {
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
        Dimension size = driver.manage().window().getSize();
        FluentWait wait = new WebDriverWait(driver, 10).ignoring(NoSuchElementException.class);
        List<WebElement> listOfElementsToFind = new ArrayList<>();
        listOfElementsToFind.add(element);
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(listOfElementsToFind));
        } catch (Exception e) {
            boolean isFoundTheElement = listOfElementsToFind.get(0).isDisplayed();
            String screenSource = driver.getPageSource();
            while (!isFoundTheElement) {
                TouchAction action = new TouchAction(driver);
                action.press((int) (size.width * 0.5), (int) (size.height * 0.6)).waitAction(Duration.ofMillis(1500)).moveTo((int) (size.width * 0.5), (int) (size.height * 0.3)).release().perform();
                isFoundTheElement = listOfElementsToFind.get(0).isDisplayed();
                if (driver.getPageSource().equals(screenSource)) {
                    throw new ElementNotVisibleException("Element niet gevonden op het scherm");
                }
                screenSource = driver.getPageSource();
            }
        }
        driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
    }
}
