package Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class AddListPage {

    private AppiumDriver driver;
    private WebDriverWait wait;

    public AddListPage(AppiumDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);

    }

    @AndroidFindBy(accessibility = "Open navigation drawer")
    private MobileElement OPEN_NAVIGATION_DRAWER;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='New list']")
    private MobileElement NEW_LIST;

    @AndroidFindBy(id = "edittext")
    private MobileElement LIST_NAME_EDIT;

    @AndroidFindBy(id = "android:id/button1")
    private MobileElement BTN_OK;

    @AndroidFindBy(id = "button_add_item")
    private MobileElement BTN_ADD;

    @AndroidFindBy(accessibility = "Clean up list")
    private MobileElement BTN_REMOVE;

    @AndroidFindBy(id = "autocomplete_add_item")
    private MobileElement ITEM_NAME_EDIT;

    @AndroidFindBy(xpath = "")

    private By ITEM_NAME = By.id("name");
    private By ITEM_CHECK = By.id("check");
    private int COUNT_BEFORE_REMOVING = 0;
    private int COUNT_AFTER_REMOVING = 0;

    private By checkboxXpathGeneratorForSelectedItem(String item) {
        return By.xpath("//android.widget.TextView[@text='" + item + "']//parent::android.widget.RelativeLayout//preceding-sibling::android.widget.RelativeLayout/android.widget.CheckBox");
    }

    private By xpathGeneratorForTheAddedLists(String listName){
        return By.xpath("//android.widget.TextView[@text='"+listName+"']");
    }

    public void selecthamburgerMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(OPEN_NAVIGATION_DRAWER));
        OPEN_NAVIGATION_DRAWER.click();
    }

    public void selectNewList() {
        wait.until(ExpectedConditions.elementToBeClickable(NEW_LIST));
        NEW_LIST.click();
    }

    public void userProvidesNameOfTheNewList(String newListName) {
        wait.until(ExpectedConditions.visibilityOf(LIST_NAME_EDIT));
        LIST_NAME_EDIT.setValue(newListName);
    }

    public void clickOkButton() {
        wait.until(ExpectedConditions.visibilityOf(BTN_OK));
        BTN_OK.click();
    }

    public void userAddItems(String item) {
        wait.until(ExpectedConditions.visibilityOf(ITEM_NAME_EDIT));
        ITEM_NAME_EDIT.setValue(item);
        BTN_ADD.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkboxXpathGeneratorForSelectedItem(item)));
    }

    public void validateCountOfAddedItem(int count) {
        List<MobileElement> listOfAddedItem = driver.findElements(ITEM_NAME);
        Assert.assertTrue(listOfAddedItem.size() == count, "Added Items count is not matching as per expected");
        COUNT_BEFORE_REMOVING = listOfAddedItem.size();
    }

    public void userSelectsItem(String item) {
        driver.findElement(checkboxXpathGeneratorForSelectedItem(item)).click();
    }

    public void userRemovesItem() {
        wait.until(ExpectedConditions.elementToBeClickable(BTN_REMOVE));
        BTN_REMOVE.click();
    }

    public void userChecksListIsCreated(String list){
        Assert.assertTrue(driver.findElements(xpathGeneratorForTheAddedLists(list)).size()==1,"List is not Created");
    }


}
