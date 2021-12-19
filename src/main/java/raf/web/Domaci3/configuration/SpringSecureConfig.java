package raf.web.Domaci3.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import raf.web.Domaci3.Paths;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.repositories.IUserRepository;
import raf.web.Domaci3.security.JwtFilter;

public class SpringSecureConfig extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder encoder;
    private IUserRepository userRepository;

    public SpringSecureConfig(BCryptPasswordEncoder encoder, IUserRepository userRepository) {
        super();
        this.encoder = encoder;
        User root = new User("Root","Roots","root@gmail.com",encoder.encode("root"));
        this.userRepository = userRepository;
        userRepository.saveAndFlush(root);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.cors().and().csrf().disable().authorizeRequests().antMatchers(Paths.LOGIN_PATH).permitAll()
                .anyRequest().authenticated()
                .and().addFilter(new JwtFilter())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
