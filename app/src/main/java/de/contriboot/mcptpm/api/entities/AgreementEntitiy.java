package de.contriboot.mcptpm.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.contriboot.mcptpm.api.entities.shared.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AgreementEntitiy {

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

    @JsonProperty("TransactionOption")
    private TransactionOption transactionOption = new TransactionOption();

    @JsonProperty("CompanyData")
    private CompanyData companyData = new CompanyData();

    @JsonProperty("TradingPartnerData")
    private TradingPartnerData tradingPartnerData = new TradingPartnerData();

    @JsonProperty("TradingPartnerDetails")
    private TradingPartnerDetails tradingPartnerDetails = new TradingPartnerDetails();

    @JsonProperty("ParentId")
    private String parentId;

    @JsonProperty("SourceTemplateName")
    private String sourceTemplateName;

    @JsonProperty("B2BScenarioDetailsId")
    private String b2bScenarioDetailsId;

    @JsonProperty("CommunicationPartnerData")
    private CommunicationPartnerData communicationPartnerData = new CommunicationPartnerData();

    @JsonProperty("TransactionsNumber")
    private int transactionsNumber;

}
