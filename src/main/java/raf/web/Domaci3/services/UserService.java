package raf.web.Domaci3.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.repositories.IUserRepository;
import raf.web.Domaci3.response_request.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final IUserRepository repository;

    @Autowired
    public UserService(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> myUserOpt = this.repository.findByEmail(email);
        if(!myUserOpt.isPresent()) {
            throw new UsernameNotFoundException("Email "+email+" not found");
        }
        User myUser = myUserOpt.get();
        //Hibernate.initialize(myUser.getPermissionsList());

        return new org.springframework.security.core.userdetails.User(myUser.getEmail(), myUser.getPassword(), myUser.getPermissionsList());

    }

    public User getUserByEmail(String email){
        Optional<User> myUserOpt = this.repository.findByEmail(email);
        if(!myUserOpt.isPresent()) {
            throw new UsernameNotFoundException("Email "+email+" not found");
        }
        return myUserOpt.get();
    }


}
