package com.mitra.middleware.router;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.config.xml.SynapsePath;
import org.apache.synapse.registry.Registry;
import org.apache.synapse.util.xpath.SynapseXPath;
import org.jaxen.JaxenException;
import org.wso2.carbon.mediation.registry.WSO2Registry;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.mitra.middleware.router.constant.Error;
import com.mitra.middleware.router.dto.RouterRule;
import com.mitra.middleware.router.exception.RuleManagerException;

public class RuleManager {

	private static final Log LOG = LogFactory.getLog(RuleManager.class);
	private static final String RULE_FILE_PATH = "routerRuleDefinitionPath";
	private Gson gson = new Gson();

	public Set<String> getTargetSequences(MessageContext ctx) throws RuleManagerException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Getting  target sequences");
		}
		RouterRule[] rules = loadRules(ctx);
		RouterRule rule = getActiveRule(rules);
		String conditionValue = applyXpathToPayload(ctx, rule.getXpathRule());
		return getSequences(rule, conditionValue);
	}

	private RouterRule[] loadRules(MessageContext ctx) throws RuleManagerException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Loading rules from router rule file.");
		}
		Object ruleFilePath = ctx.getProperty(RULE_FILE_PATH);
		if (ruleFilePath == null) {
			handleError(Error.Message.RULE_FILE_PATH_IS_NULL, Error.Code.RULE_FILE_PATH_IS_NULL);
		}
		RouterRule[] routerRules = null;
		String ruleJson = readRuleFileFromRegistry(ctx, ruleFilePath.toString());
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Loading rules from router rule json : " + ruleJson);
			}
			routerRules = gson.fromJson(ruleJson, RouterRule[].class);
		} catch (JsonSyntaxException e) {
			handleError(Error.Message.JSON_PARSER_ERROR, Error.Code.JSON_PARSER_ERROR, e);
		}
		return routerRules;
	}

	private String applyXpathToPayload(MessageContext ctx, String xpathRule) throws RuleManagerException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Evaluating xpath against the payload. Xpath : " + xpathRule);
		}
		if (StringUtils.isEmpty(xpathRule)) {
			handleError(Error.Message.XPATH_NOT_DEFINED, Error.Code.XPATH_NOT_DEFINED_IN_RULE);
		}

		String value = null;
		try {
			SynapsePath xpath = new SynapseXPath(xpathRule);
			value = xpath.stringValueOf(ctx);
			if (value == null) {
				handleError(Error.Message.XPATH_EVAL_NO_RESULT, Error.Code.XPATH_EVAL_NO_RESULT);
			}
		} catch (JaxenException e) {
			handleError(Error.Message.XPATH_EVAL_ERROR, Error.Code.XPATH_EVAL_ERROR);
		}
		return value;
	}

	private Set<String> getSequences(RouterRule rule, String condition) throws RuleManagerException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Getting target sequences from active rule for condition : " + condition);
		}
		List<RouterRule.Conditions> conditions = rule.getConditions();
		RouterRule.Conditions matchedCondition = getMatchingCondition(conditions, condition);
		Set<String> sequences = getSequencesOfMatchedCondition(matchedCondition);
		if (LOG.isDebugEnabled()) {
			StringBuilder stringBuilder = new StringBuilder("Found sequences : ");
			for (String sequence : sequences) {
				stringBuilder.append(sequence).append(", ");
			}
			LOG.debug(StringUtils.removeEnd(stringBuilder.toString(), ", "));
		}
		return sequences;
	}

	private RouterRule.Conditions getMatchingCondition(List<RouterRule.Conditions> conditions, String condition)
			throws RuleManagerException {
		if (CollectionUtils.isEmpty(conditions)) {
			handleError(Error.Message.CONDITIONS_NOT_PROVIDED_IN_RULE, Error.Code.CONDITIONS_NOT_PROVIDED_IN_RULE);
		}
		RouterRule.Conditions matchedCondition = null;
		for (RouterRule.Conditions conditionItem : conditions) {
			if (conditionItem.getCondition() != null && conditionItem.getCondition().equals(condition)) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Matching condition found for conditions : " + condition);
				}
				if (matchedCondition != null) {
					handleError(Error.Message.MULTIPLE_ACTIVE_CONDITION_FOUND,
							Error.Code.MULTIPLE_ACTIVE_CONDITION_FOUND);
				}
				matchedCondition = conditionItem;
			}
		}
		if (matchedCondition == null) {
			handleError(Error.Message.MATCHING_CONDITION_NOT_FOUND, Error.Code.MATCHING_CONDITION_NOT_FOUND);
		}
		return matchedCondition;
	}

	private Set<String> getSequencesOfMatchedCondition(RouterRule.Conditions matchedCondition)
			throws RuleManagerException {
		Set<String> sequences = matchedCondition.getSequences();
		if (CollectionUtils.isEmpty(sequences)) {
			handleError(Error.Message.SEQUENCES_NOT_FOUND_IN_MATCHING_CASE,
					Error.Code.SEQUENCES_NOT_FOUND_IN_MATCHING_CASE);
		}
		return sequences;
	}

	private RouterRule getActiveRule(RouterRule[] rules) throws RuleManagerException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Getting active rule");
		}
		if (ArrayUtils.isEmpty(rules)) {
			handleError(Error.Message.NO_RULES_FOUND_IN_RULE_FILE, Error.Code.NO_RULES_FOUND_IN_RULE_FILE);
		}
		RouterRule activeRule = null;
		for (RouterRule rule : rules) {
			if (rule.isActive()) {
				if (activeRule != null) {
					handleError(Error.Message.MULTIPLE_ACTIVE_RULE_FOUND, Error.Code.MULTIPLE_ACTIVE_RULE_FOUND);
				}
				activeRule = rule;
			}
		}
		if (activeRule == null) {
			handleError(Error.Message.NO_ACTIVE_RULE_FOUND, Error.Code.NO_ACTIVE_RULE_FOUND);
		}
		return activeRule;
	}

	private void handleError(String errorMessage, String errorCode) throws RuleManagerException {
		String errorMsg = errorCode + " : " + errorMessage;
		if (LOG.isDebugEnabled()) {
			LOG.debug("Handling error : " + errorMsg);
		}
		throw new RuleManagerException(errorMessage, errorCode);
	}

	private void handleError(String errorMessage, String errorCode, Exception e) throws RuleManagerException {
		String errorMsg = errorCode + " : " + errorMessage;
		if (LOG.isDebugEnabled()) {
			LOG.debug("Handling error : " + errorMsg);
		}
		throw new RuleManagerException(errorMessage, errorCode, e);
	}

	private String readRuleFileFromRegistry(MessageContext ctx, String resourcePath) throws RuleManagerException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Reading resource from registry on registry resource path : " + resourcePath);
		}
		Registry registry = ctx.getConfiguration().getRegistry();
		WSO2Registry wso2Registry = (WSO2Registry) registry;
		Resource resource = wso2Registry.getResource(resourcePath);
		String ruleJson = null;
		if (resource != null) {
			try {
				Object content = resource.getContent();
				ruleJson = new String((byte[]) content);
			} catch (RegistryException e) {
				handleError(Error.Message.ERROR_WHILE_READING_REGISTRY_RESOURCE,
						Error.Code.ERROR_WHILE_READING_REGISTRY_RESOURCE, e);
			}
		} else {
			handleError(Error.Message.FILE_NOT_FOUND_IN_REGISTRY, Error.Code.FILE_NOT_FOUND_IN_REGISTRY);
		}
		return ruleJson;
	}
}