package com.mitra.middleware.testsupportRouter.dto;

public class RouterRule {

    private String xpathRule;
    private String activeStatus;
    private String localRuleFileName;
    private String conditionsFileName;

    public String getXpathRule() {
        return xpathRule;
    }

    public void setXpathRule(String xpathRule) {
        this.xpathRule = xpathRule;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getLocalRuleFileName() {
        return localRuleFileName;
    }

    public void setLocalRuleFileName(String localRuleFileName) {
        this.localRuleFileName = localRuleFileName;
    }

    public String getConditionsFileName() {
        return conditionsFileName;
    }

    public void setConditionsFileName(String conditionsFileName) {
        this.conditionsFileName = conditionsFileName;
    }
}
