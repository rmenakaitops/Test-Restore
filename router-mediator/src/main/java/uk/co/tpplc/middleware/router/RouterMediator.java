package com.mitra.middleware.router;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseException;
import org.apache.synapse.mediators.AbstractMediator;

import com.mitra.middleware.router.exception.RouterException;
import com.mitra.middleware.router.exception.RouterMediatorException;
import com.mitra.middleware.router.exception.RuleManagerException;

public class RouterMediator extends AbstractMediator {
	private static final String ROUTER_LOG_MESSAGE = "routerLogMessage";
	private static final String ROUTER_ERROR_CODE = "routerErrorCode";
	private static final String ROUTER_ERROR_MESSAGE = "routerErrorMessage";
	private static final String ROUTER_UNHANDLE_ERROR = "routerUnhandledError";
	private static final Log LOG = LogFactory.getLog(RouterMediator.class);
	private RuleManager ruleManager = new RuleManager();
	private Router router = new Router();

	public boolean mediate(MessageContext context) {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Invoking mediate method with payload : " + context.getEnvelope().toString());
			}
			Set<String> targetSequences = ruleManager.getTargetSequences(context);
			Set<String> failedSequences = router.route(context, targetSequences);
			setRouterLogMessageToContext(context, targetSequences, failedSequences);
		} catch (RuleManagerException e) {
			setErrorDetailToContext(context, e);
			String msg = "Error while getting target sequences";
			LOG.error(msg, e);
			throw new SynapseException(msg, e);
		} catch (RouterException e) {
			setErrorDetailToContext(context, e);
			String msg = "Error while routing to target sequences";
			LOG.error(msg, e);
			throw new SynapseException(msg, e);
		}
		return true;
	}

	private void setRouterLogMessageToContext(MessageContext context, Set<String> targetSequences,
			Set<String> failedSequences) {
		Set<String> successSequences = new HashSet<String>(targetSequences);
		successSequences.removeAll(failedSequences);

		String routerLogMessage = "Routing completed with success sequences " + successSequences
				+ " and failed sequences " + failedSequences;
		if (LOG.isDebugEnabled()) {
			LOG.debug(routerLogMessage);
		}
		context.setProperty(ROUTER_LOG_MESSAGE, routerLogMessage);
	}

	private void setErrorDetailToContext(MessageContext context, RouterMediatorException e) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Setting error details to message context. Error code :" + e.getErrorCode()
					+ " , Error message : '" + e.getMessage());
		}
		String errorCode = e.getErrorCode();
		String errorMsg = e.getMessage();
		context.setProperty(ROUTER_ERROR_CODE, errorCode);
		context.setProperty(ROUTER_ERROR_MESSAGE, errorMsg);
		context.setProperty(ROUTER_UNHANDLE_ERROR, false);
	}
}