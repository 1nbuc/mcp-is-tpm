package de.contriboot.mcptpm.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.contriboot.mcptpm.api.entities.shared.AdministrativeData;
import de.contriboot.mcptpm.api.entities.shared.ArtifactProperties;
import de.contriboot.mcptpm.api.entities.shared.ArtifactRelations;
import de.contriboot.mcptpm.api.entities.shared.CompanyData;
import de.contriboot.mcptpm.api.entities.shared.CommunicationPartnerData;
import de.contriboot.mcptpm.api.entities.shared.Relation;
import de.contriboot.mcptpm.api.entities.shared.SearchableAttributes;
import de.contriboot.mcptpm.api.entities.shared.TradingPartnerData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class AgreementTemplateEntity {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Version")
    private String version;

    @JsonProperty("OwnedBy")
    private String ownedBy;

    @JsonProperty("OwnerId")
    private String ownerId;

    @JsonProperty("Shared")
    private boolean shared;

    @JsonProperty("TransactionsNumber")
    private int transactionsNumber;

    @JsonProperty("CompanyData")
    private CompanyData companyData = new CompanyData();

    @JsonProperty("TradingPartnerData")
    private TradingPartnerData tradingPartnerData = new TradingPartnerData();

    @JsonProperty("CommunicationPartnerData")
    private CommunicationPartnerData communicationPartnerData = new CommunicationPartnerData();

    @JsonProperty("B2BScenarioDetailsId")
    private String b2bScenarioDetailsId;

    @JsonProperty("DocumentSchemaVersion")
    private String documentSchemaVersion;

    @JsonProperty("administrativeData")
    private AdministrativeData administrativeData = new AdministrativeData();

    @JsonProperty("searchableAttributes")
    private SearchableAttributes searchableAttributes = new SearchableAttributes();

    @JsonProperty("artifactProperties")
    private ArtifactProperties artifactProperties = new ArtifactProperties();

    @JsonProperty("relations")
    private List<Relation> relations = new ArrayList<>();

    @JsonProperty("ArtifactRelations")
    private ArtifactRelations artifactRelations = new ArtifactRelations();

    @JsonProperty("artifactStatus")
    private String artifactStatus;

    @JsonProperty("uniqueId")
    private String uniqueId;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("semanticVersion")
    private String semanticVersion;

    @JsonProperty("id")
    private String id;

    @JsonProperty("artifactType")
    private String artifactType;

}
