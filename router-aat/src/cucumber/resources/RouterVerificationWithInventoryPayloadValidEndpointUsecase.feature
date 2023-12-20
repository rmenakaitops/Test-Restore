@router
Feature: Router Mediation Flow verification

  Scenario Outline: Verify Router Mediation Flow with Valid input values
    Given I have a valid "inventory" xml payload
    And I set parameters "<Rule_File_Path>" and "<Error_Seq>"
    And I set router "router-v1-rules-set-valid-endpoint.json" file as "valid" local json rule file
    And I create "static" json rule file in greg
    When I send them to router building block "endpoint" use case
    Then I verify response "Target 1"

    Examples: 
      | Rule_File_Path                                     | Error_Seq                               |
      | router-rules/router-v1-sample-rule-definition.json | router-v1-sample-error-message-sequence |
      | router-rules/router-v1-sample-rule-definition.json |                                         |

  Scenario Outline: Verify Router Mediation Flow with Valid input values  - Single Json Rule Object
    Given I have a valid "inventory" xml payload
    And I set router file parameters "<Active_Rule>", "<Xpath_Rule>", "<Error_Seq>" and "valid" "endpoint" "<Condition_File_Path>"
    And I set router "router-v1-rules-set-valid-endpoint.json" file as "valid" local json rule file
    And I create "dynamic" json rule file in greg
    When I send them to router building block "endpoint" use case
    Then I verify response "<Target>"

    Examples: 
      | Active_Rule           | Xpath_Rule   | Condition_File_Path   | Error_Seq          | Target   |
      | validActiveStatusTRUE | validXpath1  | validConditionsJson1  | validErrorSequence | Target 1 |
      | validActiveStatusTRUe | validXpath2  | validConditionsJson2  | validErrorSequence | Target 1 |
      | validActiveStatusTrue | validXpath4  | validConditionsJson4  | validErrorSequence | Target 1 |
      | validActiveStatustrue | validXpath5  | validConditionsJson5  | validErrorSequence | Target 2 |
      | validActiveStatusTRUE | validXpath6  | validConditionsJson6  | validErrorSequence | Target 2 |
      | validActiveStatustrue | validXpath10 | validConditionsJson10 | validErrorSequence | Target 1 |
      | validActiveStatusTRUE | validXpath11 | validConditionsJson11 | validErrorSequence | Target 1 |
      | validActiveStatusTRUe | validXpath12 | validConditionsJson12 | validErrorSequence | Target 1 |
      | validActiveStatusTRue | validXpath13 | validConditionsJson13 | validErrorSequence | Target 1 |
      | validActiveStatusTrue | validXpath14 | validConditionsJson14 | validErrorSequence | Target 1 |
      | validActiveStatustrue | validXpath15 | validConditionsJson15 | validErrorSequence | Target 1 |
      | validActiveStatusTRUE | validXpath16 | validConditionsJson16 | validErrorSequence | Target 2 |
      | validActiveStatusTRUe | validXpath17 | validConditionsJson17 | validErrorSequence | Target 2 |
