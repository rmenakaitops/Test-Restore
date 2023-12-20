package com.mitra.middleware.router;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.apache.synapse.MessageContext;
import org.apache.synapse.config.SynapseConfiguration;
import org.junit.Test;
import org.wso2.carbon.mediation.registry.WSO2Registry;
import org.wso2.carbon.registry.core.Resource;

import com.mitra.middleware.router.constant.Error;
import com.mitra.middleware.router.exception.RuleManagerException;
import com.mitra.middleware.router.testutil.TestUtil;

public class RuleManagerTest {

	private static final String RULE_REG_FILE_PATH = "gov:/router/rule.json";
	private static final String SAMPLE_PAYLOAD = "sample-payload.xml";
	private static final String VALID_RULE_JSON = "valid-rule.json";
	private static final String NO_ACTIVE_RULE_JSON = "no-active-rule.json";
	private static final String MULTIPLE_ACTIVE_RULE_JSON = "multiple-active-rule.json";
	private static final String NO_RULES_JSON = "no-rules.json";
	private static final String SYNTAX_ERROR_JSON = "syntax-error.json";
	private static final String XPATH_NOT_DEFINED_JSON = "xpath-not-defined.json";
	private static final String INVALID_XPATH_JSON = "invalid-xpath.json";
	private static final String CONDITIONS_NOT_DEFINED_JSON = "conditions-not-defined.json";
	private static final String SEQUENCES_NOT_DEFINED_JSON = "sequences-not-defined.json";
	private static final String UNMATCHING_CONDITION_JSON = "unmtaching-condition.json";

	@Test
	public void shouldReturnTargetSequences() throws Exception {
		Set<String> sequences = invokeGetTargetSequencesMethod(SAMPLE_PAYLOAD, VALID_RULE_JSON, RULE_REG_FILE_PATH);
		assertNotNull(sequences);
		assertEquals(2, sequences.size());
	}

	@Test
	public void shouldThrowExceptionWithErrorCodeRuleFilePathIsNull() throws Exception {
		try {
			invokeGetTargetSequencesMethod(SAMPLE_PAYLOAD, VALID_RULE_JSON, null);
			assertTrue(false);
		} catch (RuleManagerException ex) {
			assertEquals(Error.Code.RULE_FILE_PATH_IS_NULL, ex.getErrorCode());
		}
	}

	@Test
	public void shouldThrowExceptionWithErrorCodeJsonParserError() throws Exception {
		try {
			invokeGetTargetSequencesMethod(SAMPLE_PAYLOAD, SYNTAX_ERROR_JSON, RULE_REG_FILE_PATH);
			assertTrue(false);
		} catch (RuleManagerException ex) {
			assertEquals(Error.Code.JSON_PARSER_ERROR, ex.getErrorCode());
		}
	}

	@Test
	public void shouldThrowExceptionWithErrorCodeNoRulesFound() throws Exception {
		try {
			invokeGetTargetSequencesMethod(SAMPLE_PAYLOAD, NO_RULES_JSON, RULE_REG_FILE_PATH);
			assertTrue(false);
		} catch (RuleManagerException ex) {
			assertEquals(Error.Code.NO_RULES_FOUND_IN_RULE_FILE, ex.getErrorCode());
		}
	}

	@Test
	public void shouldThrowExceptionWithErrorCodeNoActiveRuleFound() throws Exception {
		try {
			invokeGetTargetSequencesMethod(SAMPLE_PAYLOAD, MULTIPLE_ACTIVE_RULE_JSON, RULE_REG_FILE_PATH);
			assertTrue(false);
		} catch (RuleManagerException ex) {
			assertEquals(Error.Code.MULTIPLE_ACTIVE_RULE_FOUND, ex.getErrorCode());
		}
	}

	@Test
	public void shouldThrowExceptionWithErrorCodeMultipleActiveRuleFound() throws Exception {
		try {
			invokeGetTargetSequencesMethod(SAMPLE_PAYLOAD, NO_ACTIVE_RULE_JSON, RULE_REG_FILE_PATH);
			assertTrue(false);
		} catch (RuleManagerException ex) {
			assertEquals(Error.Code.NO_ACTIVE_RULE_FOUND, ex.getErrorCode());
		}
	}

	@Test
	public void shouldThrowExceptionWithErrorCodeXpathNotDefined() throws Exception {
		try {
			invokeGetTargetSequencesMethod(SAMPLE_PAYLOAD, XPATH_NOT_DEFINED_JSON, RULE_REG_FILE_PATH);
			assertTrue(false);
		} catch (RuleManagerException ex) {
			assertEquals(Error.Code.XPATH_NOT_DEFINED_IN_RULE, ex.getErrorCode());
		}
	}

	@Test
	public void shouldThrowExceptionWithErrorCodeXpathEvalError() throws Exception {
		try {
			invokeGetTargetSequencesMethod(SAMPLE_PAYLOAD, INVALID_XPATH_JSON, RULE_REG_FILE_PATH);
			assertTrue(false);
		} catch (RuleManagerException ex) {
			assertEquals(Error.Code.XPATH_EVAL_ERROR, ex.getErrorCode());
		}
	}

	@Test
	public void shouldThrowExceptionWithErrorCodeCasesNotDefined() throws Exception {
		try {
			invokeGetTargetSequencesMethod(SAMPLE_PAYLOAD, CONDITIONS_NOT_DEFINED_JSON, RULE_REG_FILE_PATH);
			assertTrue(false);
		} catch (RuleManagerException ex) {
			assertEquals(Error.Code.CONDITIONS_NOT_PROVIDED_IN_RULE, ex.getErrorCode());
		}
	}

	@Test
	public void shouldThrowExceptionWithErrorCodeMatchingConditionNotFound() throws Exception {
		try {
			invokeGetTargetSequencesMethod(SAMPLE_PAYLOAD, UNMATCHING_CONDITION_JSON, RULE_REG_FILE_PATH);
			assertTrue(false);
		} catch (RuleManagerException ex) {
			assertEquals(Error.Code.MATCHING_CONDITION_NOT_FOUND, ex.getErrorCode());
		}
	}

	@Test
	public void shouldThrowExceptionWithErrorCodeSequencesNotDefined() throws Exception {
		try {
			invokeGetTargetSequencesMethod(SAMPLE_PAYLOAD, SEQUENCES_NOT_DEFINED_JSON, RULE_REG_FILE_PATH);
			assertTrue(false);
		} catch (RuleManagerException ex) {
			assertEquals(Error.Code.SEQUENCES_NOT_FOUND_IN_MATCHING_CASE, ex.getErrorCode());
		}
	}

	private Set<String> invokeGetTargetSequencesMethod(String payloadFilePath, String ruleJsonFilePath,
			String registryPath) throws Exception {
		MessageContext messageContext = TestUtil.buildTestMessageContext(payloadFilePath, registryPath);
		String fileContent = TestUtil.readFileFromResources(ruleJsonFilePath);
		// create mock
		MessageContext context = spy(messageContext);
		SynapseConfiguration synapseConfiguration = mock(SynapseConfiguration.class);
		WSO2Registry wso2Registry = mock(WSO2Registry.class);
		Resource resource = mock(Resource.class);
		// define return value for methods
		when(context.getConfiguration()).thenReturn(synapseConfiguration);
		when(synapseConfiguration.getRegistry()).thenReturn(wso2Registry);
		when(wso2Registry.getResource(anyString())).thenReturn(resource);
		when(resource.getContent()).thenReturn(fileContent.getBytes());
		RuleManager ruleManager = new RuleManager();
		return ruleManager.getTargetSequences(context);
	}
}