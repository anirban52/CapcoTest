package Runner;

import com.test.util.TestBase;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"./src/main/java/Features/"},
        glue = {"StepDefinitions"},
        dryRun = false,
        publish = true,
        plugin = {"html:Html-Output/result.html","json:target/cucumber.json","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)

public class JunitRunner extends TestBase {


}

