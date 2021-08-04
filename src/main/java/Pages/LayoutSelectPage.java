package Pages;

import com.test.util.TestBase;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LayoutSelectPage {

    private AppiumDriver driver;
    private WebDriverWait wait;

    public LayoutSelectPage(AppiumDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);

    }

    @AndroidFindBy(id = "layout_choice_bottom")
    private MobileElement LAYOUT_CHOICE_BOTTOM;

    public void selectBottomLayput(){
        wait.until(ExpectedConditions.elementToBeClickable(LAYOUT_CHOICE_BOTTOM));
        LAYOUT_CHOICE_BOTTOM.click();
    }


}
