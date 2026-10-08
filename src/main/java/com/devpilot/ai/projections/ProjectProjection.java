package com.devpilot.ai.projections;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder
public interface ProjectProjection {
	
	 Long getId();

	 String getName();

	 String getDescription();

	 String getStatus();

}
