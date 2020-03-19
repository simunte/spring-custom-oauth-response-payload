package custom.oauth.oauth.configuration;


import custom.oauth.oauth.domain.User;
import custom.oauth.oauth.dto.RoleDTO;
import custom.oauth.oauth.dto.UserDTO;
import custom.oauth.oauth.properties.SecurityConfigProperties;
import custom.oauth.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(SecurityConfigProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    private final SecurityConfigProperties securityConfigProperties;
    private final UserDetailsService userDetailsService;
    private final ClientDetailsService clientDetailsService;
    private final UserRepository userRepository;

    public WebSecurityConfig(SecurityConfigProperties securityConfigProperties, UserDetailsService userDetailsService, ClientDetailsService clientDetailsService, UserRepository userRepository) {
        this.securityConfigProperties = securityConfigProperties;
        this.userDetailsService = userDetailsService;
        this.clientDetailsService = clientDetailsService;
        this.userRepository = userRepository;
    }

    @Bean
    BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }

    @Bean
    JwtAccessTokenConverter accessTokenConverter() {
//        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        final JwtAccessTokenConverter converter = new CustomTokenEnhancer();
        converter.setKeyPair(new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"),
                securityConfigProperties.getKeyStorePassword().toCharArray()).getKeyPair("jwt"));

        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices
                = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        return tokenServices;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .httpBasic()
                .realmName(securityConfigProperties.getSecurityRealm())
            .and()
                .anonymous().disable()
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .parentAuthenticationManager(authenticationManagerBean())
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

//    CUSTOM TOKEN
    @Bean
    public OAuth2RequestFactory requestFactory() {
        CustomOauth2RequestFactory requestFactory = new CustomOauth2RequestFactory(clientDetailsService);
        requestFactory.setCheckUserScopes(true);

        return requestFactory;
    }

    @Bean
    public TokenEndpointAuthenticationFilter tokenEndpointAuthenticationFilter() {
        return new TokenEndpointAuthenticationFilter(authenticationManager, requestFactory());
    }

    class CustomTokenEnhancer extends JwtAccessTokenConverter {
        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            Optional<User> user = userRepository.findTop1ByUsername(authentication.getName());
            Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.get().getUsername());
            userDTO.setEmail(user.get().getEmail());
            userDTO.setRoleDTOS(
                    user.get().getRoles().stream()
                            .map(role -> {
                                RoleDTO roleDTO = new RoleDTO();
                                roleDTO.setRoleId(role.getId());
                                roleDTO.setRoleName(role.getName());
                                roleDTO.setRoleDescription(role.getDescription());
                                return roleDTO;
                            }).collect(Collectors.toCollection(LinkedList::new))
            );
            info.put("custom_payload", userDTO);
            accessToken = super.enhance(accessToken, authentication);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
            return accessToken;
        }
    }

    class CustomOauth2RequestFactory extends DefaultOAuth2RequestFactory {
        @Autowired
        private TokenStore tokenStore;

        public CustomOauth2RequestFactory(ClientDetailsService clientDetailsService) {
            super(clientDetailsService);
        }

        @Override
        public TokenRequest createTokenRequest(Map<String, String> requestParameters,
                                               ClientDetails authenticatedClient) {
            if (requestParameters.get("grant_type").equals("refresh_token")) {
                OAuth2Authentication authentication = tokenStore.readAuthenticationForRefreshToken(
                        tokenStore.readRefreshToken(requestParameters.get("refresh_token")));
                SecurityContextHolder.getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(authentication.getName(), null,
                                userDetailsService.loadUserByUsername(authentication.getName()).getAuthorities()));
            }
            return super.createTokenRequest(requestParameters, authenticatedClient);
        }
    }
}
