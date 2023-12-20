package com.mitra.middleware.router;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.mitra.middleware.router.constant.Error;
import com.mitra.middleware.router.exception.RouterException;
import com.mitra.middleware.router.exception.RuleManagerException;
import com.mitra.middleware.router.testutil.TestUtil;

@RunWith(MockitoJUnitRunner.class)
public class RouterMediatorTest {

	private static final String PAYLOAD_FILE = "sample-payload.xml";
	private static final String ROUTER_ERROR_CODE = "routerErrorCode";
	private static final String ROUTER_ERROR_MESSAGE = "routerErrorMessage";
	private static final String ROUTER_UNHANDLE_ERROR = "routerUnhandledError";
	private MessageContext context;
	@Mock
	private RuleManager ruleManager;
	@Mock
	private Router router;
	@InjectMocks
	private RouterMediator routerMediator = new RouterMediator();

	@Before
	public void before() throws Exception {
		context = TestUtil.buildTestMessageContext(PAYLOAD_FILE, null);
	}

	@Test
	public void shouldThrowSynapseExceptionWhenRuleManagerFails() throws Exception {
		RuleManagerException ruleManagerException = new RuleManagerException(Error.Message.RULE_FILE_PATH_IS_NULL,
				Error.Code.RULE_FILE_PATH_IS_NULL);
		Mockito.when(ruleManager.getTargetSequences(context)).thenThrow(ruleManagerException);

		try {
			routerMediator.mediate(context);
			assertTrue(false);
		} catch (SynapseException e) {
			String errorMsg = "Error while getting target sequences";
			assertEquals(errorMsg, e.getMessage());
			assertEquals(Error.Code.RULE_FILE_PATH_IS_NULL, (String) context.getProperty(ROUTER_ERROR_CODE));
			assertEquals(Error.Message.RULE_FILE_PATH_IS_NULL, (String) context.getProperty(ROUTER_ERROR_MESSAGE));
			assertEquals(false, (Boolean) context.getProperty(ROUTER_UNHANDLE_ERROR));
		}
	}

	@Test
	public void shouldThrowSynapseExceptionWhenRouterFails() throws Exception {
		RouterException routerException = new RouterException(Error.Message.TARGET_SEQUENCES_NOT_PROVIDED,
				Error.Code.TARGET_SEQUENCES_NOT_PROVIDED);
		Set<String> sequences = new HashSet<String>();
		Mockito.when(ruleManager.getTargetSequences(context)).thenReturn(sequences);
		Mockito.doThrow(routerException).when(router).route(context, sequences);

		try {
			routerMediator.mediate(context);
			assertTrue(false);
		} catch (SynapseException e) {
			String errorMsg = "Error while routing to target sequences";
			assertEquals(errorMsg, e.getMessage());
			assertEquals(Error.Code.TARGET_SEQUENCES_NOT_PROVIDED, (String) context.getProperty(ROUTER_ERROR_CODE));
			assertEquals(Error.Message.TARGET_SEQUENCES_NOT_PROVIDED,
					(String) context.getProperty(ROUTER_ERROR_MESSAGE));
			assertEquals(false, (Boolean) context.getProperty(ROUTER_UNHANDLE_ERROR));
		}
	}

	@Test
	public void shouldNotTrowSynapseExceptionWhenRoutingSuccess() throws Exception {
		Set<String> sequences = new HashSet<String>();
		Mockito.when(ruleManager.getTargetSequences(context)).thenReturn(sequences);
		Mockito.doReturn(sequences).when(router).route(context, sequences);
		assertTrue(true);
	}
}