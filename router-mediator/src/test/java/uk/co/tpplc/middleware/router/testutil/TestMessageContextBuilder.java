package com.mitra.middleware.router.testutil;

import java.io.StringReader;

import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.om.util.StAXUtils;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.engine.AxisConfiguration;
import org.apache.synapse.MessageContext;
import org.apache.synapse.config.SynapseConfiguration;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.core.axis2.Axis2SynapseEnvironment;

/**
 * Utility class to buildMessageContext test message context.
 */
public class TestMessageContextBuilder {

	private String contentString;

	public void setBodyFromString(String contentString) {
		this.contentString = contentString;
	}

	public MessageContext buildMessageContext() throws Exception {
		SynapseConfiguration testConfig = new SynapseConfiguration();
		SynapseEnvironment synEnv = new Axis2SynapseEnvironment(new ConfigurationContext(new AxisConfiguration()),
				testConfig);
		MessageContext synCtx = new Axis2MessageContext(new org.apache.axis2.context.MessageContext(), testConfig,
				synEnv);
		XMLStreamReader parser = StAXUtils.createXMLStreamReader(new StringReader(contentString));
		SOAPEnvelope envelope = OMAbstractFactory.getSOAP11Factory().getDefaultEnvelope();
		SOAPBody body = envelope.getBody();
		StAXOMBuilder builder = new StAXOMBuilder(parser);
		OMElement bodyElement = builder.getDocumentElement();
		body.addChild(bodyElement);
		synCtx.setEnvelope(envelope);
		return synCtx;
	}
}