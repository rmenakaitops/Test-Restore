package com.mitra.middleware.router;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.apache.synapse.Mediator;
import org.apache.synapse.MessageContext;
import org.junit.Before;
import org.junit.Test;

import com.mitra.middleware.router.constant.Error;
import com.mitra.middleware.router.exception.RouterException;
import com.mitra.middleware.router.testutil.TestUtil;

public class RouterTest {
	
	private static final String SAMPLE_PAYLOAD = "sample-payload.xml";
	private static final String RULE_REG_FILE_PATH = "gov:/router/rule.json";
	private static final String SEQUENCE1 = "sequence1";
	private static final String SEQUENCE2 = "sequence2";
	private MessageContext context;
	private Router router;

	@Before
	public void setUp() throws Exception {
		MessageContext messageContext = TestUtil.buildTestMessageContext(SAMPLE_PAYLOAD, RULE_REG_FILE_PATH);
		router = new Router();
		// Mock return values
		context = spy(messageContext);
		Mediator sequenceMediator = mock(Mediator.class);
		when(context.getSequence(anyString())).thenReturn(sequenceMediator);
		when(sequenceMediator.mediate(any(MessageContext.class))).thenReturn(true);
	}

	@Test
	public void shouldRouteToTheSequences() throws Exception {
		HashSet targetSequences = new HashSet<String>();
		targetSequences.add(SEQUENCE1);
		targetSequences.add(SEQUENCE2);
		router.route(context, targetSequences);
		assert (true);
	}

	@Test
	public void shouldThrowRouterExceptionWithErrorCodeTargetSequencesNotFound() throws Exception {
		try {
			router.route(context, null);
			assert (true);
		} catch (RouterException e) {
			assertEquals(Error.Code.TARGET_SEQUENCES_NOT_PROVIDED, e.getErrorCode());
		}
	}
}