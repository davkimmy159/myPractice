package kr.co.ps119.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.co.ps119.config.handler.CustomAuthenticationFailureHandler;
import kr.co.ps119.config.handler.CustomAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		String usersByUsernameQuery =
				"SELECT username, password, enabled " +
				"FROM member " +
				"WHERE username = ?";
		
		String authoritiesByUsernameQuery =
				"SELECT username, authority " +
				"FROM authority auth " +
				"INNER JOIN member_authority mem_auth " +
				"ON auth.authority_id = mem_auth.authority_id " +
					"INNER JOIN member mem " +
					"ON mem_auth.member_id = mem.member_id " +
				"WHERE username = ?";
		
		auth.jdbcAuthentication()
			.passwordEncoder(passwordEncoder())
			.dataSource(dataSource)
			.usersByUsernameQuery(usersByUsernameQuery)
			.authoritiesByUsernameQuery(authoritiesByUsernameQuery);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// h2 db console setting
		http.csrf().ignoringAntMatchers("/h2/**/");
		http.headers().frameOptions().disable();
		
		http.authorizeRequests()
			.antMatchers("/login/login").permitAll()	//anonymous()
			.antMatchers("/new_account/registration_input_form").anonymous()
			.antMatchers("/index").permitAll()
			.antMatchers("/member/**").hasAnyAuthority("role_member", "role_admin")
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.usernameParameter("loginId")
			.passwordParameter("loginPassword")
			.loginPage("/login/login")
			.loginProcessingUrl("/login/validate_process")
			.defaultSuccessUrl("/member/member_main")
			.failureUrl("/login/login?fail=true")
//			.successHandler(customAuthenticationSuccessHandler())
			.failureHandler(customAuthenticationFailureHandler())
			.and()
			.logout()
			.logoutUrl("/login/logout_process")
			.logoutSuccessUrl("/index")
			.invalidateHttpSession(true);
			/*
			.and()
			.requiresChannel()
			.antMatchers("/new_account/**").requiresSecure();
			*/
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
	
	@Bean
	public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
		CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler = new CustomAuthenticationSuccessHandler();
		customAuthenticationSuccessHandler.setTargetUrlParameter("loginRedirect");
		customAuthenticationSuccessHandler.setUseReferer(false);
		customAuthenticationSuccessHandler.setDefaultUrl("member/member_main");
		return customAuthenticationSuccessHandler;
	};
	
	@Bean
	public CustomAuthenticationFailureHandler customAuthenticationFailureHandler() {
		 CustomAuthenticationFailureHandler customAuthenticationFailureHandler = new CustomAuthenticationFailureHandler();
		 customAuthenticationFailureHandler.setLoginIdParamName("loginId");
		 customAuthenticationFailureHandler.setLoginPasswordParamName("loginPassword");
		 customAuthenticationFailureHandler.setLoginRedirectParamName("loginRedirect");
		 customAuthenticationFailureHandler.setSecurityExceptionMsgParamName("securityExceptionMsg");
		 customAuthenticationFailureHandler.setDefaultFailureUrlParamName("defaultFailureUrl");
		 customAuthenticationFailureHandler.setDefaultFailureUrl("/login/login");
		 return customAuthenticationFailureHandler;
	}
}