Feature: Shopping List creation in OpenIntent

  Scenario Outline: Create a new Shopping List and Add Item


    Given User selected the correct Layout
    Then User clicks on hamberger menu
    And user selects new List
    Then user provides the name of the new List "<FirstListName>"
    And user clicks on Ok Button
    Then user adds "<Item1>" "<Item2>" and "<Item3>" in the list
    Then user validates the count of the added items "<count>"
    Then user selects Item "<Item2>"
    Then user removes one item
    And user validates the count is reduced "<count>"


    Examples:

      | FirstListName  | Item1  | Item2  | Item3 | count |
      | List           | Bread  | Butter | Sugar |  3    |


  Scenario Outline: User creates two lists and validate the number of lists added

    Given User selected the correct Layout
    Then User clicks on hamberger menu
    And user selects new List
    Then user provides the name of the new List "<FirstListName>"
    And user clicks on Ok Button
    Then user adds "<Item1>" "<Item2>" and "<Item3>" in the list
    Then User clicks on hamberger menu
    And user selects new List
    Then user provides the name of the new List "<SecondListName>"
    And user clicks on Ok Button
    Then User clicks on hamberger menu
    Then user validates "<FirstListName>" "<SecondListName>" lists are added

    Examples:

      | FirstListName  | Item1  | Item2  | Item3 | SecondListName |
      | List1          | Bread  | Butter | Sugar |  List2         |