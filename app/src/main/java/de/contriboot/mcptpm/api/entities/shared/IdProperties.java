package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class IdProperties {
    @JsonProperty("Id")
    private String id;

    @JsonProperty("Label_Name")
    private String labelName;

    @JsonProperty("Label_SchemeCode")
    private String labelSchemeCode;

    @JsonProperty("Label_SchemeName")
    private String labelSchemeName;

    @JsonProperty("Label_TypeSystemId")
    private String labelTypeSystemId;

    @JsonProperty("Label_TypeSystemName")
    private String labelTypeSystemName;

    @JsonProperty("Label_IsGroup")
    private String labelIsGroup;

    @JsonProperty("Label_Purpose")
    private String labelPurpose;

}
