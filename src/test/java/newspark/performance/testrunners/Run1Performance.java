package newspark.performance.testrunners;

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
        "src/test/resources/features/performance/"
},
        glue = {"newspark"}
        )
public class Run1Performance extends RunnerHooks {
}
