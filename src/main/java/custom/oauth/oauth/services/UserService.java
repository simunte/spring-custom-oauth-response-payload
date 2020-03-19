package custom.oauth.oauth.services;


import custom.oauth.oauth.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    UserDetails loadUserByUsername(String s);

    List<UserDTO> getAllUser();
}
