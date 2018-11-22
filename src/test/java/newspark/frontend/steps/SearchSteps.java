package newspark.frontend.steps;

import newspark.frontend.screenobjects.BottomNavigationObject;
import newspark.frontend.screenobjects.ResultsScreen;
import newspark.frontend.screenobjects.SearchBarObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Component
@Scope("cucumber-glue")
public class SearchSteps {

    @Autowired
    private BottomNavigationObject bottomNavigation;
    @Autowired
    private SearchBarObject searchBar;
    @Autowired
    private ResultsScreen resultsScreen;

    public void navigateToSearch() {
        bottomNavigation.navigateToSearch();
    }

    public void enterSearchQueryAndSearch() {
        searchBar.enterSearchQueryAndSearch();
    }

    public void enterSearchQueryAndCancel() {
        searchBar.enterSearchQueryAndCancelSearch();
    }

    public void checkResults() {
        assertThat("No Results shown", resultsScreen.getArticleScrollList().isDisplayed(), is(true));
    }

    public void checkSearchIsCanceled() {
        assertThat("Search not canceled", resultsScreen.getArticleScrollList().isDisplayed(), is(true));
    }

    public void scrollInResults() {
        resultsScreen.scrollDown();
        resultsScreen.scrollDown();
    }
}
