package com.knowhow.shield.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private final TokenStore tokenStore;
    private final String USER_PATTERN = "/user/**";

    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/users/activate/**").permitAll().antMatchers("/users/register")
                .permitAll().antMatchers(HttpMethod.OPTIONS, USER_PATTERN).permitAll()
                .antMatchers(HttpMethod.GET, USER_PATTERN).access("#oauth2.hasScope('shield')")
                .antMatchers(HttpMethod.POST, USER_PATTERN).access("#oauth2.hasScope('shield')")
                .antMatchers(HttpMethod.PATCH, USER_PATTERN).access("#oauth2.hasScope('shield')")
                .antMatchers(HttpMethod.PUT, USER_PATTERN).access("#oauth2.hasScope('shield')")
                .antMatchers(HttpMethod.DELETE, USER_PATTERN).access("#oauth2.hasScope('shield')").anyRequest()
                .authenticated().and().csrf().disable();
    }
}
