package newspark.utils;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.appium.java_client.AppiumDriver;
import newspark.utils.performancetestutils.DumpsysGFX;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

@Scope("cucumber-glue")
public class StepDefHooks {

    @Autowired
    public WebDriverUtils utils;

    @Autowired
    public AppiumDriver driver;

    @Autowired
    private DumpsysGFX dumpsysGFX;


    @Value("${app.package:com.newspark}")
    private String appPackage;
    @Value("${browserstack.username}")
    private String browserstackUsername;
    @Value("${browserstack.key}")
    private String browserstackKey;
    @Value("${implicit.wait}")
    public int implicitWaitTime;

    private Scenario scenario;
    private String sessionId;

    @Value("${device.name}")
    private String deviceName;

    @After("@performance")
    public void afterPerformanceTestScenario(Scenario scenario){
        waitFiveSecs();
        dumpsysGFX.writeDumpsysGFX(scenario.getName().replace(" ", ""), appPackage);
        driver.closeApp();
    }

    @After
    public void markAndClearSession(Scenario scenario) {
            String sessionId = driver.getSessionId().toString();
            String scenarioName = scenario.getName();
        try {
            markTest(getTestResult(scenario), sessionId, scenarioName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    private void markTest(String result, String sessionId, String scenarioName) throws URISyntaxException, IOException {
        URI uri = new URI("https://"+ browserstackUsername + ":" + browserstackKey +"@www.browserstack.com/app-automate/sessions/" + sessionId +".json");
        HttpPut putRequest = new HttpPut(uri);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add((new BasicNameValuePair("status", result)));
        nameValuePairs.add((new BasicNameValuePair("reason", scenarioName)));
        putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpClientBuilder.create().build().execute(putRequest);
    }

    public String getTestResult(Scenario scenario) {
        if(scenario.isFailed()) {
            return "failed";
        }
        else {
            return "passed";
        }
    }

    @Before("@performance")
    public void beforePerformanceTestScenario(){
        dumpsysGFX.resetDumpsys(appPackage);
    }

    public void waitFiveSecs() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
