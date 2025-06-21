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
public class BusinessTransactionActivity {
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
}
