package com.mitra.middleware.router.dto;

import java.util.List;
import java.util.Set;

public class RouterRule {

	private boolean active;
	private String xpathRule;
	private List<Conditions> conditions;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getXpathRule() {
		return xpathRule;
	}

	public void setXpathRule(String xpathRule) {
		this.xpathRule = xpathRule;
	}

	public List<Conditions> getConditions() {
		return conditions;
	}

	public void setConditions(List<Conditions> conditions) {
		this.conditions = conditions;
	}

	public class Conditions {
		
		String condition;
		Set<String> sequences;

		public String getCondition() {
			return condition;
		}

		public void setCondition(String condition) {
			this.condition = condition;
		}

		public Set<String> getSequences() {
			return sequences;
		}

		public void setSequences(Set<String> sequences) {
			this.sequences = sequences;
		}

	}
}