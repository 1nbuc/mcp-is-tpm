package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CompanyData {
    @JsonProperty("Id")
    private String id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Role")
    private String role;

    @JsonProperty("SystemInstance")
    private SystemInstance systemInstance = new SystemInstance();

    @JsonProperty("TypeSystem")
    private TypeSystem typeSystem = new TypeSystem();

    @JsonProperty("TypeSystemVersion")
    private String typeSystemVersion;

    @JsonProperty("IdAsSender")
    private IdReference idAsSender = new IdReference();

    @JsonProperty("IdAsReceiver")
    private IdReference idAsReceiver = new IdReference();

    @JsonProperty("ContactPerson")
    private ContactPerson contactPerson = new ContactPerson();
}
