package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class BusinessTransaction {
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
