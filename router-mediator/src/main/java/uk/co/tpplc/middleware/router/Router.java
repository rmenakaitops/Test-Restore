package com.mitra.middleware.router;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.axis2.AxisFault;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.Mediator;
import org.apache.synapse.MessageContext;
import org.apache.synapse.util.MessageHelper;

import com.mitra.middleware.router.constant.Error;
import com.mitra.middleware.router.exception.RouterException;

public class Router {

	private static final Log log = LogFactory.getLog(Router.class);

	public Set<String> route(MessageContext ctx, Set<String> sequences) throws RouterException {
		if (log.isDebugEnabled()) {
			log.debug("Routing message to target sequences");
		}
		if (CollectionUtils.isEmpty(sequences)) {
			handleError(Error.Message.TARGET_SEQUENCES_NOT_PROVIDED, Error.Code.TARGET_SEQUENCES_NOT_PROVIDED);
		}
		Map<String, Mediator> mediatorMap = getSequenceMediators(ctx, sequences);
		Set<String> successSequences = mediatorMap.keySet();
		Set<String> failedSequences = new HashSet<String>(sequences);
		failedSequences.removeAll(successSequences);
		for (String sequenceName : successSequences) {
			if (log.isDebugEnabled()) {
				log.debug("Routing message to target sequence : " + sequenceName);
			}
			try {
				route(ctx, mediatorMap.get(sequenceName));
			} catch (RouterException e) {
				// Mark the sequence as failed
				log.error("Error while routing to sequence : " + sequenceName, e);
				failedSequences.add(sequenceName);
			}
		}
		handleFailedSequences(sequences, failedSequences);
		return failedSequences;
	}

	private void handleFailedSequences(Set<String> sequences, Set<String> failedSequences) throws RouterException {
		if (sequences.size() == failedSequences.size()) {
			StringBuilder routeFailureMessageBuilder = new StringBuilder();
			routeFailureMessageBuilder.append("Routing failed, couldn't route to target sequences " + sequences);
			handleError(routeFailureMessageBuilder.toString(), Error.Code.ROUTE_FAILURE);
		}
	}

	private void route(MessageContext ctx, Mediator sequenceMediator) throws RouterException {
		try {
			MessageContext clonedMessageContext = MessageHelper.cloneMessageContext(ctx);
			sequenceMediator.mediate(clonedMessageContext);
		} catch (AxisFault axisFault) {
			handleError(Error.Message.MESSAGE_CONTEXT_CLONE_ERROR, Error.Code.MESSAGE_CONTEXT_CLONE_ERROR, axisFault);
		}
	}

	private void handleError(String errorMessage, String errorCode) throws RouterException {
		String errorMsg = errorCode + " : " + errorMessage;
		if (log.isDebugEnabled()) {
			log.debug("Handling error : " + errorMessage);
		}
		throw new RouterException(errorMsg, errorCode);
	}

	private void handleError(String errorMessage, String errorCode, Exception e) throws RouterException {
		String errorMsg = errorCode + " : " + errorMessage;
		if (log.isDebugEnabled()) {
			log.debug("Handling error : " + errorMessage);
		}
		throw new RouterException(errorMsg, errorCode, e);
	}

	private Map<String, Mediator> getSequenceMediators(MessageContext ctx, Set<String> sequences) {
		if (log.isDebugEnabled()) {
			log.debug("Getting sequence mediators for given sequence names");
		}
		Map<String, Mediator> mediatorMap = new HashMap<String, Mediator>();
		for (String sequenceName : sequences) {
			Mediator mediator = ctx.getSequence(sequenceName);
			if (mediator != null) {
				if (log.isDebugEnabled()) {
					log.debug("Sequence mediator found for the sequence : " + sequenceName);
				}
				mediatorMap.put(sequenceName, mediator);
			}
		}
		return mediatorMap;
	}
}