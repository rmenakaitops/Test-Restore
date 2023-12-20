package com.mitra.middleware.testsupportRouter.util;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.wso2.carbon.registry.resource.stub.ResourceAdminServiceExceptionException;
import org.wso2.carbon.registry.resource.stub.ResourceAdminServiceStub;

public class RegistryAdminServiceClient {

	private static final String SERVICE_NAME = "/ResourceAdminService";
	private ResourceAdminServiceStub resourceAdminServiceStub;

	public RegistryAdminServiceClient(String backEndUrl, String sessionCookie) throws AxisFault {
		resourceAdminServiceStub = new ResourceAdminServiceStub(backEndUrl + SERVICE_NAME);
		ServiceClient serviceClient = resourceAdminServiceStub._getServiceClient();
		Options option = serviceClient.getOptions();
		option.setManageSession(true);
		option.setProperty(HTTPConstants.COOKIE_STRING, sessionCookie);
	}

	public void updateContent(String gregPath, String content)
			throws RemoteException, ResourceAdminServiceExceptionException {
		resourceAdminServiceStub.updateTextContent(gregPath, content);
	}

}