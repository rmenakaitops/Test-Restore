package com.mitra.middleware.testsupportRouter.util;

import java.io.IOException;
import java.rmi.RemoteException;

import org.wso2.carbon.authenticator.stub.LoginAuthenticationExceptionException;
import org.wso2.carbon.authenticator.stub.LogoutAuthenticationExceptionException;
import org.wso2.carbon.registry.resource.stub.ResourceAdminServiceExceptionException;

import gherkin.deps.com.google.gson.Gson;
import gherkin.deps.com.google.gson.JsonElement;
import gherkin.deps.com.google.gson.JsonObject;
import gherkin.deps.com.google.gson.JsonSyntaxException;
import com.mitra.middleware.testsupportRouter.constants.RouterConstants;
import com.mitra.middleware.testsupportRouter.dto.AdminServiceData;
import com.mitra.middleware.testsupportRouter.dto.RouterRule;

public class JSONHandler {

	public static String generateRouterRuleJson(RouterRule routerRuleObj) throws IOException {
		JsonElement routerRuleJson = getJsonElementFromFile(routerRuleObj.getLocalRuleFileName());
		JsonElement routerConditionsJson = getJsonElementFromFile(routerRuleObj.getConditionsFileName());
		for (JsonElement routerRule : routerRuleJson.getAsJsonArray()) {
			JsonObject routerRuleJsonObj = routerRule.getAsJsonObject();
			if (routerRuleJsonObj.get(RouterConstants.RULE_KEY_ACTIVE).getAsBoolean()) {
				routerRuleJsonObj.addProperty(RouterConstants.RULE_KEY_ACTIVE, routerRuleObj.getActiveStatus());
				routerRuleJsonObj.addProperty(RouterConstants.RULE_KEY_XPATH, routerRuleObj.getXpathRule());
				routerRuleJsonObj.add(RouterConstants.RULE_KEY_CONDITIONS, routerConditionsJson);
			}
		}
		return routerRuleJson.getAsJsonArray().toString();
	}

	private static JsonElement getJsonElementFromFile(String filePath) throws JsonSyntaxException, IOException {
		Gson gson = new Gson();
		return gson.fromJson(FileLoader.readResourceContent(filePath), JsonElement.class);
	}

	public static void updateRouterRuleGregResource(AdminServiceData adminServiceData)
			throws RemoteException, LoginAuthenticationExceptionException, ResourceAdminServiceExceptionException,
			LogoutAuthenticationExceptionException {
		AuthenticationAdminServiceClient authClient = new AuthenticationAdminServiceClient(
				adminServiceData.getBackEndUrl());
		String sessionCookie = authClient.authenticate(adminServiceData.getUserName(), adminServiceData.getPassword(),
				adminServiceData.getHost());
		RegistryAdminServiceClient serviceAdminClient = new RegistryAdminServiceClient(adminServiceData.getBackEndUrl(),
				sessionCookie);
		serviceAdminClient.updateContent(adminServiceData.getGregPath(), adminServiceData.getContent());
		authClient.logOut();
	}

}
