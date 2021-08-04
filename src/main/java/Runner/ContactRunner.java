package Runner;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.test.util.TestBase;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import io.cucumber.testng.FeatureWrapper;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;


@CucumberOptions(
        features = ("./src/main/java/Features/Contact.feature"),
        glue = {"StepDefinitions"},
        dryRun = false,
        publish = true,
//        monochrome = true,
//        strict = true,
        plugin = {"html:Html-Output/result.html","json:target/cucumber.json","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)

public class ContactRunner extends TestBase {

    private ExtentReports extentReports;
    private ExtentTest extentTest;
    private ExtentSparkReporter extentSparkReporter;
    private TestNGCucumberRunner testNGCucumberRunner ;
    private String scenarioName;


    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        this.testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(
            groups = {"cucumber"},
            description = "Runs Cucumber Scenarios",
            dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        extentTest= extentReports.createTest(pickleWrapper.getPickle().getName());
        scenarioName = pickleWrapper.getPickle().getName();
        this.testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider()
    public Object[][] scenarios() {
        return this.testNGCucumberRunner == null ? new Object[0][0] : this.testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(
            alwaysRun = true
    )
    public void tearDownClass() {
        System.out.println("*************** Inside After class");

        if (this.testNGCucumberRunner != null) {
            this.testNGCucumberRunner.finish();
        }
    }

    @BeforeTest
    public void setUpExtent(){
        System.out.println("************* Inside Before Test ****************");
        extentReports = new ExtentReports();
        extentSparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/test-output/extentReport/"+this.getClass().getName()+"Report.html");
        extentReports.attachReporter(extentSparkReporter);
        extentReports.setSystemInfo("Host Name","Anirban Mac");
        extentReports.setSystemInfo("User Name","Anirban");
        extentReports.setSystemInfo("Environment","QA");
        extentReports.setSystemInfo("Browser","Chrome");
        extentReports.setSystemInfo("Application","Free CRM");
        extentReports.setReportUsesManualConfiguration(true);
        extentSparkReporter.config().setDocumentTitle("Extent Report Demo");
        extentSparkReporter.config().setReportName(this.getClass().getName()+" Report");
        extentSparkReporter.config().setTheme(Theme.DARK);
        extentSparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        loggerThreadLocal.set(Logger.getLogger(this.getClass().getName()));
    }

    @BeforeMethod
    public void initialize(){
        System.out.println("************** Inside Before Method **************");
        System.out.println("Runing on Thread - "+Thread.currentThread().getId());
        initialization();
    }

    @AfterMethod
    public void getResult(ITestResult result) throws IOException {
        System.out.println("************** Inside After Method **************");
        if(result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(Status.FAIL, MarkupHelper.createLabel(scenarioName +" FAILED ", ExtentColor.RED));
            extentTest.fail(result.getThrowable());
            extentTest.addScreenCaptureFromPath(getScreenShot(getDriver(),scenarioName));

        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, MarkupHelper.createLabel(scenarioName +" PASSED ", ExtentColor.GREEN));
        }
        else {
            extentTest.log(Status.SKIP, MarkupHelper.createLabel(scenarioName +" SKIPPED ", ExtentColor.ORANGE));
            extentTest.skip(result.getThrowable());
        }
        getDriver().quit();
    }

    @AfterTest
    public void flushExtent(){
        System.out.println("************* Inside After Test");
        extentReports.flush();
    }



}
