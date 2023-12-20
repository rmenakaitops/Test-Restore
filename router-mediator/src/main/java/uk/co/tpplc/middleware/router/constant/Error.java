package com.mitra.middleware.router.constant;

public class Error {
	/**
	 * Define error codes Rule related error - ROUTER_(100-200) Routing related
	 * error - ROUTER_(300-400)
	 */

	public class Code {

		public static final String RULE_FILE_PATH_IS_NULL = "1000";
		public static final String FILE_NOT_FOUND_IN_REGISTRY = "1001";
		public static final String CONDITIONS_NOT_PROVIDED_IN_RULE = "1002";
		public static final String MATCHING_CONDITION_NOT_FOUND = "1003";
		public static final String SEQUENCES_NOT_FOUND_IN_MATCHING_CASE = "1004";
		public static final String NO_RULES_FOUND_IN_RULE_FILE = "1005";
		public static final String NO_ACTIVE_RULE_FOUND = "1006";
		public static final String XPATH_NOT_DEFINED_IN_RULE = "1007";
		public static final String MULTIPLE_ACTIVE_RULE_FOUND = "1008";
		public static final String JSON_PARSER_ERROR = "1009";
		public static final String XPATH_EVAL_NO_RESULT = "1011";
		public static final String XPATH_EVAL_ERROR = "1012";
		public static final String TARGET_SEQUENCES_NOT_PROVIDED = "1013";
		public static final String MESSAGE_CONTEXT_CLONE_ERROR = "1014";
		public static final String ROUTE_FAILURE = "1015";
		public static final String ERROR_WHILE_READING_REGISTRY_RESOURCE = "1016";
		public static final String MULTIPLE_ACTIVE_CONDITION_FOUND = "1017";

		private Code() {
		}
	}

	/**
	 * Define error messages
	 */
	public class Message {

		public static final String RULE_FILE_PATH_IS_NULL = "Rule file path is not provided";
		public static final String FILE_NOT_FOUND_IN_REGISTRY = "Could not find rule file in given registry file path";
		public static final String NO_ACTIVE_RULE_FOUND = "No active rule found in routing rules file";
		public static final String NO_RULES_FOUND_IN_RULE_FILE = "No rules found in router rule file";
		public static final String CONDITIONS_NOT_PROVIDED_IN_RULE = "Conditions not provided for active rule";
		public static final String MATCHING_CONDITION_NOT_FOUND = "Matching condition not found in active rule";
		public static final String SEQUENCES_NOT_FOUND_IN_MATCHING_CASE = "Sequences not found for matching condition for active rule";
		public static final String MULTIPLE_ACTIVE_RULE_FOUND = "Multiple active rule found in routing " + "rule file";
		public static final String JSON_PARSER_ERROR = "Error while parsing routing rule file";
		public static final String XPATH_NOT_DEFINED = "XPATH is not defined for active rule";
		public static final String XPATH_EVAL_NO_RESULT = "No result found for given XPATH expression";
		public static final String XPATH_EVAL_ERROR = "Error while evaluating the XPATH";
		public static final String MESSAGE_CONTEXT_CLONE_ERROR = "Error while cloning the message context";
		public static final String TARGET_SEQUENCES_NOT_PROVIDED = "Target sequences are not proved to route";
		public static final String ERROR_WHILE_READING_REGISTRY_RESOURCE = "Error while reading rule json file from "
				+ "registry";
		public static final String MULTIPLE_ACTIVE_CONDITION_FOUND = "Multiple active conditions found in routing "
				+ "rule file";

		private Message() {
		}
	}
}