package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArtifactMetadata{

	@JsonProperty("SchemaVersion")
	private String schemaVersion;

	@JsonProperty("ArtifactType")
	private String artifactType;
}