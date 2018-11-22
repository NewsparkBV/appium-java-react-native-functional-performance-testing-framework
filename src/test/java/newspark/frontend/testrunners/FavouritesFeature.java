package newspark.frontend.testrunners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import newspark.utils.RunnerHooks;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"newspark"})
@Configuration
@RunWith(Cucumber.class)
@CucumberOptions(plugin = "json:target/cucumber.json", features = {
        "src/test/resources/features/news/favourites.feature"},
        glue = "newspark")
public class FavouritesFeature extends RunnerHooks {
}
