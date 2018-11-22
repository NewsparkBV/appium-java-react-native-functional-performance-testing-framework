package newspark.performance;

import cucumber.api.java.en.Given;
import newspark.frontend.steps.SearchSteps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class PerformanceStepDefs {

    @Autowired
    private PerformanceSteps performanceSteps;

    @Autowired
    private SearchSteps searchSteps;

    @Value("${app.package}")
    private String appPackage;

    @Given("^user is on the searchpage, start perfomance logs$")
    public void userOnSearchStartPerformanceLogs() {
        performanceSteps.startGFX(appPackage);
        searchSteps.navigateToSearch();

    }
}
