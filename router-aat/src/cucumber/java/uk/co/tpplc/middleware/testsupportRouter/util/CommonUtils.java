package com.mitra.middleware.testsupportRouter.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import javax.xml.xpath.XPathExpressionException;

import com.mitra.middleware.testsupportRouter.constants.PayloadFilesConstants;
import com.mitra.middleware.testsupportRouter.constants.RouterConstants;
import com.mitra.middleware.testsupportRouter.dto.RouterParameterData;
import com.mitra.middleware.testsupportRouter.dto.RouterResponse;

public class CommonUtils {

	public static void verifyHTTPResponse(RouterParameterData routerData) {
		RouterResponse routerResponse = routerData.getRouterResponse();
		assertThat(routerResponse.getStatusCode()).isEqualTo(RouterConstants.API_STATUS_CODE_200);
		assertThat(routerResponse.getReasonPhrase()).isEqualTo(RouterConstants.OK);
	}

	public static void verifyResponseMessageByType(String responseMessage, String expectedResponse)
			throws XPathExpressionException {
		assertThat(responseMessage.contains(expectedResponse)).isTrue();
		assertThat(responseMessage.contains(RouterConstants.SUCCESS)).isTrue();
	}

	public static void verifyResponseErrorMessage(String responseMessage) throws XPathExpressionException {
		assertThat(responseMessage.contains(RouterConstants.ERROR)).isFalse();
		assertThat(responseMessage.contains(RouterConstants.ERROR_REASON)).isFalse();
		assertThat(responseMessage.contains(RouterConstants.UNHANDLED_ERROR)).isFalse();
	}

	public static void verifyErrorMessage(String responseMessage, String errorCode, String errorMessage,
			String isUnhadle) {
		assertThat(responseMessage.contains(RouterConstants.ERROR)).isTrue();
		assertThat(responseMessage.contains(errorCode)).isTrue();
		assertThat(responseMessage.contains(errorMessage)).isTrue();
		assertThat(responseMessage.contains(RouterConstants.UNHANDLED_ERROR_PREFIX.concat(isUnhadle))).isTrue();
	}

	public static void verifyErrorMessageIsEmpty(String responseMessage, int statusCode) {
		assertThat(statusCode).isEqualTo(RouterConstants.API_STATUS_CODE_202);
		assertThat(responseMessage.isEmpty()).isTrue();
	}

	public static String setPayload(String payLoadType) throws IOException {
		String fileName = null;
		switch (payLoadType) {
		case PayloadFilesConstants.INVENTORY_XML_PAYLOAD_TYPE:
			fileName = PayloadFilesConstants.INVENTORY_XML_PAYLOAD_FILE;
			break;
		case PayloadFilesConstants.XML_PAYLOAD_WITH_NAMESPACE_TYPE:
			fileName = PayloadFilesConstants.XML_PAYLOAD_WITH_NAMESPACE_FILE;
			break;
		default:
			CommonUtils.throwIllegalArgumentException(payLoadType);
		}
		return FileLoader.readResourceContent(fileName);
	}

	public static String getRouterConditionsResourcePath(String useCaseType, String payloadType, String validStatus) {
		String filePath = null;
		switch (payloadType) {
			case PayloadFilesConstants.INVENTORY_XML_PAYLOAD_TYPE:
				if (validStatus.equals(RouterConstants.STATUS_VALID)) {
					if (useCaseType.equals(RouterConstants.USE_CASE_TYPE_ENDPOINT)) {
						filePath = RouterConstants.VALID_CONDITIONS_ENDPOINT_INVENTORY_PATH;
					} else {
						filePath = RouterConstants.VALID_CONDITIONS_FOR_INVENTORY_PATH;
					}
				} else {
					filePath = RouterConstants.INVALID_CONDITIONS_PATH;
				}
				break;
			case PayloadFilesConstants.XML_PAYLOAD_WITH_NAMESPACE_TYPE:
				if (validStatus.equals(RouterConstants.STATUS_VALID)) {
					filePath = RouterConstants.VALID_CONDITIONS_FOR_NAMESPACE_PATH;
				} else {
					filePath = RouterConstants.INVALID_CONDITIONS_PATH;
				}
				break;
		}
		return filePath;
	}

	public static String getLocalRouterRuleResourcePath(String validStatus) {
		String filePath = null;
		switch (validStatus) {
		case RouterConstants.STATUS_VALID:
			filePath = RouterConstants.RULE_VALID_RESOURCE_PATH;
			break;
		case RouterConstants.STATUS_INVALID:
			filePath = RouterConstants.RULE_INVALID_RESOURCE_PATH;
			break;
		}
		return filePath;
	}

	public static void throwIllegalArgumentException(String arg) {
		throw new IllegalArgumentException(RouterConstants.SWITCH_CASE_COMMON_DEFAULT_MSG + arg);
	}

	public static void verifyFailedSequences(String response, String[] failedSeq) {
		String failedSequences = response.substring(response.lastIndexOf("[") + 1, response.lastIndexOf("]"));
		for (String seq : failedSeq) {
			assertThat(failedSequences.contains(seq)).isTrue();
		}
	}
}