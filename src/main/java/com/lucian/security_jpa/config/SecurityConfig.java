package com.lucian.security_jpa.config;

import com.lucian.security_jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserService userService;

    @Autowired
    MyFilter myFilter;

    @Autowired
    MyAccessDecisionManager myAccessDecisionManager;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    JdbcTokenRepositoryImpl jdbcTokenRepository() throws SQLException {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 如果persistent_login表不存在则新建这张表
        Connection con = dataSource.getConnection();
        ResultSet result = con.getMetaData().getTables(null, null, "persistent_logins", null);
        if (!result.next()){
            jdbcTokenRepository.setCreateTableOnStartup(true);
        }
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(myAccessDecisionManager);
                        o.setSecurityMetadataSource(myFilter);
                        return o;
                    }
                })
                .and()
                .formLogin()
                .permitAll()
                .and()
                .rememberMe()
                .tokenRepository(jdbcTokenRepository())
                .key("lucian")
                .and()
                .csrf()
                .disable();
    }
}
