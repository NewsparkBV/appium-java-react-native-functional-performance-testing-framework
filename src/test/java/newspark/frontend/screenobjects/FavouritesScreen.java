package newspark.frontend.screenobjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import newspark.utils.ScreenObjectUtils.BaseScreenObject;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class FavouritesScreen extends BaseScreenObject {

    @Autowired
    public FavouritesScreen(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    public WebElement getNoFavouritesMessage() {
        String iosClassChain = "**/XCUIElementTypeStaticText[`name CONTAINS[cd] \"No Favourites\"`]";
        String androidXpath  = "//android.widget.TextView[contains(@text, 'No Favourites')]";
        return findElementByIOSClassChainorAndroidXpath(androidXpath , iosClassChain);
    }

}
