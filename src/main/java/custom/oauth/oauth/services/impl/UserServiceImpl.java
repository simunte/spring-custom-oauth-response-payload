package custom.oauth.oauth.services.impl;

import custom.oauth.oauth.domain.User;
import custom.oauth.oauth.dto.UserDTO;
import custom.oauth.oauth.repository.UserRepository;
import custom.oauth.oauth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {
    private static final String ERROR_NOT_FOUND = "error.not.found";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findTop1ByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage(ERROR_NOT_FOUND,
                        new String[]{s},
                        LocaleContextHolder.getLocale())));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getAuthorities());
    }

    @Override
    public List<UserDTO> getAllUser() {
        return userRepository.findAll().stream().map(user -> {
                UserDTO userDTO = new UserDTO();
                userDTO.setUsername(user.getUsername());
                userDTO.setEmail(user.getEmail());
                return userDTO;
        }
        ).collect(Collectors.toCollection(LinkedList::new));
    }


}
