package newspark.frontend.stepdefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import newspark.frontend.steps.SearchSteps;
import org.springframework.beans.factory.annotation.Autowired;

public class SearchStepDefs {

    @Autowired
    private SearchSteps searchSteps;

    @Given("^user is on the search screen$")
    public void userIsOnSearchScreen() {
        searchSteps.navigateToSearch();
    }

    @When("^user performs a searchquery$")
    public void userEntersSearchQuery() {
        searchSteps.enterSearchQueryAndSearch();
    }

    @When("^user enter a searchquery and presses cancel$")
    public void userEntersSearchQueryCancels() {
        searchSteps.enterSearchQueryAndCancel();
    }

    @Then("^a scrollable list with news items is shown$")
    public void scrollibleNewsListShown() {
        searchSteps.checkResults();
        searchSteps.scrollInResults();
    }

    @Then("^the searchquery is canceled$")
    public void queryCanceled() {
        searchSteps.checkSearchIsCanceled();
    }
}
