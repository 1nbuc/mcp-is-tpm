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
public class TransactionOption {
    // Either Bind or Reference
    @JsonProperty("Option")
    private String option;

    @JsonProperty("TransactionIds")
    private List<String> transactionIds = new ArrayList<>();
}
