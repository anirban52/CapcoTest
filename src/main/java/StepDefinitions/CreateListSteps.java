package StepDefinitions;

import Pages.AddListPage;
import Pages.LayoutSelectPage;
import com.test.util.TestBase;
import io.cucumber.java.en.*;

public class CreateListSteps extends TestBase {

    private LayoutSelectPage layoutSelectPage = new LayoutSelectPage(getMobileDriver(),getWait());
    private AddListPage addListPage = new AddListPage(getMobileDriver(),getWait());

    @Given("User selected the correct Layout")
    public void user_selected_the_correct_layout() {
        layoutSelectPage.selectBottomLayput();
    }
    @Then("User clicks on hamberger menu")
    public void user_clicks_on_hamberger_menu() {
        addListPage.selecthamburgerMenu();
    }
    @Then("user selects new List")
    public void user_selects_new_list() {
        addListPage.selectNewList();
    }
    @Then("user provides the name of the new List {string}")
    public void user_provides_the_name_of_the_new_list(String string) {
        addListPage.userProvidesNameOfTheNewList(string);
    }
    @Then("user clicks on Ok Button")
    public void user_clicks_on_ok_button() {
        addListPage.clickOkButton();
    }
    @Then("user adds {string} {string} and {string} in the list")
    public void user_adds_and_in_the_list(String item1, String item2, String item3) {
        addListPage.userAddItems(item1);
        addListPage.userAddItems(item2);
        addListPage.userAddItems(item3);
    }
    @Then("user validates the count of the added items {string}")
    public void user_validates_the_count_of_the_added_items(String string) {
        addListPage.validateCountOfAddedItem(Integer.parseInt(string));
    }
    @Then("user selects Item {string}")
    public void user_selects_item(String string) {
        addListPage.userSelectsItem(string);
    }
    @Then("user removes one item")
    public void user_removes_one_item() {
        addListPage.userRemovesItem();
    }
    @Then("user validates the count is reduced {string}")
    public void user_validates_the_count_is_reduced(String string) {
        addListPage.validateCountOfAddedItem(Integer.parseInt(string)-1);
    }

    @Then("user validates {string} {string} lists are added")
    public void user_validates_lists_are_added(String list1, String list2) {
        addListPage.userChecksListIsCreated(list1);
        addListPage.userChecksListIsCreated(list2);
    }


}
