package newspark.frontend.screenobjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidKeyCode;
import newspark.utils.ScreenObjectUtils.BaseScreenObject;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("cucumber-glue")
public class SearchBarObject extends BaseScreenObject {

    @Autowired
    public SearchBarObject(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    public WebElement getSearchField() {
        String iosClassChain = "**/XCUIElementTypeOther[`name = \"\uE8B6 Search Newspark\"`]";
        String androidXpath  = "//android.widget.EditText[contains(@text, 'Search Newspark')]";
        return findElementByIOSClassChainorAndroidXpath(androidXpath , iosClassChain);
    }

    public WebElement getSearchFieldWithEnteredQuery() {
        String iosClassChain = "**/XCUIElementTypeOther[`name =] \"\uE645\"`]";
        String androidXpath  = "//android.widget.EditText[contains(@text, 'search query')]";
        return findElementByIOSClassChainorAndroidXpath(androidXpath , iosClassChain);
    }

    public MobileElement getCancelButton() {
        String iosClassChain  = "**/XCUIElementTypeOther[`name = \"Cancel\"`]";
        String androidXpath = "//android.widget.TextView[contains(@text, 'Cancel')]/..";
        return findElementByIOSClassChainorAndroidXpath(androidXpath , iosClassChain);
    }

    public void enterSearchQueryAndSearch() {
        getSearchField().sendKeys("search query");
        if(activeProfile.contentEquals("android")) {
            getSearchFieldWithEnteredQuery().click();
        }
        wait(1);
        hitEnterButton();
        wait(2);
    }

    public void enterSearchQueryAndCancelSearch() {
        getSearchField().sendKeys("search query");
        if(activeProfile.contentEquals("android")) {
            getSearchFieldWithEnteredQuery().click();
        }
        getCancelButton().click();
    }
}
