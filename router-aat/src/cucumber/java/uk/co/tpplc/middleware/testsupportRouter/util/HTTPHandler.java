package com.mitra.middleware.testsupportRouter.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.entity.ContentType;

import com.mitra.middleware.testsupportRouter.constants.RouterConstants;
import com.mitra.middleware.testsupportRouter.dto.RouterParameterData;
import com.mitra.middleware.testsupportRouter.dto.RouterResponse;

public class HTTPHandler {

    public static RouterResponse sendRequest(String apiInvocationURL, RouterParameterData routerData)
            throws IOException {
        RouterResponse routerResponse;
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(apiInvocationURL);
        String contentType = ContentType.APPLICATION_XML.toString();
        setHeadersToPostMethod(postMethod, routerData, contentType);
        try {
            RequestEntity requestEntity = new StringRequestEntity(routerData.getPayload(), contentType, null);
            postMethod.setRequestEntity(requestEntity);
            client.executeMethod(postMethod);
            String responseMessage = retrieveResponseMessage(postMethod);
            routerResponse = setRouterResponseValues(postMethod, responseMessage);
        } finally {
            postMethod.releaseConnection();
        }
        return routerResponse;
    }

    private static void setHeadersToPostMethod(PostMethod postMethod, RouterParameterData routerData,
                                               String contentType) {
        postMethod.setRequestHeader(RouterConstants.CONTENT_TYPE, contentType);
        postMethod.setRequestHeader(RouterConstants.RULE_FILE_PATH, routerData.getRuleFilePath());
        postMethod.setRequestHeader(RouterConstants.ON_ERROR_SEQUENCE, routerData.getErrorSequence());
        postMethod.setRequestHeader(RouterConstants.CONNECTION, RouterConstants.CLOSE);
    }

    private static String retrieveResponseMessage(PostMethod postMethod) throws IOException {
        StringBuilder builder = null;
        InputStream responseBody = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        String responseMessage = null;
        try {
            responseBody = postMethod.getResponseBodyAsStream();
            if (responseBody != null) {
                inputStreamReader = new InputStreamReader(responseBody);
                bufferedReader = new BufferedReader(inputStreamReader);
                builder = new StringBuilder();
                while ((responseMessage = bufferedReader.readLine()) != null) {
                    builder.append(responseMessage);
                }
                responseMessage = builder.toString();
            }
        } finally {
            closeConnections(builder, bufferedReader, inputStreamReader, responseBody);
        }
        return responseMessage;
    }

    private static RouterResponse setRouterResponseValues(PostMethod postMethod, String responseMessage) {
        RouterResponse routerResponse = new RouterResponse();
        routerResponse.setStatusCode(postMethod.getStatusCode());
        routerResponse.setReasonPhrase(postMethod.getStatusLine().getReasonPhrase());
        routerResponse.setResponseMessage(responseMessage);
        return routerResponse;
    }

    private static void closeConnections(StringBuilder stringBuilder, BufferedReader bufferedReader,
                                         InputStreamReader inputStreamReader, InputStream inputStream) throws IOException {
        if (bufferedReader != null) {
            bufferedReader.close();
        }

        if (inputStreamReader != null) {
            inputStreamReader.close();
        }

        if (inputStream != null) {
            inputStream.close();
        }

        if (stringBuilder != null) {
            stringBuilder.setLength(0);
        }
    }

}