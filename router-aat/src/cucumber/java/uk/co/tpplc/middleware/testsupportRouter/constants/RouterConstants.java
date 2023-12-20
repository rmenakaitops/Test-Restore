package com.mitra.middleware.testsupportRouter.constants;

public final class RouterConstants {
	public static final int API_STATUS_CODE_200 = 200;
	public static final int API_STATUS_CODE_202 = 202;
	public static final String CLOSE = "close";
	public static final String CONNECTION = "Connection";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String ERROR = "error";
	public static final String ERROR_REASON = "error-reason";
	public static final String ON_ERROR_SEQUENCE = "On-Error-Sequence";
	public static final String OK = "OK";
	public static final String API_URL_PROPERTY_NAME = "routerApiURL";
	public static final String RULE_FILE_PATH = "Router-Deff-Path";
	public static final String SWITCH_CASE_COMMON_DEFAULT_MSG = "Invalid argument: ";
	public static final String SUCCESS = "SUCCESS";
	public static final String SUCCESS_REASON = "Payload has been successfully routed";
	public static final String UNHANDLED_ERROR = "error-unhandledError";
	public static final String UNHANDLED_ERROR_PREFIX = "router_";
	public static final String RESOURCE_DIR = "src/cucumber/resources/";
	public static final String RULE_RESOURCE_PATH = "router/rules/";
	public static final String RULE_VALID_RESOURCE_PATH = RULE_RESOURCE_PATH + "valid/";
	public static final String RULE_INVALID_RESOURCE_PATH = RULE_RESOURCE_PATH + "invalid/";
	public static final String CONDITIONS_RESOURCE_PATH = "router/conditions/";
	public static final String CONDITIONS_FOR_INVENTORY_PATH = CONDITIONS_RESOURCE_PATH + "inventory/";
	public static final String CONDITIONS_FOR_NAMESPACE_PATH = CONDITIONS_RESOURCE_PATH + "namespace/";
	public static final String VALID_CONDITIONS_FOR_INVENTORY_PATH = CONDITIONS_FOR_INVENTORY_PATH + "valid/";
	public static final String VALID_CONDITIONS_ENDPOINT_INVENTORY_PATH = CONDITIONS_FOR_INVENTORY_PATH + "endpoint/valid/";
	public static final String INVALID_CONDITIONS_PATH = CONDITIONS_RESOURCE_PATH + "invalid/";
	public static final String VALID_CONDITIONS_FOR_NAMESPACE_PATH = CONDITIONS_FOR_NAMESPACE_PATH + "valid/";
	public static final String GREG_PATH = "/_system/governance/";
	public static final String RULE_GREG_PATH = "router-rules/router-v1-sample-rule-definition.json";
	public static final String RULE_KEY_ACTIVE = "active";
	public static final String RULE_KEY_XPATH = "xpathRule";
	public static final String RULE_KEY_CONDITIONS = "conditions";
	public static final String PROPERTY_FILE_PATH = "router/properties/";
	public static final String PROPERTY_FILE_NAME_INVENTORY = "inventory.properties";
	public static final String PROPERTY_FILE_NAME_NAMESPACE = "namespace.properties";
	public static final String PROPERTY_FILE_NAME_COMMON = "common.properties";
	public static final String INVENTORY_PROPERTY_FILE_PATH = RESOURCE_DIR + PROPERTY_FILE_PATH
			+ PROPERTY_FILE_NAME_INVENTORY;
	public static final String COMMON_PROPERTY_FILE_PATH = RESOURCE_DIR + PROPERTY_FILE_PATH
			+ PROPERTY_FILE_NAME_COMMON;
	public static final String NAMESPACE_PROPERTY_FILE_PATH = RESOURCE_DIR + PROPERTY_FILE_PATH
			+ PROPERTY_FILE_NAME_NAMESPACE;
	public static final String STATUS_VALID = "valid";
	public static final String STATUS_INVALID = "invalid";
	public static final String LOCAL_RULE_FILE_TYPE_DYNAMIC = "dynamic";
	public static final String USE_CASE_TYPE_ENDPOINT = "endpoint";
	public static final String API_RESOURCE_ENDPOINT = "/external-endpoint";
}
