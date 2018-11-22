package newspark.frontend.screenobjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import newspark.utils.ScreenObjectUtils.BaseScreenObject;

@Component
@Scope("cucumber-glue")
public class BottomNavigationObject extends BaseScreenObject {

    @Autowired
    public BottomNavigationObject(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    public WebElement getSearchNavigationItem() {
        String iosClassChain  = "**/XCUIElementTypeOther[`name = \"\uE8B6 \uE8B6 SEARCH\"`]";
        String androidXpath = "//android.widget.TextView[contains(@text, 'SEARCH')]/..";
        return findElementByIOSClassChainorAndroidXpath(androidXpath , iosClassChain);
    }

    public MobileElement getFavouritesNavigationItem() {
        String iosClassChain = "**/XCUIElementTypeOther[`name = \"\uE87D \uE87D FAVS\"`]";
        String androidXpath = "//android.widget.TextView[contains(@text, 'FAVS')]/..";
        return findElementByIOSClassChainorAndroidXpath(androidXpath , iosClassChain);
    }

    public void navigateToSearch() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(getSearchNavigationItem()));
        getSearchNavigationItem().click();
    }

    public void navigateToFavourites() {
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.elementToBeClickable(getFavouritesNavigationItem()));
        getFavouritesNavigationItem().click();
    }
}
