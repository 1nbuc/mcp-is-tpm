package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TradingPartnerData {
    @JsonProperty("Role")
    private String role;

    @JsonProperty("AliasForSystemInstance")
    private AliasReference aliasForSystemInstance = new AliasReference();

    @JsonProperty("TypeSystem")
    private TypeSystem typeSystem = new TypeSystem();

    @JsonProperty("TypeSystemVersion")
    private String typeSystemVersion;

    @JsonProperty("AliasForIdentifierInOwnTS")
    private AliasReference aliasForIdentifierInOwnTS = new AliasReference();

    @JsonProperty("AliasForIdentifierInCompanyTS")
    private AliasReference aliasForIdentifierInCompanyTS = new AliasReference();

    @JsonProperty("AliasForContactPersonRole")
    private AliasReference aliasForContactPersonRole = new AliasReference();
}
