package newspark.frontend.screenobjects;

import newspark.utils.ScreenObjectUtils.BaseScreenObject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class ResultsScreen extends BaseScreenObject {

    @Autowired
    public ResultsScreen(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    public MobileElement getArticleScrollList() {
        String iosClassChain = "**/XCUIElementTypeScrollView";
        String androidXpath ="//android.widget.ScrollView";
        return findElementByIOSClassChainorAndroidXpath(androidXpath, iosClassChain);
    }
}
