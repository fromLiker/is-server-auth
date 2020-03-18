/**
 * 
 */
package com.dsw.security.server.auth;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
//import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

/**
 * @author Liker
 *
 */
//@EnableJdbcHttpSession(maxInactiveIntervalInSeconds = 3600)
@EnableJdbcHttpSession
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
//    @Autowired
//    private PasswordEncoder passwordEncoder;
	
//  // input body(Client) compare with Memory
//  @Override
//  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//      clients.inMemory()
//              .withClient("orderApp")
//              .secret(passwordEncoder.encode("123456"))
//              .scopes("read", "write")
//              .accessTokenValiditySeconds(3600)
//              .resourceIds("order-server")
//              .authorizedGrantTypes("password")
//              .and()
//              .withClient("orderService")
//              .secret(passwordEncoder.encode("123456"))
//              .scopes("read")
//              .accessTokenValiditySeconds(3600)
//              .resourceIds("order-service")
//              .authorizedGrantTypes("password");
//  }
	

// above memory/below jdbc mysql
	
//  // Tool:  create encrypted password save into auth_client_details table instead of .secret(passwordEncoder.encode("123456"))
//  public static void main(String[] args) {
//  	System.out.println(new BCryptPasswordEncoder().encode("123456"));   	
//  }
	
    // url body compare with DB
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}
	
	// userid and pw
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	// compare if form-data id and password correct or not
        endpoints
        	.userDetailsService(userDetailsService)  // be used by refresh token
        	.tokenStore(tokenStore())
        	.authenticationManager(authenticationManager);
    }
  
    // get token via jdbc
	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
//		return new JwtTokenStore(jwtTokenEnhancer());
	}
	
	// only isAuthenticated can be checkTokenAccess
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//		security.tokenKeyAccess("isAuthenticated()")
//			    .checkTokenAccess("isAuthenticated()");
		security.checkTokenAccess("isAuthenticated()");
	}
	

    

    

    

	
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	
//	@Autowired
//	private DataSource dataSource;
//	
//	@Autowired
//	private UserDetailsService userDetailsService;
//	
//	@Bean
//	public TokenStore tokenStore() {
////		return new JdbcTokenStore(dataSource);
//		return new JwtTokenStore(jwtTokenEnhancer());
//	}
//	
//	@Bean
//	public JwtAccessTokenConverter jwtTokenEnhancer() {
//		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
////		converter.setSigningKey("123456");
//		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jojo.key"), "123456".toCharArray());
//		converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jojo"));
//		return converter;
//	}
//
//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints
//			.userDetailsService(userDetailsService)
//			.tokenStore(tokenStore())
//			.tokenEnhancer(jwtTokenEnhancer())
//			.authenticationManager(authenticationManager);
//	}
//	

}