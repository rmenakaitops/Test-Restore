package com.mitra.middleware.router.testutil;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.synapse.MessageContext;

public class TestUtil {

	private static final String ROUTER_RULE_DEFINITION_SOURCE = "routerRuleDefinitionPath";

	public static String readFileFromResources(String filePath) throws Exception {
		URL url = TestUtil.class.getClassLoader().getResource(filePath);
		File file = new File(url.toURI());
		return FileUtils.readFileToString(file);
	}

	public static MessageContext buildTestMessageContext(String payloadFileName, String ruleFilePath) throws Exception {
		String body = readFileFromResources(payloadFileName);
		TestMessageContextBuilder testMessageContextBuilder = new TestMessageContextBuilder();
		testMessageContextBuilder.setBodyFromString(body);
		MessageContext messageContext = testMessageContextBuilder.buildMessageContext();
		if (ruleFilePath != null) {
			messageContext.setProperty(ROUTER_RULE_DEFINITION_SOURCE, ruleFilePath);
		}
		return messageContext;
	}
}