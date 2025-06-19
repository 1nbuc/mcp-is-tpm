package de.contriboot.mcptpm.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class B2BScenarioEntity {

    // somehow lombok is not working for the parentId when trying to get within the client
    public String getParentId() {
        return parentId;
    }

    @JsonProperty("IsVanScenario")
    private boolean isVanScenario;

    @JsonProperty("FunctionalAck")
    private FunctionalAck functionalAck = new FunctionalAck();

    @JsonProperty("BusinessTransactions")
    private List<BusinessTransaction> businessTransactions = new ArrayList<>();

    @JsonProperty("DocumentSchemaVersion")
    private String documentSchemaVersion;

    @JsonProperty("administrativeData")
    private AdministrativeData administrativeData = new AdministrativeData();

    @JsonProperty("ParentId")
    private String parentId;

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

    @Getter
    @Setter
    @ToString
    public static class FunctionalAck {
        // Empty as per JSON: {}
    }

    @Getter
    @Setter
    @ToString
    public static class BusinessTransaction {
        @JsonProperty("Id")
        private String id;

        @JsonProperty("PatternReference")
        private PatternReference patternReference = new PatternReference();

        @JsonProperty("Sequence")
        private int sequence;

        @JsonProperty("TransactionProperties")
        private TransactionProperties transactionProperties = new TransactionProperties();

        @JsonProperty("BusinessTransactionActivities")
        private List<BusinessTransactionActivity> businessTransactionActivities = new ArrayList<>();
    }

    @Getter
    @Setter
    @ToString
    public static class PatternReference {
        @JsonProperty("Id")
        private String id;

        @JsonProperty("Version")
        private String version;

        @JsonProperty("Name")
        private String name;
    }

    @Getter
    @Setter
    @ToString
    public static class TransactionProperties {
        @JsonProperty("Properties")
        private TransactionInnerProperties properties = new TransactionInnerProperties();
    }

    @Getter
    @Setter
    @ToString
    public static class TransactionInnerProperties {
        @JsonProperty("Initiator")
        private String initiator;

        @JsonProperty("Label_ReactorName")
        private String labelReactorName;

        @JsonProperty("CommunicationPartnerId")
        private String communicationPartnerId;

        @JsonProperty("Reactor")
        private String reactor;

        @JsonProperty("Name")
        private String name;

        @JsonProperty("Label_InitiatorName")
        private String labelInitiatorName;
    }

    @Getter
    @Setter
    @ToString
    public static class BusinessTransactionActivity {
        @JsonProperty("Id")
        private String id;

        @JsonProperty("BusinessTransactionActivityReference")
        private BusinessTransactionActivityReference businessTransactionActivityReference = new BusinessTransactionActivityReference();

        @JsonProperty("Sequence")
        private int sequence;

        @JsonProperty("BusinessTransactionActivityProperties")
        private BusinessTransactionActivityProperties businessTransactionActivityProperties = new BusinessTransactionActivityProperties();

        @JsonProperty("ActivityParameters")
        private List<ActivityParameter> activityParameters = new ArrayList<>();

        @JsonProperty("ChoreographyProperties")
        private ChoreographyProperties choreographyProperties = new ChoreographyProperties();

        @JsonProperty("ExtractionRuleset")
        private ExtractionRuleset extractionRuleset = new ExtractionRuleset();
    }

    @Getter
    @Setter
    @ToString
    public static class ExtractionRuleset {
        @JsonProperty("type")
        private String type;

        @JsonProperty("sourceId")
        private String sourceId;

        @JsonProperty("rules")
        private List<Rule> rules = new ArrayList<>();
    }

    @Getter
    @Setter
    @ToString
    public static class Rule {
        @JsonProperty("id")
        private String id;

        @JsonProperty("value")
        private String value;
    }

    @Getter
    @Setter
    @ToString
    public static class BusinessTransactionActivityReference {
        @JsonProperty("Id")
        private String id;

        @JsonProperty("Version")
        private String version;

        @JsonProperty("Name")
        private String name;
    }

    @Getter
    @Setter
    @ToString
    public static class BusinessTransactionActivityProperties {
        @JsonProperty("Properties")
        private ActivityProperties properties = new ActivityProperties();
    }

    @Getter
    @Setter
    @ToString
    public static class ActivityProperties {
        // Empty as per JSON: {}
    }

    @Getter
    @Setter
    @ToString
    public static class ActivityParameter {
        @JsonProperty("ParameterSource")
        private ParameterSource parameterSource = new ParameterSource();

        @JsonProperty("Direction")
        private String direction;

        @JsonProperty("Parameters")
        private Map<String, String> parameters;
    }

    @Getter
    @Setter
    @ToString
    public static class ParameterSource {
        @JsonProperty("SourceId")
        private String sourceId;

        @JsonProperty("SourceName")
        private String sourceName;

        @JsonProperty("ArtifactType")
        private String artifactType;
    }

    @Getter
    @Setter
    @ToString
    public static class ChoreographyProperties {
        @JsonProperty("Properties")
        private ChoreographyInnerProperties properties = new ChoreographyInnerProperties();
    }

    @Getter
    @Setter
    @ToString
    public static class ChoreographyInnerProperties {
        @JsonProperty("SENDER_SYSTEM")
        private ChoreographySystemProperties senderSystem = new ChoreographySystemProperties();

        @JsonProperty("SENDER_ADAPTER")
        private ChoreographySenderAdapterProperties senderAdapter = new ChoreographySenderAdapterProperties();

        @JsonProperty("SENDER_INTERCHANGE")
        private ChoreographySenderInterchangeProperties senderInterchange = new ChoreographySenderInterchangeProperties();

        @JsonProperty("MAPPING")
        private ChoreographyMappingProperties mapping = new ChoreographyMappingProperties();

        @JsonProperty("RECEIVER_INTERCHANGE")
        private ChoreographyReceiverInterchangeProperties receiverInterchange = new ChoreographyReceiverInterchangeProperties();

        @JsonProperty("RECEIVER_ADAPTER")
        private ChoreographyReceiverAdapterProperties receiverAdapter = new ChoreographyReceiverAdapterProperties();

        @JsonProperty("RECEIVER_SYSTEM")
        private ChoreographySystemProperties receiverSystem = new ChoreographySystemProperties();
    }

    @Getter
    @Setter
    @ToString
    public static class ChoreographySystemProperties {
        @JsonProperty("Properties")
        private SystemDetails properties = new SystemDetails();
    }

    @Getter
    @Setter
    @ToString
    public static class SystemDetails {
        @JsonProperty("TypeSystem")
        private String typeSystem;
        @JsonProperty("SystemInstanceId")
        private String systemInstanceId;
        @JsonProperty("IdAsSender")
        private String idAsSender;
        @JsonProperty("IdAsReceiverAlias")
        private String idAsReceiverAlias;
        @JsonProperty("IdAsSenderAlias")
        private String idAsSenderAlias;
        @JsonProperty("SystemInstanceAlias")
        private String systemInstanceAlias;
        @JsonProperty("IdAsReceiver")
        private String idAsReceiver;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("TypeSystemVersion")
        private String typeSystemVersion;
        @JsonProperty("Label_Name")
        private String labelName;
        @JsonProperty("Label_IdAsSenderName")
        private String labelIdAsSenderName;
        @JsonProperty("Label_SenderSchemeCode")
        private String labelSenderSchemeCode;
        @JsonProperty("Label_SenderSchemeName")
        private String labelSenderSchemeName;
        @JsonProperty("Label_SenderTypeSystemId")
        private String labelSenderTypeSystemId;
        @JsonProperty("Label_SenderTypeSystemName")
        private String labelSenderTypeSystemName;
        @JsonProperty("Label_IdAsReceiverName")
        private String labelIdAsReceiverName;
        @JsonProperty("Label_ReceiverSchemeCode")
        private String labelReceiverSchemeCode;
        @JsonProperty("Label_ReceiverSchemeName")
        private String labelReceiverSchemeName;
        @JsonProperty("Label_ReceiverTypeSystemId")
        private String labelReceiverTypeSystemId;
        @JsonProperty("Label_ReceiverTypeSystemName")
        private String labelReceiverTypeSystemName;
        @JsonProperty("Label_SystemInstanceName")
        private String labelSystemInstanceName;
        @JsonProperty("Label_Purpose")
        private String labelPurpose;
    }

    @Getter
    @Setter
    @ToString
    public static class ChoreographySenderAdapterProperties {
        @JsonProperty("Properties")
        private SenderAdapterDetails properties = new SenderAdapterDetails();
    }

    @Getter
    @Setter
    @ToString
    public static class SenderAdapterDetails {
        @JsonProperty("CCTForSenderFunctionalACKAlias")
        private String cctForSenderFunctionalACKAlias;
        @JsonProperty("CommunicationChannelTemplateId")
        private String communicationChannelTemplateId;
        @JsonProperty("CommunicationChannelTemplateAlias")
        private String communicationChannelTemplateAlias;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("Label_CommunicationChannelTemplateName")
        private String labelCommunicationChannelTemplateName;
        @JsonProperty("Label_CCTForSenderFunctionalACKName")
        private String labelCCTForSenderFunctionalACKName;
    }

    @Getter
    @Setter
    @ToString
    public static class ChoreographyReceiverAdapterProperties {
        @JsonProperty("Properties")
        private ReceiverAdapterDetails properties = new ReceiverAdapterDetails();
    }

    @Getter
    @Setter
    @ToString
    public static class ReceiverAdapterDetails {
        @JsonProperty("CommunicationChannelTemplateId")
        private String communicationChannelTemplateId;
        @JsonProperty("CommunicationChannelTemplateAlias")
        private String communicationChannelTemplateAlias;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("Label_CommunicationChannelTemplateName")
        private String labelCommunicationChannelTemplateName;
        @JsonProperty("Label_ACKCommunicationChannelTemplateName")
        private String labelACKCommunicationChannelTemplateName;
    }

    @Getter
    @Setter
    @ToString
    public static class ChoreographySenderInterchangeProperties {
        @JsonProperty("Properties")
        private SenderInterchangeDetails properties = new SenderInterchangeDetails();
    }

    @Getter
    @Setter
    @ToString
    public static class SenderInterchangeDetails {
        @JsonProperty("Validation")
        private String validation;
        @JsonProperty("ImportCorrelationObjectId")
        private String importCorrelationObjectId;
        @JsonProperty("TypeSystem")
        private String typeSystem;
        @JsonProperty("Label_MIGIdName")
        private String labelMIGIdName;
        @JsonProperty("ContactPersonId")
        private String contactPersonId;
        @JsonProperty("ObjectGUID")
        private String objectGUID;
        @JsonProperty("HasEnvelope")
        private String hasEnvelope;
        @JsonProperty("MIGGUID")
        private String migguid;
        @JsonProperty("Label_MIGStatus")
        private String labelMIGStatus;
        @JsonProperty("CreateAcknowledgement")
        private String createAcknowledgement;
        @JsonProperty("FunctionalAck")
        private String functionalAck;
        @JsonProperty("ImportCorrelationGroupId")
        private String importCorrelationGroupId;
        @JsonProperty("MIGLink")
        private String migLink;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("MessageType")
        private String messageType;
        @JsonProperty("TypeSystemVersion")
        private String typeSystemVersion;
        @JsonProperty("MIGVersionId")
        private String migVersionId;
        @JsonProperty("Label_TypeSystemName")
        private String labelTypeSystemName;
        @JsonProperty("custom_properties")
        private String customProperties;
    }

    @Getter
    @Setter
    @ToString
    public static class ChoreographyReceiverInterchangeProperties {
        @JsonProperty("Properties")
        private ReceiverInterchangeDetails properties = new ReceiverInterchangeDetails();
    }

    @Getter
    @Setter
    @ToString
    public static class ReceiverInterchangeDetails {
        @JsonProperty("Validation")
        private String validation;
        @JsonProperty("ImportCorrelationObjectId")
        private String importCorrelationObjectId;
        @JsonProperty("TypeSystem")
        private String typeSystem;
        @JsonProperty("Label_MIGIdName")
        private String labelMIGIdName;
        @JsonProperty("ContactPersonId")
        private String contactPersonId;
        @JsonProperty("ObjectGUID")
        private String objectGUID;
        @JsonProperty("HasEnvelope")
        private String hasEnvelope;
        @JsonProperty("MIGGUID")
        private String migguid;
        @JsonProperty("NumberRange")
        private String numberRange;
        @JsonProperty("Label_MIGStatus")
        private String labelMIGStatus;
        @JsonProperty("FunctionalAck")
        private String functionalAck;
        @JsonProperty("ImportCorrelationGroupId")
        private String importCorrelationGroupId;
        @JsonProperty("MIGLink")
        private String migLink;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("MessageType")
        private String messageType;
        @JsonProperty("TypeSystemVersion")
        private String typeSystemVersion;
        @JsonProperty("MIGVersionId")
        private String migVersionId;
        @JsonProperty("RACKRequired")
        private String rackRequired;
        @JsonProperty("Label_TypeSystemName")
        private String labelTypeSystemName;
        @JsonProperty("custom_properties")
        private String customProperties;
    }

    @Getter
    @Setter
    @ToString
    public static class ChoreographyMappingProperties {
        @JsonProperty("Properties")
        private MappingDetails properties = new MappingDetails();
    }

    @Getter
    @Setter
    @ToString
    public static class MappingDetails {
        @JsonProperty("MAGVersionId")
        private String magVersionId;
        @JsonProperty("ImportCorrelationObjectId")
        private String importCorrelationObjectId;
        @JsonProperty("Label_MAGName")
        private String labelMAGName;
        @JsonProperty("MAGGUID")
        private String magguid;
        @JsonProperty("ImportCorrelationGroupId")
        private String importCorrelationGroupId;
        @JsonProperty("MAGLink")
        private String magLink;
        @JsonProperty("ObjectGUID")
        private String objectGUID;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("Label_MAGStatus")
        private String labelMAGStatus;
    }

    @Getter
    @Setter
    @ToString
    public static class AdministrativeData {
        @JsonProperty("createdAt")
        private long createdAt;

        @JsonProperty("createdBy")
        private String createdBy;

        @JsonProperty("modifiedAt")
        private long modifiedAt;

        @JsonProperty("modifiedBy")
        private String modifiedBy;
    }

    @Getter
    @Setter
    @ToString
    public static class SearchableAttributes {
        @JsonProperty("CUSTOM_SEARCH_ATTRIBUTE")
        private List<String> customSearchAttribute = new ArrayList<>();

        @JsonProperty("TENANT")
        private List<String> tenant = new ArrayList<>();
    }

    @Getter
    @Setter
    @ToString
    public static class ArtifactProperties {
        @JsonProperty("TransactionsNumber")
        private String transactionsNumber;
    }

    @Getter
    @Setter
    @ToString
    public static class Relation {
        // Empty as per JSON: []
    }

    @Getter
    @Setter
    @ToString
    public static class ArtifactRelations {
        // Empty as per JSON: {}
    }
}
