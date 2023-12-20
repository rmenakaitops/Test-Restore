@router
Feature: Router Mediation Flow verification

  Scenario Outline: Verify Router Mediation Flow with Valid input values
    Given I have a valid "namespace" xml payload
    And I set parameters "<Rule_File_Path>" and "<Error_Seq>"
    And I set router "router-v1-rules-set-valid-namespace-single.json" file as "valid" local json rule file
    And I create "static" json rule file in greg
    When I send them to router building block "sequence" use case
    Then I verify response

    Examples: 
      | Rule_File_Path                                     | Error_Seq                               |
      | router-rules/router-v1-sample-rule-definition.json | router-v1-sample-error-message-sequence |
      | router-rules/router-v1-sample-rule-definition.json |                                         |

  Scenario Outline: Verify Router Mediation Flow with Valid input values  - Single Json Rule Object
    Given I have a valid "namespace" xml payload
    And I set router file parameters "<Active_Rule>", "<Xpath_Rule>", "<Error_Seq>" and "valid" "sequence" "<Condition_File_Path>"
    And I set router "router-v1-rules-set-valid-namespace-single.json" file as "valid" local json rule file
    And I create "dynamic" json rule file in greg
    When I send them to router building block "sequence" use case
    Then I verify response

    Examples: 
      | Active_Rule           | Xpath_Rule   | Condition_File_Path   | Error_Seq          |
      | validActiveStatusTRUE | validXpath1  | validConditionsJson1  | validErrorSequence |
      | validActiveStatusTRUe | validXpath2  | validConditionsJson2  | validErrorSequence |
      | validActiveStatusTrue | validXpath4  | validConditionsJson4  | validErrorSequence |
      | validActiveStatustrue | validXpath5  | validConditionsJson5  | validErrorSequence |
      | validActiveStatusTRUE | validXpath6  | validConditionsJson6  | validErrorSequence |
      | validActiveStatustrue | validXpath10 | validConditionsJson10 | validErrorSequence |
      | validActiveStatusTRUE | validXpath11 | validConditionsJson11 | validErrorSequence |
      | validActiveStatusTRUe | validXpath12 | validConditionsJson12 | validErrorSequence |
      | validActiveStatusTRue | validXpath13 | validConditionsJson13 | validErrorSequence |
      | validActiveStatusTrue | validXpath14 | validConditionsJson14 | validErrorSequence |
      | validActiveStatustrue | validXpath15 | validConditionsJson15 | validErrorSequence |
      | validActiveStatusTRUE | validXpath16 | validConditionsJson16 | validErrorSequence |
      | validActiveStatusTRUe | validXpath17 | validConditionsJson17 | validErrorSequence |

  Scenario Outline: Verify Router Mediation Flow with Valid input values - Multiple Json Rule Objects
    Given I have a valid "namespace" xml payload
    And I set router file parameters "<Active_Rule>", "<Xpath_Rule>", "<Error_Seq>" and "valid" "sequence" "<Condition_File_Path>"
    And I set router "router-v1-rules-set-valid-namespace-multiple.json" file as "valid" local json rule file
    And I create "dynamic" json rule file in greg
    When I send them to router building block "sequence" use case
    Then I verify response

    Examples: 
      | Active_Rule           | Xpath_Rule   | Condition_File_Path   | Error_Seq          |
      | validActiveStatusTRUE | validXpath1  | validConditionsJson1  | validErrorSequence |
      | validActiveStatusTRUe | validXpath2  | validConditionsJson2  | validErrorSequence |
      | validActiveStatusTrue | validXpath4  | validConditionsJson4  | validErrorSequence |
      | validActiveStatustrue | validXpath5  | validConditionsJson5  | validErrorSequence |
      | validActiveStatusTRUE | validXpath6  | validConditionsJson6  | validErrorSequence |
      | validActiveStatustrue | validXpath10 | validConditionsJson10 | validErrorSequence |
      | validActiveStatusTRUE | validXpath11 | validConditionsJson11 | validErrorSequence |
      | validActiveStatusTRUe | validXpath12 | validConditionsJson12 | validErrorSequence |
      | validActiveStatusTRue | validXpath13 | validConditionsJson13 | validErrorSequence |
      | validActiveStatusTrue | validXpath14 | validConditionsJson14 | validErrorSequence |
      | validActiveStatustrue | validXpath15 | validConditionsJson15 | validErrorSequence |
      | validActiveStatusTRUE | validXpath16 | validConditionsJson16 | validErrorSequence |
      | validActiveStatusTRUe | validXpath17 | validConditionsJson17 | validErrorSequence |

  Scenario Outline: Verify Router Mediation Flow for Partially Success Scenario
    Given I have a valid "namespace" xml payload
    And I set router file parameters "<Active_Rule>", "<Xpath_Rule>", "<Error_Seq>" and "valid" "sequence" "<Condition_File_Path>"
    And I set router "router-v1-rules-set-valid-namespace-single.json" file as "valid" local json rule file
    And I create "dynamic" json rule file in greg
    When I send them to router building block "sequence" use case
    Then I verify the partially success response with "<Faild_Seqs>"

    Examples: 
      | Active_Rule           | Xpath_Rule  | Condition_File_Path   | Error_Seq          | Faild_Seqs                                                              |
      | validActiveStatusTRue | validXpath1 | validConditionsJson18 | validErrorSequence | router-v1-sample-channel12-sequence                                     |
      | validActiveStatusTrue | validXpath1 | validConditionsJson19 | validErrorSequence | router-v1-sample-channel11-sequence,router-v1-sample-channel12-sequence |
