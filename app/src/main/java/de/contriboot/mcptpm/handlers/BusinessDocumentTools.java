package de.contriboot.mcptpm.handlers;

import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.b2bscenario.BusinessDocumentsClient;
import com.figaf.integration.tpm.entity.ErrorDetails;
import com.figaf.integration.tpm.entity.Interchange;
import com.figaf.integration.tpm.entity.InterchangePayloadData;
import com.figaf.integration.tpm.entity.InterchangeRequest;
import de.contriboot.mcptpm.utils.Config;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class BusinessDocumentTools {
    private BusinessDocumentsClient client;

    public BusinessDocumentTools() {
        this.client = new BusinessDocumentsClient(new HttpClientsFactory());
    }

    private Date parseIso8601Date(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        // ISO 8601 format, e.g., "YYYY-MM-DDTHH:mm:ss.sssZ"
        // Adjust format as needed based on expected input precision
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format for string: " + dateStr + ". Expected ISO 8601 (yyyy-MM-dd'T'HH:mm:ss.SSS'Z').", e);
        }
    }

    @Tool(name = "search-interchanges", description = "Search for interchanges based on filter criteria.")
    public List<Interchange> searchInterchanges(
            @ToolParam(description = "Left bound date for search (ISO 8601 format, e.g., YYYY-MM-DDTHH:mm:ss.sssZ)") String leftBoundDateStr,
            @ToolParam(description = "Right bound date for search (ISO 8601 format, e.g., YYYY-MM-DDTHH:mm:ss.sssZ)", required = false) String rightBoundDateStr,
            @ToolParam(description = "List of overall statuses to filter by (e.g., COMPLETED, FAILED)", required = false) List<String> overallStatuses,
            @ToolParam(description = "List of processing statuses to filter by (e.g., AWAITING_PROCESSING, PROCESSED)", required = false) List<String> processingStatuses,
            @ToolParam(description = "Agreed sender identifier at sender side", required = false) String agreedSenderIdentiferAtSenderSide,
            @ToolParam(description = "Agreed sender identifier qualifier at sender side", required = false) String agreedSenderIdentiferQualifierAtSenderSide,
            @ToolParam(description = "Agreed receiver identifier at sender side", required = false) String agreedReceiverIdentiferAtSenderSide,
            @ToolParam(description = "Agreed receiver identifier qualifier at sender side", required = false) String agreedReceiverIdentiferQualifierAtSenderSide,
            @ToolParam(description = "Agreed sender identifier at receiver side", required = false) String agreedSenderIdentiferAtReceiverSide,
            @ToolParam(description = "Agreed sender identifier qualifier at receiver side", required = false) String agreedSenderIdentiferQualifierAtReceiverSide,
            @ToolParam(description = "Agreed receiver identifier at receiver side", required = false) String agreedReceiverIdentiferAtReceiverSide,
            @ToolParam(description = "Agreed receiver identifier qualifier at receiver side", required = false) String agreedReceiverIdentiferQualifierAtReceiverSide,
            @ToolParam(description = "Sender adapter type", required = false) String senderAdapterType,
            @ToolParam(description = "Sender document standard", required = false) String senderDocumentStandard,
            @ToolParam(description = "Sender message type", required = false) String senderMessageType,
            @ToolParam(description = "Receiver document standard", required = false) String receiverDocumentStandard,
            @ToolParam(description = "Receiver message type", required = false) String receiverMessageType
    ) {
        Date leftBoundDate = parseIso8601Date(leftBoundDateStr);
        if (leftBoundDate == null) {
            throw new IllegalArgumentException("leftBoundDateStr is required and cannot be null or empty.");
        }
        InterchangeRequest interchangeRequest = new InterchangeRequest(leftBoundDate);
        interchangeRequest.setRightBoundDate(parseIso8601Date(rightBoundDateStr));
        if (overallStatuses != null) interchangeRequest.setOverallStatuses(overallStatuses);
        if (processingStatuses != null) interchangeRequest.setProcessingStatuses(processingStatuses);
        if (agreedSenderIdentiferAtSenderSide != null) interchangeRequest.setAgreedSenderIdentiferAtSenderSide(agreedSenderIdentiferAtSenderSide);
        if (agreedSenderIdentiferQualifierAtSenderSide != null) interchangeRequest.setAgreedSenderIdentiferQualifierAtSenderSide(agreedSenderIdentiferQualifierAtSenderSide);
        if (agreedReceiverIdentiferAtSenderSide != null) interchangeRequest.setAgreedReceiverIdentiferAtSenderSide(agreedReceiverIdentiferAtSenderSide);
        if (agreedReceiverIdentiferQualifierAtSenderSide != null) interchangeRequest.setAgreedReceiverIdentiferQualifierAtSenderSide(agreedReceiverIdentiferQualifierAtSenderSide);
        if (agreedSenderIdentiferAtReceiverSide != null) interchangeRequest.setAgreedSenderIdentiferAtReceiverSide(agreedSenderIdentiferAtReceiverSide);
        if (agreedSenderIdentiferQualifierAtReceiverSide != null) interchangeRequest.setAgreedSenderIdentiferQualifierAtReceiverSide(agreedSenderIdentiferQualifierAtReceiverSide);
        if (agreedReceiverIdentiferAtReceiverSide != null) interchangeRequest.setAgreedReceiverIdentiferAtReceiverSide(agreedReceiverIdentiferAtReceiverSide);
        if (agreedReceiverIdentiferQualifierAtReceiverSide != null) interchangeRequest.setAgreedReceiverIdentiferQualifierAtReceiverSide(agreedReceiverIdentiferQualifierAtReceiverSide);
        if (senderAdapterType != null) interchangeRequest.setSenderAdapterType(senderAdapterType);
        if (senderDocumentStandard != null) interchangeRequest.setSenderDocumentStandard(senderDocumentStandard);
        if (senderMessageType != null) interchangeRequest.setSenderMessageType(senderMessageType);
        if (receiverDocumentStandard != null) interchangeRequest.setReceiverDocumentStandard(receiverDocumentStandard);
        if (receiverMessageType != null) interchangeRequest.setReceiverMessageType(receiverMessageType);

        return client.searchInterchanges(Config.getRequestContextFromEnv(), interchangeRequest);
    }

    @Tool(name = "get-interchange-payloads", description = "Get payload data list for a specific interchange.")
    public List<InterchangePayloadData> getInterchangePayloads(
            @ToolParam(description = "ID of the interchange") String interchangeId
    ) {
        return client.getBusinessDocumentPayloadDataListByInterchangeId(Config.getRequestContextFromEnv(), interchangeId);
    }

    @Tool(name = "get-interchange-last-error", description = "Get last error details for a specific interchange.")
    public ErrorDetails getInterchangeLastError(
            @ToolParam(description = "ID of the interchange") String interchangeId
    ) {
        return client.getLastErrorDetailsByInterchangeId(Config.getRequestContextFromEnv(), interchangeId);
    }

    @Tool(name = "download-interchange-payload", description = "Download a specific payload by its ID. Returns byte array.")
    public String downloadInterchangePayload(
            @ToolParam(description = "ID of the payload") String payloadId
    ) {
        byte[] binPayload = client.downloadPayload(Config.getRequestContextFromEnv(), payloadId);
        if (binPayload == null) {
            return null;
        }
        return new String(binPayload, StandardCharsets.UTF_8);
    }
}
