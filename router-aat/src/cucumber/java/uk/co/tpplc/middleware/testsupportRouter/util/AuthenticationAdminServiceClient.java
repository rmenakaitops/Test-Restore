package com.mitra.middleware.testsupportRouter.util;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.wso2.carbon.authenticator.stub.AuthenticationAdminStub;
import org.wso2.carbon.authenticator.stub.LoginAuthenticationExceptionException;
import org.wso2.carbon.authenticator.stub.LogoutAuthenticationExceptionException;
import org.apache.axis2.context.ServiceContext;
import java.rmi.RemoteException;

public class AuthenticationAdminServiceClient {

	private static final String SERVICE_NAME = "/AuthenticationAdmin";
	private AuthenticationAdminStub authenticationAdminStub;

	public AuthenticationAdminServiceClient(String backEndUrl) throws AxisFault {
		authenticationAdminStub = new AuthenticationAdminStub(backEndUrl + SERVICE_NAME);
	}

	public String authenticate(String userName, String password, String host)
			throws RemoteException, LoginAuthenticationExceptionException {
		String sessionCookie = null;
		if (authenticationAdminStub.login(userName, password, host)) {
			ServiceContext serviceContext = authenticationAdminStub._getServiceClient().getLastOperationContext()
					.getServiceContext();
			sessionCookie = (String) serviceContext.getProperty(HTTPConstants.COOKIE_STRING);
		}

		return sessionCookie;
	}

	public void logOut() throws RemoteException, LogoutAuthenticationExceptionException {
		authenticationAdminStub.logout();
	}
}