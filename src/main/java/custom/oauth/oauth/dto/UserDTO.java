package custom.oauth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String username;
    private String email;
    @JsonProperty(value = "list_role")
    private List<RoleDTO> roleDTOS;
}
