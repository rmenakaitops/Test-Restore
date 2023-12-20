package com.mitra.middleware.testsupportRouter.dto;

public class RouterParameterData {

	private String errorSequence;
	private String payload;
	private String payloadType;
	private String ruleFilePath;
	private String useCaseType;
	private RouterResponse routerResponse;
	private RouterRule routerRule;

	public String getErrorSequence() {
		return errorSequence;
	}

	public void setErrorSequence(String errorSequence) {
		this.errorSequence = errorSequence;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getPayloadType() {
		return payloadType;
	}

	public void setPayloadType(String payloadType) {
		this.payloadType = payloadType;
	}

	public String getRuleFilePath() {
		return ruleFilePath;
	}

	public void setRuleFilePath(String ruleFilePath) {
		this.ruleFilePath = ruleFilePath;
	}

	public RouterResponse getRouterResponse() {
		return routerResponse;
	}

	public void setRouterResponse(RouterResponse routerResponse) {
		this.routerResponse = routerResponse;
	}

	public RouterRule getRouterRule() {
		return routerRule;
	}

	public void setRouterRule(RouterRule routerRule) {
		this.routerRule = routerRule;
	}

	public String getUseCaseType() {
		return useCaseType;
	}

	public void setUseCaseType(String useCaseType) {
		this.useCaseType = useCaseType;
	}


}