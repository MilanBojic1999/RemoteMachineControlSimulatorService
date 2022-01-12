package raf.web.Domaci3.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import raf.web.Domaci3.Paths;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.PermissionsEnum;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.repositories.IMachineRepository;
import raf.web.Domaci3.repositories.IUserRepository;
import raf.web.Domaci3.security.JWTAuthorization;
import raf.web.Domaci3.security.JwtFilter;
import raf.web.Domaci3.security.MachineActionAuthorization;


@EnableWebSecurity
public class SpringSecureConfig extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder encoder;
    private IUserRepository userRepository;

    private JwtFilter jwtFilter;
    private JWTAuthorization jwtAuthorization;
    private MachineActionAuthorization machineActionAuthorization;

    @Autowired
    public SpringSecureConfig(BCryptPasswordEncoder encoder,IUserRepository userRepository,IMachineRepository machineRepository,JwtFilter jwtFilter,JWTAuthorization jwtAuthorization,MachineActionAuthorization machineActionAuthorization) {
        super();
        this.encoder = encoder;
        this.userRepository = userRepository;
        if(!userRepository.existsByEmail("root@gmail.com")) {
            User root = new User("Root", "Roots", "root@gmail.com", this.encoder.encode("root"));
            for(PermissionsEnum permissionsEnum:PermissionsEnum.values()){
                root.addPermission(permissionsEnum);
            }
            User user1 = new User("Milan","Bojic","mbojic12@raf.rs",this.encoder.encode("milan"));
            user1.addPermission(PermissionsEnum.CAN_READ_USERS);

            Machine machine = new Machine(root,"root1");
            Machine machine2 = new Machine(user1,"user12");
            Machine machine3 = new Machine(root,"script");
            machine3.setActive(false);

            userRepository.save(root);
            userRepository.save(user1);

            machineRepository.save(machine);
            machineRepository.save(machine2);
            machineRepository.save(machine3);
        }
        this.jwtFilter = jwtFilter;
        this.jwtAuthorization = jwtAuthorization;
        this.machineActionAuthorization = machineActionAuthorization;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        System.out.println(Paths.LOGIN_PATH+"*");
        http.cors().and().csrf().disable().authorizeRequests().antMatchers(Paths.USER_PATH+Paths.LOGIN_PATH+"*").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(this.jwtAuthorization,UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(this.machineActionAuthorization,JWTAuthorization.class);

    }



}
