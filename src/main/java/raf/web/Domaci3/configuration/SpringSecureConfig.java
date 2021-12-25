package raf.web.Domaci3.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import raf.web.Domaci3.Paths;
import raf.web.Domaci3.model.PermissionsEnum;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.repositories.IUserRepository;
import raf.web.Domaci3.security.JwtFilter;

@EnableWebSecurity
public class SpringSecureConfig extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder encoder;
    private IUserRepository userRepository;
    private JwtFilter jwtFilter;

    @Autowired
    public SpringSecureConfig(BCryptPasswordEncoder encoder,IUserRepository userRepository,JwtFilter jwtFilter) {
        super();
        this.encoder = encoder;
        this.userRepository = userRepository;
        if(!userRepository.existsByEmail("root@gmail.com")) {
            User root = new User("Root", "Roots", "root@gmail.com", this.encoder.encode("root"));
            root.addPermission(PermissionsEnum.CAN_CREATE_USERS);
            root.addPermission(PermissionsEnum.CAN_DELETE_USERS);
            root.addPermission(PermissionsEnum.CAN_READ_USERS);
            root.addPermission(PermissionsEnum.CAN_UPDATE_USERS);
            User user1 = new User("Milan","Bojic","mbojic12@raf.rs","milan");
            userRepository.save(user1);
            userRepository.saveAndFlush(root);
        }
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.cors().and().csrf().disable().authorizeRequests().antMatchers(Paths.LOGIN_PATH+"/**").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
