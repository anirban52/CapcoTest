package StepDefinitions;

import com.test.util.TestBase;
import io.cucumber.java.After;
import io.cucumber.java.Before;


public class Hooks extends TestBase {

    @Before()
    public void launchDriver(){
        System.out.println("********** Inside Before Hook ************");
        initialization();
    }

    @After
    public void tearDown() {
        System.out.println("********** Inside After Hook ************");
        if(properties.getProperty("type").equalsIgnoreCase("Mobile")){
            getMobileDriver().quit();
            service.stop();
        }
        else{
            getDriver().quit();
        }
    }
}
