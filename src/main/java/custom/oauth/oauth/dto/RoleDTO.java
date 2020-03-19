package custom.oauth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;

@Data
public class RoleDTO {
    @JsonProperty(value = "role_id")
    private Long roleId;
    @JsonProperty(value = "role_name")
    private String roleName;
    @JsonProperty(value = "role_description")
    private String roleDescription;
}
