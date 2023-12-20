@router
Feature: Router Mediation Flow Verification for Invalid Scenario

  Scenario Outline: Verify Router Mediation Flow with Invalid input values - Single Json Rule Object
    Given I have a valid "namespace" xml payload
    And I set router file parameters "<Active_Rule>", "<Xpath_Rule>", "<Error_Seq>" and "invalid" "sequence" "<Condition_File_Path>"
    And I set router "router-v1-rules-set-valid-namespace-single.json" file as "valid" local json rule file
    And I create "dynamic" json rule file in greg
    When I send them to router building block "sequence" use case
    Then I verify error response with "<Error_Code>", "<Error_Message>" and "<Unhandled_Error_State>"

    Examples: 
      | Active_Rule                     | Xpath_Rule                      | Condition_File_Path     | Error_Seq          | Error_Code | Error_Message                                         | Unhandled_Error_State |
      | emptyInput                      | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | nullInput                       | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | nanInput                        | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | hiInput                         | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | numberInputNeg5                 | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | numberInputNeg1                 | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | numberInput0                    | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | numberInput10                   | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | invalidXmlElement1              | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | invalidXmlElement2              | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | invalidXmlElement3              | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | invalidXmlPath1                 | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | invalidXmlPath2                 | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacters               | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacterStar            | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacterAtStar          | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacterSlashStar       | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacterDoubleSlashStar | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacterDoubleQuotes    | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacterDoubleQuote     | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacterDot             | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | validActiveStatusTRUE           | emptyInput                      | invalidConditionsJson14 | validErrorSequence |       1007 | XPATH is not defined for active rule                  | false                 |
      | validActiveStatusTRUE           | nullInput                       | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | nanInput                        | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | hiInput                         | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | numberInputNeg5                 | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | numberInputNeg1                 | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | numberInput0                    | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | numberInput10                   | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | invalidXmlElement1              | invalidConditionsJson14 | validErrorSequence |       1012 | Error while evaluating the XPATH                      | false                 |
      | validActiveStatusTRUE           | invalidXmlElement2              | invalidConditionsJson14 | validErrorSequence |       1012 | Error while evaluating the XPATH                      | false                 |
      | validActiveStatusTRUE           | invalidXmlElement3              | invalidConditionsJson14 | validErrorSequence |       1012 | Error while evaluating the XPATH                      | false                 |
      | validActiveStatusTRUE           | invalidXmlPath1                 | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | invalidXmlPath2                 | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | specialCharacters               | invalidConditionsJson14 | validErrorSequence |       1012 | Error while evaluating the XPATH                      | false                 |
      | validActiveStatusTRUE           | specialCharacterStar            | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | specialCharacterAtStar          | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | specialCharacterSlashStar       | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | specialCharacterDoubleSlashStar | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | specialCharacterDoubleQuotes    | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | specialCharacterDoubleQuote     | invalidConditionsJson14 | validErrorSequence |       1012 | Error while evaluating the XPATH                      | false                 |
      | validActiveStatusTRUE           | specialCharacterDot             | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | invalidXpath1                   | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | invalidXpath2                   | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | invalidXpath3                   | invalidConditionsJson14 | validErrorSequence |       1012 | Error while evaluating the XPATH                      | false                 |
      | validActiveStatusTRUE           | invalidXpath4                   | invalidConditionsJson14 | validErrorSequence |       1012 | Error while evaluating the XPATH                      | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson2  | validErrorSequence |       1017 | Multiple active conditions found in routing rule file | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson3  | validErrorSequence |       1002 | Conditions not provided for active rule               | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson4  | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson5  | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson6  | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson7  | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson8  | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson10 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson11 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson12 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson13 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |

  Scenario Outline: Verify Router Mediation Flow with Invalid input values - Multiple Json Rule Object
    Given I have a valid "namespace" xml payload
    And I set router file parameters "<Active_Rule>", "<Xpath_Rule>", "<Error_Seq>" and "invalid" "sequence" "<Condition_File_Path>"
    And I set router "router-v1-rules-set-valid-namespace-multiple.json" file as "valid" local json rule file
    And I create "dynamic" json rule file in greg
    When I send them to router building block "sequence" use case
    Then I verify error response with "<Error_Code>", "<Error_Message>" and "<Unhandled_Error_State>"

    Examples: 
      | Active_Rule                     | Xpath_Rule                      | Condition_File_Path     | Error_Seq          | Error_Code | Error_Message                                         | Unhandled_Error_State |
      | emptyInput                      | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | nullInput                       | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | nanInput                        | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | hiInput                         | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | numberInputNeg5                 | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | numberInputNeg1                 | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | numberInput0                    | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | numberInput10                   | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | invalidXmlElement1              | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | invalidXmlElement2              | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | invalidXmlElement3              | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | invalidXmlPath1                 | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | invalidXmlPath2                 | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacters               | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacterStar            | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacterAtStar          | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacterSlashStar       | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacterDoubleSlashStar | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacterDoubleQuotes    | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacterDoubleQuote     | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | specialCharacterDot             | validXpath1                     | invalidConditionsJson14 | validErrorSequence |       1006 | No active rule found in routing rules file            | false                 |
      | validActiveStatusTRUE           | emptyInput                      | invalidConditionsJson14 | validErrorSequence |       1007 | XPATH is not defined for active rule                  | false                 |
      | validActiveStatusTRUE           | nullInput                       | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | nanInput                        | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | hiInput                         | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | numberInputNeg5                 | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | numberInputNeg1                 | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | numberInput0                    | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | numberInput10                   | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | invalidXmlElement1              | invalidConditionsJson14 | validErrorSequence |       1012 | Error while evaluating the XPATH                      | false                 |
      | validActiveStatusTRUE           | invalidXmlElement2              | invalidConditionsJson14 | validErrorSequence |       1012 | Error while evaluating the XPATH                      | false                 |
      | validActiveStatusTRUE           | invalidXmlElement3              | invalidConditionsJson14 | validErrorSequence |       1012 | Error while evaluating the XPATH                      | false                 |
      | validActiveStatusTRUE           | invalidXmlPath1                 | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | invalidXmlPath2                 | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | specialCharacters               | invalidConditionsJson14 | validErrorSequence |       1012 | Error while evaluating the XPATH                      | false                 |
      | validActiveStatusTRUE           | specialCharacterStar            | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | specialCharacterAtStar          | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | specialCharacterSlashStar       | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | specialCharacterDoubleSlashStar | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | specialCharacterDoubleQuotes    | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | specialCharacterDoubleQuote     | invalidConditionsJson14 | validErrorSequence |       1012 | Error while evaluating the XPATH                      | false                 |
      | validActiveStatusTRUE           | specialCharacterDot             | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | invalidXpath1                   | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | invalidXpath2                   | invalidConditionsJson14 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | invalidXpath3                   | invalidConditionsJson14 | validErrorSequence |       1012 | Error while evaluating the XPATH                      | false                 |
      | validActiveStatusTRUE           | invalidXpath4                   | invalidConditionsJson14 | validErrorSequence |       1012 | Error while evaluating the XPATH                      | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson2  | validErrorSequence |       1017 | Multiple active conditions found in routing rule file | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson3  | validErrorSequence |       1002 | Conditions not provided for active rule               | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson4  | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson5  | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson6  | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson7  | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson8  | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson10 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson11 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson12 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |
      | validActiveStatusTRUE           | validXpath1                     | invalidConditionsJson13 | validErrorSequence |       1003 | Matching condition not found in active rule           | false                 |

  Scenario Outline: Verify Router Mediation Flow with Invalid error sequence -
    Given I have a valid "namespace" xml payload
    And I set router file parameters "<Active_Rule>", "<Xpath_Rule>", "<Error_Seq>" and "invalid" "sequence" "<Condition_File_Path>"
    And I set router "router-v1-rules-set-valid-namespace-single.json" file as "valid" local json rule file
    And I create "dynamic" json rule file in greg
    When I send them to router building block "sequence" use case
    Then I verify error response is empty

    Examples: 
      | Active_Rule        | Xpath_Rule  | Condition_File_Path     | Error_Seq |
      | invalidXmlElement1 | validXpath1 | invalidConditionsJson14 | nanInput  |
