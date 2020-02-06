package com.knowhow.shield.config;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.knowhow.shield.dto.EnhancedUserDetails;
import java.io.IOException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@RequiredArgsConstructor
public class TokenConfiguration {

    private final AppProperties appProperties;
    private final AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() throws IOException {
        AppProperties.Jwt jwt = appProperties.getJwt();
        KeyPair keyPair = new KeyStoreKeyFactory(jwt.getKeyStore(), jwt.getKeyStorePassword().toCharArray())
                .getKeyPair(jwt.getKeyPairAlias(), jwt.getKeyPairPassword().toCharArray());
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair);
        // jwtAccessTokenConverter.enhance(accessToken, authentication);
        jwtAccessTokenConverter.setVerifierKey(
                Files.asCharSource(appProperties.getJwt().getPublicKey().getFile(), Charsets.UTF_8).read());
        return jwtAccessTokenConverter;
    }


    @Bean
    @DependsOn("jwtAccessTokenConverter")
    public TokenStore tokenStore(final JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    public TokenEnhancer tokenEnhancer(){
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInfo = new HashMap<>();
            EnhancedUserDetails user = (EnhancedUserDetails) authentication.getPrincipal();
            additionalInfo.put("partyId", user.getPartyId().get().toString());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

    @Bean
    @DependsOn("tokenStore")
    public DefaultTokenServices tokenServices(final TokenStore tokenStore,
            final ClientDetailsService clientDetailsService) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setAuthenticationManager(this.authenticationManager);
        return tokenServices;
    }
}
