package com.pkpm.httpclientutil;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubJobs {

	private Entities entities;
	
	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class Entities {

		private String desktop_id;

	}

}

