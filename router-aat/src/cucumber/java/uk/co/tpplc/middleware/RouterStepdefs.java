package com.mitra.middleware;

import java.io.IOException;
import java.util.Properties;

import javax.xml.xpath.XPathExpressionException;

import org.wso2.carbon.authenticator.stub.LoginAuthenticationExceptionException;
import org.wso2.carbon.authenticator.stub.LogoutAuthenticationExceptionException;
import org.wso2.carbon.registry.resource.stub.ResourceAdminServiceExceptionException;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.co.mruoc.properties.FileSystemPropertyLoader;
import uk.co.mruoc.properties.PropertyLoader;
import com.mitra.middleware.testsupport.cucumber.CucumberEnvironment;
import com.mitra.middleware.testsupportRouter.constants.PayloadFilesConstants;
import com.mitra.middleware.testsupportRouter.constants.RouterConstants;
import com.mitra.middleware.testsupportRouter.dto.AdminServiceData;
import com.mitra.middleware.testsupportRouter.dto.RouterParameterData;
import com.mitra.middleware.testsupportRouter.dto.RouterResponse;
import com.mitra.middleware.testsupportRouter.dto.RouterRule;
import com.mitra.middleware.testsupportRouter.util.CommonUtils;
import com.mitra.middleware.testsupportRouter.util.FileLoader;
import com.mitra.middleware.testsupportRouter.util.HTTPHandler;
import com.mitra.middleware.testsupportRouter.util.JSONHandler;

public class RouterStepdefs {
	private static PropertyLoader propertyLoader;
	private static Properties inventoryProperties;
	private static Properties namespaceProperties;
	private RouterParameterData routerData;
	private CucumberEnvironment environment;

	public RouterStepdefs(CucumberEnvironment cucumberEnvironment) {
		this.routerData = new RouterParameterData();
		this.environment = cucumberEnvironment;
		this.routerData.setRouterRule(new RouterRule());
	}

	@Before
	public void initialize() {
		if (inventoryProperties == null) {
			propertyLoader = new FileSystemPropertyLoader();
			inventoryProperties = propertyLoader.load(RouterConstants.INVENTORY_PROPERTY_FILE_PATH);
			namespaceProperties = propertyLoader.load(RouterConstants.NAMESPACE_PROPERTY_FILE_PATH);
			Properties commonProperties = propertyLoader.load(RouterConstants.COMMON_PROPERTY_FILE_PATH);
			inventoryProperties.putAll(commonProperties);
			namespaceProperties.putAll(commonProperties);
		}
	}

	private String getPropertyValue(String propertyKey) {
		String propertyValue = null;
		if (this.routerData.getPayloadType().equals(PayloadFilesConstants.INVENTORY_XML_PAYLOAD_TYPE)) {
			propertyValue = inventoryProperties.getProperty(propertyKey);
		} else {
			propertyValue = namespaceProperties.getProperty(propertyKey);
		}
		return propertyValue;
	}

	@Given("^I have a valid \"(.*?)\" xml payload$")
	public void setPayloadType(String payloadType) throws IOException {
		this.routerData.setPayloadType(payloadType);
		this.routerData.setPayload(CommonUtils.setPayload(payloadType));
	}

	@And("^I set parameters \"(.*?)\" and \"(.*?)\"$")
	public void setRequestParameters(String ruleFilePath, String errorSequence) {
		this.routerData.setRuleFilePath(ruleFilePath);
		this.routerData.setErrorSequence(errorSequence);
	}

	@And("^I set router file parameters \"(.*?)\", \"(.*?)\", \"(.*?)\" and \"(valid|invalid)\" \"(sequence|endpoint)\" \"(.*?)\"$")
	public void setRequestParameters(String activeStatus, String xpathRule, String errorSequence, String validStatus,
			String useCaseType, String conditionFilePath) {
		this.routerData.setUseCaseType(useCaseType);
		this.routerData.getRouterRule().setActiveStatus(getPropertyValue(activeStatus));
		this.routerData.getRouterRule().setXpathRule(getPropertyValue(xpathRule));
		this.routerData.getRouterRule()
				.setConditionsFileName(CommonUtils.getRouterConditionsResourcePath(this.routerData.getUseCaseType(),
						this.routerData.getPayloadType(), validStatus) + getPropertyValue(conditionFilePath));
		this.routerData.setRuleFilePath(RouterConstants.RULE_GREG_PATH);
		this.routerData.setErrorSequence(getPropertyValue(errorSequence));
	}

	@And("^I set router \"(.*?)\" file as \"(valid|invalid)\" local json rule file$")
	public void setJsonRuleFile(String ruleFileName, String validStatus) {
		this.routerData.getRouterRule()
				.setLocalRuleFileName(CommonUtils.getLocalRouterRuleResourcePath(validStatus) + ruleFileName);
	}

	@And("I create \"(dynamic|static)\" json rule file in greg")
	public void generateDynamicJsonRuleFile(String fileType) throws LoginAuthenticationExceptionException,
			ResourceAdminServiceExceptionException, LogoutAuthenticationExceptionException, IOException {
		AdminServiceData adminServiceData = new AdminServiceData();
		if (fileType.equals(RouterConstants.LOCAL_RULE_FILE_TYPE_DYNAMIC)) {
			adminServiceData.setContent(JSONHandler.generateRouterRuleJson(this.routerData.getRouterRule()));
		} else {
			adminServiceData
					.setContent(FileLoader.readResourceContent(this.routerData.getRouterRule().getLocalRuleFileName()));
		}
		adminServiceData.setGregPath(RouterConstants.GREG_PATH + this.routerData.getRuleFilePath());
		adminServiceData.setBackEndUrl(environment.getProperty("adminServiceApiURL"));
		adminServiceData.setUserName(environment.getMBUsername());
		adminServiceData.setPassword(environment.getMBPassword());
		JSONHandler.updateRouterRuleGregResource(adminServiceData);
	}

	@When("^I send them to router building block \"(.*?)\" use case$")
	public void sendRequestToRouterBuildingBlock(String usecaseType) throws InterruptedException, IOException {
		String endpointUrl = this.environment.getProperty(RouterConstants.API_URL_PROPERTY_NAME);
		if (usecaseType.equals(RouterConstants.USE_CASE_TYPE_ENDPOINT)) {
			endpointUrl = endpointUrl + RouterConstants.API_RESOURCE_ENDPOINT;
		}
		RouterResponse response = HTTPHandler.sendRequest(endpointUrl, this.routerData);
		if (response != null) {
			this.routerData.setRouterResponse(response);
		}
	}

	@Then("^I verify response$")
	public void verifyResponseMessage() throws XPathExpressionException {
		CommonUtils.verifyHTTPResponse(this.routerData);
		CommonUtils.verifyResponseMessageByType(this.routerData.getRouterResponse().getResponseMessage(),
				RouterConstants.SUCCESS_REASON);
		CommonUtils.verifyResponseErrorMessage(this.routerData.getRouterResponse().getResponseMessage());
	}

	@Then("^I verify response \"(.*?)\"$")
	public void verifyResponseMessage(String targetValue) throws XPathExpressionException {
		this.verifyResponseMessage();
		CommonUtils.verifyResponseMessageByType(this.routerData.getRouterResponse().getResponseMessage(), targetValue);
	}

	@Then("^I verify the partially success response with \"(.*?)\"$")
	public void verifyPartiallySuccessResponseMessage(String failSequences) throws XPathExpressionException {
		CommonUtils.verifyHTTPResponse(this.routerData);
		CommonUtils.verifyResponseMessageByType(this.routerData.getRouterResponse().getResponseMessage(),
				RouterConstants.SUCCESS_REASON);
		CommonUtils.verifyResponseErrorMessage(this.routerData.getRouterResponse().getResponseMessage());
		CommonUtils.verifyFailedSequences(this.routerData.getRouterResponse().getResponseMessage(),
				failSequences.split(","));
	}

	@Then("^I verify error response with \"(.*?)\", \"(.*?)\" and \"(.*?)\"$")
	public void verifyInvalidResponseMessage(String errorCode, String errorMessage, String isUnhadle) {
		CommonUtils.verifyErrorMessage(this.routerData.getRouterResponse().getResponseMessage(), errorCode,
				errorMessage, isUnhadle);
	}

	@Then("^I verify error response is empty$")
	public void verifyErrorMessageIsEmpty() {
		CommonUtils.verifyErrorMessageIsEmpty(this.routerData.getRouterResponse().getResponseMessage(),
				this.routerData.getRouterResponse().getStatusCode());
	}
}