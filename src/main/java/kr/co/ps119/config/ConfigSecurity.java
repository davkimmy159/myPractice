package kr.co.ps119.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

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
				"INNER JOIN member_authority mem_auth" +
				"ON auth.athority_id = mem_auth.authority_id " +
					"INNER JOIN member mem" +
					"ON mem_auth.member_id = mem.member_id" +
				"WHERE username = ?";
		
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery(usersByUsernameQuery )
			.authoritiesByUsernameQuery(authoritiesByUsernameQuery)
			.passwordEncoder(new StandardPasswordEncoder("1l4faqd32"));
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// h2 db console setting
		http.csrf().ignoringAntMatchers("/h2/**/");
		http.headers().frameOptions().disable();
		
		/*
		http.authorizeRequests().antMatchers("/test.html", "/h2/**", "/index.html", "/login/login", "new_account/registration_input_form").anonymous()
			.antMatchers("member/member_main").hasRole("role_member")
			.anyRequest().authenticated();
		*/
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder("secret");
    }   

}